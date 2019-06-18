package com.sefa.challenge.bookrecommendationservice.utils.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private Books books;

    public Controller() {
        logger.info("Reading book data...");
        this.books = new Books("books.txt");
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

}
