/*
SQLyog Community Edition- MySQL GUI v7.01 
MySQL - 5.0.27-community-nt : Database - speciesclass
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`speciesclass` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `speciesclass`;

/*Table structure for table `disease` */

DROP TABLE IF EXISTS `disease`;

CREATE TABLE `disease` (
  `id` int(255) NOT NULL auto_increment,
  `diseasename` varchar(255) default NULL,
  `p1` varchar(255) default NULL,
  `p2` varchar(255) default NULL,
  `p3` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `disease` */

insert  into `disease`(`id`,`diseasename`,`p1`,`p2`,`p3`) values (1,'Pepper__bell___Bacterial_spot','Copper-based fungicides','such as copper hydroxide','copper oxychloride.'),(2,'Pepper__bell___healthy','Ensure proper crop rotation','use disease-resistant varieties','practice good sanitation'),(3,'Potato___Early_blight','ungicides containing chlorothalonil','copper','No treatment needed (healthy plant)'),(4,'Potato___healthy','Practice crop rotation','maintain good field hygiene','No treatment needed (healthy plant)'),(5,'Potato___Late_blight','Fungicides containing mancozeb','copper-based fungicides','Fungicides containing mancozeb or copper-based sprays'),(6,'Tomato_Bacterial_spot','Copper-based bactericides','copper-based fungicides','Copper-based sprays'),(7,'Tomato_Early_blight','Fungicides containing chlorothalonil','copper','Fungicides containing chlorothalonil sprays'),(8,'Tomato_healthy','Crop rotation','use resistant varieties','proper sanitation'),(9,'Tomato_Late_blight','Fungicides containing mancozeb','chlorothalonil','copper-based fungicides.'),(10,'Tomato_Leaf_Mold','Fungicides containing chlorothalonil ','copper-based sprays','copper'),(11,'Tomato_Septoria_leaf_spot','Fungicides containing azoxystrobin','chlorothalonil','copper'),(12,'Tomato__Target_Spot','Fungicides containing boscalid','pyraclostrobin','copper'),(13,'Tomato__Tomato_mosaic_virus','Use virus-resistant tomato varieties','control aphids','practice good sanitation.');

/*Table structure for table `register` */

DROP TABLE IF EXISTS `register`;

CREATE TABLE `register` (
  `id` int(255) NOT NULL auto_increment,
  `username` varchar(255) default NULL,
  `email` varchar(255) default NULL,
  `mobile` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `register` */

insert  into `register`(`id`,`username`,`email`,`mobile`,`password`) values (1,'a','a@gmail.com','2356898963','a'),(2,'b','b@gmail.com','9874563212','b');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
