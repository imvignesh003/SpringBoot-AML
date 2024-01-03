package com.example.aml.service;

import com.example.aml.dao.BookDao;
import com.example.aml.model.Book;

public class BookService {
    private final BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public int addBook(Book book) {
        return bookDao.insertBook(book);
    }
}
