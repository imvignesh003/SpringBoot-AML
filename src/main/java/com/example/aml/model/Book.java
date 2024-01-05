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

    public Book(@JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("author") String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getAuthor() { return author; }
 }
