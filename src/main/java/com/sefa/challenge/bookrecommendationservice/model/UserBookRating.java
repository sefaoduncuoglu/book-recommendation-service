package com.sefa.challenge.bookrecommendationservice.model;

import org.json.JSONObject;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity()
@Table(name = "user_book_rate", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "book_asin"})})
@EntityListeners(AuditingEntityListener.class)
public class UserBookRating implements Serializable {

    @EmbeddedId
    private UserBookRatingKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("book_asin")
    @JoinColumn(name = "book_asin")
    private Book book;

    @Column(name = "rate")
    private int rate;

    public UserBookRating() {
    }

    public UserBookRating(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    /**
     * Gets id.
     *
     * @return the instance of the UserBookRatingKey class
     */
    public UserBookRatingKey getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id is an instance of the UserBookRatingKey class
     */
    public void setId(UserBookRatingKey id) {
        this.id = id;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets book.
     *
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets book.
     *
     * @param book the ASIN
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Gets rate.
     *
     * @return the rate
     */
    public int getRate() {
        return rate;
    }

    /**
     * Sets book rate.
     *
     * @param rate the rate
     */
    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        JSONObject response = new JSONObject();
        response.put("ASIN", String.valueOf(getBook().getASIN()));
        response.put("rate", String.valueOf(getRate()));

        return response.toString();
    }

}
