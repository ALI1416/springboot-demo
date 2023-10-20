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

 Date: 19/10/2023 18:06:31
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
  `create_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id：外键user.id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `create_id`(`create_id` ASC) USING BTREE,
  CONSTRAINT `role__create_id` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (0, '全部权限', 0, 0);
INSERT INTO `role` VALUES (1, '用户管理', 1, 0);
INSERT INTO `role` VALUES (2, '用户管理(限制)', 2, 0);
INSERT INTO `role` VALUES (3, '角色管理', 3, 0);
INSERT INTO `role` VALUES (4, '角色管理(限制)', 4, 0);
INSERT INTO `role` VALUES (5, '路由管理', 5, 0);

-- ----------------------------
-- Table structure for role_route
-- ----------------------------
DROP TABLE IF EXISTS `role_route`;
CREATE TABLE `role_route`  (
  `id` bigint NOT NULL COMMENT 'id',
  `role_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色id：外键role.id，唯一role_id和route_id',
  `route_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '路由id：外键route.id，唯一role_id和route_id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_id__route_id`(`role_id` ASC, `route_id` ASC) USING BTREE,
  INDEX `route_id`(`route_id` ASC) USING BTREE,
  CONSTRAINT `role_route__role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_route__route_id` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色-路由' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_route
-- ----------------------------
INSERT INTO `role_route` VALUES (0, 0, 0);
INSERT INTO `role_route` VALUES (1000, 1, 1000);
INSERT INTO `role_route` VALUES (2000, 2, 2001);
INSERT INTO `role_route` VALUES (2001, 2, 2003);
INSERT INTO `role_route` VALUES (2002, 2, 2005);
INSERT INTO `role_route` VALUES (2003, 2, 2006);
INSERT INTO `role_route` VALUES (2004, 2, 2008);
INSERT INTO `role_route` VALUES (2005, 2, 2010);
INSERT INTO `role_route` VALUES (2006, 2, 2012);
INSERT INTO `role_route` VALUES (3000, 3, 3000);
INSERT INTO `role_route` VALUES (4000, 4, 3001);
INSERT INTO `role_route` VALUES (4001, 4, 3002);
INSERT INTO `role_route` VALUES (4002, 4, 3004);
INSERT INTO `role_route` VALUES (4003, 4, 3006);
INSERT INTO `role_route` VALUES (4004, 4, 3009);
INSERT INTO `role_route` VALUES (4005, 4, 3011);
INSERT INTO `role_route` VALUES (4006, 4, 3013);
INSERT INTO `role_route` VALUES (5000, 5, 4000);
INSERT INTO `role_route` VALUES (5001, 5, 5000);

