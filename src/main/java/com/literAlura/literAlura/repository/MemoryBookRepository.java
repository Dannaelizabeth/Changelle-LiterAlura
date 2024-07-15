package com.literAlura.literAlura.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.literAlura.literAlura.model.Book;

public class MemoryBookRepository implements BookRepository {
     private List<Book> librosBuscados;

    public MemoryBookRepository() {
        this.librosBuscados = new ArrayList<>();
    }

    @Override
    public void guardarLibro(Book libro) {
        librosBuscados.add(libro);
    }

    @Override
    public List<Book> listarTodos() {
        return new ArrayList<>(librosBuscados);
    }

     @Override
     public List<Book> listLangBooks(String language) {
        return librosBuscados.stream()
                .filter(libro -> !libro.getLanguages().isEmpty() && libro.getLanguages().get(0).equalsIgnoreCase(language))
                .collect(Collectors.toList());
    }
}
