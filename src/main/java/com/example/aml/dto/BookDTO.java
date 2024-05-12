package com.example.aml.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor(onConstructor=@__(@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)))
public class BookDTO {
    private final UUID id;
    @NotBlank private final String work_title;
    private final String primary_author;
    private final int year_published;
    private final int word_count;
}
