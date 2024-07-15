package com.literAlura.literAlura.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.literAlura.literAlura.model.Author;
import com.literAlura.literAlura.model.Book;
import java.util.List;

// public interface BookRepository {
//     void guardarLibro(Book libro);
//     List<Book> listarTodos();
//     List<Book> listLangBooks(String language);
// }
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByLanguage(String language);
    List<Book> findByAuthorsContaining(Author author);

     @Query("SELECT COUNT(l) FROM Libro l WHERE l.language = :language")
    long countByLanguage(String language);
}