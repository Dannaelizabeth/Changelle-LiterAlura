package com.literAlura.literAlura.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.literAlura.literAlura.model.Author;

// public interface AutorRepository {
//     void guardarAutor(Author author);
//     List<Author> listAllAuthors();
//     List<Author> listVivosEnYear(int year);
// }
@Repository
public interface AutorRepository extends JpaRepository<Author, Long> {
    List<Author> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(int birthYear, int deathYear);
}
