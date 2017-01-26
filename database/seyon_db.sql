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
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (53,'MAILING','No. 108, T.K.Mudali St, Choolai',NULL,NULL,'Tamil Nadu','Chennai','India','600112','2017-01-26 07:31:45'),(103,'MAILING','No. 108, T.K.Mudali St, Choolai',NULL,NULL,'Tamil Nadu','Chennai','India','600112','2017-01-26 07:40:49'),(153,'MAILING','No. 108, T.K.Mudali St, Choolai',NULL,NULL,'Tamil Nadu','Chennai','India','600112','2017-01-26 07:41:34'),(253,'MAILING','No. 108, T.K.Mudali St, Choolai',NULL,NULL,'Tamil Nadu','Chennai','India','600112','2017-01-26 07:45:15'),(353,'MAILING','No. 108, T.K.Mudali St, Choolai',NULL,NULL,'Tamil Nadu','Chennai','India','600112','2017-01-26 07:47:20'),(653,'MAILING','No. 108, T.K.Mudali St, Choolai',NULL,NULL,'Tamil Nadu','Chennai','India','600112','2017-01-26 08:08:49'),(803,'MAILING','No. 108, T.K.Mudali St, Choolai',NULL,NULL,'Tamil Nadu','Chennai','India','600112','2017-01-26 08:18:07'),(853,'MAILING','No. 108, T.K.Mudali St, Choolai',NULL,NULL,'Tamil Nadu','Chennai','India','600112','2017-01-26 11:50:05'),(903,'BILLING','No. 108, T.K.Mudali St, Choolai',NULL,NULL,'Tamil Nadu','Chennai','India','600112','2017-01-26 11:58:06'),(1003,'BILLING','No. 108, T.K.Mudali St, Choolai',NULL,NULL,'Tamil Nadu','Chennai','India','600112','2017-01-26 12:16:54');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (657,'12345','2017-01-26 13:38:48','Sample',NULL,'Pending',2000.33,654,'2017-01-26 08:08:49'),(807,'12345','2017-01-26 13:48:07','Sample',806,'Pending',2000.33,804,'2017-01-26 08:18:07'),(857,'12345','2017-01-26 17:20:05','Sample',856,'Pending',2000.33,854,'2017-01-26 11:50:05'),(907,'12345','2017-01-26 17:28:06','Sample',906,'Pending',2000.33,904,'2017-01-26 11:58:06'),(1007,'12345','2017-01-26 17:46:54','ADVANCE',1006,'Pending',2000.33,1004,'2017-01-26 12:16:54');
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `document`
--

