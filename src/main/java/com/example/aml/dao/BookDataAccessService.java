package com.example.aml.dao;

import com.example.aml.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
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
                INSERT INTO book(id, name, primary_author, year_published, word_count)
                VALUES (?, ?, ?, ?, ?)
                """;
        return jdbcTemplate.update(
                statement,
                id,
                book.getName(),
                book.getAuthor(),
                book.getYear_published(),
                book.getWord_count());
    }

    @Override
    public List<Book> selectAllBooks() {
        return jdbcTemplate.query(
                "SELECT * FROM book",
                (resultSet, i) -> new Book(
                        UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("name"),
                        resultSet.getString("primary_author"),
                        resultSet.getInt("year_published"),
                        resultSet.getInt("word_count")));
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
                    name = ?,
                    primary_author = ?,
                    year_published = ?,
                    word_count = ?
                WHERE id = ?
                """;
        return jdbcTemplate.update(
                statement,
                id,
                book.getName(),
                book.getAuthor(),
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
                        resultSet.getString("name"),
                        resultSet.getString("primary_author"),
                        resultSet.getInt("year_published"),
                        resultSet.getInt("word_count"))));
    }

    @Override
    public List<Book> selectBooksByAuthor(String authorName) {
        return jdbcTemplate.query(
                """
                        SELECT *
                        FROM book
                        WHERE primary_author = ?;
                        """,
                new String[]{authorName},
                new int[] {
                        Types.VARCHAR
                },
                (resultSet, i) -> new Book(
                        UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("name"),
                        resultSet.getString("primary_author"),
                        resultSet.getInt("year_published"),
                        resultSet.getInt("word_count")));
    }
}
