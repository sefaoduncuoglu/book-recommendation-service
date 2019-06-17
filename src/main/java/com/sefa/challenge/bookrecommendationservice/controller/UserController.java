package com.sefa.challenge.bookrecommendationservice.controller;

import com.sefa.challenge.bookrecommendationservice.exception.ResourceNotFoundException;
import com.sefa.challenge.bookrecommendationservice.model.User;
import com.sefa.challenge.bookrecommendationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    /**
     * Gets users.
     *
     * @return the all users
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    /**
     * Gets user by userId.
     *
     * @param userId the user id
     * @return the user by userId
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public ResponseEntity<User> getUserById(@RequestParam("userId") Long userId)
            throws ResourceNotFoundException {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return ResponseEntity.ok().body(user);
    }

    /**
     * Create user.
     *
     * @param userData the user details
     * @return the user
     */
    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createUser(@Valid @RequestBody User userData) {
        return ResponseEntity.ok().body(userRepository.save(userData));
    }

    /**
     * Update user response entity.
     *
     * @param userId      the user id
     * @param userDetails the user details
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PutMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(
            @RequestParam("userId") Long userId, @Valid @RequestBody User userDetails)
            throws ResourceNotFoundException {

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        user.setEmail(userDetails.getEmail());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        user.setUpdatedAt(new Date());

        return ResponseEntity.ok().body(userRepository.save(user));
    }

    /**
     * Delete user map.
     *
     * @param userId the user id
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity deleteUser(@RequestParam("userId") Long userId) throws Exception {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok().body(response);
    }

}
