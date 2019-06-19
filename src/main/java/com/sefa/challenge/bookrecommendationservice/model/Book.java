package com.sefa.challenge.bookrecommendationservice.model;

import org.json.JSONObject;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "books")
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @Column(name = "book_asin", unique = true, nullable = false)
    private long ASIN;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "genre", nullable = false)
    private String genre;

    // Don't create getter and setter methods for this object
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserBookRating> userBookRatings;

    public Book() {
    }

    public Book(long ASIN, String title, String author, String genre) {
        this.ASIN = ASIN;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(long ASIN, String title, String author, String genre, Set<UserBookRating> userBookRatings) {
        this.ASIN = ASIN;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.userBookRatings = userBookRatings;
    }

    /**
     * Gets book ASIN.
     *
     * @return the ASIN
     */
    public long getASIN() {
        return ASIN;
    }

    /**
     * Sets book ASIN.
     *
     * @param ASIN the ASIN
     */
    public void setASIN(long ASIN) {
        this.ASIN = ASIN;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author the author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets genre.
     *
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets genre.
     *
     * @param genre the genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Set<UserBookRating> getUserBookRatings() {
        return userBookRatings;
    }

    public void setUserBookRatings(Set<UserBookRating> userBookRatings) {
        this.userBookRatings = userBookRatings;
    }

    @Override
    public String toString() {
        JSONObject response = new JSONObject();
        response.put("ASIN", String.valueOf(getASIN()));
        response.put("title", getTitle());
        response.put("author", getAuthor());
        response.put("genre", getGenre());

        return response.toString();
    }

}
