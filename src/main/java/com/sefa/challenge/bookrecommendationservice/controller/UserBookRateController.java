package com.sefa.challenge.bookrecommendationservice.controller;

import com.sefa.challenge.bookrecommendationservice.exception.ResourceNotFoundException;
import com.sefa.challenge.bookrecommendationservice.model.User;
import com.sefa.challenge.bookrecommendationservice.model.UserBookRating;
import com.sefa.challenge.bookrecommendationservice.repository.BookRepository;
import com.sefa.challenge.bookrecommendationservice.repository.UserBookRateRepository;
import com.sefa.challenge.bookrecommendationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rates")
public class UserBookRateController {

    @Autowired
    private UserBookRateRepository userBookRateRepository;

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
    public Boolean rateBooks(
            @RequestParam("userId") Long userId, @Valid @RequestBody List<UserBookRating> bookRates)
            throws ResourceNotFoundException {

        return true;
    }
}