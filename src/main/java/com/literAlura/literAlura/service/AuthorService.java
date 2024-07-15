package com.literAlura.literAlura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.literAlura.literAlura.model.Author;
import com.literAlura.literAlura.repository.AutorRepository;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Author> findAuthorsAliveInYear(int year) {
        return autorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(year, year);
    }
}