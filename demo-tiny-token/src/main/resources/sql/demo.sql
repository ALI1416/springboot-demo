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

 Date: 11/08/2023 15:08:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint UNSIGNED NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `seq` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '顺序',
  `create_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id，外键：user.id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `create_id`(`create_id` ASC) USING BTREE,
  CONSTRAINT `role_ibfk_1` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (0, '全部权限', 0, 0);
INSERT INTO `role` VALUES (1, '用户管理', 1, 0);
INSERT INTO `role` VALUES (2, '角色路由管理', 2, 0);
INSERT INTO `role` VALUES (3, '无权限', 3, 0);
INSERT INTO `role` VALUES (4, '测试', 4, 1);
INSERT INTO `role` VALUES (5, '测试1', 5, 1);
INSERT INTO `role` VALUES (6, '测试2', 6, 1);
INSERT INTO `role` VALUES (7, '测试3', 7, 1);

-- ----------------------------
-- Table structure for role_route
-- ----------------------------
DROP TABLE IF EXISTS `role_route`;
CREATE TABLE `role_route`  (
  `id` bigint NOT NULL COMMENT 'id',
  `role_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色id，外键：role.id，role_id和route_id唯一',
  `route_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '路由id，外键：route.id，role_id和route_id唯一',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_id_route_id`(`role_id` ASC, `route_id` ASC) USING BTREE,
  INDEX `route_id`(`route_id` ASC) USING BTREE,
  CONSTRAINT `role_route_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_route_ibfk_2` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色-路由' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_route
-- ----------------------------
INSERT INTO `role_route` VALUES (0, 0, 0);
INSERT INTO `role_route` VALUES (1, 1, 20000);
INSERT INTO `role_route` VALUES (2, 2, 30000);
INSERT INTO `role_route` VALUES (3, 2, 40000);
INSERT INTO `role_route` VALUES (4, 2, 50000);
INSERT INTO `role_route` VALUES (5, 4, 60000);

