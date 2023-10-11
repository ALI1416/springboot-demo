/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : demo-mysql

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 06/04/2023 15:25:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for login_log_test
-- ----------------------------
DROP TABLE IF EXISTS `login_log_test`;
CREATE TABLE `login_log_test`  (
  `id` bigint UNSIGNED NOT NULL COMMENT 'id',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'IP地址',
  `user_agent` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '浏览器标识',
  `ip_country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'IP地址-国家',
  `ip_province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'IP地址-省份',
  `ip_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'IP地址-城市',
  `ip_isp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'IP地址-ISP',
  `ua_os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '浏览器标识-操作系统',
  `ua_browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '浏览器标识-浏览器',
  `ua_is_mobile` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '浏览器标识-是手机',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC COMMENT = '登录日志测试';

SET FOREIGN_KEY_CHECKS = 1;
