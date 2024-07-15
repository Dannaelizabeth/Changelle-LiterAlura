package com.literAlura.literAlura.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.literAlura.literAlura.model.Author;

public class MemoryAuthorRepository implements AutorRepository {
    private List<Author> authorsBuscasdos;

    public MemoryAuthorRepository(){
        this.authorsBuscasdos = new ArrayList<>();

    }

    @Override 
    public void guardarAutor(Author autor){
        authorsBuscasdos.add(autor);
    }

    @Override 
    public List<Author> listAllAuthors(){
        return new ArrayList<>(authorsBuscasdos);
    }

    @Override
    public List<Author> listVivosEnYear(int year){
        return authorsBuscasdos.stream()
        .filter(autor -> autor.getBirthYear() != null && autor.getBirthYear() <=    year &&
                        (autor.getDeathYear() == null || autor.getDeathYear() > year))
                .collect(Collectors.toList());
    }
}
