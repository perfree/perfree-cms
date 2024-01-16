/*
 Navicat Premium Data Transfer

 Source Server         : 153.153.234.13
 Source Server Type    : MySQL
 Source Server Version : 80100
 Source Host           : 153.153.234.13:3306
 Source Schema         : perfree

 Target Server Type    : MySQL
 Target Server Version : 80100
 File Encoding         : 65001

 Date: 16/01/2024 11:22:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for p_access_logs
-- ----------------------------
DROP TABLE IF EXISTS `p_access_logs`;
CREATE TABLE `p_access_logs`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `system_info` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '系统信息',
                                  `system_group` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '系统家族',
                                  `system_type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '系统类型',
                                  `browser_version` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '浏览器版本',
                                  `browser_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '浏览器名称',
                                  `browser_group` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '浏览器家族',
                                  `ip` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'ip',
                                  `date` datetime NULL DEFAULT NULL COMMENT '时间',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_access_logs
-- ----------------------------

-- ----------------------------
-- Table structure for p_article
-- ----------------------------
DROP TABLE IF EXISTS `p_article`;
CREATE TABLE `p_article`  (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `title` varchar(256) CHARACTER SET utf8mb4  NOT NULL COMMENT '文章标题',
                              `content` longtext CHARACTER SET utf8mb4  NULL COMMENT '文章内容',
                              `type` varchar(32) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '文章类型:article文章,page页面',
                              `summary` varchar(1024) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '文章摘要',
                              `categoryId` int NULL DEFAULT NULL COMMENT '所属分类',
                              `metaKeywords` varchar(512) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT 'SEO关键字',
                              `metaDescription` varchar(512) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT 'SEO描述',
                              `thumbnail` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '缩略图',
                              `isTop` int NULL DEFAULT 0 COMMENT '是否置顶0:否,1:是',
                              `status` int NULL DEFAULT 0 COMMENT '状态0:已发布,1:草稿',
                              `commentCount` int NULL DEFAULT 0 COMMENT '评论数',
                              `viewCount` int NULL DEFAULT 0 COMMENT '访问量',
                              `userId` int NOT NULL COMMENT '创建人',
                              `isComment` int NULL DEFAULT 1 COMMENT '是否允许评论0:否,1是',
                              `createTime` datetime NOT NULL COMMENT '创建时间',
                              `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
                              `contentModel` varchar(32) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '内容类型:html/markdown',
                              `slug` varchar(128) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT 'slug',
                              `flag` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '标识',
                              `template` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '模板',
                              `greatCount` int NULL DEFAULT 0 COMMENT '点赞数',
                              PRIMARY KEY (`id`) USING BTREE,
                              INDEX `slug`(`slug`) USING BTREE,
                              INDEX `type`(`type`) USING BTREE,
                              INDEX `categoryId`(`categoryId`) USING BTREE,
                              INDEX `commentCount`(`commentCount`) USING BTREE,
                              INDEX `viewCount`(`viewCount`) USING BTREE,
                              INDEX `isTop`(`isTop`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8mb4  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_article
-- ----------------------------

-- ----------------------------
-- Table structure for p_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `p_article_tag`;
CREATE TABLE `p_article_tag`  (
                                  `articleId` int NOT NULL COMMENT '文章id',
                                  `tagId` int NOT NULL COMMENT '标签id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_article_tag
-- ----------------------------

-- ----------------------------
-- Table structure for p_attach
-- ----------------------------
DROP TABLE IF EXISTS `p_attach`;
CREATE TABLE `p_attach`  (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `name` varchar(256) CHARACTER SET utf8mb4  NOT NULL COMMENT '附件名',
                             `desc` varchar(512) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '附件描述',
                             `path` varchar(512) CHARACTER SET utf8mb4  NOT NULL COMMENT '附件路径',
                             `suffix` varchar(32) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '附件后缀',
                             `flag` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '标识',
                             `type` varchar(32) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '文件类型',
                             `createTime` datetime NOT NULL COMMENT '创建时间',
                             `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             `saveType` varchar(32) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '存储方式',
                             `fileKey` varchar(512) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT 'fileKey',
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `type`(`type`) USING BTREE,
                             INDEX `saveType`(`saveType`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 244 CHARACTER SET = utf8mb4  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_attach
-- ----------------------------

-- ----------------------------
-- Table structure for p_category
-- ----------------------------
DROP TABLE IF EXISTS `p_category`;
CREATE TABLE `p_category`  (
                               `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `name` varchar(256) CHARACTER SET utf8mb4  NOT NULL COMMENT '分类名',
                               `pid` int NOT NULL DEFAULT -1 COMMENT '父级id',
                               `desc` varchar(512) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '描述',
                               `count` int NOT NULL DEFAULT 0 COMMENT '文章数量',
                               `metaKeywords` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT 'SEO关键字',
                               `metaDescription` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT 'SEO描述内容',
                               `status` int NOT NULL DEFAULT 0 COMMENT '状态0:正常,1禁用',
                               `createTime` datetime NOT NULL COMMENT '创建时间',
                               `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
                               `thumbnail` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '封面图',
                               `slug` varchar(128) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT 'slug',
                               PRIMARY KEY (`id`) USING BTREE,
                               INDEX `status`(`status`) USING BTREE,
                               INDEX `slug`(`slug`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4  COMMENT = '分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_category
-- ----------------------------

-- ----------------------------
-- Table structure for p_comment
-- ----------------------------
DROP TABLE IF EXISTS `p_comment`;
CREATE TABLE `p_comment`  (
                              `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `articleId` int NOT NULL COMMENT '文章id',
                              `pid` int NULL DEFAULT -1 COMMENT '父级id',
                              `topPid` int NULL DEFAULT -1 COMMENT '顶层父级id',
                              `userId` int NULL DEFAULT NULL COMMENT '用户iD',
                              `content` varchar(2048) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '评论内容',
                              `status` int NULL DEFAULT 0 COMMENT '状态:0正常,1:待审核',
                              `avatar` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '头像',
                              `website` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '网站地址',
                              `email` varchar(256) CHARACTER SET utf8mb4  NOT NULL COMMENT '邮箱',
                              `userName` varchar(256) CHARACTER SET utf8mb4  NOT NULL COMMENT '评论人',
                              `createTime` datetime NOT NULL COMMENT '创建时间',
                              `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
                              PRIMARY KEY (`id`) USING BTREE,
                              INDEX `articleId`(`articleId`) USING BTREE,
                              INDEX `status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = utf8mb4  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_comment
-- ----------------------------

-- ----------------------------
-- Table structure for p_link
-- ----------------------------
DROP TABLE IF EXISTS `p_link`;
CREATE TABLE `p_link`  (
                           `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `name` varchar(256) CHARACTER SET utf8mb4  NOT NULL COMMENT '网站名',
                           `logo` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '网站logo',
                           `desc` varchar(512) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '网站描述',
                           `address` varchar(256) CHARACTER SET utf8mb4  NOT NULL COMMENT '网站地址',
                           `createTime` datetime NOT NULL COMMENT '创建时间',
                           `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_link
-- ----------------------------

-- ----------------------------
-- Table structure for p_menu
-- ----------------------------
DROP TABLE IF EXISTS `p_menu`;
CREATE TABLE `p_menu`  (
                           `id` varchar(64) CHARACTER SET utf8mb4  NOT NULL COMMENT '主键',
                           `parentId` varchar(64) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '父级id',
                           `name` varchar(128) CHARACTER SET utf8mb4  NOT NULL COMMENT '菜单名',
                           `path` varchar(128) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '菜单链接',
                           `icon` varchar(64) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '菜单图标',
                           `seq` int NULL DEFAULT NULL COMMENT '排序序号',
                           `type` int NOT NULL DEFAULT 0 COMMENT '菜单类型0:前台,1:后台',
                           `target` int NULL DEFAULT 0 COMMENT '菜单打开方式:0本页,1:新窗口',
                           `status` int NOT NULL DEFAULT 0 COMMENT '菜单状态0:启用,1禁用',
                           `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
                           `component` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '组件路径',
                           `isFrame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
                           `menuType` int NULL DEFAULT NULL COMMENT '菜单类型（0目录1菜单2按钮）',
                           `visible` int NULL DEFAULT 0 COMMENT '菜单状态（0显示 1隐藏）',
                           `perms` varchar(100) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '权限标识',
                           `pluginId` int NULL DEFAULT NULL COMMENT '插件id',
                           `flag` int NULL DEFAULT NULL COMMENT '菜单标识:0:系统自带,1:用户创建,2:插件',
                           `componentName` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '组件名称',
                           `module` varchar(64) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '模块',
                           PRIMARY KEY (`id`) USING BTREE,
                           INDEX `type`(`type`) USING BTREE,
                           INDEX `status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_menu
-- ----------------------------
INSERT INTO `p_menu` VALUES ('1', '-1', '首页', '/home', 'House', 1, 1, 0, 0, '2023-12-08 08:50:22', '2023-12-08 08:50:24', 'home', 1, 1, 0, NULL, NULL, NULL, 'home', 'home');
INSERT INTO `p_menu` VALUES ('5', '-1', '系统管理', NULL, 'Coin', 3, 1, 0, 0, '2024-01-12 13:29:37', '2024-01-12 13:29:39', NULL, 1, 1, 0, NULL, NULL, NULL, NULL, '');
INSERT INTO `p_menu` VALUES ('8', '5', '菜单管理', '/menu', '', 3, 1, 0, 0, '2024-01-15 09:48:51', '2024-01-15 09:48:53', 'menu', 1, 1, 0, NULL, NULL, NULL, 'menu', 'menu');
INSERT INTO `p_menu` VALUES ('b5c029f9c1be4ff08b2dc1a6c421ab0c', 'b771ec3e55c242829f255778ad483cbe', '测试222', '/ecs', '', 0, 1, 0, 0, '2024-01-16 11:10:11', NULL, 'ecs', 1, 1, 0, '', NULL, NULL, NULL, 'elastic');
INSERT INTO `p_menu` VALUES ('b771ec3e55c242829f255778ad483cbe', '-1', '测试', '', 'CircleCheckFilled', 0, 1, 0, 0, '2024-01-16 11:07:55', NULL, '', 1, 0, 0, '', NULL, NULL, NULL, '');

-- ----------------------------
-- Table structure for p_option
-- ----------------------------
DROP TABLE IF EXISTS `p_option`;
CREATE TABLE `p_option`  (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `key` varchar(256) CHARACTER SET utf8mb4  NOT NULL COMMENT 'key',
                             `value` text CHARACTER SET utf8mb4  NULL COMMENT 'value',
                             PRIMARY KEY (`id`, `key`) USING BTREE,
                             UNIQUE INDEX `key`(`key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8mb4  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_option
-- ----------------------------

-- ----------------------------
-- Table structure for p_photo
-- ----------------------------
DROP TABLE IF EXISTS `p_photo`;
CREATE TABLE `p_photo`  (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `photosId` int NOT NULL COMMENT '相册id',
                            `name` varchar(64) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '名称',
                            `desc` varchar(64) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '描述',
                            `url` varchar(64) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT 'url',
                            `userId` int NOT NULL COMMENT '用户id',
                            `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_photo
-- ----------------------------

-- ----------------------------
-- Table structure for p_photos
-- ----------------------------
DROP TABLE IF EXISTS `p_photos`;
CREATE TABLE `p_photos`  (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `name` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '相册名',
                             `desc` varchar(1024) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '描述',
                             `coverUrl` varchar(128) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '封面url',
                             `isEncryption` int NULL DEFAULT 0 COMMENT '是否加密:0正常,1:加密',
                             `password` varchar(64) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '密码',
                             `userId` int NOT NULL COMMENT '用户id',
                             `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_photos
-- ----------------------------

-- ----------------------------
-- Table structure for p_plugin
-- ----------------------------
DROP TABLE IF EXISTS `p_plugin`;
CREATE TABLE `p_plugin`  (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `name` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '插件名',
                             `path` varchar(256) CHARACTER SET utf8mb4  NOT NULL COMMENT '路径',
                             `desc` varchar(512) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '插件描述',
                             `version` varchar(64) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '版本',
                             `author` varchar(64) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '作者',
                             `createTime` datetime NOT NULL COMMENT '创建时间',
                             `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             `status` int NULL DEFAULT NULL COMMENT '插件状态:0禁用,1启用',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_plugin
-- ----------------------------

-- ----------------------------
-- Table structure for p_role
-- ----------------------------
DROP TABLE IF EXISTS `p_role`;
CREATE TABLE `p_role`  (
                           `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `name` varchar(32) CHARACTER SET utf8mb4  NOT NULL COMMENT '角色名',
                           `description` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '角色描述',
                           `code` varchar(32) CHARACTER SET utf8mb4  NOT NULL COMMENT '角色码',
                           `createTime` datetime NOT NULL COMMENT '创建时间',
                           `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_role
-- ----------------------------
INSERT INTO `p_role` VALUES (1, '管理员', '网站管理员', 'admin', '2020-12-17 13:11:31', NULL);
INSERT INTO `p_role` VALUES (2, '普通用户', '网站用户', 'user', '2020-12-17 13:11:50', NULL);
INSERT INTO `p_role` VALUES (3, '文章编辑', '文章编辑', 'editor', '2021-09-15 13:59:43', NULL);
INSERT INTO `p_role` VALUES (4, '文章贡献', '文章贡献', 'contribute', '2021-09-15 14:00:21', NULL);

-- ----------------------------
-- Table structure for p_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `p_role_menu`;
CREATE TABLE `p_role_menu`  (
                                `roleId` int NOT NULL COMMENT '角色id',
                                `menuId` varchar(64) CHARACTER SET utf8mb4  NOT NULL COMMENT '菜单id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_role_menu
-- ----------------------------
INSERT INTO `p_role_menu` VALUES (1, '1');
INSERT INTO `p_role_menu` VALUES (1, '5');
INSERT INTO `p_role_menu` VALUES (1, '8');
INSERT INTO `p_role_menu` VALUES (1, 'b5c029f9c1be4ff08b2dc1a6c421ab0c');
INSERT INTO `p_role_menu` VALUES (1, 'b771ec3e55c242829f255778ad483cbe');

-- ----------------------------
-- Table structure for p_tag
-- ----------------------------
DROP TABLE IF EXISTS `p_tag`;
CREATE TABLE `p_tag`  (
                          `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `name` varchar(256) CHARACTER SET utf8mb4  NOT NULL COMMENT '标签名',
                          `userId` int NOT NULL COMMENT '添加人',
                          `createTime` datetime NOT NULL COMMENT '创建时间',
                          `updateTime` datetime NULL DEFAULT NULL COMMENT '修改时间',
                          `color` varchar(128) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '颜色',
                          `thumbnail` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '缩略图',
                          `slug` varchar(128) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT 'slug',
                          PRIMARY KEY (`id`) USING BTREE,
                          INDEX `slug`(`slug`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_tag
-- ----------------------------

-- ----------------------------
-- Table structure for p_user
-- ----------------------------
DROP TABLE IF EXISTS `p_user`;
CREATE TABLE `p_user`  (
                           `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `account` varchar(32) CHARACTER SET utf8mb4  NOT NULL COMMENT '账户',
                           `userName` varchar(32) CHARACTER SET utf8mb4  NOT NULL COMMENT '账户名',
                           `password` varchar(32) CHARACTER SET utf8mb4  NOT NULL COMMENT '密码',
                           `salt` varchar(32) CHARACTER SET utf8mb4  NOT NULL COMMENT '盐值',
                           `status` int NOT NULL DEFAULT 0 COMMENT '状态:0正常,1禁用',
                           `avatar` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '头像',
                           `roleId` int NOT NULL COMMENT '角色id',
                           `email` varchar(128) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '邮箱',
                           `website` varchar(256) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '网站地址',
                           `createTime` datetime NOT NULL COMMENT '创建时间',
                           `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
                           PRIMARY KEY (`id`) USING BTREE,
                           INDEX `account`(`account`) USING BTREE,
                           INDEX `status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_user
-- ----------------------------
INSERT INTO `p_user` VALUES (1, 'perfree', 'perfree', '0c305abdc9ad05437d4a07b609611dba', '5b8bffb8978d421c8245d3f92dce717e', 0, '635e66d06c6c1ed34903fc3afca02dfa', 1, 'perfree@126.com', '', '2021-09-01 12:00:51', '2022-08-20 16:35:52');

SET FOREIGN_KEY_CHECKS = 1;
