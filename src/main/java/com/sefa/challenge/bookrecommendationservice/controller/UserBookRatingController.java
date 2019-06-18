package com.sefa.challenge.bookrecommendationservice.controller;

import com.sefa.challenge.bookrecommendationservice.exception.ResourceNotFoundException;
import com.sefa.challenge.bookrecommendationservice.model.Book;
import com.sefa.challenge.bookrecommendationservice.model.User;
import com.sefa.challenge.bookrecommendationservice.model.UserBookRating;
import com.sefa.challenge.bookrecommendationservice.model.UserBookRatingKey;
import com.sefa.challenge.bookrecommendationservice.repository.BookRepository;
import com.sefa.challenge.bookrecommendationservice.repository.UserBookRatingRepository;
import com.sefa.challenge.bookrecommendationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rates")
public class UserBookRatingController {

    @Autowired
    private UserBookRatingRepository userBookRatingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;


    @GetMapping(value = "/ratedbooks", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getUserRatedBooks(@RequestParam("userId") long userId)
            throws ResourceNotFoundException {

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        return ResponseEntity.ok().body(user.toString());
    }

    /**
     * Update user response entity.
     *
     * @param userId    the user id
     * @param bookRates the rates of the books
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PutMapping(value = "/bookrates", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity rateBooks(
            @RequestParam("userId") Long userId, @Valid @RequestBody List<HashMap> bookRates)
            throws ResourceNotFoundException {

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        List<UserBookRating> updatedBookRates = new ArrayList<>();


        for (HashMap<String, String> map : bookRates) {
            long bookASIN = Long.valueOf(map.get("asin"));
            int rate = Integer.valueOf(map.get("rate"));
            Book book =
                    bookRepository
                            .findById(bookASIN)
                            .orElseThrow(() -> new ResourceNotFoundException("Book not found!"));

            UserBookRating userBookRating = new UserBookRating(user, book);
            UserBookRatingKey userBookRatingKey = new UserBookRatingKey(userId, bookASIN);

            userBookRating.setId(userBookRatingKey);
            userBookRating.setRate(rate);
            updatedBookRates.add(userBookRating);

        }

        return ResponseEntity.ok().body(userBookRatingRepository.saveAll(updatedBookRates).toString());
    }
}