LOCK TABLES `document` WRITE;
/*!40000 ALTER TABLE `document` DISABLE KEYS */;
INSERT INTO `document` VALUES (656,'Sample',NULL,'Sample','Sample','2017-01-26 08:08:49',654),(806,'Sample',NULL,'Sample','Sample','2017-01-26 08:18:07',804),(856,'Sample',NULL,'Sample','Sample','2017-01-26 11:50:05',854),(906,'Sample',NULL,'Sample','Sample','2017-01-26 11:58:06',904),(1006,'Sample',NULL,'Sample','BILL','2017-01-26 12:16:54',1004);
/*!40000 ALTER TABLE `document` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`iddrawing`),
  KEY `FK_drawing_project_id` (`project_id`),
  KEY `FK_drawing_drawing_document_id` (`drawing_document_id`),
  CONSTRAINT `FK_drawing_drawing_document_id` FOREIGN KEY (`drawing_document_id`) REFERENCES `document` (`iddocument`),
  CONSTRAINT `FK_drawing_project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`idproject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drawing`
--

LOCK TABLES `drawing` WRITE;
/*!40000 ALTER TABLE `drawing` DISABLE KEYS */;
INSERT INTO `drawing` VALUES (655,'1234','Mail','2017-01-26 13:38:48','FINAL',NULL,'2017-01-26 08:08:49',654),(805,'1234','Mail','2017-01-26 13:48:07','FINAL',806,'2017-01-26 08:18:07',804),(855,'1234','Mail','2017-01-26 17:20:05','FINAL',856,'2017-01-26 11:50:05',854),(905,'1234','Mail','2017-01-26 17:28:06','FINAL',906,'2017-01-26 11:58:06',904),(1005,'1234','STRUCTURAL','2017-01-26 17:46:54','FINAL',1006,'2017-01-26 12:16:54',1004);
/*!40000 ALTER TABLE `drawing` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES (809,'Added','Sample','<none>','Sample','Sample','2017-01-26 08:18:07',804),(859,'Added','Sample','<none>','Sample','Sample','2017-01-26 11:50:05',854),(909,'Added','Sample','<none>','Sample','Sample','2017-01-26 11:58:06',904),(1009,'ADD','Sample','<none>','Sample','Sample','2017-01-26 12:16:54',1004);
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (51,'sasipraveen39','sample',NULL,0,1,'ADMIN','2017-01-26 07:31:45',52,NULL),(101,'sasipraveen40','sample',NULL,0,1,'ADMIN','2017-01-26 07:40:49',102,NULL),(151,'sasipraveen41','sample',NULL,0,1,'ADMIN','2017-01-26 07:41:34',152,NULL),(251,'sasipraveen42','sample',NULL,0,1,'ADMIN','2017-01-26 07:45:15',252,NULL),(351,'sasipraveen43','sample',NULL,0,1,'ADMIN','2017-01-26 07:47:20',352,NULL),(651,'sasipraveen44','sample',NULL,0,1,'ADMIN','2017-01-26 08:08:49',652,NULL),(801,'sasipraveen45','sample',NULL,0,1,'ADMIN','2017-01-26 08:18:07',802,NULL),(851,'sasipraveen46','sample',NULL,0,1,'Admin','2017-01-26 11:50:05',852,NULL),(901,'sasipraveen47','sample',NULL,0,1,'ADMIN','2017-01-26 11:58:06',902,NULL),(1001,'sasipraveen48','sample',NULL,0,1,'ADMIN','2017-01-26 12:16:54',1002,NULL);
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

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
  CONSTRAINT `FK_payment_receipt_document_id` FOREIGN KEY (`receipt_document_id`) REFERENCES `document` (`iddocument`),
  CONSTRAINT `FK_payment_bill_id` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`idbill`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (658,NULL,NULL,'2017-01-26 13:38:48','not Paid',0,NULL,NULL,NULL,100.22,657,'2017-01-26 08:08:49'),(808,NULL,NULL,'2017-01-26 13:48:07','not Paid',0,NULL,NULL,806,100.22,807,'2017-01-26 08:18:07'),(858,NULL,NULL,'2017-01-26 17:20:05','not Paid',0,NULL,NULL,856,100.22,857,'2017-01-26 11:50:05'),(908,NULL,NULL,'2017-01-26 17:28:06','not Paid',0,NULL,NULL,906,100.22,907,'2017-01-26 11:58:06'),(1008,NULL,NULL,'2017-01-26 17:46:54','not Paid',0,NULL,NULL,1006,100.22,1007,'2017-01-26 12:16:54');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`idproject`),
  KEY `FK_project_user_id` (`user_id`),
  KEY `FK_project_address_id` (`address_id`),
  CONSTRAINT `FK_project_address_id` FOREIGN KEY (`address_id`) REFERENCES `address` (`idaddress`),
  CONSTRAINT `FK_project_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`iduser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (354,'Sample Project1','1234','Sasi Praveen',353,'COMMERCIAL',2500,'2017-01-26 13:17:20','2017-01-26 13:17:20','2017-01-26 13:17:20',NULL,1000.12,352,'2017-01-26 07:47:20'),(654,'Sample Project1','1234','Sasi Praveen',653,'COMMERCIAL',2500,'2017-01-26 13:38:48','2017-01-26 13:38:48','2017-01-26 13:38:48',NULL,1000.12,652,'2017-01-26 08:08:49'),(804,'Sample Project1','1234','Sasi Praveen',803,'COMMERCIAL',2500,'2017-01-26 13:48:07','2017-01-26 13:48:07','2017-01-26 13:48:07',NULL,1000.12,802,'2017-01-26 08:18:07'),(854,'Sample Project1','1234','Sasi Praveen',853,'COMMERCIAL',2500,'2017-01-26 17:20:05','2017-01-26 17:20:05','2017-01-26 17:20:05',NULL,1000.12,852,'2017-01-26 11:50:05'),(904,'Sample Project1','1234','Sasi Praveen',903,'COMMERCIAL',2500,'2017-01-26 17:28:06','2017-01-26 17:28:06','2017-01-26 17:28:06',NULL,1000.12,902,'2017-01-26 11:58:06'),(1004,'Sample Project1','1234','Sasi Praveen',1003,'COMMERCIAL',2500,'2017-01-26 17:46:54','2017-01-26 17:46:54','2017-01-26 17:46:54',NULL,1000.12,1002,'2017-01-26 12:16:54');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `sequence`
--

LOCK TABLES `sequence` WRITE;
/*!40000 ALTER TABLE `sequence` DISABLE KEYS */;
INSERT INTO `sequence` VALUES ('SEQ_GEN',1050);
/*!40000 ALTER TABLE `sequence` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`iduser`),
  KEY `FK_user_address_id` (`address_id`),
  CONSTRAINT `FK_user_address_id` FOREIGN KEY (`address_id`) REFERENCES `address` (`idaddress`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (52,'Sasi Praveen','sasipraveen39@gmail.com','9790829078','04442817868',53,'2017-01-26 07:31:45'),(102,'Sasi Praveen','sasipraveen39@gmail.com','9790829078','04442817868',103,'2017-01-26 07:40:49'),(152,'Sasi Praveen','sasipraveen39@gmail.com','9790829078','04442817868',153,'2017-01-26 07:41:34'),(252,'Sasi Praveen','sasipraveen39@gmail.com','9790829078','04442817868',253,'2017-01-26 07:45:15'),(352,'Sasi Praveen','sasipraveen39@gmail.com','9790829078','04442817868',353,'2017-01-26 07:47:20'),(652,'Sasi Praveen','sasipraveen39@gmail.com','9790829078','04442817868',653,'2017-01-26 08:08:49'),(802,'Sasi Praveen','sasipraveen39@gmail.com','9790829078','04442817868',803,'2017-01-26 08:18:07'),(852,'Sasi Praveen','sasipraveen39@gmail.com','9790829078','04442817868',853,'2017-01-26 11:50:05'),(902,'Sasi Praveen','sasipraveen39@gmail.com','9790829078','04442817868',903,'2017-01-26 11:58:06'),(1002,'Sasi Praveen','sasipraveen39@gmail.com','9790829078','04442817868',1003,'2017-01-26 12:16:54');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-26 18:29:12
