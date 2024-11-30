package com.karinadev.literaturalibros;

import com.karinadev.literaturalibros.principal.Principal;
import com.karinadev.literaturalibros.repository.AuthorsRepository;
import com.karinadev.literaturalibros.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturalibrosApplication implements CommandLineRunner {
	@Autowired
	private BooksRepository booksRepository;
	private AuthorsRepository authorsRepository;

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(booksRepository);
		principal.muestraMenu();
	}


	public static void main(String[] args) {
		SpringApplication.run(LiteraturalibrosApplication.class, args);
	}


}
