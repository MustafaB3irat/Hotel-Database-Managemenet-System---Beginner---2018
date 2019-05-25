-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: hotel
-- ------------------------------------------------------
-- Server version	8.0.13

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
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bill` (
  `bid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date_issued` date NOT NULL,
  `amount` float NOT NULL,
  `cid` int(10) unsigned NOT NULL,
  `bNum` int(10) unsigned NOT NULL,
  PRIMARY KEY (`bid`),
  UNIQUE KEY `bNum_UNIQUE` (`bNum`),
  KEY `FK_bill_1_idx` (`cid`),
  CONSTRAINT `FK_bill_1` FOREIGN KEY (`cid`) REFERENCES `client` (`cid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_bill_2` FOREIGN KEY (`bNum`) REFERENCES `reservation` (`bnum`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3471893113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (3471893112,'2018-12-19',5500,4128814244,6);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clean_history`
--

DROP TABLE IF EXISTS `clean_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `clean_history` (
  `eid` int(10) unsigned NOT NULL,
  `rNum` varchar(45) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`rNum`,`date`,`eid`),
  KEY `FK_clean_2_idx` (`rNum`),
  KEY `FK_clean_2_idx1` (`eid`),
  CONSTRAINT `FK_clean_1` FOREIGN KEY (`eid`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_clean_2` FOREIGN KEY (`rNum`) REFERENCES `room` (`rnum`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clean_history`
--

LOCK TABLES `clean_history` WRITE;
/*!40000 ALTER TABLE `clean_history` DISABLE KEYS */;
INSERT INTO `clean_history` VALUES (2452940178,'12429','2018-12-17'),(3916601064,'1A','2018-12-10');
/*!40000 ALTER TABLE `clean_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cleaner`
--

DROP TABLE IF EXISTS `cleaner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cleaner` (
  `eid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pid` int(10) unsigned NOT NULL,
  `job_type` varchar(45) NOT NULL,
  PRIMARY KEY (`eid`),
  KEY `FK_cleaner_1_idx` (`pid`),
  CONSTRAINT `FK_cleaner_1` FOREIGN KEY (`pid`) REFERENCES `person` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_cleaner_2` FOREIGN KEY (`eid`) REFERENCES `employee` (`eid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3916601063 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cleaner`
--

LOCK TABLES `cleaner` WRITE;
/*!40000 ALTER TABLE `cleaner` DISABLE KEYS */;
INSERT INTO `cleaner` VALUES (633216648,2475970309,'B7'),(1366439419,1544301696,'X2706O1'),(2452940177,1544301693,'1P'),(2452940178,562438032,'W'),(2452940179,562438032,'0MN8A59YZ2'),(3487104330,1114804967,'BMO'),(3487104332,2475970309,'H'),(3916601060,562438030,'Y171'),(3916601062,2046473580,'X');
/*!40000 ALTER TABLE `cleaner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `client` (
  `cid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pid` int(10) unsigned NOT NULL,
  `doc_type` varchar(45) NOT NULL,
  `doc_no` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`cid`),
  UNIQUE KEY `doc_no_UNIQUE` (`doc_no`),
  KEY `FK-client-1_idx` (`pid`),
  CONSTRAINT `FK-client-1` FOREIGN KEY (`pid`) REFERENCES `person` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4128814245 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (4128814238,3901389860,'Hawya',12),(4128814239,3901389861,'Passport',1124578963),(4128814240,3901389862,'Passport',147852963),(4128814241,3901389863,'Passport',123654789369),(4128814242,3901389864,'Hawya',159357852654),(4128814243,3901389869,'Passport',4036987111),(4128814244,3901389870,'Passport',123654987);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `employee` (
  `eid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pid` int(10) unsigned NOT NULL,
  `rating` float NOT NULL,
  `address` varchar(45) NOT NULL,
  `base_salary` float NOT NULL,
  `log_inName` varchar(45) NOT NULL,
  `log_inPassword` varchar(45) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date DEFAULT NULL,
  `login_type` varchar(45) NOT NULL,
  PRIMARY KEY (`eid`),
  UNIQUE KEY `log_inName_UNIQUE` (`log_inName`),
  KEY `FK_employee-1_idx` (`pid`),
  CONSTRAINT `FK_employee-1` FOREIGN KEY (`pid`) REFERENCES `person` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3916601070 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (203719919,2046473580,1389,'2935 Cedar Tree Highway',51683,'Moises294','72','1970-01-14',NULL,'Receptionist'),(203719920,562438032,-443239,'3948 East Parkwood Lane',31995,'Alfaro542','S','2004-08-04',NULL,'cleaner'),(633216648,2046473580,-9084020,'3796 1st Ct',106417,'Crouse646','TZ7680V','1992-09-25',NULL,'Receptionist'),(633216649,562438032,58,'3062 Hunting Hill Pkwy',83315,'Frederick2022','7A02184EMA9C75QXI','1970-01-06',NULL,'Receptionist'),(936942688,2046473577,-91,'941 Market Dr',36591,'Eugenie1979','4J','1972-07-03','2018-12-02','cleaner'),(936942689,3471893105,-7157740,'501 Old Rose Hill Hwy',102408,'Melonie3','83S4PV2MVB','1988-10-31',NULL,'Receptionist'),(1366439418,562438030,-812368,'31 SW Riverview Hwy',117466,'Wilbanks49','DT','1976-10-15',NULL,'Receptionist'),(1366439419,2654862620,-343,'690 East Riverview St',98062,'Joe92','1EC81O79JF0','1979-05-27',NULL,'Receptionist'),(2023443447,3471893106,-8230260,'54 West Quailwood Avenue',27349,'Alonso823','2356R','1971-05-15',NULL,'cleaner'),(2023443448,1544301695,-2268650,'1146 Rose Hill Avenue',70535,'Adah769','5VITG9172L90A1UZJ','1976-01-20',NULL,'Receptionist'),(2023443449,562438030,-8175,'85 N Ironwood Hwy',111384,'Stephen2028','1','1992-05-20',NULL,'Receptionist'),(2452940176,3471893106,-5872150,'101 Meadowview Pkwy',57922,'Goetz1965','0','1980-02-16',NULL,'Receptionist'),(2452940177,1544301696,3098,'3937 Hunting Hill Avenue',46436,'Winstead2015','3T6IDJVR','1970-01-31',NULL,'cleaner'),(2452940178,2475970307,6259640,'521 SW Flintwood Pkwy',47666,'Abbie2007','16','2003-07-10',NULL,'cleaner'),(2452940179,2654862623,-5634260,'3746 South Rose Hill Dr',52915,'Abernathy1993','KH6H','2017-03-22',NULL,'Receptionist'),(2616477471,1544301695,-56,'145 SW Deepwood Hwy',95657,'Blake1987','3IKV7PC6AW','1971-07-21',NULL,'Receptionist'),(3045974201,562438031,4489970,'3091 Fox Hill Way',79403,'Sachiko27','79','1981-03-23',NULL,'Receptionist'),(3487104330,3084359347,1740240,'1821 Mountain Ct',32051,'Ligon568','K18TQ71R127Y96A','2008-01-11',NULL,'cleaner'),(3487104331,1114804962,-8957420,'2876 West Ashwood Ln',43290,'Overton2023','D6HJ1Y5ZP94J8170ZA9K25FD','1977-11-29',NULL,'cleaner'),(3487104332,2046473575,3401180,'1207 W Social Ct',54700,'Effie769','54E9H52','1995-11-13',NULL,'Receptionist'),(3487104333,2046473577,-6,'53 Quailwood Loop',47071,'Argentina111','24N17TH68353','1971-10-09',NULL,'cleaner'),(3916601060,3901389836,-5149210,'486 Highland Dr',81831,'Florentina2022','E','1977-07-11',NULL,'Receptionist'),(3916601061,132941301,9124010,'1698 White Hunting Hill Ct',58590,'Albertson9','6K','2005-05-20',NULL,'Receptionist'),(3916601062,3901389835,-9239820,'3008 W Hunting Hill Street',95279,'Almeida2000','KGA99LZ','1999-02-17',NULL,'Receptionist'),(3916601063,3901389837,10,'Ramallah',5500,'Mustafa','mustafa','2018-01-20',NULL,'Manager'),(3916601064,3901389839,10,'Ramallah',5500,'saber','saber','2018-12-18',NULL,'Cleaner'),(3916601066,3901389841,5,'Jerusalem',5500,'Hazem','hazem','2018-12-14',NULL,'RECEPTIONIST'),(3916601068,3901389867,0,'ramallah',2500,'nido','1','2018-12-26',NULL,'Cleaner'),(3916601069,3901389868,4,'Jeruasalem',25640,'EZZ','ezz','2018-12-17','2018-12-26','RECEPTIONIST');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `person` (
  `pid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `pname` varchar(45) NOT NULL,
  `DOB` date NOT NULL,
  `pphone` varchar(45) NOT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `index7` (`pname`)
) ENGINE=InnoDB AUTO_INCREMENT=3901389871 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (132941301,'Gutierrez73@example.com','Woodrow2009','1959-02-28','(350) 234-6559'),(132941302,'NathanielC_Christman823@example.com','Brinson972','1987-06-15','(789) 568-6157'),(132941303,'CierraCheatham@nowhere.com','Verona3','1982-06-07','(565) 967-6647'),(132941304,'Ted.Mace49@example.com','Cornell6','1975-02-22','(267) 504-9171'),(562438030,'Bray162@example.com','Kathern49','1957-01-21','(513) 643-0254'),(562438031,'uadm55@example.com','Christopher1976','1975-12-18','(122) 838-0637'),(562438032,'MathewDeal16@example.com','Alexander3','1974-06-02','(894) 989-7256'),(562438033,'SanfordRivas721@example.com','Brigida1989','1978-03-28','(130) 566-1060'),(1114804962,'Bolt@nowhere.com','Letha241','1949-03-08','(459) 613-9467'),(1114804963,'Caldwell@example.com','Stephan1976','1948-01-05','(529) 468-6964'),(1114804964,'SmalleyS@nowhere.com','Carvalho72','1950-07-30','(659) 832-5274'),(1114804965,'ikzv7@nowhere.com','Paris2022','1948-03-23','(674) 683-5376'),(1114804966,'pqfmmt48@example.com','Hubert2025','1950-04-10','(461) 654-2968'),(1114804967,'Ketchum@example.com','Jefferies7','1959-11-22','(720) 779-2685'),(1114804968,'Adolph.Antoine@example.com','Rivka533','1960-05-13','(307) 844-2142'),(1544301692,'FelipaAbernathy@nowhere.com','Chantel318','1948-03-04','(157) 934-3116'),(1544301693,'Rich.F_Hutchins@example.com','Doyle2','1994-04-26','(948) 826-7804'),(1544301694,'gxsgr25@example.com','Aguilera1993','1962-04-28','(899) 567-0654'),(1544301695,'AmosBliss189@example.com','Jerrod1982','1976-02-22','(835) 754-7630'),(1544301696,'pcvmbj959@nowhere.com','Carlson9','1952-10-29','(250) 604-2488'),(1544301697,'Tanner_Banuelos@example.com','Lanette688','1965-03-20','(748) 936-9702'),(2046473575,'qpzre411@example.com','Quintin5','1985-02-08','(681) 747-0352'),(2046473576,'Adler@example.com','Aisha2010','1986-11-12','(465) 454-8347'),(2046473577,'sdyfq9887@nowhere.com','Abreu232','1991-01-26','(878) 889-3248'),(2046473578,'Russell@nowhere.com','Alcala1977','1963-10-18','(365) 356-4078'),(2046473579,'LakeeshaAbel8@example.com','Muncy1','1985-12-11','(113) 450-2756'),(2046473580,'Alley91@example.com','Weston1954','1986-08-12','(153) 446-2344'),(2475970304,'Morehead@example.com','Mollie1975','1967-09-20','(730) 955-6764'),(2475970305,'xmhz1900@nowhere.com','Shenita2027','1957-02-22','(943) 664-5705'),(2475970306,'AdalineW.Radford8@example.com','Booker6','1968-10-12','(718) 229-1171'),(2475970307,'Newsome21@example.com','Shank212','1996-06-25','(792) 850-9043'),(2475970308,'OdellG752@example.com','Paris24','1948-04-07','(463) 980-1962'),(2475970309,'MalcomKnutson757@nowhere.com','Crystle2000','1993-04-03','(346) 279-2551'),(2654862618,'Valdez@example.com','Carpenter1994','1948-01-01','(507) 184-2162'),(2654862619,'Hoover@example.com','Elise28','1948-11-08','(898) 388-9426'),(2654862620,'Scarborough@example.com','Jestine2015','1997-01-07','(249) 938-5859'),(2654862621,'LucillaAbreu267@example.com','Lorretta2028','1952-03-18','(896) 756-6541'),(2654862622,'Nickie_B_Ambrose@nowhere.com','Abdul134','1951-11-25','(871) 703-6933'),(2654862623,'utxpbh4739@example.com','Ernesto599','1949-01-31','(542) 738-1744'),(3084359347,'Monroe.Rinehart@nowhere.com','Arnita2004','1987-04-29','(282) 510-9326'),(3084359348,'phkfhcmu_jjjdjuvrms@example.com','Johnston1952','1951-11-21','(286) 001-2185'),(3084359350,'LaveraLRoberge63@nowhere.com','Seymour1989','1957-11-26','(134) 228-5449'),(3084359351,'Sturgill@example.com','Andre58','1950-03-20','(948) 551-7855'),(3084359352,'Tenisha_Mcclintock@nowhere.com','Alberto1984','1963-09-08','(303) 737-0588'),(3084359353,'BalderasG5@example.com','Opal1955','1967-05-01','(705) 905-9161'),(3471893105,'Sidney_Agnew3@nowhere.com','Felisa221','1984-02-14','(776) 960-1978'),(3471893106,'OlgaFerry@example.com','Lindsey295','1948-03-28','(296) 043-6622'),(3901389835,'jajk3595@example.com','Suarez82','1980-03-13','(397) 639-0832'),(3901389836,'Rudolph358@example.com','Christian1985','1948-05-21','(852) 645-5229'),(3901389837,'mustafaadwi@gmail.com','Mustafa','1998-04-20','0598894790'),(3901389838,'Saber@Gmail.com','Saber','1998-04-10','059878415'),(3901389839,'saber','Saber','2018-12-06','0598894715'),(3901389841,'hazem@royal.com','Hazem','2018-11-01','0564789140'),(3901389860,'husaj','Husam','2018-12-01','0147'),(3901389861,'sufean@yahoo.com','Sufean','2017-01-09','05671450123'),(3901389862,'Zuhair@Gmail.com','Zuhair','2018-12-01','068714921254'),(3901389863,'anasy@gmail.com','Anas','1998-04-20','0568741268'),(3901389864,'samasonati@gmail.com','Sama','1997-01-02','0569874159'),(3901389867,'nido','Nidal','2018-12-05','0581452326'),(3901389868,'ezz','Ezz','1998-10-19','0258741369'),(3901389869,'belal@Gmail.com','Belal','1998-04-20','0568147852'),(3901389870,'anas@gmail.com','Anas','2018-12-04','0596321654');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receptionist`
--

DROP TABLE IF EXISTS `receptionist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `receptionist` (
  `eid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pid` int(10) unsigned NOT NULL,
  `noOfClients` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`eid`),
  KEY `FK_receptionist_1_idx` (`pid`),
  CONSTRAINT `FK_receptionist_1` FOREIGN KEY (`pid`) REFERENCES `person` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_receptionist_2` FOREIGN KEY (`eid`) REFERENCES `employee` (`eid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3916601062 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receptionist`
--

LOCK TABLES `receptionist` WRITE;
/*!40000 ALTER TABLE `receptionist` DISABLE KEYS */;
INSERT INTO `receptionist` VALUES (203719919,2654862619,1770952274),(203719920,2475970309,1531646711),(633216648,2475970304,2097300945),(633216649,2475970304,1319003892),(936942689,1544301693,732121471),(1366439418,2654862622,4350341),(1366439419,2654862621,1339733510),(2023443447,1114804962,814716132),(2023443448,2475970305,2),(2452940177,1114804968,855052294),(2452940178,2654862619,495926294),(2452940179,2046473580,NULL),(3487104332,3901389835,1795055902),(3916601061,3471893105,407954175);
/*!40000 ALTER TABLE `receptionist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reservation` (
  `bNum` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(10) unsigned NOT NULL,
  `eid` int(10) unsigned NOT NULL,
  `rNum` varchar(45) NOT NULL,
  `fromDate` date NOT NULL,
  `toDate` date NOT NULL,
  PRIMARY KEY (`bNum`,`cid`,`eid`,`rNum`,`fromDate`,`toDate`),
  KEY `FK_reservation_2_idx` (`eid`),
  KEY `FK_reservation_3_idx` (`rNum`),
  KEY `FK_reservation_1_idx` (`cid`) /*!80000 INVISIBLE */,
  CONSTRAINT `FK_reservation_1` FOREIGN KEY (`cid`) REFERENCES `client` (`cid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_reservation_2` FOREIGN KEY (`eid`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_reservation_3` FOREIGN KEY (`rNum`) REFERENCES `room` (`rnum`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (5,4128814243,3916601066,'39216','2018-12-01','2018-12-03'),(6,4128814244,3916601066,'54213','2018-12-01','2018-12-12');
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `room` (
  `rid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `noOfBeds` int(10) unsigned NOT NULL,
  `cost` float NOT NULL,
  `size` float NOT NULL,
  `floor` int(10) unsigned NOT NULL,
  `class` float NOT NULL,
  `status` varchar(45) NOT NULL,
  `rNum` varchar(45) NOT NULL,
  PRIMARY KEY (`rid`),
  UNIQUE KEY `roomcol_UNIQUE` (`rNum`)
) ENGINE=InnoDB AUTO_INCREMENT=3941953107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (178518705,93,9312,8523890,1149769749,-9359240,'Available','37389'),(178518706,1264219263,-8498690,87,8,3370840,'Reserved','39216'),(178518707,71,710,-231,2315,-9150060,'Available','95436'),(178518708,1227808073,8497220,33,3,-261,'Available','17621'),(178518709,2055279439,-8994120,4055380,378182452,-4286970,'Reserved','54213'),(178518710,7517,-751,-1730800,2045072986,-3711820,'Available','02453'),(608015434,1149624535,-1337950,310288,353149613,67,'Available','73850'),(608015435,241232202,8737770,-9929770,382060062,2206580,'Available','12429'),(608015436,77,7742,0,0,-298,'Available','01724'),(608015437,82958199,-3136180,-1144940,419258204,-4483170,'Available','45824'),(608015438,365,-36,-3449400,1309350631,-4881950,'Available','62192'),(608015439,2088354055,-5443290,6532,65,7,'Available','55446'),(608015440,3489,-3489,9,9,-7178730,'Available','16585'),(1070002294,46,460,301352,222432772,-8001960,'Available','58499'),(1070002295,1367365774,3625300,-8177,8177,9,'Available','73314'),(1070002296,264423885,8470500,-68,685,-1925250,'Available','01055'),(1070002297,1235886308,7396410,-3838630,1820597088,-1472220,'Available','40254'),(1070002298,8981,-898,-6970800,507427119,-4075470,'Available','18856'),(1499499024,38,3851,79,7,8581540,'Available','97909'),(1499499025,1752189182,-5862760,-1922810,1026883243,8693050,'Available','55322'),(1499499026,128608165,-1890930,7,7,4926260,'Available','02139'),(1499499027,8,8,718711,1650465152,-6773640,'Available','79583'),(2128225477,53,5348,-98,986,5627720,'Available','54140'),(2128225478,1578907936,8138140,2850720,122604857,-3065230,'Available','06407'),(2128225479,314067309,-202727,4634820,1257119800,-6348000,'Available','89326'),(2128225480,10,0,-3636030,551579725,5019190,'Available','18846'),(2557722206,1892557431,-1345080,7696090,714632027,2512830,'Available','55294'),(2557722207,464587320,-2950640,-269,2693,1122530,'Available','38179'),(2557722208,2005,-2005,-371,3710,9529090,'Available','54750'),(2557722209,7495,-749,-8704700,2072790562,-5473190,'Available','37641'),(2695667804,1564176311,6227360,7498310,769670604,-5723320,'Available','12143'),(2695667805,190387362,8360390,-560,5604,61,'Available','45708'),(2695667806,75,-7,-9502140,1983129840,-1942350,'Available','97235'),(2695667807,1368011278,4646290,-5938810,2093781791,-3329,'Available','25422'),(2695667808,55,-5,6579080,1058203574,2476430,'Available','66989'),(2695667809,562,-5,-1726620,406763261,-2509390,'Available','55679'),(3125164533,8,87,1452690,990299969,-8,'Available','46167'),(3125164534,1310418252,-1210730,8417390,659860229,2023240,'Available','58206'),(3125164535,900762811,232597,-3530720,186757440,1843640,'Available','76862'),(3125164536,709630961,-4508150,751,75,-4975960,'Available','84454'),(3125164537,158122371,4080550,-9103930,1095295389,2086580,'Available','99148'),(3125164538,6,69,-8657930,420971648,910,'Available','93730'),(3512456371,319,-31,3527480,1117066691,-8140820,'Available','32386'),(3512456372,1428400629,7932840,-3009060,58076601,2062500,'Available','16475'),(3512456373,908080534,-620270,-1294310,1340234239,6882860,'Available','20869'),(3512456374,909440308,198409,-5455620,1612757186,958338,'Available','68466'),(3941953101,9,9,1734550,1648089064,3532140,'Available','38499'),(3941953102,5701,-5701,438517,1812275763,-9677390,'Available','98537'),(3941953103,801835169,3677900,1931720,1472719300,-7858900,'Available','09457'),(3941953104,343274739,145781,1,0,-1905140,'Available','49235'),(3941953105,4,5500,10,1,1,'Available','1A');
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-19 18:20:31
