package com.example.aml.dao;

import com.example.aml.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("TemporaryDAO") // @Component also works -- this tells the program that this exists
public class BookDataAccessService implements BookDao {

    private static List<Book> bookDB = new ArrayList<>();

    @Override
    public int insertBook(UUID id, Book book) {
        bookDB.add(new Book(id, book.getName()));
        return 1;
    }

    @Override
    public List<Book> selectAllBooks() {
        return bookDB;
    }
}
