package com.literAlura.literAlura.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.literAlura.literAlura.model.Author;
import com.literAlura.literAlura.model.Book;
import com.literAlura.literAlura.repository.AutorRepository;
import com.literAlura.literAlura.repository.BookRepository;
import com.literAlura.literAlura.service.AuthorService;
import com.literAlura.literAlura.service.ConsumoApi;

public class MainPrincipal {
    private BookRepository bookRepository;
    private AutorRepository autorRepository;
    private AuthorService authorService;
    private ConsumoApi consumoApi;

    public MainPrincipal(BookRepository libroRepository, AutorRepository autorRepository, ConsumoApi consumoApi) {
        this.bookRepository = libroRepository;
        this.autorRepository = autorRepository;
        
        this.consumoApi = consumoApi;
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayMenu();
            System.out.println("Selecciones una Opcion Valida: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    buscarLibroPorTitulo(scanner);
                    break;
                case 2:
                    listAllBooks();
                    break;
                case 3:
                    listAuthors();
                    break;
                case 4:
                    listAuthorVivosYear(scanner);
                    break;

                case 5:
                    listBookLanguages(scanner);
                    break;

                case 6:
                    mostrarEstadistica(scanner);

                case 7:
                    System.out.println("****** Saliendo del Programa *****");
                    break;

                default:
                    System.out.println("Ingrese una Opcion Valida");
                    break;
            }
        } while (choice != 7);
    }

    private void mostrarEstadistica(Scanner scanner) {
        System.out.print("Ingrese el idioma para estadísticas (ej. 'en' para inglés, 'es' para español): ");
        String idioma = scanner.nextLine();

        long cantidadLibros = bookRepository.countByLanguage(idioma);
        System.out.println("Cantidad de libros en " + idioma + ": " + cantidadLibros);
    }
    

    public void displayMenu() {
        System.out.println("\n***********************MENU************************");
        System.out.println("1. - Buscar Libro por Titulo");
        System.out.println("2. - Listar Libros Registrados");
        System.out.println("3. - Listar Autores Registrados");
        System.out.println("4. - Listar Libros vivos en un determinado A;o");
        System.out.println("5. - Listar Libro por Idioma");
        System.out.println("6. - Salir");
        throw new UnsupportedOperationException("Unimplemented method 'displayMenu'");
    }

    private void buscarLibroPorTitulo(Scanner scanner) {
        System.out.print("Ingrese el título del libro a buscar: ");
        String titulo = scanner.nextLine();

        try {
            List<Book> libros = consumoApi.searchBooks(titulo);

            if (!libros.isEmpty()) {
                Book libro = libros.get(0);
                Book libroEntity = new Book();
                libroEntity.setTitle(libro.getTitle());

                if (!libro.getLanguages().isEmpty()) {
                    libroEntity.setLanguages(Collections.singletonList(libro.getLanguages().get(0)));
                }

                libroEntity.setDownloadCount(libro.getDownloadCount());

                if (!libro.getAuthors().isEmpty()) {
                    List<Author> authors = new ArrayList<>();

                    for (Author author : libro.getAuthors()) {
                        Author authorEntity = new Author();
                        authorEntity.setName(author.getName());
                        authorEntity.setBirthYear(author.getBirthYear());
                        authorEntity.setDeathYear(author.getDeathYear());

                        autorRepository.save(authorEntity);
                        authors.add(authorEntity);
                    }
                    libroEntity.setAuthors(authors);
                }

                bookRepository.save(libroEntity);

                System.out.println("Libro encontrado y guardado:");
                System.out.println(libroEntity);
            } else {
                System.out.println("No se encontraron libros con el título especificado.");
            }
        } catch (Exception e) {
            System.out.println("Error al buscar libro: " + e.getMessage());
        }
    }

    public void listAllBooks() {
        List<Book> libros = bookRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros buscados todavía.");
        } else {
            System.out.println("Lista de libros buscados:");
            libros.forEach(libro -> System.out.println(libro));
        }
    }

    private void listAuthors() {
        List<Author> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados todavía.");
        } else {
            System.out.println("Lista de autores registrados:");
            autores.forEach(autor -> System.out.println(autor));
        }
    }

    private void listAuthorVivosYear(Scanner scanner) {
        System.out.print("Ingrese el año para listar libros cuyos autores estaban vivos: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de leer el entero

        try {
            List<Author> autoresVivos = authorService.findAuthorsAliveInYear(year);

            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + year);
            } else {
                System.out.println("Autores vivos en el año " + year + ":");
                for (Author autor : autoresVivos) {
                    System.out.println(autor.getName());
                    List<Book> libros = bookRepository.findByAuthorsContaining(autor);
                    System.out.println("Libros escritos por este autor:");
                    for (Book libro : libros) {
                        System.out.println(libro.getTitle());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar autores: " + e.getMessage());
        }
    
    }

    private void listBookLanguages(Scanner scanner) {
        System.out.print("Ingrese el idioma: ");
        String idioma = scanner.nextLine();

        List<Book> libros = bookRepository.findByLanguage(idioma);
        if (libros.isEmpty()) {
            System.out.println("No hay libros en el idioma especificado.");
        } else {
            System.out.println("Lista de libros en " + idioma + ":");
            libros.forEach(libro -> System.out.println(libro));
        }

    }

}
