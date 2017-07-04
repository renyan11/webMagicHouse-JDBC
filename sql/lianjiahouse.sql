/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : webmagic

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2017-07-04 09:03:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `lianjiahouse`
-- ----------------------------
DROP TABLE IF EXISTS `lianjiahouse`;
CREATE TABLE `lianjiahouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `houseState` varchar(100) DEFAULT NULL COMMENT '房源状态',
  `houseName` varchar(200) DEFAULT NULL COMMENT '房源名称',
  `price` varchar(65) DEFAULT NULL COMMENT '均价',
  `updateDate` varchar(0) DEFAULT NULL COMMENT '更新日期',
  `wuyeType` varchar(200) DEFAULT NULL COMMENT '物业类型',
  `address` varchar(500) DEFAULT NULL COMMENT '项目地址',
  `openOrderDate` varchar(0) DEFAULT NULL COMMENT '最新开盘日期',
  `contextPhone` varchar(100) DEFAULT NULL COMMENT '咨询电话',
  `tags` varchar(200) DEFAULT NULL COMMENT '房源标签',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lianjiahouse
-- ----------------------------