-- ----------------------------
-- Table structure for route
-- ----------------------------
DROP TABLE IF EXISTS `route`;
CREATE TABLE `route`  (
  `id` bigint UNSIGNED NOT NULL COMMENT 'id',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路径',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `seq` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '顺序',
  `parent_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '父id：外键route.id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parent_id`(`parent_id` ASC) USING BTREE,
  CONSTRAINT `route__parent_id` FOREIGN KEY (`parent_id`) REFERENCES `route` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '路由' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of route
-- ----------------------------
INSERT INTO `route` VALUES (0, '/', '首页', 0, 0);
INSERT INTO `route` VALUES (1, 'avatar', '查看用户头像', 1, 0);
INSERT INTO `route` VALUES (1000, 'user', '用户', 0, 0);
INSERT INTO `route` VALUES (1001, 'login', '用户登录', 0, 1000);
INSERT INTO `route` VALUES (1002, 'logout', '用户注销', 1, 1000);
INSERT INTO `route` VALUES (1003, 'register', '用户注册', 2, 1000);
INSERT INTO `route` VALUES (1004, 'updatePwd', '用户修改密码', 3, 1000);
INSERT INTO `route` VALUES (1005, 'update', '用户修改信息(除密码、删除)', 4, 1000);
INSERT INTO `route` VALUES (1006, 'get', '获取用户信息', 5, 1000);
INSERT INTO `route` VALUES (1007, 'role', '获取用户角色', 6, 1000);
INSERT INTO `route` VALUES (1008, 'route', '获取用户路由', 7, 1000);
INSERT INTO `route` VALUES (1009, 'avatar', '获取用户头像', 8, 1000);
INSERT INTO `route` VALUES (2000, 'userManage', '用户管理', 1, 0);
INSERT INTO `route` VALUES (2001, 'logoutToken', '注销用户token', 0, 2000);
INSERT INTO `route` VALUES (2002, 'logoutTokenNotCheck', '注销用户token(不校验)', 1, 2000);
INSERT INTO `route` VALUES (2003, 'logoutId', '注销用户id', 2, 2000);
INSERT INTO `route` VALUES (2004, 'logoutIdNotCheck', '注销用户id(不校验)', 3, 2000);
INSERT INTO `route` VALUES (2005, 'create', '创建用户', 4, 2000);
INSERT INTO `route` VALUES (2006, 'update', '修改用户信息', 5, 2000);
INSERT INTO `route` VALUES (2007, 'updateNotCheck', '修改用户信息(不校验)', 6, 2000);
INSERT INTO `route` VALUES (2008, 'updateRole', '修改用户角色', 7, 2000);
INSERT INTO `route` VALUES (2009, 'updateRoleNotCheck', '修改用户角色(不校验)', 8, 2000);
INSERT INTO `route` VALUES (2010, 'getByRoleId', '获取拥有指定角色的用户', 9, 2000);
INSERT INTO `route` VALUES (2011, 'getByRoleIdNotCheck', '获取拥有指定角色的用户(不校验)', 10, 2000);
INSERT INTO `route` VALUES (2012, 'get', '获取所有用户', 11, 2000);
INSERT INTO `route` VALUES (2013, 'getNotCheck', '获取所有用户(不校验)', 12, 2000);
INSERT INTO `route` VALUES (3000, 'role', '角色', 2, 0);
INSERT INTO `route` VALUES (3001, 'create', '创建角色', 0, 3000);
INSERT INTO `route` VALUES (3002, 'delete', '删除角色', 1, 3000);
INSERT INTO `route` VALUES (3003, 'deleteNotCheck', '删除角色(不校验)', 2, 3000);
INSERT INTO `route` VALUES (3004, 'update', '修改角色', 3, 3000);
INSERT INTO `route` VALUES (3005, 'updateNotCheck', '修改角色(不校验)', 4, 3000);
INSERT INTO `route` VALUES (3006, 'updateRoute', '修改角色的路由', 5, 3000);
INSERT INTO `route` VALUES (3007, 'updateRouteNotCheck', '修改角色的路由(不校验)', 6, 3000);
INSERT INTO `route` VALUES (3008, 'get', '获取所有角色', 7, 3000);
INSERT INTO `route` VALUES (3009, 'user', '获取用户的角色', 8, 3000);
INSERT INTO `route` VALUES (3010, 'userNotCheck', '获取用户的角色(不校验)', 9, 3000);
INSERT INTO `route` VALUES (3011, 'copy', '复制角色', 10, 3000);
INSERT INTO `route` VALUES (3012, 'copyNotCheck', '复制角色(不校验)', 11, 3000);
INSERT INTO `route` VALUES (3013, 'refreshCache', '刷新角色缓存', 12, 3000);
INSERT INTO `route` VALUES (3014, 'refreshCacheNotCheck', '刷新角色缓存(不校验)', 13, 3000);
INSERT INTO `route` VALUES (4000, 'route', '路由', 3, 0);
INSERT INTO `route` VALUES (4001, 'create', '创建路由', 0, 4000);
INSERT INTO `route` VALUES (4002, 'update', '修改路由', 1, 4000);
INSERT INTO `route` VALUES (4003, 'delete', '删除路由', 2, 4000);
INSERT INTO `route` VALUES (4004, 'copy', '复制到父路由下', 3, 4000);
INSERT INTO `route` VALUES (4005, 'role', '获取角色路由', 4, 4000);
INSERT INTO `route` VALUES (4006, 'user', '获取用户路由', 5, 4000);
INSERT INTO `route` VALUES (4007, 'get', '获取路由列表', 6, 4000);
INSERT INTO `route` VALUES (4008, 'tree', '获取路由树', 7, 4000);
INSERT INTO `route` VALUES (4009, 'list', '获取展开后的路由列表', 8, 4000);
INSERT INTO `route` VALUES (4010, 'refreshCache', '刷新路由缓存', 9, 4000);
INSERT INTO `route` VALUES (4011, 'refreshRouteCache', '刷新用户的路由缓存', 10, 4000);
INSERT INTO `route` VALUES (5000, 'routeNotIntercept', '路由不拦截', 4, 0);
INSERT INTO `route` VALUES (5001, 'create', '创建路由不拦截', 0, 5000);
INSERT INTO `route` VALUES (5002, 'delete', '删除路由不拦截', 1, 5000);
INSERT INTO `route` VALUES (5003, 'update', '修改路由不拦截', 2, 5000);
INSERT INTO `route` VALUES (5004, 'get', '获取路由不拦截', 3, 5000);
INSERT INTO `route` VALUES (5005, 'list', '获取缓存路由不拦截', 4, 5000);
INSERT INTO `route` VALUES (5006, 'refreshCache', '刷新缓存路由不拦截', 5, 5000);

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
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT `route_not_intercept__is_match` CHECK ((`is_match` = 0) or (`is_match` = 1)),
  CONSTRAINT `route_not_intercept__need_login` CHECK ((`need_login` = 0) or (`need_login` = 1))
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '路由-不拦截' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of route_not_intercept
-- ----------------------------
INSERT INTO `route_not_intercept` VALUES (0, '/', '首页', 0, 0, 0);
INSERT INTO `route_not_intercept` VALUES (1, '/avatar', '查看用户头像', 1, 0, 1);
INSERT INTO `route_not_intercept` VALUES (1000, '/user/login', '用户登录', 0, 0, 2);
INSERT INTO `route_not_intercept` VALUES (1001, '/user/logout', '用户注销', 0, 1, 3);
INSERT INTO `route_not_intercept` VALUES (1002, '/user/register', '用户注册', 0, 0, 4);
INSERT INTO `route_not_intercept` VALUES (1003, '/user/updatePwd', '用户修改密码', 0, 1, 5);
INSERT INTO `route_not_intercept` VALUES (1004, '/user/update', '用户修改信息(除密码、删除)', 0, 1, 6);
INSERT INTO `route_not_intercept` VALUES (1005, '/user/get', '获取用户信息', 0, 1, 7);
INSERT INTO `route_not_intercept` VALUES (1006, '/user/role', '获取用户角色', 0, 1, 8);
INSERT INTO `route_not_intercept` VALUES (1007, '/user/route', '获取用户路由', 0, 1, 9);
INSERT INTO `route_not_intercept` VALUES (1008, '/user/avatar', '获取用户头像', 0, 1, 10);
INSERT INTO `route_not_intercept` VALUES (2000, '/routeNotIntercept/list', '获取缓存路由不拦截', 0, 0, 11);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint UNSIGNED NOT NULL COMMENT 'id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '账号：唯一',
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `is_delete` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '已删除',
  `create_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id：外键user.id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account`(`account` ASC) USING BTREE,
  INDEX `create_id`(`create_id` ASC) USING BTREE,
  CONSTRAINT `user__create_id` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user__is_delete` CHECK ((`is_delete` = 0) or (`is_delete` = 1))
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (0, 'root', '$2a$10$hJ1YXxTgb3xk65vf4mMtBOaS32ZeZOWlhqjlxAtgLpdL4R/H/e/9q', 'ROOT', 0, 0, '2020-01-01 00:00:00');
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$qQRy7tw0h51FUAvwu866B.GUpbn11BjTDtl.cNvA2wpIkR1Kt85OG', 'ADMIN', 0, 0, '2020-01-01 00:00:00');
INSERT INTO `user` VALUES (2, 'admin2', '$2a$10$qQRy7tw0h51FUAvwu866B.GUpbn11BjTDtl.cNvA2wpIkR1Kt85OG', 'ADMIN2', 0, 1, '2020-01-01 00:00:00');
INSERT INTO `user` VALUES (3, 'admin3', '$2a$10$qQRy7tw0h51FUAvwu866B.GUpbn11BjTDtl.cNvA2wpIkR1Kt85OG', 'ADMIN3', 0, 1, '2020-01-01 00:00:00');
INSERT INTO `user` VALUES (4, 'guest', '$2a$10$VnA1/sMYptvwQufzyArI5e3giuQoEHAGux9aRScMrJB3WwPqU2sxG', 'GUEST', 0, 1, '2020-01-01 00:00:00');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint UNSIGNED NOT NULL COMMENT 'id',
  `user_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户id：外键user.id，唯一user_id和role_id',
  `role_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色id：外键role.id，唯一user_id和role_id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id__role_id`(`user_id` ASC, `role_id` ASC) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE,
  CONSTRAINT `user_role__role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_role__user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (0, 0, 0);
INSERT INTO `user_role` VALUES (1000, 1, 0);
INSERT INTO `user_role` VALUES (2000, 2, 1);
INSERT INTO `user_role` VALUES (2001, 2, 3);
INSERT INTO `user_role` VALUES (2002, 2, 5);
INSERT INTO `user_role` VALUES (3000, 3, 2);
INSERT INTO `user_role` VALUES (3001, 3, 4);

SET FOREIGN_KEY_CHECKS = 1;
