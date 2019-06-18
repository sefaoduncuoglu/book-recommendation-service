package com.sefa.challenge.bookrecommendationservice.utils;

/**
 * The BookReaderFactory is the concrete factory implementation of the abstract ReaderFactory class
 * This factory is responsible for generating a new Book Reader for the client to interact with
 *
 * @author Sefa Oduncuoglu
 */
public class BookReaderFactory extends ReaderFactory {

    /**
     * overloaded getReader function which will take just a filename and return a book reader
     *
     * @param filename the File Name
     * @return
     */
    public Reader getReader(String filename) {
        return new BookReader(filename);
    }
}
