-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.17-log - MySQL Community Server (GPL)
-- 服务器OS:                        Win64
-- HeidiSQL 版本:                  10.1.0.5541
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for nb_pro
CREATE DATABASE IF NOT EXISTS `nb_pro` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `nb_pro`;

-- Dumping structure for table nb_pro.account
CREATE TABLE IF NOT EXISTS `account` (
  `id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `uid` char(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `create_time` bigint(20) unsigned DEFAULT NULL,
  `update_time` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table nb_pro.account: ~0 rows (大约)
DELETE FROM `account`;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;

-- Dumping structure for table nb_pro.balance_history
CREATE TABLE IF NOT EXISTS `balance_history` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) unsigned NOT NULL DEFAULT '0',
  `account_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `asset` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `business` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `change` decimal(30,8) NOT NULL,
  `balance` decimal(30,16) NOT NULL,
  `detail` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_asset` (`account_id`,`asset`),
  KEY `idx_user_asset_business` (`account_id`,`asset`,`business`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table nb_pro.balance_history: ~0 rows (大约)
DELETE FROM `balance_history`;
/*!40000 ALTER TABLE `balance_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `balance_history` ENABLE KEYS */;

-- Dumping structure for table nb_pro.deal_history
CREATE TABLE IF NOT EXISTS `deal_history` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) unsigned NOT NULL DEFAULT '0',
  `account_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `market` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `deal_id` bigint(20) unsigned NOT NULL,
  `order_id` bigint(20) unsigned NOT NULL,
  `deal_order_id` bigint(20) unsigned NOT NULL,
  `side` tinyint(3) unsigned NOT NULL,
  `role` tinyint(3) unsigned NOT NULL,
  `price` decimal(30,8) NOT NULL,
  `amount` decimal(30,8) NOT NULL,
  `deal` decimal(30,16) NOT NULL,
  `fee` decimal(30,16) NOT NULL,
  `deal_fee` decimal(30,16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_user_market` (`account_id`,`market`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table nb_pro.deal_history: ~0 rows (大约)
DELETE FROM `deal_history`;
/*!40000 ALTER TABLE `deal_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `deal_history` ENABLE KEYS */;

-- Dumping structure for table nb_pro.order_history
CREATE TABLE IF NOT EXISTS `order_history` (
  `id` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL DEFAULT '0',
  `finish_time` bigint(20) unsigned NOT NULL DEFAULT '0',
  `account_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `market` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `source` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `t` tinyint(3) unsigned NOT NULL,
  `side` tinyint(3) unsigned NOT NULL,
  `price` decimal(30,8) NOT NULL,
  `amount` decimal(30,8) NOT NULL,
  `taker_fee` decimal(30,4) NOT NULL,
  `maker_fee` decimal(30,4) NOT NULL,
  `deal_stock` decimal(30,8) NOT NULL,
  `deal_money` decimal(30,16) NOT NULL,
  `deal_fee` decimal(30,16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_market` (`account_id`,`market`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table nb_pro.order_history: ~0 rows (大约)
DELETE FROM `order_history`;
/*!40000 ALTER TABLE `order_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_history` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;