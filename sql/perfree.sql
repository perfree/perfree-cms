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

 Date: 26/02/2024 10:27:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for p_article
-- ----------------------------
DROP TABLE IF EXISTS `p_article`;
CREATE TABLE `p_article`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章内容',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章类型:article文章,page页面',
  `summary` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章摘要',
  `categoryId` int NULL DEFAULT NULL COMMENT '所属分类',
  `metaKeywords` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SEO关键字',
  `metaDescription` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SEO描述',
  `thumbnail` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缩略图',
  `isTop` int NULL DEFAULT 0 COMMENT '是否置顶0:否,1:是',
  `status` int NULL DEFAULT 0 COMMENT '状态0:已发布,1:草稿',
  `commentCount` int NULL DEFAULT 0 COMMENT '评论数',
  `viewCount` int NULL DEFAULT 0 COMMENT '访问量',
  `userId` int NOT NULL COMMENT '创建人',
  `isComment` int NULL DEFAULT 1 COMMENT '是否允许评论0:否,1是',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `contentModel` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容类型:html/markdown',
  `slug` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'slug',
  `flag` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标识',
  `template` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板',
  `greatCount` int NULL DEFAULT 0 COMMENT '点赞数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `slug`(`slug`) USING BTREE,
  INDEX `type`(`type`) USING BTREE,
  INDEX `categoryId`(`categoryId`) USING BTREE,
  INDEX `commentCount`(`commentCount`) USING BTREE,
  INDEX `viewCount`(`viewCount`) USING BTREE,
  INDEX `isTop`(`isTop`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_article_tag
-- ----------------------------

-- ----------------------------
-- Table structure for p_attach
-- ----------------------------
DROP TABLE IF EXISTS `p_attach`;
CREATE TABLE `p_attach`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '附件名',
  `desc` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件描述',
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '附件路径',
  `suffix` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件后缀',
  `flag` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标识',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `saveType` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '存储方式',
  `fileKey` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'fileKey',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `type`(`type`) USING BTREE,
  INDEX `saveType`(`saveType`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_attach
-- ----------------------------
INSERT INTO `p_attach` VALUES (1, '11', '11', '1', '1', '1', '1', '2024-02-22 10:33:51', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for p_attach_config
-- ----------------------------
DROP TABLE IF EXISTS `p_attach_config`;
CREATE TABLE `p_attach_config`  (
  `id` int NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置名',
  `storage` int NOT NULL COMMENT '存储器',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `config` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储配置',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '附件服务器配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of p_attach_config
-- ----------------------------

-- ----------------------------
-- Table structure for p_category
-- ----------------------------
DROP TABLE IF EXISTS `p_category`;
CREATE TABLE `p_category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名',
  `pid` int NOT NULL DEFAULT -1 COMMENT '父级id',
  `desc` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `count` int NOT NULL DEFAULT 0 COMMENT '文章数量',
  `metaKeywords` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SEO关键字',
  `metaDescription` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SEO描述内容',
  `status` int NOT NULL DEFAULT 0 COMMENT '状态0:正常,1禁用',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `thumbnail` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图',
  `slug` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'slug',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `slug`(`slug`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分类表' ROW_FORMAT = DYNAMIC;

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
  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `status` int NULL DEFAULT 0 COMMENT '状态:0正常,1:待审核',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `website` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站地址',
  `email` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论人',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `articleId`(`articleId`) USING BTREE,
  INDEX `status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_comment
-- ----------------------------

-- ----------------------------
-- Table structure for p_link
-- ----------------------------
DROP TABLE IF EXISTS `p_link`;
CREATE TABLE `p_link`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网站名',
  `logo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站logo',
  `desc` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站描述',
  `address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网站地址',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_link
-- ----------------------------

-- ----------------------------
-- Table structure for p_menu
-- ----------------------------
DROP TABLE IF EXISTS `p_menu`;
CREATE TABLE `p_menu`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `parentId` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级id',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单链接',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `seq` int NULL DEFAULT NULL COMMENT '排序序号',
  `type` int NOT NULL DEFAULT 0 COMMENT '菜单类型0:前台,1:后台',
  `target` int NULL DEFAULT 0 COMMENT '菜单打开方式:0本页,1:新窗口',
  `status` int NOT NULL DEFAULT 0 COMMENT '菜单状态0:启用,1禁用',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `component` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `isFrame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `menuType` int NULL DEFAULT NULL COMMENT '菜单类型（0目录1菜单2按钮）',
  `visible` int NULL DEFAULT 0 COMMENT '菜单状态（0显示 1隐藏）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `pluginId` int NULL DEFAULT NULL COMMENT '插件id',
  `flag` int NULL DEFAULT NULL COMMENT '菜单标识:0:系统自带,1:用户创建,2:插件',
  `componentName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名称',
  `module` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `type`(`type`) USING BTREE,
  INDEX `status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_menu
-- ----------------------------
INSERT INTO `p_menu` VALUES ('010a5ddc91fd464996175a24d1320657', '8', '获取菜单', '', '', 0, 1, 0, 0, '2024-02-20 10:55:01', NULL, '', 1, 2, 0, 'admin:menu:get', NULL, NULL, '', '');
INSERT INTO `p_menu` VALUES ('1', '-1', '首页', '/admin', 'House', 1, 1, 0, 0, '2023-12-08 08:50:22', '2023-12-08 08:50:24', 'home', 1, 1, 0, NULL, NULL, NULL, 'home', 'home');
INSERT INTO `p_menu` VALUES ('1b5aa94c310c4cd79a9565fcbab59f4b', '5', '角色管理', '/admin/role', '', 1, 1, 0, 0, '2024-01-16 11:27:55', NULL, 'role', 1, 1, 0, '', NULL, NULL, 'role', 'role');
INSERT INTO `p_menu` VALUES ('5', '-1', '系统管理', NULL, 'Coin', 3, 1, 0, 0, '2024-01-12 13:29:37', '2024-01-12 13:29:39', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, '');
INSERT INTO `p_menu` VALUES ('6e4b9c2c1ea64bd6954aa6f9cb05e3d3', '5', '插件管理', '/admin/plugins', '', 2, 1, 0, 0, '2024-01-17 15:27:35', NULL, 'plugins', 1, 1, 0, '', NULL, NULL, 'plugins', 'plugins');
INSERT INTO `p_menu` VALUES ('8', '5', '菜单管理', '/admin/menu', '', 0, 1, 0, 0, '2024-01-15 09:48:51', '2024-01-15 09:48:53', 'menu', 1, 1, 0, NULL, NULL, NULL, 'menu', 'menu');
INSERT INTO `p_menu` VALUES ('83f287c6c1fd4e52842d9ef023f63705', '5', '附件配置', '/admin/attachConfig', '', 5, 1, 0, 0, '2024-02-22 14:44:17', NULL, 'attachConfig', 1, 1, 0, '', NULL, NULL, 'attachConfig', 'attachConfig');
INSERT INTO `p_menu` VALUES ('8eb93f7d6c314430bf73c9db5f8e2f28', '8', '菜单分页', '', '', 0, 1, 0, 0, '2024-02-20 10:54:49', NULL, '', 1, 2, 0, 'admin:menu:page', NULL, NULL, '', '');
INSERT INTO `p_menu` VALUES ('94724be0ac7641d488302d7cea60706b', '5', '用户管理', '/admin/user', '', 3, 1, 0, 0, '2024-02-05 11:36:42', NULL, 'user', 1, 1, 0, '', NULL, NULL, 'user', 'user');
INSERT INTO `p_menu` VALUES ('9e3a01c20e114bcf9203bdcd53a5d8e4', '8', '删除菜单', '', '', 0, 1, 0, 0, '2024-02-20 10:55:31', NULL, '', 1, 2, 0, 'admin:menu:del', NULL, NULL, '', '');
INSERT INTO `p_menu` VALUES ('a1a2828793b447019510f3a90f8d8b32', '8', '更新菜单', '', '', 0, 1, 0, 0, '2024-02-20 10:55:20', NULL, '', 1, 2, 0, 'admin:menu:update', NULL, NULL, '', '');
INSERT INTO `p_menu` VALUES ('cc436fe50452420aa3db63101c26f20a', '8', '新增菜单', '', '', 0, 1, 0, 0, '2024-02-19 13:44:55', NULL, '', 1, 2, 0, 'admin:menu:create', NULL, NULL, '', '');
INSERT INTO `p_menu` VALUES ('de3bb6e43dc84b9792ec22d0ec7a6510', '5', '附件管理', '/admin/attach', '', 4, 1, 0, 0, '2024-02-21 16:03:17', NULL, 'attach', 1, 1, 0, '', NULL, NULL, 'attach', 'attach');

-- ----------------------------
-- Table structure for p_option
-- ----------------------------
DROP TABLE IF EXISTS `p_option`;
CREATE TABLE `p_option`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `key` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'key',
  `value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'value',
  `type` int NOT NULL DEFAULT 0 COMMENT '类型:0系统自带,1主题,2插件',
  `pluginId` int NULL DEFAULT NULL COMMENT '插件id',
  `themeId` int NULL DEFAULT NULL COMMENT '主题id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key`(`key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_option
-- ----------------------------
INSERT INTO `p_option` VALUES (1, 'LOGIN_CAPTCHA_ENABLE', '1', 0, NULL, NULL);
INSERT INTO `p_option` VALUES (2, 'DEFAULT_FILE_HANDLE', 'fileLocalHandleImpl', 0, NULL, NULL);

-- ----------------------------
-- Table structure for p_plugins
-- ----------------------------
DROP TABLE IF EXISTS `p_plugins`;
CREATE TABLE `p_plugins`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '插件名',
  `path` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '路径',
  `desc` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '插件描述',
  `version` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本',
  `author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` int NULL DEFAULT NULL COMMENT '插件状态:0禁用,1启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_plugins
-- ----------------------------
INSERT INTO `p_plugins` VALUES (1, '111', '11', '11', '1', '1', '2024-01-17 15:43:48', NULL, 0);

-- ----------------------------
-- Table structure for p_role
-- ----------------------------
DROP TABLE IF EXISTS `p_role`;
CREATE TABLE `p_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
  `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色码',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

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
  `menuId` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_role_menu
-- ----------------------------
INSERT INTO `p_role_menu` VALUES (1, '1');
INSERT INTO `p_role_menu` VALUES (1, '5');
INSERT INTO `p_role_menu` VALUES (1, '8');
INSERT INTO `p_role_menu` VALUES (1, 'cc436fe50452420aa3db63101c26f20a');
INSERT INTO `p_role_menu` VALUES (1, '1b5aa94c310c4cd79a9565fcbab59f4b');
INSERT INTO `p_role_menu` VALUES (1, '6e4b9c2c1ea64bd6954aa6f9cb05e3d3');
INSERT INTO `p_role_menu` VALUES (2, '1');
INSERT INTO `p_role_menu` VALUES (2, '5');
INSERT INTO `p_role_menu` VALUES (2, '8');
INSERT INTO `p_role_menu` VALUES (2, '010a5ddc91fd464996175a24d1320657');
INSERT INTO `p_role_menu` VALUES (2, '8eb93f7d6c314430bf73c9db5f8e2f28');
INSERT INTO `p_role_menu` VALUES (2, '9e3a01c20e114bcf9203bdcd53a5d8e4');
INSERT INTO `p_role_menu` VALUES (2, 'a1a2828793b447019510f3a90f8d8b32');
INSERT INTO `p_role_menu` VALUES (2, '1b5aa94c310c4cd79a9565fcbab59f4b');

-- ----------------------------
-- Table structure for p_tag
-- ----------------------------
DROP TABLE IF EXISTS `p_tag`;
CREATE TABLE `p_tag`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签名',
  `userId` int NOT NULL COMMENT '添加人',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `color` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '颜色',
  `thumbnail` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缩略图',
  `slug` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'slug',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `slug`(`slug`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_tag
-- ----------------------------

-- ----------------------------
-- Table structure for p_test
-- ----------------------------
DROP TABLE IF EXISTS `p_test`;
CREATE TABLE `p_test`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of p_test
-- ----------------------------
INSERT INTO `p_test` VALUES (1, '2');

-- ----------------------------
-- Table structure for p_user
-- ----------------------------
DROP TABLE IF EXISTS `p_user`;
CREATE TABLE `p_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账户',
  `userName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '盐值',
  `status` int NOT NULL DEFAULT 0 COMMENT '状态:0正常,1禁用',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `website` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站地址',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `account`(`account`) USING BTREE,
  INDEX `status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_user
-- ----------------------------
INSERT INTO `p_user` VALUES (1, 'perfree', 'perfree', '76248253108b98191fb369c7383f9f6c', '5b8bffb8978d421c8245d3f92dce717e', 0, '635e66d06c6c1ed34903fc3afca02dfa', 'perfree@126.com', '', '2021-09-01 12:00:51', '2024-02-06 10:44:58');
INSERT INTO `p_user` VALUES (60, 'cesces', '测试', '125fc570f5b00d06b9d402ce2fc606c5', '04bfbd790e154b268cc524d776fbdde2', 0, NULL, '', '', '2024-02-20 10:03:08', NULL);

-- ----------------------------
-- Table structure for p_user_role
-- ----------------------------
DROP TABLE IF EXISTS `p_user_role`;
CREATE TABLE `p_user_role`  (
  `userId` int NOT NULL COMMENT '用户id',
  `roleId` int NOT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of p_user_role
-- ----------------------------
INSERT INTO `p_user_role` VALUES (1, 1);
INSERT INTO `p_user_role` VALUES (60, 2);

SET FOREIGN_KEY_CHECKS = 1;
