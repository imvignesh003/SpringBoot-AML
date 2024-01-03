package com.example.aml.dao;

import com.example.aml.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookDataAccessService implements BookDao {

    private static List<Book> DB = new ArrayList<>();

    @Override
    public int insertBook(UUID id, Book book) {
        DB.add(new Book(id, book.getName()));
        return 1;
    }
}
