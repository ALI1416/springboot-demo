/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.18.248_3306
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 192.168.18.248:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 15/12/2021 15:36:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(0) UNSIGNED NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `seq` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '顺序',
  `create_id` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `createId`(`create_id`) USING BTREE,
  CONSTRAINT `role_ibfk_1` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (0, 'root', 0, 0);
INSERT INTO `role` VALUES (1, 'admin', 1, 1);
INSERT INTO `role` VALUES (2, 'dba', 2, 0);
INSERT INTO `role` VALUES (3, 'user', 3, 0);
INSERT INTO `role` VALUES (4, 'guest', 4, 0);
INSERT INTO `role` VALUES (31369698527412224, 'test update', 9000, 0);

-- ----------------------------
-- Table structure for role_route
-- ----------------------------
DROP TABLE IF EXISTS `role_route`;
CREATE TABLE `role_route`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `role_id` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色id',
  `route_id` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '路由id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_id`(`role_id`, `route_id`) USING BTREE COMMENT '不能重复',
  INDEX `route_id`(`route_id`) USING BTREE,
  CONSTRAINT `role_route_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_route_ibfk_2` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色-路由表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_route
-- ----------------------------
INSERT INTO `role_route` VALUES (0, 0, 0);
INSERT INTO `role_route` VALUES (31462870439952384, 1, 10001);
INSERT INTO `role_route` VALUES (31462870439952385, 1, 20001);
INSERT INTO `role_route` VALUES (31462870439952386, 1, 20002);
INSERT INTO `role_route` VALUES (31462870439952387, 1, 20003);
INSERT INTO `role_route` VALUES (31462870439952388, 1, 30000);
INSERT INTO `role_route` VALUES (10, 2, 30000);
INSERT INTO `role_route` VALUES (11, 2, 40000);
INSERT INTO `role_route` VALUES (12, 3, 10002);
INSERT INTO `role_route` VALUES (13, 3, 10003);
INSERT INTO `role_route` VALUES (14, 3, 10004);
INSERT INTO `role_route` VALUES (15, 3, 10005);
INSERT INTO `role_route` VALUES (16, 4, 10003);
INSERT INTO `role_route` VALUES (17, 4, 10004);
INSERT INTO `role_route` VALUES (18, 4, 10005);
INSERT INTO `role_route` VALUES (31459425541685248, 31369698527412224, 10001);
INSERT INTO `role_route` VALUES (31459425541685249, 31369698527412224, 20001);
INSERT INTO `role_route` VALUES (31459425541685250, 31369698527412224, 20002);
INSERT INTO `role_route` VALUES (31459425541685251, 31369698527412224, 30000);

-- ----------------------------
-- Table structure for role_route2
-- ----------------------------
DROP TABLE IF EXISTS `role_route2`;
CREATE TABLE `role_route2`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `role_id` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色id',
  `route2_id` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '前端路由id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_id`(`role_id`, `route2_id`) USING BTREE COMMENT '不能重复',
  INDEX `route2_id`(`route2_id`) USING BTREE,
  CONSTRAINT `role_route2_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_route2_ibfk_2` FOREIGN KEY (`route2_id`) REFERENCES `route2` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色-前端路由表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_route2
-- ----------------------------
INSERT INTO `role_route2` VALUES (0, 0, 0);
INSERT INTO `role_route2` VALUES (31462870439952389, 1, 1);
INSERT INTO `role_route2` VALUES (31462870439952390, 1, 3);
INSERT INTO `role_route2` VALUES (31462870439952391, 1, 5);

