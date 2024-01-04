package com.example.aml.api;

import com.example.aml.model.Book;
import com.example.aml.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This REST API will provide CRUD-like operations on a personal database for works of Art, Media, and Literature
// Currently this project is a very basic storage system (ex. what have I read)?

@RequestMapping("api/v1/book") // The api link
@RestController // This is a RestAPI -- exposes endpoint for client
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping // tells Spring this is a POST request (as opposed to get/put/etc.)
    public void addBook(@RequestBody Book book) {
        // @RequestBody takes the body of the api request and instantiates a Book based off of it
        bookService.addBook(book);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
}
