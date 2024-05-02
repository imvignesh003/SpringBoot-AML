package com.example.aml.dao;

import com.example.aml.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres") //uses a postgres DB
public class BookDataAccessService implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertBook(UUID id, Book book) {
        var statement = """
                INSERT INTO book(id, work_name, primary_author, year_published, word_count)
                VALUES (?, ?, ?, ?, ?)
                """;
        return jdbcTemplate.update(
                statement,
                id,
                book.getWork_name(),
                book.getPrimary_author(),
                book.getYear_published(),
                book.getWord_count());
    }

    @Override
    public int deleteBookById(UUID id) {
        var statement = """
                DELETE FROM book
                WHERE id = ?
                """;
        return jdbcTemplate.update(
                statement,
                id);
    }

    @Override
    public int updateBookById(UUID id, Book book) {
        var statement = """
                UPDATE book
                SET
                    id = ?,
                    work_name = ?,
                    primary_author = ?,
                    year_published = ?,
                    word_count = ?
                WHERE id = ?
                """;
        return jdbcTemplate.update(
                statement,
                id,
                book.getWork_name(),
                book.getPrimary_author(),
                book.getYear_published(),
                book.getWord_count(),
                id);
    }

    @Override
    public Optional<Book> selectBookById(UUID id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                "SELECT * FROM book WHERE id = ?",
                new Object[]{id},
                (resultSet, i) -> new Book(
                        UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("work_name"),
                        resultSet.getString("primary_author"),
                        resultSet.getInt("year_published"),
                        resultSet.getInt("word_count"))));
    }

    @Override
    public List<Book> selectBooks(ArrayList<String> bookQueryFilters) {
        String queryFilters = getQueryFiltersFromArray(bookQueryFilters);

        return jdbcTemplate.query(
                String.format("""
                        SELECT *
                        FROM book
                        %s;
                        """, queryFilters),
                (resultSet, i) -> new Book(
                        UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("work_name"),
                        resultSet.getString("primary_author"),
                        resultSet.getInt("year_published"),
                        resultSet.getInt("word_count")));
    }

    private static String  getQueryFiltersFromArray(ArrayList<String> bookQueryFilters) {
        StringBuilder bookQueryFiltersAsString = new StringBuilder();

        if (bookQueryFilters.size() > 0) {
            bookQueryFiltersAsString.append("WHERE ");
        }

        for (int i = 0; i < bookQueryFilters.size(); ++i) {
            bookQueryFiltersAsString.append(bookQueryFilters.get(i));
            if (i < bookQueryFilters.size() - 1) {
                bookQueryFiltersAsString.append(" AND ");
            }
        }
        return bookQueryFiltersAsString.toString();
    }
}