-- ----------------------------
-- Table structure for route
-- ----------------------------
DROP TABLE IF EXISTS `route`;
CREATE TABLE `route`  (
  `id` bigint(0) UNSIGNED NOT NULL COMMENT 'id',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路径',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `seq` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '顺序',
  `parent_id` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parent_id`(`parent_id`) USING BTREE,
  CONSTRAINT `route_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `route` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '路由表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of route
-- ----------------------------
INSERT INTO `route` VALUES (0, '/', '根路径', 0, 0);
INSERT INTO `route` VALUES (10000, 'user', '用户', 0, 0);
INSERT INTO `route` VALUES (10001, 'register', '注册', 0, 10000);
INSERT INTO `route` VALUES (10002, 'changePwd', '修改密码', 1, 10000);
INSERT INTO `route` VALUES (10003, 'logout', '注销', 2, 10000);
INSERT INTO `route` VALUES (10004, 'findRole', '查询当前用户拥有的角色', 3, 10000);
INSERT INTO `route` VALUES (10005, 'findRoute', '查询当前用户拥有的路由', 4, 10000);
INSERT INTO `route` VALUES (20000, 'role', '角色', 1, 0);
INSERT INTO `route` VALUES (20001, 'insert', '插入', 0, 20000);
INSERT INTO `route` VALUES (20002, 'update', '更新', 1, 20000);
INSERT INTO `route` VALUES (20003, 'delete', '删除', 2, 20000);
INSERT INTO `route` VALUES (20004, 'addRouteIdList', '添加路由', 3, 20000);
INSERT INTO `route` VALUES (20005, 'findAll', '查询所有', 4, 20000);
INSERT INTO `route` VALUES (20006, 'findOwnByUserId', '查询UserId拥有的角色', 5, 20000);
INSERT INTO `route` VALUES (20007, 'findByCreateId', '查询所有通过CreateId', 6, 20000);
INSERT INTO `route` VALUES (20008, 'copy', '复制', 7, 20000);
INSERT INTO `route` VALUES (20009, 'refreshRole', '仅刷新修改的角色', 8, 20000);
INSERT INTO `route` VALUES (20010, 'refresh', '刷新', 9, 20000);
INSERT INTO `route` VALUES (30000, 'route', '路由', 2, 0);
INSERT INTO `route` VALUES (30001, 'insert', '插入', 0, 30000);
INSERT INTO `route` VALUES (30002, 'findList', '查询列表', 1, 30000);
INSERT INTO `route` VALUES (30003, 'findTree', '查询树', 2, 30000);
INSERT INTO `route` VALUES (30004, 'findExpandedList', '查询展开后的列表', 3, 30000);
INSERT INTO `route` VALUES (30005, 'delete', '删除', 4, 30000);
INSERT INTO `route` VALUES (30006, 'update', '更新', 5, 30000);
INSERT INTO `route` VALUES (30007, 'findOwnByUserId', '查询UserId拥有的路由', 6, 30000);
INSERT INTO `route` VALUES (30008, 'move', '移动该节点到其他节点下', 7, 30000);
INSERT INTO `route` VALUES (30009, 'copy', '复制该节点', 8, 30000);
INSERT INTO `route` VALUES (30010, 'refresh', '刷新', 9, 30000);
INSERT INTO `route` VALUES (40000, 'routeNotIntercept', '路由不拦截', 3, 0);
INSERT INTO `route` VALUES (40001, 'findAll', '查询所有', 0, 40000);
INSERT INTO `route` VALUES (40002, 'insert', '插入', 1, 40000);
INSERT INTO `route` VALUES (40003, 'delete', '删除', 2, 40000);

-- ----------------------------
-- Table structure for route2
-- ----------------------------
DROP TABLE IF EXISTS `route2`;
CREATE TABLE `route2`  (
  `id` bigint(0) UNSIGNED NOT NULL COMMENT 'id',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路径',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `seq` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '顺序',
  `parent_id` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父id',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '组件名称或路径',
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '重定向',
  `meta` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '自定义内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parent_id`(`parent_id`) USING BTREE,
  CONSTRAINT `route2_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `route2` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '前端路由表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of route2
-- ----------------------------
INSERT INTO `route2` VALUES (0, '/', '根路径', 0, 0, '', '', '');
INSERT INTO `route2` VALUES (1, '/', '首页', 0, 0, 'Layout', '/index', '{\"isVueComponent\":true}');
INSERT INTO `route2` VALUES (2, 'index', 'Index', 1, 1, 'views/index', '', '{\"title\": \"首页\",\"icon\": \"home\",\"affix\":true,\"isVueComponent\":true}');
INSERT INTO `route2` VALUES (3, '/pcs', 'PCS监视', 0, 0, 'Layout', '/index', '{\"isVueComponent\":true}');
INSERT INTO `route2` VALUES (4, 'index', 'PCS', 1, 3, 'views/pcs', '', '{\"title\": \"PCS监视\",\"icon\": \"binoculars\",\"isVueComponent\":true}');
INSERT INTO `route2` VALUES (5, '/bms', 'BMS监视', 0, 0, 'Layout', '/index', '{\"isVueComponent\":true}');
INSERT INTO `route2` VALUES (6, 'index', 'BMS', 1, 5, 'views/bms', '', '{\"title\": \"bms监视\",\"icon\": \"desktop\",\"isVueComponent\":true}');
INSERT INTO `route2` VALUES (7, '/warning', '告警信息', 0, 0, 'Layout', '/index', '{\"isVueComponent\":true}');
INSERT INTO `route2` VALUES (8, 'index', 'warning', 0, 7, 'views/warning', '', '{\"title\": \"告警信息\",\"icon\": \"bell\",\"isVueComponent\":true}');
INSERT INTO `route2` VALUES (9, '/dataSearch', '数据查询', 0, 0, 'Layout', '/index', '{\"isVueComponent\":true}');
INSERT INTO `route2` VALUES (10, 'index', 'dataSearch', 0, 9, 'views/data/index', '', '{\"title\": \"数据查询\",\"icon\": \"search\",\"isVueComponent\":true}');
INSERT INTO `route2` VALUES (11, '/dataWatch', '数据监视', 0, 0, 'Layout', '/index', '{\"isVueComponent\":true}');
INSERT INTO `route2` VALUES (12, 'index', 'dataWatch', 0, 11, 'views/dataWatch', '', '{\"title\": \"数据监视\",\"icon\": \"layer-group\",\"isVueComponent\":true}');
INSERT INTO `route2` VALUES (13, '/dataReport', '数据报表', 0, 0, 'Layout', '/index', '{\"isVueComponent\":true}');
INSERT INTO `route2` VALUES (14, 'index', 'dataReport', 0, 13, 'views/dataReport', '', '{\"title\": \"数据报表\",\"icon\": \"file-medical-alt\",\"isVueComponent\":true}');
INSERT INTO `route2` VALUES (31539291938095104, '/testadd1', '测试添加节点11', 999999, 0, 'null', 'index', 'null');

-- ----------------------------
-- Table structure for route2_not_intercept
-- ----------------------------
DROP TABLE IF EXISTS `route2_not_intercept`;
CREATE TABLE `route2_not_intercept`  (
  `id` bigint(0) UNSIGNED NOT NULL COMMENT 'id',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路径',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `seq` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '顺序',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '组件名称或路径',
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '重定向',
  `meta` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '自定义内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '前端路由-不拦截表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of route2_not_intercept
-- ----------------------------
INSERT INTO `route2_not_intercept` VALUES (0, '/user/login', '登录', '0', '', '', '');

-- ----------------------------
-- Table structure for route_not_intercept
-- ----------------------------
DROP TABLE IF EXISTS `route_not_intercept`;
CREATE TABLE `route_not_intercept`  (
  `id` bigint(0) UNSIGNED NOT NULL COMMENT 'id',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路径',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `seq` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '路由-不拦截表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of route_not_intercept
-- ----------------------------
INSERT INTO `route_not_intercept` VALUES (0, '/user/login', '登录', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) UNSIGNED NOT NULL COMMENT 'id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '账号：唯一',
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `create_id` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建者id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account`(`account`) USING BTREE COMMENT '唯一',
  INDEX `create_id`(`create_id`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (0, 'root', '$2a$10$hJ1YXxTgb3xk65vf4mMtBOaS32ZeZOWlhqjlxAtgLpdL4R/H/e/9q', 'ROOT', 0);
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$qQRy7tw0h51FUAvwu866B.GUpbn11BjTDtl.cNvA2wpIkR1Kt85OG', 'ADMIN', 0);
INSERT INTO `user` VALUES (2, 'dba', '$2a$10$jDsq5sse3fCD6xk3vsInBeXLfL2dCR762Qr1iFN/YLlHhsVaKL5ai', 'DBA', 0);
INSERT INTO `user` VALUES (3, 'user', '$2a$10$r9xyEqPBJV8zgrpWK9rXye/B/ZaIk4lhAQkVv35bnQ945lT4/8j3i', 'USER', 0);
INSERT INTO `user` VALUES (4, 'guest', '$2a$10$VnA1/sMYptvwQufzyArI5e3giuQoEHAGux9aRScMrJB3WwPqU2sxG', 'GUEST', 0);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint(0) UNSIGNED NOT NULL COMMENT 'id',
  `user_id` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户id',
  `role_id` bigint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `role_id`) USING BTREE COMMENT '不能重复',
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (0, 0, 0);
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 1, 2);
INSERT INTO `user_role` VALUES (7, 2, 1);
INSERT INTO `user_role` VALUES (4, 2, 3);
INSERT INTO `user_role` VALUES (5, 3, 4);
INSERT INTO `user_role` VALUES (6, 4, 4);

SET FOREIGN_KEY_CHECKS = 1;
