package com.example.aml.mapper;

import com.example.aml.dto.BookDTO;
import com.example.aml.model.Book;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BookDTOMapper implements Function<Book, BookDTO> {
    @Override
    public BookDTO apply(Book book) {
        return new BookDTO(
                book.getId(),
                book.getWorkTitle(),
                book.getPrimaryAuthor(),
                book.getYearPublished(),
                book.getWordCount(),
                book.getGenres()
        );
    }
}
