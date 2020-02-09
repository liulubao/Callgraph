/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50643
Source Host           : localhost:3306
Source Database       : platform

Target Server Type    : MYSQL
Target Server Version : 50643
File Encoding         : 65001

*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for plat_friend
-- ----------------------------
DROP TABLE IF EXISTS `plat_friend`;
CREATE TABLE `plat_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `followingloginname` varchar(255) NOT NULL,
  `followingviewname` varchar(255) NOT NULL,
  `followedloginname` varchar(255) NOT NULL,
  `followedviewname` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plat_label
-- ----------------------------
DROP TABLE IF EXISTS `plat_label`;
CREATE TABLE `plat_label` (
  `labelid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `projectid` varchar(255) NOT NULL,
  `parentelement` varchar(255) NOT NULL,
  `ownername` varchar(255) NOT NULL,
  `createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`labelid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plat_message
-- ----------------------------
DROP TABLE IF EXISTS `plat_message`;
CREATE TABLE `plat_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `user_name` varchar(250) NOT NULL COMMENT '发送者Id',
  `friend_name` varchar(250) NOT NULL COMMENT '接受者Id',
  `sender_name` varchar(250) NOT NULL COMMENT '发送者id',
  `receiver_name` varchar(250) NOT NULL COMMENT '接受者Id',
  `message_type` int(4) DEFAULT NULL COMMENT '消息类型,1：普通消息 2：系统消息',
  `message_content` varchar(500) NOT NULL COMMENT '消息内容',
  `send_time` datetime DEFAULT NULL COMMENT '消息发送时间',
  `status` int(4) DEFAULT '1' COMMENT '消息状态 1：未读 2：已读 3：删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plat_project
-- ----------------------------
DROP TABLE IF EXISTS `plat_project`;
CREATE TABLE `plat_project` (
  `projectid` varchar(255) NOT NULL,
  `data` longblob NOT NULL,
  `projectname` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`pid`) USING BTREE,
  KEY `project` (`projectname`),
  KEY `name` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plat_user
-- ----------------------------
DROP TABLE IF EXISTS `plat_user`;
CREATE TABLE `plat_user` (
  `loginname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `viewname` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `loginname` (`loginname`),
  UNIQUE KEY `loginname_2` (`loginname`),
  UNIQUE KEY `uni` (`loginname`)
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plat_userpro
-- ----------------------------
DROP TABLE IF EXISTS `plat_userpro`;
CREATE TABLE `plat_userpro` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` varchar(255) NOT NULL,
  `userid` int(11) NOT NULL,
  `projectname` varchar(255) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
