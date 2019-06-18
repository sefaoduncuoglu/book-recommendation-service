package com.sefa.challenge.bookrecommendationservice.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity()
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", unique = true, nullable = false)
    private long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email_address", unique = true, nullable = false)
    private String email;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    // Don't create getter and setter methods for this object
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserBookRating> userBookRates;

    public User() {
    }

    public User(String firstName, String lastName, String email, Date createdAt, Date updatedAt, Set<UserBookRating> userBookRates) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userBookRates = userBookRates;
    }

    /**
     * Gets user id.
     *
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets updated at.
     *
     * @return the updated at
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets updated at.
     *
     * @param updatedAt the updated at
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<UserBookRating> getUserBookRating() {
        return userBookRates;
    }

    public void setUserBookRating(Set<UserBookRating> userBookRates) {
        this.userBookRates = userBookRates;
    }

    @Override
    public String toString() {

        JSONObject response = new JSONObject();

        response.put("userId", String.valueOf(getUserId()));
        response.put("firstName", getFirstName());
        response.put("lastName", getLastName());
        response.put("email", getEmail());
        response.put("createdAt", getCreatedAt());
        response.put("updatedAt", getUpdatedAt());

        if (userBookRates != null) {
            JSONArray userRatedBooksArray = new JSONArray();

            for (UserBookRating userBookRating : userBookRates) {
                userRatedBooksArray.put(
                        new JSONObject(
                                "{" +
                                        "\"ASIN\":\"" + userBookRating.getBook().getASIN() + "\"," +
                                        "\"rate\":\"" + userBookRating.getRate() + "\"" +
                                        "}"
                        )
                );
            }
            if (!userRatedBooksArray.isEmpty()) {
                response.put("ratedBooks", userRatedBooksArray);
            }
        }
        return response.toString();
    }
}