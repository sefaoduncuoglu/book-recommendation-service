package com.sefa.challenge.bookrecommendationservice.repository;

import com.sefa.challenge.bookrecommendationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* The interface User repository.
 *
 * @author Sefa Oduncuoglu
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
