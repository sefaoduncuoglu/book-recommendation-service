package com.sefa.challenge.bookrecommendationservice.repository;

import com.sefa.challenge.bookrecommendationservice.model.UserBookRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/* The interface User Book Rate repository.
 *
 * @author Sefa Oduncuoglu
 */
@Repository
public interface UserBookRateRepository extends JpaRepository<UserBookRating, Long> {
}