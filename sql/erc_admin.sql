/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : erc_admin

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 01/10/2021 11:08:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'token令牌',
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号类型',
  `status` tinyint NOT NULL COMMENT '账号权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (55, 'admin', '123456', 'MTYymNjQyODkyNDQzNg==', '信息工程学院', 0);
INSERT INTO `admin` VALUES (56, 'root', '123456', 'MTYwTMTc5NTE5OTYwMQ==', '园艺园林学院', 1);
INSERT INTO `admin` VALUES (57, 'admin1', '123456', 'MTYwMMTc5NTkzMjk2OA==', '园艺园林学院', 0);
INSERT INTO `admin` VALUES (58, 'admin2', '123456', 'MTYw0MTc5NTk2MDA2MA==', '园艺园林学院', 1);
INSERT INTO `admin` VALUES (59, 'admin3', '123456', 'MTYwVMTc5NTk4NDUxNA==', '园艺园林学院', 2);
INSERT INTO `admin` VALUES (60, 'admin4', '123456', 'MTYwlMTgyNTU3MDA3Mw==', '河南科技学校', 0);
INSERT INTO `admin` VALUES (61, '李四', '123456', 'MTYwbMTg4MjQyNTk3Mw==', '信息工程学院', 1);
INSERT INTO `admin` VALUES (62, '王五', '123456', 'MTYw2MTk2ODA5MDE4MA==', '资源与环境学院', 2);
INSERT INTO `admin` VALUES (63, '麻子', '123456', 'MTYwCMjAxNTAxMDUyNw==', '生命科技学院', 2);
INSERT INTO `admin` VALUES (64, '张三', '123456', 'MTYwQMDY3MDUyMzMyMw==', '园艺园林学院', 2);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