-- ----------------------------
-- Table structure for route
-- ----------------------------
DROP TABLE IF EXISTS `route`;
CREATE TABLE `route`  (
  `id` bigint UNSIGNED NOT NULL COMMENT 'id',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路径',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `seq` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '顺序',
  `parent_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '父id，外键：route.id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parent_id`(`parent_id` ASC) USING BTREE,
  CONSTRAINT `route_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `route` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '路由' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of route
-- ----------------------------
INSERT INTO `route` VALUES (0, '/', '根路径', 0, 0);
INSERT INTO `route` VALUES (10000, 'user', '用户', 0, 0);
INSERT INTO `route` VALUES (10001, 'login', '登录', 0, 10000);
INSERT INTO `route` VALUES (10002, 'logout', '注销', 1, 10000);
INSERT INTO `route` VALUES (10003, 'register', '注册', 2, 10000);
INSERT INTO `route` VALUES (10004, 'changePwd', '修改密码', 3, 10000);
INSERT INTO `route` VALUES (10005, 'update', '修改个人信息(除密码)', 4, 10000);
INSERT INTO `route` VALUES (10006, 'findRoute', '获取路由', 5, 10000);
INSERT INTO `route` VALUES (10007, 'findRoleAndRoute', '获取角色和路由', 6, 10000);
INSERT INTO `route` VALUES (10008, 'avatar', '用户头像', 0, 10000);
INSERT INTO `route` VALUES (20000, 'userManage', '用户管理', 1, 0);
INSERT INTO `route` VALUES (20001, 'insert', '新增用户', 0, 20000);
INSERT INTO `route` VALUES (20002, 'update', '修改用户信息', 1, 20000);
INSERT INTO `route` VALUES (20003, 'findAll', '查询全部用户', 2, 20000);
INSERT INTO `route` VALUES (20004, 'updateRole', '修改用户的角色', 3, 20000);
INSERT INTO `route` VALUES (20005, 'refreshRole', '刷新角色，通过UserId', 4, 20000);
INSERT INTO `route` VALUES (30000, 'role', '角色', 2, 0);
INSERT INTO `route` VALUES (30001, 'insert', '新增', 0, 30000);
INSERT INTO `route` VALUES (30002, 'update', '更新', 1, 30000);
INSERT INTO `route` VALUES (30003, 'delete', '删除', 2, 30000);
INSERT INTO `route` VALUES (30004, 'updateRouteIdList', '修改路由', 3, 30000);
INSERT INTO `route` VALUES (30005, 'findAll', '查询所有', 4, 20000);
INSERT INTO `route` VALUES (30006, 'findByUserId', '查询，通过UserId', 5, 20000);
INSERT INTO `route` VALUES (30007, 'findByCreateId', '查询，通过CreateId', 6, 20000);
INSERT INTO `route` VALUES (30008, 'copy', '复制该节点', 7, 20000);
INSERT INTO `route` VALUES (30009, 'refreshRole', '刷新，通过RoleId', 8, 20000);
INSERT INTO `route` VALUES (40000, 'route', '路由', 3, 0);
INSERT INTO `route` VALUES (40001, 'insert', '新增', 0, 40000);
INSERT INTO `route` VALUES (40002, 'findList', '查询列表', 1, 40000);
INSERT INTO `route` VALUES (40003, 'findTree', '查询树', 2, 40000);
INSERT INTO `route` VALUES (40004, 'findExpandedList', '查询展开后的列表', 3, 40000);
INSERT INTO `route` VALUES (40005, 'delete', '删除', 4, 40000);
INSERT INTO `route` VALUES (40006, 'update', '更新', 5, 40000);
INSERT INTO `route` VALUES (40007, 'findByUserId', '查询，通过UserId', 6, 40000);
INSERT INTO `route` VALUES (40008, 'findIdByRoleId', '查询id，通过RoleId', 7, 40000);
INSERT INTO `route` VALUES (40009, 'move', '移动该节点', 8, 40000);
INSERT INTO `route` VALUES (40010, 'copy', '复制该节点', 9, 40000);
INSERT INTO `route` VALUES (40011, 'refresh', '刷新', 10, 40000);
INSERT INTO `route` VALUES (50000, 'routeNotIntercept', '路由不拦截', 4, 0);
INSERT INTO `route` VALUES (50001, 'insert', '新增', 0, 50000);
INSERT INTO `route` VALUES (50002, 'delete', '删除', 1, 50000);
INSERT INTO `route` VALUES (50003, 'update', '更新', 2, 50000);
INSERT INTO `route` VALUES (50004, 'findAll', '查询所有', 3, 50000);
INSERT INTO `route` VALUES (50005, 'refresh', '刷新', 4, 50000);
INSERT INTO `route` VALUES (60000, 'test', '测试', 5, 0);
INSERT INTO `route` VALUES (60001, 'test1', '测试1', 0, 60000);
INSERT INTO `route` VALUES (60002, 'test2', '测试2', 1, 60000);
INSERT INTO `route` VALUES (60003, 'test3', '测试3', 2, 60000);
INSERT INTO `route` VALUES (61000, 'test1', '测试1-1', 0, 60001);
INSERT INTO `route` VALUES (61001, 'test2', '测试1-2', 1, 60001);
INSERT INTO `route` VALUES (61100, 'test1', '测试1-1-1', 0, 61000);
INSERT INTO `route` VALUES (70000, 'avatar', '查看用户头像', 0, 0);

-- ----------------------------
-- Table structure for route_not_intercept
-- ----------------------------
DROP TABLE IF EXISTS `route_not_intercept`;
CREATE TABLE `route_not_intercept`  (
  `id` bigint UNSIGNED NOT NULL COMMENT 'id',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路径',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `is_match` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '匹配模式：1是0否',
  `need_login` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '需要登录：1是0否',
  `seq` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '路由-不拦截' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of route_not_intercept
-- ----------------------------
INSERT INTO `route_not_intercept` VALUES (0, '/user/login', '登录', 0, 0, 0);
INSERT INTO `route_not_intercept` VALUES (1, '/user/logout', '注销', 0, 0, 1);
INSERT INTO `route_not_intercept` VALUES (2, '/user/register', '注册', 0, 0, 2);
INSERT INTO `route_not_intercept` VALUES (3, '/user/changePwd', '修改密码', 0, 0, 3);
INSERT INTO `route_not_intercept` VALUES (4, '/user/update', '修改个人信息(除密码)', 0, 1, 4);
INSERT INTO `route_not_intercept` VALUES (5, '/user/findRoute', '获取路由', 0, 1, 5);
INSERT INTO `route_not_intercept` VALUES (6, '/user/findRoleAndRoute', '获取角色和路由', 0, 1, 6);
INSERT INTO `route_not_intercept` VALUES (7, '/user/avatar', '用户头像', 0, 1, 7);
INSERT INTO `route_not_intercept` VALUES (8, '/avatar', '查看用户头像', 1, 0, 8);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint UNSIGNED NOT NULL COMMENT 'id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '账号：唯一',
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `create_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id，外键：user.id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account`(`account` ASC) USING BTREE,
  INDEX `create_id`(`create_id` ASC) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (0, 'root', '$2a$10$hJ1YXxTgb3xk65vf4mMtBOaS32ZeZOWlhqjlxAtgLpdL4R/H/e/9q', 'ROOT', 0);
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$qQRy7tw0h51FUAvwu866B.GUpbn11BjTDtl.cNvA2wpIkR1Kt85OG', 'ADMIN', 0);
INSERT INTO `user` VALUES (2, 'admin2', '$2a$10$qQRy7tw0h51FUAvwu866B.GUpbn11BjTDtl.cNvA2wpIkR1Kt85OG', 'ADMIN2', 0);
INSERT INTO `user` VALUES (3, 'guest', '$2a$10$VnA1/sMYptvwQufzyArI5e3giuQoEHAGux9aRScMrJB3WwPqU2sxG', 'GUEST', 0);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint UNSIGNED NOT NULL COMMENT 'id',
  `user_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户id，外键：user.id，user_id和role_id唯一',
  `role_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色id，外键：role.id，user_id和role_id唯一',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id_role_id`(`user_id` ASC, `role_id` ASC) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (0, 0, 0);
INSERT INTO `user_role` VALUES (1, 1, 0);
INSERT INTO `user_role` VALUES (2, 2, 1);
INSERT INTO `user_role` VALUES (3, 2, 2);
INSERT INTO `user_role` VALUES (4, 2, 4);

SET FOREIGN_KEY_CHECKS = 1;
