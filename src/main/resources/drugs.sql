/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50624
Source Host           : 127.0.0.1:3306
Source Database       : drugs

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-11-07 03:49:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for drugs_user
-- ----------------------------
DROP TABLE IF EXISTS `drugs_user`;
CREATE TABLE `drugs_user` (
  `uid` varchar(255) NOT NULL COMMENT '用户ID主键',
  `account` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `pwd` varchar(12) NOT NULL COMMENT '密码',
  `email` varchar(255) NOT NULL COMMENT '用户姓名',
  `phone` varchar(11) NOT NULL COMMENT '联系电话',
  `regTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `expirationTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `idx_uname` (`account`) COMMENT '用户名唯一索引',
  UNIQUE KEY `ix_phone` (`phone`) COMMENT '手机号唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of drugs_user
-- ----------------------------
INSERT INTO `drugs_user` VALUES ('0fdce742-2454-45ed-a160-9624323be626', 'bbbbb', 'abc123', 'ccloveni@163.com', '18647975025', '2016-07-19 00:53:16', '2016-07-19 00:53:16');
INSERT INTO `drugs_user` VALUES ('1c461a17-6891-44d3-b9de-32f4ad968393', 'ansoya', 'abc123', 'ccloveni@163.com', '18647975225', '2016-07-19 23:08:08', '2016-07-26 23:08:08');
INSERT INTO `drugs_user` VALUES ('2c07ba1b-9ced-456d-8117-53f290fb4d7e', 'vvvvv', 'aaa123', 'ccloveni@163.com', '18647975212', '2016-07-19 22:25:01', '2016-07-19 22:25:01');
INSERT INTO `drugs_user` VALUES ('3a8cccc5-df61-408f-86af-a62cddca89b9', 'aaaaa', 'abc123', 'ccloveni@163.com', '18647975024', '2016-07-19 00:51:00', '2016-07-19 00:51:00');
INSERT INTO `drugs_user` VALUES ('4007a69f-e180-4564-a6df-f9ddf87f770d', 'admin', 'abc123', 'admin', '18647975023', '2016-07-17 03:33:39', '2016-07-17 03:33:39');
INSERT INTO `drugs_user` VALUES ('456bb830-4403-456e-a937-aab70346d7ec', 'jyc456', 'jyc456', 'ccloveni@163.com', '18647975066', '2016-11-06 22:30:04', '2016-11-13 22:30:04');
INSERT INTO `drugs_user` VALUES ('48037b96-6563-4280-8979-96fca1f0c7df', 'ccccc', 'abc123', 'ccloveni@163.com', '18647975029', '2016-07-19 15:07:55', '2016-07-19 15:07:55');
INSERT INTO `drugs_user` VALUES ('4a267ad1-acf4-4a12-9c89-bc7d147edfd1', 'ccccd', 'abc123', 'ccloveni@163.com', '18647975028', '2016-07-19 15:14:49', '2016-07-19 15:14:49');
INSERT INTO `drugs_user` VALUES ('aeb09564-9ff5-4ed0-bf45-b8d866ca1518', 'mmmmm', 'abc123', 'ccloveni@163.com', '18647975123', '2016-07-19 18:18:32', '2016-07-19 18:18:32');
INSERT INTO `drugs_user` VALUES ('e4f8c85f-ceae-4cfb-a495-ebb421dfb956', 'guest', 'abc123', 'guest', '18647975026', '2016-07-17 05:53:41', '2016-07-17 05:53:41');
INSERT INTO `drugs_user` VALUES ('f2d60859-2d33-4b42-9f53-5fb15fd9e9da', 'jyc123', 'jyc123', 'ccloveni@163.com', '18647978888', '2016-11-06 21:45:06', '2016-11-13 21:45:06');
