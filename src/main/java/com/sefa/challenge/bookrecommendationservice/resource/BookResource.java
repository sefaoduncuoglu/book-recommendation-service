package com.sefa.challenge.bookrecommendationservice.resource;

public class BookResource {

    private long ASIN;

    private String title;

    private String author;

    private String genre;

    public BookResource() {

    }

    public BookResource(long ASIN, String title, String author, String genre) {
        this.ASIN = ASIN;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public long getASIN() {
        return ASIN;
    }

    public void setASIN(long ASIN) {
        this.ASIN = ASIN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
