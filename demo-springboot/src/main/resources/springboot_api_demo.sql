/*
 Navicat Premium Data Transfer

 Source Server         : 404z.cn_3306
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : 404z.cn:3306
 Source Schema         : springboot_api_demo

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 24/01/2021 19:50:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '账号',
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `role_api_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'api角色id',
  `role_web_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'web角色id',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '已删除',
  `create_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int unsigned NOT NULL DEFAULT '0' COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `account` (`account`) USING BTREE,
  KEY `create_id` (`create_id`) USING BTREE,
  KEY `update_id` (`update_id`) USING BTREE,
  KEY `role_api_id` (`role_api_id`) USING BTREE,
  CONSTRAINT `admin_ibfk_create_id` FOREIGN KEY (`create_id`) REFERENCES `admin` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `admin_ibfk_role_api_id` FOREIGN KEY (`role_api_id`) REFERENCES `role_api` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `admin_ibfk_update_id` FOREIGN KEY (`update_id`) REFERENCES `admin` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='管理员表';
-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (0, '', '', '', '', 0, 0, 0, 0, '2021-01-01 00:00:00', 0, '2021-01-01 00:00:00', 0);
INSERT INTO `admin` VALUES (1, 'admin', '$2a$10$Jna0eHkLJ6X8XFDoOZq4QufchuF442kUzHVFTPS3gWvwWNTdSPrUu', 'admin', 'admin', 0, 0, 0, 0, '2021-01-01 00:00:00', 0, '2021-01-01 00:00:00', 0);

-- ----------------------------
-- Table structure for admin_bak
-- ----------------------------
DROP TABLE IF EXISTS `admin_bak`;
CREATE TABLE `admin_bak` (
  `id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'id',
  `ref_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '被备份的id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '账号',
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `role_api_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'api角色id',
  `role_web_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'web角色id',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '已删除',
  `create_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int unsigned NOT NULL DEFAULT '0' COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ref_id` (`ref_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='管理员备份表';
-- ----------------------------
-- Records of admin_bak
-- ----------------------------

-- ----------------------------
-- Table structure for admin_login_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_login_log`;
CREATE TABLE `admin_login_log` (
  `id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'id',
  `ref_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '被登录的id',
  `login_success` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '登录成功',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0.0.0.0' COMMENT 'IP地址',
  `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '浏览器标识User-Agent',
  `ip_country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'IP地址-国家',
  `ip_province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'IP地址-省份',
  `ip_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'IP地址-城市',
  `ua_os_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '浏览器标识-操作系统名',
  `ua_browser_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '浏览器标识-浏览器名',
  `ua_device_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '浏览器标识-设备类型名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ref_id` (`ref_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='管理员登录日志表';
-- ----------------------------
-- Records of admin_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for role_api
-- ----------------------------
DROP TABLE IF EXISTS `role_api`;
CREATE TABLE `role_api` (
  `id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '已删除',
  `create_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int unsigned NOT NULL DEFAULT '0' COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE,
  KEY `create_id` (`create_id`) USING BTREE,
  KEY `update_id` (`update_id`) USING BTREE,
  CONSTRAINT `role_api_ibfk_create_id` FOREIGN KEY (`create_id`) REFERENCES `admin` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_api_ibfk_update_id` FOREIGN KEY (`update_id`) REFERENCES `admin` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='api角色表';
-- ----------------------------
-- Records of role_api
-- ----------------------------
INSERT INTO `role_api` VALUES (0, '', 0, 0, '2021-01-01 00:00:00', 0, '2021-01-01 00:00:00', 0);

-- ----------------------------
-- Table structure for role_api_bak
-- ----------------------------
DROP TABLE IF EXISTS `role_api_bak`;
CREATE TABLE `role_api_bak` (
  `id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'id',
  `ref_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '被备份的id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '已删除',
  `create_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int unsigned NOT NULL DEFAULT '0' COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ref_id` (`ref_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='api角色备份表';
-- ----------------------------
-- Records of role_api_bak
-- ----------------------------

-- ----------------------------
-- Table structure for role_api_ref
-- ----------------------------
DROP TABLE IF EXISTS `role_api_ref`;
CREATE TABLE `role_api_ref` (
  `id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'id',
  `role_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '角色id',
  `tree_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '角色树id',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '已删除',
  `create_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int unsigned NOT NULL DEFAULT '0' COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `create_id` (`create_id`) USING BTREE,
  KEY `update_id` (`update_id`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE,
  KEY `tree_id` (`tree_id`) USING BTREE,
  CONSTRAINT `role_api_ref_ibfk_create_id` FOREIGN KEY (`create_id`) REFERENCES `admin` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_api_ref_ibfk_role_id` FOREIGN KEY (`role_id`) REFERENCES `role_api` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_api_ref_ibfk_tree_id` FOREIGN KEY (`tree_id`) REFERENCES `role_api_tree` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_api_ref_ibfk_update_id` FOREIGN KEY (`update_id`) REFERENCES `admin` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='api角色引用表';
-- ----------------------------
-- Records of role_api_ref
-- ----------------------------
INSERT INTO `role_api_ref` VALUES (0, 0, 0, 0, 0, '2021-01-01 00:00:00', 0, '2021-01-01 00:00:00', 0);

-- ----------------------------
-- Table structure for role_api_ref_bak
-- ----------------------------
DROP TABLE IF EXISTS `role_api_ref_bak`;
CREATE TABLE `role_api_ref_bak` (
  `id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'id',
  `ref_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '被备份的id',
  `role_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '角色id',
  `tree_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '角色树id',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '已删除',
  `create_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int unsigned NOT NULL DEFAULT '0' COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ref_id` (`ref_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='api角色引用备份表';
-- ----------------------------
-- Records of role_api_ref_bak
-- ----------------------------

-- ----------------------------
-- Table structure for role_api_tree
-- ----------------------------
DROP TABLE IF EXISTS `role_api_tree`;
CREATE TABLE `role_api_tree` (
  `id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'id',
  `parent_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '父id',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路径名',
  `describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '描述',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '已删除',
  `create_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int unsigned NOT NULL DEFAULT '0' COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `parent_id_path` (`parent_id`,`path`) USING BTREE,
  KEY `create_id` (`create_id`) USING BTREE,
  KEY `update_id` (`update_id`) USING BTREE,
  KEY `parent_id` (`parent_id`) USING BTREE,
  CONSTRAINT `role_api_tree_ibfk_create_id` FOREIGN KEY (`create_id`) REFERENCES `admin` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_api_tree_ibfk_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `role_api_tree` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_api_tree_ibfk_update_id` FOREIGN KEY (`update_id`) REFERENCES `admin` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='api角色树表';
-- ----------------------------
-- Records of role_api_tree
-- ----------------------------
INSERT INTO `role_api_tree` VALUES(0, 0, '*', '', 0, 0, '2021-01-01 00:00:00.000', 0, '2021-01-01 00:00:00.000', 0);
INSERT INTO `role_api_tree` VALUES(1, 0, '', '', 0, 0, '2021-01-01 00:00:00.000', 0, '2021-01-01 00:00:00.000', 0);
INSERT INTO `role_api_tree` VALUES(11, 1, 'a', '', 0, 0, '2021-01-28 14:32:56.000', 0, '2021-01-28 14:32:56.000', 0);
INSERT INTO `role_api_tree` VALUES(12, 1, 'b', '', 0, 0, '2021-01-28 14:33:11.000', 0, '2021-01-28 14:33:11.000', 0);
INSERT INTO `role_api_tree` VALUES(13, 1, 'c', '', 0, 0, '2021-01-28 14:33:21.000', 0, '2021-01-28 14:33:21.000', 0);
INSERT INTO `role_api_tree` VALUES(111, 11, 'aa', '', 0, 0, '2021-01-28 14:33:54.000', 0, '2021-01-28 14:33:54.000', 0);
INSERT INTO `role_api_tree` VALUES(112, 11, 'ab', '', 0, 0, '2021-01-28 14:34:13.000', 0, '2021-01-28 14:34:13.000', 0);
INSERT INTO `role_api_tree` VALUES(113, 11, 'ac', '', 0, 0, '2021-01-28 14:34:24.000', 0, '2021-01-28 14:34:24.000', 0);
INSERT INTO `role_api_tree` VALUES(121, 12, 'ba', '', 0, 0, '2021-01-28 14:35:12.000', 0, '2021-01-28 14:35:12.000', 0);
INSERT INTO `role_api_tree` VALUES(122, 12, 'bb', '', 0, 0, '2021-01-28 14:35:25.000', 0, '2021-01-28 14:35:25.000', 0);
INSERT INTO `role_api_tree` VALUES(131, 13, 'ca', '', 0, 0, '2021-01-28 14:35:39.000', 0, '2021-01-28 14:35:39.000', 0);
INSERT INTO `role_api_tree` VALUES(1111, 111, 'aaa', '', 0, 0, '2021-01-28 14:36:15.000', 0, '2021-01-28 14:36:15.000', 0);
INSERT INTO `role_api_tree` VALUES(1112, 111, 'aab', '', 0, 0, '2021-01-28 14:36:24.000', 0, '2021-01-28 14:36:24.000', 0);
INSERT INTO `role_api_tree` VALUES(1131, 113, 'aca', '', 0, 0, '2021-01-28 14:36:48.000', 0, '2021-01-28 14:36:48.000', 0);
INSERT INTO `role_api_tree` VALUES(1311, 131, 'caa', '', 0, 0, '2021-01-28 14:37:04.000', 0, '2021-01-28 14:37:04.000', 0);

-- ----------------------------
-- Table structure for role_api_tree_bak
-- ----------------------------
DROP TABLE IF EXISTS `role_api_tree_bak`;
CREATE TABLE `role_api_tree_bak` (
  `id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'id',
  `ref_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '被备份的id',
  `parent_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '父id',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路径名',
  `describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '描述',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '已删除',
  `create_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int unsigned NOT NULL DEFAULT '0' COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ref_id` (`ref_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='api角色树备份表';
-- ----------------------------
-- Records of role_api_tree_bak
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '账号',
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `gender` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '性别',
  `year` int unsigned NOT NULL DEFAULT '0' COMMENT '出生年',
  `profile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '个人简介',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '电子邮箱',
  `qq_openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'qqOpenid',
  `qq_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'qq昵称',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '已删除',
  `create_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int unsigned NOT NULL DEFAULT '0' COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `account` (`account`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE,
  UNIQUE KEY `qq_openid` (`qq_openid`) USING BTREE,
  KEY `create_id` (`create_id`) USING BTREE,
  KEY `update_id` (`update_id`) USING BTREE,
  CONSTRAINT `user_ibfk_create_id` FOREIGN KEY (`create_id`) REFERENCES `admin` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_ibfk_update_id` FOREIGN KEY (`update_id`) REFERENCES `admin` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户表';
-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (0, '', '', '', 0, 0, '', '', '', '', '', 0, 0, '2021-01-01 00:00:00', 0, '2021-01-01 00:00:00', 0);

-- ----------------------------
-- Table structure for user_bak
-- ----------------------------
DROP TABLE IF EXISTS `user_bak`;
CREATE TABLE `user_bak` (
  `id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'id',
  `ref_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '被备份的id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '账号',
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `gender` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '性别',
  `year` int unsigned NOT NULL DEFAULT '0' COMMENT '出生年',
  `profile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '个人简介',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '电子邮箱',
  `qq_openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'qqOpenid',
  `qq_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'qq昵称',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '已删除',
  `create_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '更新者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int unsigned NOT NULL DEFAULT '0' COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ref_id` (`ref_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户备份表';

-- ----------------------------
-- Records of user_bak
-- ----------------------------

-- ----------------------------
-- Table structure for user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `user_login_log`;
CREATE TABLE `user_login_log` (
  `id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'id',
  `ref_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '被登录的id',
  `login_type` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '登录类型：0、账号，1、邮箱，2、QQ',
  `login_success` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '登录成功',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0.0.0.0' COMMENT 'IP地址',
  `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '浏览器标识User-Agent',
  `ip_country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'IP地址-国家',
  `ip_province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'IP地址-省份',
  `ip_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'IP地址-城市',
  `ua_os_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '浏览器标识-操作系统名',
  `ua_browser_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '浏览器标识-浏览器名',
  `ua_device_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '浏览器标识-设备类型名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ref_id` (`ref_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户登录日志表';
-- ----------------------------
-- Records of user_login_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
