package com.example.aml.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class Book {
    private final UUID id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String author;
    private final int yearPublished;
    private final int wordCount;

    public Book(@JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("author") String author,
                @JsonProperty("year_published") int yearPublished,
                @JsonProperty("word_count") int wordCount) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.yearPublished = yearPublished;
        this.wordCount = wordCount;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getAuthor() { return author; }
    public int getYearPublished() { return yearPublished; }
    public int getWordCount() { return wordCount; }
 }
