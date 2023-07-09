/*
 Navicat Premium Data Transfer

 Source Server         : 公司本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : chat

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 30/05/2023 11:09:31
*/

CREATE DATABASE IF NOT EXISTS `chat`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id自增',
    `username` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户名',
    `password` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '密码',
    `motto` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '座右铭',
    `head_sculpture` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '头像',
    `age` int(3) NOT NULL DEFAULT '0' COMMENT '岁数',
    `sex` int(1) DEFAULT NULL COMMENT '性别 1:男 2:女',
    `email` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for friends
-- ----------------------------
DROP TABLE IF EXISTS `friends`;
CREATE TABLE `friends` (
                           `self_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '自己的用户id',
                           `friend_id` int(10) unsigned NOT NULL COMMENT '朋友的用户id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
