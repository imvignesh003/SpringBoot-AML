package com.example.aml.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BookCoverService {

    @Value("${book-cover-api.url}")
    private String bookCoverApiUrl;

    public Optional<byte[]> getBookCoverURL(String bookTitle, String authorName) {
        RestTemplate restTemplate = new RestTemplate();
        if (authorName.isEmpty()) {
            authorName = " ";
        }
        String apiUrl = bookCoverApiUrl + "?book_title=" + bookTitle + "&author_name=" + authorName;
        String url = null;
        try {
            String result = restTemplate.getForObject(apiUrl, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(result);
            url = jsonNode.get("url").asText();
        } catch (HttpServerErrorException err) {
            Logger.getAnonymousLogger().log(
                    Level.INFO,
                    String.format("Book Cover not found for '%s' by '%s'", bookTitle, authorName));
        } catch (JsonProcessingException e) {
            Logger.getAnonymousLogger().log(
                    Level.INFO,
                    String.format("Unable to parse JSON. Error: %s", e));
        }
        if (url == null) 
            return Optional.empty();
        return Optional.ofNullable(restTemplate.getForObject(url, byte[].class));
    }
}

