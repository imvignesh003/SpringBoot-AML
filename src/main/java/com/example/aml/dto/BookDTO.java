package com.example.aml.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(onConstructor=@__(@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)))
public class BookDTO {
    @NotBlank private final String work_title;
    private final String primary_author;
    private final int year_published;
    private final int word_count;
}
