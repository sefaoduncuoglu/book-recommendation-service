package com.sefa.challenge.bookrecommendationservice.controller;

import com.sefa.challenge.bookrecommendationservice.exception.ResourceAlreadyExistsException;
import com.sefa.challenge.bookrecommendationservice.exception.ResourceNotFoundException;
import com.sefa.challenge.bookrecommendationservice.model.Book;
import com.sefa.challenge.bookrecommendationservice.model.User;
import com.sefa.challenge.bookrecommendationservice.repository.BookRepository;
import com.sefa.challenge.bookrecommendationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllBooks() {
        return ResponseEntity.ok().body(bookRepository.findAll());
    }

    /**
     * Gets book by bookASIN.
     *
     * @param bookASIN the book ASIN
     * @return the book by bookASIN
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public ResponseEntity<String> getBookById(@RequestParam("bookASIN") Long bookASIN)
            throws ResourceNotFoundException {
        Book book =
                bookRepository
                        .findById(bookASIN)
                        .orElseThrow(() -> new ResourceNotFoundException("Book not found!"));
        return ResponseEntity.ok().body(book.toString());
    }


    /**
     * Create book.
     *
     * @param bookData the book details
     * @return the book
     */
    @PostMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createBook(@Valid @RequestBody Book bookData)
            throws ResourceAlreadyExistsException {

        if (bookRepository.existsById(bookData.getASIN())) {
            throw new ResourceAlreadyExistsException("Book Already Exists!");
        }

        return ResponseEntity.ok().body(bookRepository.save(bookData));
    }


    /**
     * Update book response entity.
     *
     * @param bookASIN the book ASIN
     * @param bookData the book data
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PutMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateBook(
            @RequestParam("bookASIN") Long bookASIN, @Valid @RequestBody Book bookData)
            throws ResourceNotFoundException {

        Book book =
                bookRepository
                        .findById(bookASIN)
                        .orElseThrow(() -> new ResourceNotFoundException("Book not found!"));

        book.setTitle(bookData.getTitle());
        book.setAuthor(bookData.getAuthor());
        book.setGenre(bookData.getGenre());

        return ResponseEntity.ok().body(bookRepository.save(book).toString());
    }

    /**
     * Delete book.
     *
     * @param bookASIN the book ASIN
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity deleteUser(@RequestParam("bookASIN") Long bookASIN) throws Exception {
        Book book =
                bookRepository
                        .findById(bookASIN)
                        .orElseThrow(() -> new ResourceNotFoundException("Book not found!"));

        bookRepository.delete(book);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok().body(response);
    }

    /**
     * Get recommended books by user id
     *
     * @param userId
     * @return the recommended books by user id
     * @throws ResourceNotFoundException if there is no registered User
     */
    @GetMapping("/recommended")
    public String getRecommendedBooksByUserId(@RequestParam("userId") Long userId)
            throws ResourceNotFoundException {

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));


        return "Fuck the System!";
    }

}
