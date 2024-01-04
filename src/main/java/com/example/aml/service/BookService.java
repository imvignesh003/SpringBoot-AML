package com.example.aml.service;

import com.example.aml.dao.BookDao;
import com.example.aml.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookDao bookDao;

    @Autowired // constructor will run automatically with parameters stored in Spring reference area
    public BookService(@Qualifier("TemporaryDAO") BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public int addBook(Book book) {
        return bookDao.insertBook(book);
    }

    public List<Book> getAllBooks() {
        return bookDao.selectAllBooks();
    }
}
