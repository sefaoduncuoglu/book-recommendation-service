package com.sefa.challenge.bookrecommendationservice.controller;

import com.sefa.challenge.bookrecommendationservice.exception.ResourceAlreadyExistsException;
import com.sefa.challenge.bookrecommendationservice.exception.ResourceNotFoundException;
import com.sefa.challenge.bookrecommendationservice.model.Book;
import com.sefa.challenge.bookrecommendationservice.model.User;
import com.sefa.challenge.bookrecommendationservice.repository.BookRepository;
import com.sefa.challenge.bookrecommendationservice.repository.UserRepository;
import com.sefa.challenge.bookrecommendationservice.resource.BookResource;
import com.sefa.challenge.bookrecommendationservice.service.Recommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllBooks() {

        List<BookResource> bookResources = new ArrayList<>();
        bookRepository.findAll().forEach(book -> bookResources.add(getBookResource(book)));
        return ResponseEntity.ok().body(bookResources);
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
    public ResponseEntity<BookResource> getBookById(@RequestParam("bookASIN") Long bookASIN)
            throws ResourceNotFoundException {
        Book book =
                bookRepository
                        .findById(bookASIN)
                        .orElseThrow(() -> new ResourceNotFoundException("Book not found!"));

        BookResource bookResource = new BookResource();
        bookResource.setASIN(book.getASIN());
        bookResource.setTitle(book.getTitle());
        bookResource.setAuthor(book.getAuthor());
        bookResource.setGenre(book.getGenre());

        return ResponseEntity.ok().body(bookResource);
    }

    /**
     * Create book.
     *
     * @param bookData the book details
     * @return the book
     */
    @PostMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<BookResource> createBook(@Valid @RequestBody Book bookData)
            throws ResourceAlreadyExistsException {

        if (bookRepository.existsById(bookData.getASIN())) {
            throw new ResourceAlreadyExistsException("Book Already Exists!");
        }

        return ResponseEntity.ok().body(getBookResource(bookRepository.save(bookData)));
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
    public ResponseEntity<BookResource> updateBook(
            @RequestParam("bookASIN") Long bookASIN, @Valid @RequestBody Book bookData)
            throws ResourceNotFoundException {

        Book book =
                bookRepository
                        .findById(bookASIN)
                        .orElseThrow(() -> new ResourceNotFoundException("Book not found!"));

        book.setTitle(bookData.getTitle());
        book.setAuthor(bookData.getAuthor());
        book.setGenre(bookData.getGenre());

        return ResponseEntity.ok().body(getBookResource(bookRepository.save(book)));
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
    @GetMapping(value = "/recommended", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRecommendedBooksByUserId(@RequestParam("userId") Long userId)
            throws ResourceNotFoundException {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found!");
        }

        Recommender recommender = new Recommender();
        String recommendedBooks = recommender.recommendedBooks(userId, userRepository, bookRepository);

        return ResponseEntity.ok().body(recommendedBooks);
    }

    private BookResource getBookResource(Book book) {
        return new BookResource(
                book.getASIN()
                , book.getTitle()
                , book.getAuthor()
                , book.getGenre());
    }
}
