package com.example.aml.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Book {
    private final UUID id;
    @NotBlank private final String workTitle;
    private final String primaryAuthor;
    private final int yearPublished;
    private final int wordCount;
    private final UUID picture;
    @NotBlank private final Date createdAt;
    @NotBlank private final Date updatedAt;
}
