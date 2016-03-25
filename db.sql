-- MySQL dump 10.13  Distrib 5.6.27, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: studentsworld
-- ------------------------------------------------------
-- Server version	5.6.27-0ubuntu0.15.04.1

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
-- Table structure for table `comment`
--

/*DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email_id` varchar(256) DEFAULT NULL,
  `username` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL,
  `otp` varchar(256) DEFAULT NULL,
  `valid` tinyint(1) DEFAULT NULL,
  `cook` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_id` (`email_id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (24,'sljlss@sljsl.com','asljf@sljfslj','lsjfjsdkl','4229251',NULL,'1348102'),(25,'animes@ani.com','askjf','sfasd','5835366',NULL,'1348102'),(26,'test@t.com','sljdfkl','slfjsdl','8024217',NULL,'1348102'),(27,'testa@t.com','asdkljf','sfd','2469167',NULL,'1348102'),(28,'testab@t.com','asfsa','asdfs','8132946',NULL,'1348102'),(30,'testabc@t.com','sdafs','asdfsd','10177210',NULL,'1348102'),(31,'testabcd@t.com','sadfa','asdfs','1532885',NULL,'1348102'),(32,'testabcde@t.com','asdfs','sfsdfds','974804',NULL,'1348102'),(33,'t@t.com','asdf','sdfgdgd','5607221',NULL,'1348102'),(34,'a@t.com','sds','sdddds','8376594',NULL,'1348102'),(35,'c@t.com','sddddas','sdfsdf','2930873',NULL,'1348102'),(36,'d@t.com','sdddfsf','asdfs','7997984',NULL,'1348102'),(37,'d2t@t.com','sadfs','sdfs','8074693',NULL,'1348102'),(38,'d3@t.com','sdf','sdfgdf','187737',NULL,'1348102'),(39,'d4@t.com','sldj','sdffgd','1537686',NULL,'1348102'),(40,'d5@t.com','asdf','sdfssd','1794866',NULL,'8161800'),(41,'d56@t.com','sds','sdsdfs','4011910',NULL,'6556496'),(42,'d66@t.com','fsddddddd','sddds','7911102',NULL,'1666604'),(43,'d68@t.com','asdds','sdsssssssss','7120279',NULL,'1505149'),(44,'d69@t.com','asd','sdfs','5517312',NULL,'4136599'),(45,'d70@t.com','sdds','sds','3137295',NULL,'6413009'),(47,'d71@gmail.com','dfsd','sds','4136869',NULL,'4816433'),(48,'d11@t.com','sddds','sdf','9467320',1,'9089198'),(49,'d15@t.com','sddddsd','dfg','5715858',NULL,'7725771'),(50,'anim@anim.com','animesh','ani','2443372',NULL,'1726430'),(51,'anim@sani.com','nisjdk','skljsl','3051087',NULL,'10006619'),(52,'asdf@sdf.com','askljdfkas','asdf','2737657',NULL,'7848319'),(53,'asfdas@sdf.com','anikm','sddfg','1539695',NULL,'7008719'),(54,'asdf@dfg.com','saddd','asd','824666',1,'4827069'),(55,'asf@sdf.com','asdf','dsd','1933352',NULL,'6222944'),(56,'sdfg@dfg.com','sdddg','dfgd','1189811',NULL,'135787'),(58,'asdf@sdfasd.com','sdg','df','5086840',NULL,'6731010'),(59,'sadfasdf@sdf.com','asdf','ddddd','9180626',NULL,'2156859'),(60,'prat@ya.com','prat','prat','10044505',NULL,'569576'),(61,'praty@ya.com','prat','prat','7277010',1,'408435'),(62,'pratyu@ya.com','prat','prat','6547729',NULL,'4953170'),(63,'qwerty@gmail.com','qwerty','388d24aac3a47b0891c957bc01647a41','2485913',1,'[B@51a0d68d');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`post_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (9,63,'asddf','sdfdd','2015-11-18 04:07:14'),(10,63,'asdfer','Sgdfg','2015-11-18 04:16:07');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) NOT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,'life'),(2,'android'),(3,'mathematics'),(5,'java');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;



CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `post_id` (`post_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--



--
-- Table structure for table `post_tag`
--

DROP TABLE IF EXISTS `post_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_tag` (
  `post_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  KEY `post_id` (`post_id`),
  KEY `tag_id` (`tag_id`),
  CONSTRAINT `post_tag_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`),
  CONSTRAINT `post_tag_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_tag`
--

LOCK TABLES `post_tag` WRITE;
/*!40000 ALTER TABLE `post_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(500) NOT NULL,
  `option_a` varchar(256) DEFAULT NULL,
  `option_b` varchar(256) DEFAULT NULL,
  `option_c` varchar(256) DEFAULT NULL,
  `option_d` varchar(256) DEFAULT NULL,
  `answer` char(1) DEFAULT NULL,
  `explanation` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'compute 2+2','3','2','4','1','c',NULL),(2,'compute 2*3','6','3','2','5','a',NULL);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--


--
-- Table structure for table `technical`
--

DROP TABLE IF EXISTS `technical`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `technical` (
  `user_id` int(11) DEFAULT NULL,
  `javascript` int(11) DEFAULT NULL,
  `html` int(11) DEFAULT NULL,
  `java` int(11) DEFAULT NULL,
  `c` int(11) DEFAULT NULL,
  `cpp` int(11) DEFAULT NULL,
  `python` int(11) DEFAULT NULL,
  KEY `user_id` (`user_id`),
  CONSTRAINT `technical_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `technical`
--

LOCK TABLES `technical` WRITE;
/*!40000 ALTER TABLE `technical` DISABLE KEYS */;
/*!40000 ALTER TABLE `technical` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `test_id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(256) DEFAULT NULL,
  `length` int(11) DEFAULT '0',
  PRIMARY KEY (`test_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,'maths',5),(2,'coding',7);
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_history`
--

DROP TABLE IF EXISTS `test_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_history` (
  `user_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `user_response` char(1) DEFAULT 'n',
  KEY `user_id` (`user_id`),
  KEY `question_id` (`question_id`),
  CONSTRAINT `test_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `test_history_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_history`
--

LOCK TABLES `test_history` WRITE;
/*!40000 ALTER TABLE `test_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_question`
--

DROP TABLE IF EXISTS `test_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_question` (
  `test_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  KEY `test_id` (`test_id`),
  KEY `question_id` (`question_id`),
  CONSTRAINT `test_question_ibfk_1` FOREIGN KEY (`test_id`) REFERENCES `test` (`test_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `test_question_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_question`
--

LOCK TABLES `test_question` WRITE;
/*!40000 ALTER TABLE `test_question` DISABLE KEYS */;
INSERT INTO `test_question` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `test_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_user`
--

DROP TABLE IF EXISTS `test_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_user` (
  `user_id` int(11) DEFAULT NULL,
  `test_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_user`
--

LOCK TABLES `test_user` WRITE;
/*!40000 ALTER TABLE `test_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--


--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_profile` (
  `college_name` varchar(256) NOT NULL,
  `firstname` varchar(256) NOT NULL,
  `lastname` varchar(256) NOT NULL,
  `mobile` varchar(256) DEFAULT NULL,
  `degree` varchar(256) NOT NULL,
  `start_degree` date NOT NULL,
  `end_degree` date NOT NULL,
  `profile_pic` varchar(256) DEFAULT NULL,
  `resume` varchar(256) DEFAULT NULL,
  `address` varchar(256) NOT NULL,
  `dob` date NOT NULL,
  `id` int(11) DEFAULT NULL,
  KEY `id` (`id`),
  CONSTRAINT `user_profile_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-21  0:02:20
