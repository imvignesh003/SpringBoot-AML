package com.example.aml.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Book {
    private final UUID id;
    private final String name;

    public Book(@JsonProperty("id") UUID id,
                @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
 }
