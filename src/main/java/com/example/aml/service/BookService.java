package com.example.aml.service;

import com.example.aml.dao.BookDao;
import com.example.aml.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BookService {
    private final BookDao bookDao;

    @Autowired // constructor will run automatically with parameters stored in Spring reference area
    public BookService(@Qualifier("postgres") BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public int addBook(Book book) {
        return bookDao.insertBook(book);
    }

    public Optional<Book> selectBookById(UUID id) {
        return bookDao.selectBookById(id);
    }

    public List<Book> getBooks(Map<String, String> params) {
        ArrayList<String> bookQueryWhereFilters = new ArrayList<>();
        ArrayList<String> bookQueryOtherFilters = new ArrayList<>();
        
        addBookQueryStringFilters(params, bookQueryWhereFilters, "primary_author");
        addBookQueryStringFilters(params, bookQueryWhereFilters, "work_name");

        addBookQueryRangeFilters(params, "word_count", bookQueryWhereFilters, true);
        addBookQueryRangeFilters(params, "word_count", bookQueryWhereFilters, false);
        addBookQueryRangeFilters(params, "year_published", bookQueryWhereFilters, true);
        addBookQueryRangeFilters(params, "year_published", bookQueryWhereFilters, false);

        addSortByCondition(params, bookQueryOtherFilters);

        return bookDao.selectBooks(bookQueryWhereFilters, bookQueryOtherFilters);
    }

    public int deleteBookById(UUID id) {
        return bookDao.deleteBookById(id);
    }

    public int updateBookById(UUID id, Book book) {
        return bookDao.updateBookById(id, book);
    }

    private static void addBookQueryStringFilters(
            Map<String, String> params, ArrayList<String> bookQueryFilters, String columnName) {
        if (params.containsKey(columnName)) {
            String authorName = params.get(columnName);
            if (!authorName.trim().equals("")) {
                bookQueryFilters.add(String.format("strpos(%s, '%s') > 0", columnName, authorName));
            }
        }
    }

    private static void addBookQueryRangeFilters(
            Map<String, String> params, String columnName, ArrayList<String> bookQueryFilters, boolean upper) {
        String keyName = columnName + (upper? "_upper_" : "_lower_") + "limit";
        if (params.containsKey(keyName)) {
            String limitAsString = params.get(keyName);
            if (!limitAsString.trim().equals("")) {
                try {
                    Integer limit = Integer.parseInt(limitAsString);
                    bookQueryFilters.add(
                            String.format("%s %s %d", columnName, (upper? " <= " : " >= "), limit));
                } catch (NumberFormatException err) {
                    Logger.getAnonymousLogger().log(
                            Level.INFO, err.getMessage());
                }
            }
        }
    }

    private static void addSortByCondition(
            Map<String, String> params, ArrayList<String> bookQueryOtherFilters) {
        if (params.containsKey("sort_by")) {
            String columnName = params.get("sort_by");
            if (!columnName.trim().equals("")) {
                String order = Objects.requireNonNullElse(params.get("sorting_order"), ""); // ASC or DESC
                bookQueryOtherFilters.add(
                        String.format("ORDER BY %s %s", columnName, order));
            }
        }
    }
}
