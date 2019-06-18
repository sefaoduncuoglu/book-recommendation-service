package com.sefa.challenge.bookrecommendationservice.utils;

/**
 * This abstract factory specifies the behavior concrete factories
 * will need to implement as part of instantiating the BookReader
 *
 * @author Sefa Oduncuoglu
 */
public class ReaderFactory {

    /**
     * Create a concrete factory to go create that reader using the filename
     *
     * @param filename the File Name
     * @return
     */
    public Reader getReader(String type, String filename) {

        if (!type.equalsIgnoreCase("books")) {
            return null;
        }

        ReaderFactory rf = new BookReaderFactory();
        return rf.getReader(filename);
    }

    /**
     * This function is here so that rf.getReader(filename) does not create a compile-time error
     *
     * @param filename the File Name
     * @return
     */
    public Reader getReader(String filename) {
        return null;
    }
}
