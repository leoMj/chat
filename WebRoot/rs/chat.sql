/*
Navicat MySQL Data Transfer

Source Server         : localhost_chat
Source Server Version : 50554
Source Host           : localhost:3306
Source Database       : chat

Target Server Type    : MYSQL
Target Server Version : 50554
File Encoding         : 65001

Date: 2017-01-21 22:24:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `chat_message`
-- ----------------------------
DROP TABLE IF EXISTS `chat_message`;
CREATE TABLE `chat_message` (
  `Id` bigint(11) NOT NULL AUTO_INCREMENT,
  `content` text COMMENT '聊天内容表',
  `sender` bigint(11) DEFAULT NULL,
  `receiver` bigint(11) DEFAULT NULL COMMENT '接收者',
  `createTime` bigint(14) DEFAULT NULL,
  `state` int(1) DEFAULT NULL COMMENT '0：未读 1：已经读',
  `formatTime` varchar(255) DEFAULT NULL COMMENT '格式化时间',
  PRIMARY KEY (`Id`),
  KEY `fk_chat_user_id` (`sender`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_message
-- ----------------------------

-- ----------------------------
-- Table structure for `chat_user`
-- ----------------------------
DROP TABLE IF EXISTS `chat_user`;
CREATE TABLE `chat_user` (
  `Id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  `createTime` bigint(20) DEFAULT NULL,
  `nickName` varchar(50) DEFAULT NULL,
  `desc` varchar(100) DEFAULT NULL COMMENT '简介',
  `password` varchar(50) DEFAULT NULL,
  `friends` varchar(255) DEFAULT NULL COMMENT '好友',
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='聊天用户表';

-- ----------------------------
-- Records of chat_user
-- ----------------------------
INSERT INTO `chat_user` VALUES ('1', 'flyed@126.com', null, '麻花藤', null, '123456', '2,4', '');
INSERT INTO `chat_user` VALUES ('2', '475297906@qq.com', null, '小马', null, '123456', '1', '');
INSERT INTO `chat_user` VALUES ('3', '475297907@qq.com', null, '小新', null, '123456', '1,2', '');
INSERT INTO `chat_user` VALUES ('4', '563454312@qq.com', null, '铭铭', null, '123456', '1,2,3', '');
INSERT INTO `chat_user` VALUES ('5', 'leogen', null, 'leo猫', null, '123456', '1,2,3', null);
INSERT INTO `chat_user` VALUES ('6', 'ff@12.com', '1484837910201', 'ai', null, '123456', null, null);
INSERT INTO `chat_user` VALUES ('7', 'ff@124.com', '1484891631731', '马云', null, '123456', null, null);
INSERT INTO `chat_user` VALUES ('8', 'ff@121.com', '1484891827749', '京东', null, '123456', null, null);
INSERT INTO `chat_user` VALUES ('9', 'limenghao2@huawei.com', '1484894627130', 'yingchun', null, '123456', null, null);
INSERT INTO `chat_user` VALUES ('10', 'limenghao2@huawei.com', '1484894627129', 'yingchun', null, '123456', null, null);
