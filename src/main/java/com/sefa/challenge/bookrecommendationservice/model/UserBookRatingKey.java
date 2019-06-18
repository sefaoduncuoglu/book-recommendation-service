package com.sefa.challenge.bookrecommendationservice.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserBookRatingKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "book_asin")
    private Long asin;

    public UserBookRatingKey() {
    }

    public UserBookRatingKey(Long userId, Long asin) {
        this.userId = userId;
        this.asin = asin;
    }

    public Long getuserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getASIN() {
        return asin;
    }

    public void setAsin(Long asin) {
        this.asin = asin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UserBookRatingKey that = (UserBookRatingKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(asin, that.asin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, asin);
    }

}
