/*
Navicat MySQL Data Transfer

Source Server         : miaosha
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : miaosha

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-05-13 15:03:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `import_data_task`
-- ----------------------------
DROP TABLE IF EXISTS `import_data_task`;
CREATE TABLE `import_data_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `day` varchar(20) NOT NULL COMMENT '抽取数据日期',
  `type` varchar(40) NOT NULL COMMENT '1:point 2:point_log 3:user_loan_record 4:finance_plan_user_stat 5:user_stat',
  `status` int(11) NOT NULL COMMENT '0 未处理,1已处理',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_import_data_task_day_type` (`day`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=497 DEFAULT CHARSET=utf8 COMMENT='数据抽取任务表';

-- ----------------------------
-- Records of import_data_task
-- ----------------------------
INSERT INTO `import_data_task` VALUES ('496', '20190430', 'point', '1', '2019-04-30 11:27:09', '2019-04-30 11:27:20', '1260');
