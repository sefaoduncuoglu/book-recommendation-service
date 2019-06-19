package com.sefa.challenge.bookrecommendationservice.service;

import com.sefa.challenge.bookrecommendationservice.repository.BookRepository;
import com.sefa.challenge.bookrecommendationservice.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Recommender {

    // Number of output neighbourhoods
    private static final int NUM_NEIGHBOURHOODS = 3;
    // Number of output recommendations
    private static final int NUM_RECOMMENDATIONS = 20;
    // Min value of recommended book rates
    private static final int MIN_VALUE_RECOMMENDATION = 4;

    /**
     * Map with the user id as key and its ratings as value that is a map with book ASIN as key and its rating as value
     */
    private Map<Long, Map<Long, Integer>> ratings;

    /**
     * Average rating of each user where the key is the user id and the value its average rating
     */
    private Map<Long, Double> averageRating;

    /**
     * Constructor
     */
    public Recommender() {
        ratings = new HashMap<>();
        averageRating = new HashMap<>();
    }

    public Map<Long, Map<Long, Integer>> getRatings() {
        return ratings;
    }

    private void setRatings(Map<Long, Map<Long, Integer>> ratings) {
        this.ratings = ratings;
    }

    public Map<Long, Double> getAverageRating() {
        return averageRating;
    }

    private void setAverageRating(Map<Long, Double> averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * Get the k-nearest neighbourhoods using Pearson:
     * sim(i,j) = numerator / (sqrt(userDenominator^2) * sqrt(otherUserDenominator^2))
     * numerator = sum((r(u,i) - r(u)) * (r(v,i) - r(v)))
     * userDenominator = sum(r(u,i) - r(i))
     * otherUserDenominator = sum(r(v,i) - r(v))
     * r(u,i): rating of the book i by the user u
     * r(u): average rating of the user u
     *
     * @param userRatings ratings of the user
     * @return nearest neighbourhoods
     */
    private Map<Long, Double> getNeighbourhoods(Map<Long, Integer> userRatings) {
        Map<Long, Double> neighbourhoods = new HashMap<>();
        ValueComparator valueComparator = new ValueComparator(neighbourhoods);
        Map<Long, Double> sortedNeighbourhoods = new TreeMap<>(valueComparator);

        double userAverage = getAverage(userRatings);

        for (long user : ratings.keySet()) {
            ArrayList<Long> matches = new ArrayList<>();
            for (long bookASIN : userRatings.keySet()) {
                if (ratings.get(user).containsKey(bookASIN)) {
                    matches.add(bookASIN);
                }
            }
            double matchRate;
            if (matches.size() > 0) {
                double numerator = 0, userDenominator = 0, otherUserDenominator = 0;
                for (long bookASIN : matches) {
                    double u = userRatings.get(bookASIN) - userAverage;
                    double v = ratings.get(user).get(bookASIN) - averageRating.get(user);

                    numerator += u * v;
                    userDenominator += u * u;
                    otherUserDenominator += v * v;
                }
                if (userDenominator == 0 || otherUserDenominator == 0) {
                    matchRate = 0;
                } else {
                    matchRate = numerator / (Math.sqrt(userDenominator) * Math.sqrt(otherUserDenominator));
                }
            } else {
                matchRate = 0;
            }

            neighbourhoods.put(user, matchRate);
        }
        sortedNeighbourhoods.putAll(neighbourhoods);

        Map<Long, Double> output = new TreeMap<>();

        Iterator entries = sortedNeighbourhoods.entrySet().iterator();
        int i = 0;
        while (entries.hasNext() && i < NUM_NEIGHBOURHOODS) {
            Map.Entry entry = (Map.Entry) entries.next();
            if ((double) entry.getValue() > 0) {
                output.put((long) entry.getKey(), (double) entry.getValue());
                i++;
            }
        }

        return output;
    }

    /**
     * Get predictions of each book by a user giving some ratings and its neighbourhood:
     * r(u,i) = r(u) + sum(sim(u,v) * (r(v,i) - r(v))) / sum(abs(sim(u,v)))
     * sim(u,v): similarity between u and v users
     * r(u,i): rating of the book i by the user u
     * r(u): average rating of the user u
     *
     * @param userRatings    ratings of the user
     * @param neighbourhoods nearest neighbourhoods
     * @param books          books in the database
     * @return predictions for each book
     */
    private Map<Long, Double> getRecommendations(Map<Long, Integer> userRatings,
                                                Map<Long, Double> neighbourhoods, Map<Long, String> books) {
        Map<Long, Double> predictedRatings = new HashMap<>();

        double userAverage = getAverage(userRatings);

        for (Long bookASIN : books.keySet()) {
            if (!userRatings.containsKey(bookASIN)) {
                double numerator = 0, denominator = 0;
                for (Long neighbourhood : neighbourhoods.keySet()) {
                    if (ratings.get(neighbourhood).containsKey(bookASIN)) {
                        double matchRate = neighbourhoods.get(neighbourhood);
                        numerator +=
                                matchRate * (ratings.get(neighbourhood).get(bookASIN) - averageRating.get(neighbourhood));
                        denominator += Math.abs(matchRate);
                    }
                }
                double predictedRating = 0;
                if (denominator > 0) {
                    predictedRating = userAverage + numerator / denominator;
                    if (predictedRating > 5) {
                        predictedRating = 5;
                    }
                }
                predictedRatings.put(bookASIN, predictedRating);
            }
        }

        return predictedRatings;
    }

    /**
     * Get average of the ratings of a user
     *
     * @param userRatings ratings of a user
     * @return average or the ratings of a user
     */
    private double getAverage(Map<Long, Integer> userRatings) {
        double userAverage = 0;
        for (Map.Entry<Long, Integer> longIntegerEntry : userRatings.entrySet()) {
            userAverage += (int) ((Map.Entry) longIntegerEntry).getValue();
        }
        return userAverage / userRatings.size();
    }

    public String recommendedBooks(Long userId, UserRepository userRepository, BookRepository bookRepository) {

        Map<Long, Integer> userRatings = new HashMap<>();
        Map<Long, Double> averageRating = new HashMap<>();
        Map<Long, Map<Long, Integer>> userWithRatesMap = new TreeMap<>();

        userRepository.findAll().forEach(userItem -> {

            Long userID = userItem.getUserId();

            // We must return the user that requested the recommendation
            if (userId.equals(userID)) {
                return;
            }

            userItem.getUserBookRating().forEach(bookRating -> userRatings.put(bookRating.getBook().getASIN(), bookRating.getRate()));
            userWithRatesMap.put(userID, userRatings);
            setRatings(userWithRatesMap);
            averageRating.put(userID, 0.0);

            for (Map.Entry<Long, Integer> longIntegerEntry : userRatings.entrySet()) {

                if (ratings.containsKey(userID)) {
                    ratings.get(userID).put(longIntegerEntry.getKey(), longIntegerEntry.getValue());
                    averageRating.put(userID, averageRating.get(userID) + (double) longIntegerEntry.getValue());
                } else {
                    Map<Long, Integer> bookRating = new HashMap<>();
                    bookRating.put(longIntegerEntry.getKey(), longIntegerEntry.getValue());
                    ratings.put(userID, bookRating);
                    averageRating.put(userID, (double) longIntegerEntry.getValue());
                }
            }
        });


        for (Map.Entry<Long, Double> longDoubleEntry : averageRating.entrySet()) {
            if (ratings.containsKey(longDoubleEntry.getKey())) {
                double x = (double) ratings.get(longDoubleEntry.getKey()).size();
                longDoubleEntry.setValue(longDoubleEntry.getValue() / x);
            }
        }

        setAverageRating(averageRating);


        Map<Long, String> books = new HashMap<>();

        bookRepository.findAll().forEach(book -> books.put(book.getASIN(), book.getTitle()));


        Map<Long, Double> neighbourhoods = getNeighbourhoods(userRatings);
        Map<Long, Double> recommendations = getRecommendations(userRatings, neighbourhoods, books);
        ValueComparator valueComparator = new ValueComparator(recommendations);
        Map<Long, Double> sortedRecommendations = new TreeMap<>(valueComparator);
        sortedRecommendations.putAll(recommendations);

        Iterator<Map.Entry<Long, Double>> sortedREntries = sortedRecommendations.entrySet().iterator();
        JSONObject recommendedBooks = new JSONObject("{}");
        JSONArray recommendedBooksArray = new JSONArray();

        int i = 0;
        while (sortedREntries.hasNext() && i < NUM_RECOMMENDATIONS) {
            Map.Entry<Long, Double> entry = sortedREntries.next();
            if (entry.getValue() >= MIN_VALUE_RECOMMENDATION) {
                recommendedBooks.put("Book Title", books.get(entry.getKey()));
                recommendedBooks.put("Rate", entry.getValue());
                recommendedBooksArray.put(recommendedBooks);
                i++;
            }
        }
        return recommendedBooksArray.toString();
    }
}
