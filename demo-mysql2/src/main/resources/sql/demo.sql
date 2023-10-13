/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 13/10/2023 10:37:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint UNSIGNED NOT NULL COMMENT 'id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '账号：唯一',
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `gender` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '性别：0女1男，默认0',
  `year` int UNSIGNED NOT NULL DEFAULT 2000 COMMENT '出生年：默认2000',
  `profile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '个人简介',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `is_delete` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '已删除',
  `create_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account`(`account` ASC) USING BTREE,
  INDEX `create_id`(`create_id` ASC) USING BTREE,
  INDEX `update_id`(`update_id` ASC) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`update_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (0, 'ROOT', 'ROOT', 'ROOT', 0, 2000, 'ROOT', 'ROOT', 0, 0, '2020-01-01 00:00:00', 0, '2020-01-01 00:00:00', 0);

-- ----------------------------
-- Table structure for user_bak
-- ----------------------------
DROP TABLE IF EXISTS `user_bak`;
CREATE TABLE `user_bak`  (
  `id` bigint UNSIGNED NOT NULL COMMENT 'id',
  `ref_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT 'user.id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '账号：唯一',
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `gender` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '性别：0女1男，默认0',
  `year` int UNSIGNED NOT NULL DEFAULT 2000 COMMENT '出生年：默认2000',
  `profile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '个人简介',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `is_delete` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '已删除',
  `create_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ref_id`(`ref_id` ASC) USING BTREE,
  CONSTRAINT `user_bak_ibfk_1` FOREIGN KEY (`ref_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户备份' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_bak
-- ----------------------------
INSERT INTO `user_bak` VALUES (0, 0, 'ROOT', 'ROOT', 'ROOT', 0, 2000, 'ROOT', 'ROOT', 0, 0, '2020-01-01 00:00:00', 0, '2020-01-01 00:00:00', 0);

SET FOREIGN_KEY_CHECKS = 1;
