CREATE DATABASE  IF NOT EXISTS `seyon` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `seyon`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: seyon
-- ------------------------------------------------------
-- Server version	5.5.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `idaddress` int(11) NOT NULL,
  `address_type` varchar(45) NOT NULL,
  `address_line1` varchar(300) NOT NULL,
  `address_line2` varchar(300) DEFAULT NULL,
  `address_line3` varchar(300) DEFAULT NULL,
  `state` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  `pincode` varchar(45) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idaddress`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bill` (
  `idbill` int(11) NOT NULL,
  `bill_number` varchar(100) NOT NULL,
  `bill_date` datetime NOT NULL,
  `bill_type` varchar(45) NOT NULL,
  `bill_document_id` int(11) DEFAULT NULL,
  `bill_status` varchar(45) NOT NULL,
  `total_bill_amount` decimal(12,2) DEFAULT NULL,
  `project_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idbill`),
  KEY `FK_bill_project_id` (`project_id`),
  KEY `FK_bill_bill_document_id` (`bill_document_id`),
  CONSTRAINT `FK_bill_bill_document_id` FOREIGN KEY (`bill_document_id`) REFERENCES `document` (`iddocument`),
  CONSTRAINT `FK_bill_project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`idproject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `document`
--

DROP TABLE IF EXISTS `document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `document` (
  `iddocument` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `file_location` varchar(10000) NOT NULL,
  `document_type` varchar(45) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `project_id` int(11) NOT NULL,
  PRIMARY KEY (`iddocument`),
  KEY `FK_document_project_id` (`project_id`),
  CONSTRAINT `FK_document_project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`idproject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `drawing`
--

DROP TABLE IF EXISTS `drawing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `drawing` (
  `iddrawing` int(11) NOT NULL,
  `drawing_number` varchar(100) NOT NULL,
  `type_of_drawing` varchar(45) NOT NULL,
  `date_of_issue` datetime DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  `drawing_document_id` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `project_id` int(11) NOT NULL,
  `estimated_date_of_issue` datetime DEFAULT NULL,
  PRIMARY KEY (`iddrawing`),
  KEY `FK_drawing_project_id` (`project_id`),
  KEY `FK_drawing_drawing_document_id` (`drawing_document_id`),
  CONSTRAINT `FK_drawing_drawing_document_id` FOREIGN KEY (`drawing_document_id`) REFERENCES `document` (`iddocument`),
  CONSTRAINT `FK_drawing_project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`idproject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history` (
  `idhistory` int(11) NOT NULL,
  `history_type` varchar(45) NOT NULL,
  `field_name` varchar(45) NOT NULL,
  `previous_value` varchar(1000) NOT NULL,
  `current_value` varchar(1000) NOT NULL,
  `change_reason` varchar(300) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `project_id` int(11) NOT NULL,
  PRIMARY KEY (`idhistory`),
  KEY `FK_history_project_id` (`project_id`),
  CONSTRAINT `FK_history_project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`idproject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `idlogin` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(1000) NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `logged_in` tinyint(1) NOT NULL DEFAULT '0',
  `active` tinyint(1) NOT NULL DEFAULT '0',
  `user_type` varchar(45) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `current_login` datetime DEFAULT NULL,
  PRIMARY KEY (`idlogin`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `FK_login_user_id` (`user_id`),
  CONSTRAINT `FK_login_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`iduser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `idpayment` int(11) NOT NULL,
  `receipt_number` varchar(100) DEFAULT NULL,
  `receipt_date` datetime DEFAULT NULL,
  `due_date` datetime NOT NULL,
  `status` varchar(45) NOT NULL,
  `payment_date` int(11) DEFAULT NULL,
  `mode_of_payment` varchar(45) DEFAULT NULL,
  `reference_number` varchar(100) DEFAULT NULL,
  `receipt_document_id` int(11) DEFAULT NULL,
  `amount_payable` decimal(12,2) NOT NULL,
  `bill_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpayment`),
  KEY `FK_payment_bill_id` (`bill_id`),
  KEY `FK_payment_receipt_document_id` (`receipt_document_id`),
  CONSTRAINT `FK_payment_bill_id` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`idbill`),
  CONSTRAINT `FK_payment_receipt_document_id` FOREIGN KEY (`receipt_document_id`) REFERENCES `document` (`iddocument`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `idproject` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `code` varchar(100) NOT NULL,
  `client_name` varchar(100) NOT NULL,
  `address_id` int(11) NOT NULL,
  `poject_type` varchar(45) NOT NULL,
  `total_area_of_project` int(11) DEFAULT NULL,
  `requested_date` datetime NOT NULL,
  `start_date` datetime NOT NULL,
  `estimated_end_date` datetime DEFAULT NULL,
  `actual_end_date` datetime DEFAULT NULL,
  `estimated_total_amount` decimal(12,2) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `project_number` varchar(10) NOT NULL,
  PRIMARY KEY (`idproject`),
  UNIQUE KEY `project_number_UNIQUE` (`project_number`),
  KEY `FK_project_user_id` (`user_id`),
  KEY `FK_project_address_id` (`address_id`),
  CONSTRAINT `FK_project_address_id` FOREIGN KEY (`address_id`) REFERENCES `address` (`idaddress`),
  CONSTRAINT `FK_project_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`iduser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sequence`
--

DROP TABLE IF EXISTS `sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequence` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `iduser` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobile_number` varchar(100) NOT NULL,
  `landline_number` varchar(100) DEFAULT NULL,
  `address_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `account_number` varchar(10) NOT NULL,
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `account_number_UNIQUE` (`account_number`),
  KEY `FK_user_address_id` (`address_id`),
  CONSTRAINT `FK_user_address_id` FOREIGN KEY (`address_id`) REFERENCES `address` (`idaddress`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-11 21:47:52
