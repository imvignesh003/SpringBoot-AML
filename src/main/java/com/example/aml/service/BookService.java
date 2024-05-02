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

        if (params.containsKey("primary_author")) {
            String authorName = params.get("primary_author");
            if (!authorName.trim().equals("")) {
                bookQueryFilters.add(String.format("strpos(primary_author, '%s') > 0", authorName));
            }
        }
        if (params.containsKey("work_name")) {
            String workName = params.get("work_name");
            if (!workName.trim().equals("")) {
                bookQueryFilters.add(String.format("strpos(work_name, '%s') > 0", workName));
            }
        }

        return bookDao.selectBooks(bookQueryFilters);
    }

    public int deleteBookById(UUID id) {
        return bookDao.deleteBookById(id);
    }

    public int updateBookById(UUID id, Book book) {
        return bookDao.updateBookById(id, book);
    }
}
