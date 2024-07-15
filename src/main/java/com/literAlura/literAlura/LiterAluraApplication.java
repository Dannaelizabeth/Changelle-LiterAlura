package com.literAlura.literAlura;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.literAlura.literAlura.Principal.MainPrincipal;
import com.literAlura.literAlura.repository.AutorRepository;
import com.literAlura.literAlura.repository.BookRepository;
import com.literAlura.literAlura.service.ConsumoApi;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	private BookRepository bookRepository;
    private AutorRepository autorRepository;
    private ConsumoApi consumoApi;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override 
	public void run(String... args) throws Exception{

    MainPrincipal mainPrincipal = new MainPrincipal(bookRepository, autorRepository, consumoApi);
    mainPrincipal.menu(); 
		
	}

}
