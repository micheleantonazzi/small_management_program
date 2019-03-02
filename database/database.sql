-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versione server:              10.3.9-MariaDB - mariadb.org binary distribution
-- S.O. server:                  Win64
-- HeidiSQL Versione:            9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;



CREATE DATABASE IF NOT EXISTS `test_db` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `test_db`;

-- Dump della struttura di tabella administrators
CREATE TABLE IF NOT EXISTS `administrators` (
  `id_administrator` smallint(6) NOT NULL AUTO_INCREMENT,
  `acronym` varchar(3) NOT NULL,
  `name` varchar(15) NOT NULL,
  `surname` varchar(15) NOT NULL,
  PRIMARY KEY (`id_administrator`),
  UNIQUE KEY `unique_acronym` (`acronym`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.
-- Dump della struttura di tabella billings
CREATE TABLE IF NOT EXISTS `billings` (
  `id_condo` smallint(6) NOT NULL,
  `year` year(4) NOT NULL,
  `total` float NOT NULL,
  `paid` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_condo`,`year`),
  CONSTRAINT `billings_ibfk_1` FOREIGN KEY (`id_condo`) REFERENCES `condos` (`id_condo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.
-- Dump della struttura di tabella billing_periods
CREATE TABLE IF NOT EXISTS `billing_periods` (
  `id_period` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id_period`),
  UNIQUE KEY `unique_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.
-- Dump della struttura di tabella billing_period_month
CREATE TABLE IF NOT EXISTS `billing_period_month` (
  `id_month` tinyint(4) NOT NULL,
  `id_period` tinyint(4) NOT NULL,
  PRIMARY KEY (`id_month`),
  KEY `foreign_key_period` (`id_period`),
  CONSTRAINT `billing_period_month_ibfk_1` FOREIGN KEY (`id_month`) REFERENCES `months` (`id_month`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `billing_period_month_ibfk_2` FOREIGN KEY (`id_period`) REFERENCES `billing_periods` (`id_period`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.
-- Dump della struttura di tabella bills
CREATE TABLE IF NOT EXISTS `bills` (
  `id_condo` smallint(6) NOT NULL,
  `year` year(4) NOT NULL,
  `month` tinyint(4) NOT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`id_condo`,`month`,`year`) USING BTREE,
  KEY `id_condo` (`id_condo`,`year`),
  CONSTRAINT `bills_ibfk_1` FOREIGN KEY (`id_condo`, `year`) REFERENCES `billings` (`id_condo`, `year`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.
-- Dump della struttura di tabella condos
CREATE TABLE IF NOT EXISTS `condos` (
  `id_condo` smallint(6) NOT NULL,
  `code` varchar(11) NOT NULL,
  `id_administrator` smallint(6) NOT NULL,
  `month` tinyint(4) NOT NULL,
  `name` varchar(40) NOT NULL,
  `province` varchar(2) NOT NULL,
  `city` varchar(30) NOT NULL,
  `address` varchar(50) NOT NULL,
  `cap` varchar(5) NOT NULL,
  `flats` smallint(6) NOT NULL,
  PRIMARY KEY (`id_condo`),
  KEY `foreign_key_administrator` (`id_administrator`) USING BTREE,
  KEY `id_month` (`month`),
  CONSTRAINT `condo_administrator` FOREIGN KEY (`id_administrator`) REFERENCES `administrators` (`id_administrator`),
  CONSTRAINT `condo_month` FOREIGN KEY (`month`) REFERENCES `months` (`id_month`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dump della struttura di tabella months
CREATE TABLE IF NOT EXISTS `months` (
  `id_month` tinyint(4) NOT NULL,
  PRIMARY KEY (`id_month`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- L’esportazione dei dati non era selezionata.
-- Dump della struttura di vista last_billings
-- Rimozione temporanea di tabella e creazione della struttura finale della vista
CREATE VIEW last_billings AS SELECT a.* FROM billings a JOIN (SELECT id_condo, MIN(year) year FROM billings WHERE paid = 0 GROUP BY id_condo) b ON a.id_condo = b.id_condo AND a.year = b.year;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
