package com.example.aml.service;

import com.example.aml.dao.BookDao;
import com.example.aml.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

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
        ArrayList<String> bookQueryFilters = new ArrayList<>();
        
        addBookQueryStringFilters(params, bookQueryFilters, "primary_author");
        addBookQueryStringFilters(params, bookQueryFilters, "work_name");

        addBookQueryRangeFilters(params, "word_count", bookQueryFilters, true);
        addBookQueryRangeFilters(params, "word_count", bookQueryFilters, false);
        addBookQueryRangeFilters(params, "year_published", bookQueryFilters, true);
        addBookQueryRangeFilters(params, "year_published", bookQueryFilters, false);

        return bookDao.selectBooks(bookQueryFilters);
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
            String wordCountUpperLimit = params.get(keyName);
            if (!wordCountUpperLimit.trim().equals("")) {
                try {
                    Integer upperLimit = Integer.parseInt(wordCountUpperLimit);
                    bookQueryFilters.add(
                            String.format(columnName + (upper? " <= " : " >= ") + "%d", upperLimit));
                } catch (NumberFormatException ignored) {

                }
            }
        }
    }
}
