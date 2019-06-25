# 📚 Book Recommendation Service

This Book Recommendation Service Application provides to the following functionalities:

1. People can add/delete/update/get or get all the user(s) or the book(s)
2. People can rate books and also can get books that rated themselves.
3. People can get the recommended books by given User Id
   * The used recommendation algorithm is User-Based Collaborative Filtering using K-Nearest Neighbors with Pearson

The following technologies used in this project:
* Programming Language: Java 8
* Framework: Spring Boot 2.1.5
* Database: MySQL 8.0.16
* Others: RESTful API, Postman

The following design patterns were used.
- Three Tier Architecture,
- Concrete and Abstract Factory Design,
- Dependency Injection.

How to use this application service? Good Question! please follow the instructions:
1. Import the sfa-db.sql to MySQL database
2. Make sure the MySQL connection password should be set in application.properties
3. Make sure books.txt file is not empty
4. Start the Book Recommendation Service Application
5. Use the Postman API collection for API Requests.

That's All!
