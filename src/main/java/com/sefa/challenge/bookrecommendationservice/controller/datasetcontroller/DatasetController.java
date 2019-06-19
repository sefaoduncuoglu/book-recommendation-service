package com.sefa.challenge.bookrecommendationservice.controller.datasetcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatasetController {

    private static final Logger logger = LoggerFactory.getLogger(DatasetController.class);
    private Books books;

    public DatasetController() {
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
