package com.example.aml.api;

import com.example.aml.model.Book;
import com.example.aml.service.BookService;

public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void addBook(Book book) {
        bookService.addBook(book);
    }
}
