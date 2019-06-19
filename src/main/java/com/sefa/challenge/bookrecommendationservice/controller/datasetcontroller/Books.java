package com.sefa.challenge.bookrecommendationservice.controller.datasetcontroller;

import com.sefa.challenge.bookrecommendationservice.controller.datareadercontroller.BookReader;
import com.sefa.challenge.bookrecommendationservice.controller.datareadercontroller.ReaderFactory;
import com.sefa.challenge.bookrecommendationservice.model.Book;

import java.util.HashMap;

public class Books {

    private HashMap<Long, Book> theBooks = new HashMap<>();

    /**
     * Constructor
     *
     * @param filename the File Name
     */
    public Books(String filename) {
        createBooks(filename);
    }

    /**
     * Using filename and ReaderFactory, create a collection of books with all necessary data
     *
     * @param filename the File Name
     */
    private void createBooks(String filename) {
        ReaderFactory readerFactory = new ReaderFactory();
        BookReader bookReader = (BookReader) readerFactory.getReader("books", filename);
        theBooks = bookReader.getTheBooks();
    }

    /**
     * @return the theBooks
     */
    public HashMap<Long, Book> getBooks() {
        return theBooks;
    }


}
