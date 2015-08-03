SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

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
  `Word` varchar(50)  COLLATE utf8_bin NOT NULL COMMENT 'Words save by the user',
  `Excluded` varchar(5) NOT NULL DEFAULT 'FALSE' COMMENT 'Determine if the word is excluded or not',
  `Find` varchar(5) NOT NULL DEFAULT 'FALSE' COMMENT 'Determine if the word will be searched or not',
  `Labels` varchar(535)  COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Labels associated with this word'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='list of words used by wordsfinder';

-- Make word as the primary key of the table
ALTER TABLE `words`
  ADD PRIMARY KEY (`Word`);
