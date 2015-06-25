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
-- Remark: The default value for Excluded and Find colomns is 'FALSE' 
--         to exclude that row when making a consult
--         And for Labels is an empty string ('') to append the labels added by the user
--

CREATE TABLE IF NOT EXISTS `words` (
  `Word` varchar(50) NOT NULL COMMENT 'Words save by the user',
  `Excluded` varchar(5) NOT NULL DEFAULT 'FALSE' COMMENT 'Determine if the word is excluded or not',
  `Find` varchar(5) NOT NULL DEFAULT 'FALSE' COMMENT 'Determine if the word will be searched or not',
  `Labels` varchar(535) NOT NULL DEFAULT '' COMMENT 'Labels associated with this word'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='list of words used by wordsfinder';

-- Make word as the primary key of the table
ALTER TABLE `words`
  ADD PRIMARY KEY (`Word`);
