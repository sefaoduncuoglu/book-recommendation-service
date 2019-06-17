package com.sefa.challenge.bookrecommendationservice.repository;

import com.sefa.challenge.bookrecommendationservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* The interface Book repository.
 *
 * @author Sefa Oduncuoglu
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}