package com.example.aml.model;

import java.util.UUID;

public class Book {
    private final UUID id;
    private final String name;

    public Book(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
 }
