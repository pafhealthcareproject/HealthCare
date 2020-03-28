-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: Mar 28, 2020 at 10:46 AM
-- Server version: 8.0.18
-- PHP Version: 7.4.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `healthcare`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
CREATE TABLE IF NOT EXISTS `appointment` (
  `appointmentID` int(200) NOT NULL AUTO_INCREMENT,
  `userID` varchar(200) NOT NULL,
  `doctorID` varchar(200) NOT NULL,
  `appointmentDate` date NOT NULL,
  `appointmentTime` time(6) NOT NULL,
  PRIMARY KEY (`appointmentID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
CREATE TABLE IF NOT EXISTS `doctor` (
  `doctorID` int(200) NOT NULL AUTO_INCREMENT,
  `doctorName` varchar(200) NOT NULL,
  `specialization` varchar(200) NOT NULL,
  `doctorUsername` varchar(200) NOT NULL,
  `doctorPassword` varchar(200) NOT NULL,
  PRIMARY KEY (`doctorID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `hospital`
--

DROP TABLE IF EXISTS `hospital`;
CREATE TABLE IF NOT EXISTS `hospital` (
  `hospitalID` int(200) NOT NULL AUTO_INCREMENT,
  `hospitalName` varchar(200) NOT NULL,
  `hospitalAddress` varchar(200) NOT NULL,
  `hospitalUsername` varchar(200) NOT NULL,
  `hospitalPassword` varchar(200) NOT NULL,
  PRIMARY KEY (`hospitalID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `hospitalphone`
--

DROP TABLE IF EXISTS `hospitalphone`;
CREATE TABLE IF NOT EXISTS `hospitalphone` (
  `hospitalID` varchar(200) NOT NULL,
  `hospitalPhone` varchar(200) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `hospitalvisit`
--

DROP TABLE IF EXISTS `hospitalvisit`;
CREATE TABLE IF NOT EXISTS `hospitalvisit` (
  `hospitalID` varchar(200) NOT NULL,
  `doctorID` varchar(200) NOT NULL,
  `visitTime` time(6) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment` (
  `paymentID` int(200) NOT NULL AUTO_INCREMENT,
  `appointmentID` varchar(200) NOT NULL,
  `paymentAmount` varchar(200) NOT NULL,
  `paymentDate` date NOT NULL,
  `paymentTime` time(6) NOT NULL,
  `paymentType` varchar(200) NOT NULL,
  PRIMARY KEY (`paymentID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `userID` int(200) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(200) NOT NULL,
  `lastName` varchar(200) NOT NULL,
  `age` int(200) NOT NULL,
  `gender` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `username` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `userphone`
--

DROP TABLE IF EXISTS `userphone`;
CREATE TABLE IF NOT EXISTS `userphone` (
  `userID` varchar(200) NOT NULL,
  `userPhone` varchar(200) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
