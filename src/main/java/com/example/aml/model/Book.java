package com.example.aml.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Book {
    private final UUID id;
    @NotBlank private final String work_title;
    private final String primary_author;
    private final int year_published;
    private final int word_count;
    private final UUID picture;
    @NotBlank private final Date created_at;
    @NotBlank private final Date updated_at;
 }
