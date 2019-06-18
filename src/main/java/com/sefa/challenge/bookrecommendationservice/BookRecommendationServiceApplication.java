package com.sefa.challenge.bookrecommendationservice;

import com.sefa.challenge.bookrecommendationservice.model.Book;
import com.sefa.challenge.bookrecommendationservice.repository.BookRepository;
import com.sefa.challenge.bookrecommendationservice.utils.Controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BookRecommendationServiceApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BookRecommendationServiceApplication.class);
    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookRecommendationServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {

        logger.info("BookRecommendationServiceApplication....");

        Controller controller = new Controller();
        List<Book> bookList = new ArrayList<>();

        controller.getBooks().getBooks().forEach((key, book) -> {
            bookList.add(book);
        });
        bookRepository.saveAll(bookList);

    }
}
