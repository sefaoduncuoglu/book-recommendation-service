-- MySQL dump 10.13  Distrib 8.0.16, for macos10.14 (x86_64)
--
-- Host: localhost    Database: sfa-db
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `books` (
  `book_asin` bigint(20) NOT NULL,
  `title` varchar(250) NOT NULL,
  `author` varchar(45) NOT NULL,
  `genre` varchar(30) NOT NULL,
  PRIMARY KEY (`book_asin`),
  UNIQUE KEY `book_id_UNIQUE` (`book_asin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (2182718,'Improve Your Bowls','Tony Allcock','Sports & Outdoors'),(7210361,'Asthma-Free Naturally: Everything You Need to Know About Taking Control of Your Asthma--Featuring the Buteyko Breathing Method Suitable for Adults and Children','Patrick G. McKeown','Health, Fitness & Dieting'),(7262833,'Seeing Red','Graham Poll','Biographies & Memoirs');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_book_rate`
--

DROP TABLE IF EXISTS `user_book_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_book_rate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `book_asin` bigint(20) NOT NULL,
  `rate` tinyint(5) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_and_book_asin_unique_index` (`user_id`,`book_asin`),
  UNIQUE KEY `UK6nqqiddw0vme7ikbxsdiqwoh7` (`user_id`,`book_asin`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_user_id_idx` (`user_id`),
  KEY `fk_book_asin_idx` (`book_asin`),
  CONSTRAINT `fk_book_asin` FOREIGN KEY (`book_asin`) REFERENCES `books` (`book_asin`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_book_rate`
--

LOCK TABLES `user_book_rate` WRITE;
/*!40000 ALTER TABLE `user_book_rate` DISABLE KEYS */;
INSERT INTO `user_book_rate` VALUES (1,7,2182718,1),(2,7,7210361,4),(3,9,2182718,1),(4,9,7210361,4),(5,7,7262833,5);
/*!40000 ALTER TABLE `user_book_rate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email_address` varchar(50) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `email_address_UNIQUE` (`email_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (7,'sefa','oduncuoglu','sefaoduncuoglu@gmail.com','2019-06-15 16:56:36','2019-06-15 16:56:36'),(9,'sefa3','oduncuoglu3','sefaoduncuoglu3@gmail.com','2019-06-16 10:27:31','2019-06-16 10:27:31'),(10,'sefa4','oduncuoglu4','sefaoduncuoglu4@gmail.com','2019-06-16 10:29:21','2019-06-16 10:29:21'),(62,'sefa6','oduncuoglu6','sefaoduncuoglu6@gmail.com','2019-06-18 13:02:09','2019-06-18 13:02:09'),(63,'sefa7','oduncuoglu7','sefaoduncuoglu7@gmail.com','2019-06-18 13:08:44','2019-06-18 13:08:44');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-18 13:15:03
