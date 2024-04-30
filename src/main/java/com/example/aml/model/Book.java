package com.example.aml.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor(onConstructor=@__(@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)))
public class Book {
    private final UUID id;
    @NotBlank private final String work_name;
    @NotBlank private final String primary_author;
    private final int year_published;
    private final int word_count;
 }
