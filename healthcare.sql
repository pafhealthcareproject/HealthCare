-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: Apr 11, 2020 at 11:13 AM
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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `adminID` int(200) NOT NULL AUTO_INCREMENT,
  `username` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  PRIMARY KEY (`adminID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`appointmentID`, `userID`, `doctorID`, `appointmentDate`, `appointmentTime`) VALUES
(1, '1', '2', '0000-00-00', '00:00:03.000000');

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
  `adminID` int(200) NOT NULL,
  PRIMARY KEY (`doctorID`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`doctorID`, `doctorName`, `specialization`, `doctorUsername`, `doctorPassword`, `adminID`) VALUES
(1, 'SAG', 'dsg', 'dsgv', 'fad', 1);

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
  `adminID` int(200) NOT NULL,
  `appointmentCharge` varchar(200) NOT NULL,
  PRIMARY KEY (`hospitalID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `hospital`
--

INSERT INTO `hospital` (`hospitalID`, `hospitalName`, `hospitalAddress`, `hospitalUsername`, `hospitalPassword`, `adminID`, `appointmentCharge`) VALUES
(1, 'Nawaloka', 'Nawaloka Hospital, Malabe', 'navalokamlb', 'malabe123', 0, ''),
(2, 'gh', 'thht', 'hth', 'thh', 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `hospitalphone`
--

DROP TABLE IF EXISTS `hospitalphone`;
CREATE TABLE IF NOT EXISTS `hospitalphone` (
  `hospitalID` int(200) NOT NULL AUTO_INCREMENT,
  `hospitalPhone` varchar(200) NOT NULL,
  PRIMARY KEY (`hospitalID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `hospitalphone`
--

INSERT INTO `hospitalphone` (`hospitalID`, `hospitalPhone`) VALUES
(1, '0111020452'),
(2, '5635');

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
  `address` varchar(200) NOT NULL,
  `username` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userID`, `firstName`, `lastName`, `age`, `gender`, `email`, `address`, `username`, `password`) VALUES
(1, 'pasan', 'perera', 18, 'Male', 'pasan@gmail.com', 'kurunegala Maspotha', 'pasan', 'Th333');

-- --------------------------------------------------------

--
-- Table structure for table `userphone`
--

DROP TABLE IF EXISTS `userphone`;
CREATE TABLE IF NOT EXISTS `userphone` (
  `userID` int(200) NOT NULL AUTO_INCREMENT,
  `userPhone` varchar(200) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `userphone`
--

INSERT INTO `userphone` (`userID`, `userPhone`) VALUES
(1, '0770060608');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
