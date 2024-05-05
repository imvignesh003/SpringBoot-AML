package com.example.aml.dao;

import com.example.aml.model.AssociatedImage;
import com.example.aml.model.Book;
import com.example.aml.utility.BookConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository("postgres") //uses a postgres DB
public class BookDataAccessService implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertImage(UUID bookId, byte[] imageAsByteArray) {
        UUID pictureId = UUID.randomUUID();
        var pictureStatement = """
                INSERT INTO pictures(id, picture)
                VALUES (?, ?)
                """;
        int pictureIdStatement = jdbcTemplate.update(
                pictureStatement,
                pictureId,
                Base64.getEncoder().encode(imageAsByteArray)
        );
        int bookAddPictureStatement = jdbcTemplate.update(
                """
                UPDATE book
                SET
                    picture_id = ?
                WHERE id = ?
                """,
                pictureId,
                bookId
        );
        return pictureIdStatement + bookAddPictureStatement - 1;
    }

    @Override
    public AssociatedImage getImageForBook(UUID bookId) {
        AssociatedImage image = null;
        try {
            image = jdbcTemplate.queryForObject(
                    String.format("""
                        SELECT p.id, p.picture
                         FROM book b
                         JOIN pictures p
                         ON b.picture_id = p.id
                         WHERE b.id = '%s';
                        """, bookId.toString()),
                    (resultSet, i) -> {
                        return new AssociatedImage(
                                UUID.fromString(resultSet.getString("id")),
                                Base64.getDecoder().decode(resultSet.getBytes("picture"))
                        );
                    }
                );
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(
                    Level.INFO, e.toString());
            image = jdbcTemplate.queryForObject(
                    String.format("""
                        SELECT p.id, p.picture
                         FROM pictures p
                         WHERE p.id = '%s';
                        """, BookConstants.MISSING_IMAGE_ID),
                    (resultSet, i) -> new AssociatedImage(
                            UUID.fromString(resultSet.getString("id")),
                            Base64.getDecoder().decode(resultSet.getBytes("picture")))
            );
        }

        return image;
    }

    @Override
    public int insertBook(UUID id, Book book) {
        var statement = """
                INSERT INTO book(id, work_title, primary_author, year_published, word_count)
                VALUES (?, ?, ?, ?, ?)
                """;
        return jdbcTemplate.update(
                statement,
                id,
                book.getWork_title(),
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
                    work_title = ?,
                    primary_author = ?,
                    year_published = ?,
                    word_count = ?
                WHERE id = ?
                """;
        return jdbcTemplate.update(
                statement,
                id,
                book.getWork_title(),
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
                        resultSet.getString("work_title"),
                        resultSet.getString("primary_author"),
                        resultSet.getInt("year_published"),
                        resultSet.getInt("word_count"),
                        null)));
    }

    @Override
    public Optional<Book> selectBookByNameAndAuthor(String workTitle, String primaryAuthor) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        String.format("""
                        SELECT *
                        FROM book
                        WHERE work_title = '%s' AND primary_author = '%s'
                        """, workTitle, primaryAuthor),
                        (resultSet, i) -> new Book(
                                UUID.fromString(resultSet.getString("id")),
                                resultSet.getString("work_title"),
                                resultSet.getString("primary_author"),
                                resultSet.getInt("year_published"),
                                resultSet.getInt("word_count"),
                                null)));
    }

    @Override
    public List<Book> selectBooks(
            ArrayList<String> bookQueryWhereFilters, ArrayList<String> bookQueryOtherFilters) {
        String queryWhereFilters = getWhereFiltersFromArray(bookQueryWhereFilters);
        String queryOtherFilters = getOtherFiltersFromOther(bookQueryOtherFilters);

        return jdbcTemplate.query(
                String.format("""
                        SELECT *
                        FROM book
                        %s
                        %s;
                        """, queryWhereFilters, queryOtherFilters),
                (resultSet, i) -> new Book(
                        UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("work_title"),
                        resultSet.getString("primary_author"),
                        resultSet.getInt("year_published"),
                        resultSet.getInt("word_count"),
                        null));
    }

    private static String getWhereFiltersFromArray(ArrayList<String> bookQueryFilters) {
        StringBuilder bookQueryFiltersAsString = new StringBuilder();

        if (!bookQueryFilters.isEmpty()) {
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

    private static String getOtherFiltersFromOther(ArrayList<String> bookQueryFilters) {
        StringBuilder bookQueryFiltersAsString = new StringBuilder();
        for (String bookQueryFilter : bookQueryFilters) {
            bookQueryFiltersAsString.append(bookQueryFilter);
            bookQueryFiltersAsString.append('\n');
        }
        return bookQueryFiltersAsString.toString();
    }
}
