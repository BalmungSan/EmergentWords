-- phpMyAdmin SQL Dump
-- version 4.4.9
-- http://www.phpmyadmin.net

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET NAMES utf8 */;

--
-- Database: `WordsFinder`
--

-- --------------------------------------------------------

--
-- Table structure for table `words`
-- Remark: The default value for every column is 'FALSE' 
--         to exclude that row when making a consult
--

CREATE TABLE IF NOT EXISTS `words` (
  `Word` varchar(50) NOT NULL COMMENT 'Words save by the user',
  `Excluded` varchar(5) NOT NULL DEFAULT 'FALSE' COMMENT 'Determine if the word is excluded or not',
  `Find` varchar(5) NOT NULL DEFAULT 'FALSE' COMMENT 'Determine if the word will be searched or not',
  `Label` varchar(50)NOT NULL DEFAULT 'FALSE' COMMENT 'Determine the label of the word'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='list of words used by wordsfinder';

-- Make word as the primary key of the table
ALTER TABLE `words`
  ADD PRIMARY KEY (`Word`);
