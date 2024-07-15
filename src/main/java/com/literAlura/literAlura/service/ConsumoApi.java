package com.literAlura.literAlura.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literAlura.literAlura.model.Book;


public class ConsumoApi {

    private static final String API_URL = "https://gutendex.com/books";

    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public ConsumoApi() {
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
    }

    public List<Book> searchBooks(String query) throws Exception {
        String searchUrl = API_URL + "?search=" + query;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(searchUrl))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode resultsNode = rootNode.get("results");

            return objectMapper.readValue(
                    resultsNode.toString(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Book.class)
            );
        } else {
            throw new Exception("Failed to fetch books: " + response.statusCode());
        }
    }
}
