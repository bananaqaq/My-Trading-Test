-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.15 - MySQL Community Server - GPL
-- 服务器OS:                        Win64
-- HeidiSQL 版本:                  10.1.0.5518
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for new_bbb
DROP DATABASE IF EXISTS `new_bbb`;
CREATE DATABASE IF NOT EXISTS `new_bbb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `new_bbb`;

-- Dumping structure for table new_bbb.fb_account
DROP TABLE IF EXISTS `fb_account`;
CREATE TABLE IF NOT EXISTS `fb_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` char(32) NOT NULL DEFAULT '',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table new_bbb.fb_account_asset
DROP TABLE IF EXISTS `fb_account_asset`;
CREATE TABLE IF NOT EXISTS `fb_account_asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_uid` int(32) NOT NULL DEFAULT '0',
  `coin_id` int(11) NOT NULL DEFAULT '0',
  `amt` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `forzen_amt` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `create_time` bigint(20) NOT NULL DEFAULT '0',
  `update_time` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table new_bbb.fb_buy_order
DROP TABLE IF EXISTS `fb_buy_order`;
CREATE TABLE IF NOT EXISTS `fb_buy_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(32) NOT NULL COMMENT '唯一标识',
  `account_uid` varchar(32) NOT NULL COMMENT '用户唯一标识',
  `price` decimal(20,8) NOT NULL COMMENT '价格',
  `volume` decimal(20,8) NOT NULL COMMENT '数量',
  `initial_volume` decimal(20,8) NOT NULL COMMENT '挂单数量',
  `status` smallint(6) NOT NULL COMMENT '订单状态： 1：未成交  2：部分成交  3：已完成  4：撤销  5：回滚',
  `create_time` bigint(20) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table new_bbb.fb_coin
DROP TABLE IF EXISTS `fb_coin`;
CREATE TABLE IF NOT EXISTS `fb_coin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `zh_name` varchar(50) NOT NULL,
  `en_name` varchar(50) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table new_bbb.fb_sell_order
DROP TABLE IF EXISTS `fb_sell_order`;
CREATE TABLE IF NOT EXISTS `fb_sell_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(32) NOT NULL COMMENT '唯一标识',
  `account_uid` varchar(32) NOT NULL COMMENT '用户唯一标识',
  `price` decimal(20,8) NOT NULL COMMENT '价格',
  `volume` decimal(20,8) NOT NULL COMMENT '数量',
  `initial_volume` decimal(20,8) NOT NULL COMMENT '挂单数量',
  `status` smallint(6) NOT NULL COMMENT '订单状态： 1：未成交  2：部分成交  3：已完成  4：撤销  5：回滚',
  `create_time` bigint(20) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table new_bbb.fb_tx_record
DROP TABLE IF EXISTS `fb_tx_record`;
CREATE TABLE IF NOT EXISTS `fb_tx_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` char(32) NOT NULL DEFAULT '0',
  `buyer_uid` char(32) NOT NULL DEFAULT '0',
  `seller_uid` char(32) NOT NULL DEFAULT '0',
  `deal_price` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `volume` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `tx_fee` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `create_time` bigint(20) NOT NULL DEFAULT '0',
  `update_time` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
