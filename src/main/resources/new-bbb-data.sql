-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.17-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.5.0.5284
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 nb 的数据库结构
CREATE DATABASE IF NOT EXISTS `nb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `nb`;

-- 导出  表 nb.fb_account 结构
CREATE TABLE IF NOT EXISTS `fb_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` char(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  nb.fb_account 的数据：~4 rows (大约)
DELETE FROM `fb_account`;
/*!40000 ALTER TABLE `fb_account` DISABLE KEYS */;
INSERT INTO `fb_account` (`id`, `uid`, `create_time`, `update_time`) VALUES
	(1, '1b855c98f2b341ae8c1b29728ffc64ab', 1555399210837, 1555399210837),
	(2, '4bf34b3546ed49389e7c732bfe23cb79', 1555399369542, 1555399369542);
/*!40000 ALTER TABLE `fb_account` ENABLE KEYS */;

-- 导出  表 nb.fb_account_asset 结构
CREATE TABLE IF NOT EXISTS `fb_account_asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_uid` char(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `coin_id` int(11) NOT NULL DEFAULT '0',
  `amt` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `forzen_amt` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `create_time` bigint(20) NOT NULL DEFAULT '0',
  `update_time` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  nb.fb_account_asset 的数据：~8 rows (大约)
DELETE FROM `fb_account_asset`;
/*!40000 ALTER TABLE `fb_account_asset` DISABLE KEYS */;
INSERT INTO `fb_account_asset` (`id`, `account_uid`, `coin_id`, `amt`, `forzen_amt`, `create_time`, `update_time`) VALUES
	(1, '1b855c98f2b341ae8c1b29728ffc64ab', 1, 1000.00000000, 0.00000000, 1555399210837, 1555399210837),
	(2, '1b855c98f2b341ae8c1b29728ffc64ab', 2, 1000.00000000, 0.00000000, 1555399210837, 1555399210837),
	(3, '4bf34b3546ed49389e7c732bfe23cb79', 1, 1000.00000000, 0.00000000, 1555399369542, 1555399369542),
	(4, '4bf34b3546ed49389e7c732bfe23cb79', 2, 1000.00000000, 0.00000000, 1555399369542, 1555399369542);
/*!40000 ALTER TABLE `fb_account_asset` ENABLE KEYS */;

-- 导出  表 nb.fb_buy_order 结构
CREATE TABLE IF NOT EXISTS `fb_buy_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '唯一标识',
  `tx_pair_id` int(11) NOT NULL COMMENT '交易对唯一标识',
  `account_uid` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户唯一标识',
  `price` decimal(20,8) NOT NULL COMMENT '价格',
  `volume` decimal(20,8) NOT NULL COMMENT '数量',
  `initial_volume` decimal(20,8) NOT NULL COMMENT '挂单数量',
  `status` smallint(6) NOT NULL COMMENT '订单状态： 1：未成交  2：部分成交  3：已完成  4：撤销  5：回滚',
  `create_time` bigint(20) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  nb.fb_buy_order 的数据：~0 rows (大约)
DELETE FROM `fb_buy_order`;
/*!40000 ALTER TABLE `fb_buy_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `fb_buy_order` ENABLE KEYS */;

-- 导出  表 nb.fb_coin 结构
CREATE TABLE IF NOT EXISTS `fb_coin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `zh_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `en_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_time` bigint(20) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  nb.fb_coin 的数据：~2 rows (大约)
DELETE FROM `fb_coin`;
/*!40000 ALTER TABLE `fb_coin` DISABLE KEYS */;
INSERT INTO `fb_coin` (`id`, `zh_name`, `en_name`, `create_time`, `update_time`) VALUES
	(1, '比特币', 'BTC', 1555307949719, 1555307949719),
	(2, '泰达币', 'USDT', 1555307949719, 1555307949719);
/*!40000 ALTER TABLE `fb_coin` ENABLE KEYS */;

-- 导出  表 nb.fb_sell_order 结构
CREATE TABLE IF NOT EXISTS `fb_sell_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '唯一标识',
  `tx_pair_id` int(11) NOT NULL,
  `account_uid` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户唯一标识',
  `price` decimal(20,8) NOT NULL COMMENT '价格',
  `volume` decimal(20,8) NOT NULL COMMENT '数量',
  `initial_volume` decimal(20,8) NOT NULL COMMENT '挂单数量',
  `status` smallint(6) NOT NULL COMMENT '订单状态： 1：未成交  2：部分成交  3：已完成  4：撤销  5：回滚',
  `create_time` bigint(20) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  nb.fb_sell_order 的数据：~0 rows (大约)
DELETE FROM `fb_sell_order`;
/*!40000 ALTER TABLE `fb_sell_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `fb_sell_order` ENABLE KEYS */;

-- 导出  表 nb.fb_tx_pair 结构
CREATE TABLE IF NOT EXISTS `fb_tx_pair` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pair_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '交易对名称，例：BTC-USDT',
  `f_coin_id` int(11) NOT NULL DEFAULT '0',
  `a_coin_id` int(11) NOT NULL DEFAULT '0',
  `create_time` bigint(20) NOT NULL DEFAULT '0',
  `update_time` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  nb.fb_tx_pair 的数据：~1 rows (大约)
DELETE FROM `fb_tx_pair`;
/*!40000 ALTER TABLE `fb_tx_pair` DISABLE KEYS */;
INSERT INTO `fb_tx_pair` (`id`, `pair_name`, `f_coin_id`, `a_coin_id`, `create_time`, `update_time`) VALUES
	(1, 'BTC-USDT', 1, 2, 1555569435892, 1555569435892);
/*!40000 ALTER TABLE `fb_tx_pair` ENABLE KEYS */;

-- 导出  表 nb.fb_tx_record 结构
CREATE TABLE IF NOT EXISTS `fb_tx_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` char(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `buyer_uid` char(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `seller_uid` char(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `deal_price` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `volume` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `tx_fee` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `create_time` bigint(20) NOT NULL DEFAULT '0',
  `update_time` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  nb.fb_tx_record 的数据：~0 rows (大约)
DELETE FROM `fb_tx_record`;
/*!40000 ALTER TABLE `fb_tx_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `fb_tx_record` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
