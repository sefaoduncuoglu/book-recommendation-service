package com.sefa.challenge.bookrecommendationservice.controller.datareadercontroller;

import com.sefa.challenge.bookrecommendationservice.model.Book;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Implements abstract class Reader and enables client to parse, store and analyze the Book data
 */
public class BookReader implements Reader {

    private HashMap<Long, Book> theBooks;

    /**
     * Constructor takes in a file name and uses that to initialize a hashmap of books
     *
     * @param filename the File Name
     */
    public BookReader(String filename) {
        theBooks = new HashMap<>();
        readFile(filename);
    }

    /**
     * This returns the hashmap of Books to the client
     *
     * @return the theBooks
     */
    public HashMap<Long, Book> getTheBooks() {
        return theBooks;
    }

    @Override
    /**
     * Using the filename, readFile() parses the books.txt file to create
     * new Books and uploads these into a Hashmap <long, book> theBooks
     */
    public void readFile(String file) {
        try (Scanner n = new Scanner(new File(file), "UTF-8")) {
            //iterate through all lines in file
            while (n.hasNextLine()) {
                String line = n.nextLine();
                //check if line is correctly formatted, and if so, call createObjects with given line
                if (line.length() > 0) {
                    createObjects(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createObjects(String bookInfo) {
        String[] info = bookInfo.split(";");

        if (!info[0].matches("-?(0|[1-9]\\d*)")) {
            return;
        }

        long ASIN = Long.parseLong(info[0]);
        String title = info[1];
        String author = info[2];
        String genre = info[3];

        //Branched behavior for if book is in hashmap
        if (!theBooks.containsKey(ASIN)) {
            Book newBook = new Book(ASIN, title, author, genre);
            theBooks.put(ASIN, newBook);
        }
    }

}
