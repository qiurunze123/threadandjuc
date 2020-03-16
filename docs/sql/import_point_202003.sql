/*
Navicat MySQL Data Transfer

Source Server         : miaosha
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : miaosha

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-05-13 15:03:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `import_point_201904`
-- ----------------------------
DROP TABLE IF EXISTS `import_point_201904`;
CREATE TABLE `import_point_202003` (
  `day` varchar(20) NOT NULL,
  `userId` int(11) NOT NULL DEFAULT '0',
  `availablePoints` decimal(16,2) DEFAULT NULL,
  `frozenPoints` decimal(16,2) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '数据库创建时间',
  PRIMARY KEY (`day`,`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of import_point_201904
-- ----------------------------
