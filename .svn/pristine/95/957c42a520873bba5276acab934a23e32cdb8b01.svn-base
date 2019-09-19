/*
Navicat MySQL Data Transfer

Source Server         : suzhou
Source Server Version : 50644
Source Host           : 223.247.213.27:53306
Source Database       : dog_or_network

Target Server Type    : MYSQL
Target Server Version : 50644
File Encoding         : 65001

Date: 2019-07-14 17:36:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `t_dictionary`;
CREATE TABLE `t_dictionary` (
  `dictionary_id` int(11) NOT NULL AUTO_INCREMENT,
  `dictionary_field` varchar(20) NOT NULL,
  `dictionary_value` int(11) NOT NULL,
  `dictionary_describe` varchar(50) NOT NULL,
  `creation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`dictionary_id`)
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dictionary
-- ----------------------------
INSERT INTO `t_dictionary` VALUES ('1', 'device_type', '0', 'RFID', '2018-08-20 18:02:16');
INSERT INTO `t_dictionary` VALUES ('2', 'device_type', '1', '项圈', '2018-08-20 18:02:32');
INSERT INTO `t_dictionary` VALUES ('3', 'dog_breed_type', '0', '中华田园犬', '2018-09-14 03:41:49');
INSERT INTO `t_dictionary` VALUES ('4', 'dog_breed_type', '1', '泰迪熊犬', '2018-08-20 18:09:30');
INSERT INTO `t_dictionary` VALUES ('5', 'dog_breed_type', '2', '比熊犬', '2018-08-20 18:09:55');
INSERT INTO `t_dictionary` VALUES ('6', 'dog_breed_type', '3', '博美犬', '2018-08-20 18:10:15');
INSERT INTO `t_dictionary` VALUES ('7', 'dog_breed_type', '4', '吉娃娃犬', '2018-08-20 10:15:17');
INSERT INTO `t_dictionary` VALUES ('8', 'dog_breed_type', '5', '巴哥犬', '2018-08-20 10:15:31');
INSERT INTO `t_dictionary` VALUES ('9', 'dog_breed_type', '6', '雪纳瑞犬', '2018-08-20 10:15:58');
INSERT INTO `t_dictionary` VALUES ('10', 'dog_breed_type', '7', '法国斗牛犬', '2018-08-20 10:16:26');
INSERT INTO `t_dictionary` VALUES ('11', 'dog_breed_type', '8', '英国斗牛犬', '2018-08-20 10:16:45');
INSERT INTO `t_dictionary` VALUES ('12', 'dog_breed_type', '9', '柯基犬', '2018-08-20 10:17:02');
INSERT INTO `t_dictionary` VALUES ('13', 'dog_breed_type', '10', '比格犬', '2018-08-20 10:17:13');
INSERT INTO `t_dictionary` VALUES ('14', 'dog_breed_type', '11', '金毛犬', '2018-08-20 10:17:56');
INSERT INTO `t_dictionary` VALUES ('15', 'dog_breed_type', '12', '拉布拉多犬', '2018-08-20 10:18:14');
INSERT INTO `t_dictionary` VALUES ('16', 'dog_breed_type', '13', '萨摩耶犬', '2018-08-20 10:18:28');
INSERT INTO `t_dictionary` VALUES ('17', 'dog_breed_type', '14', '哈士奇犬', '2018-08-20 10:18:40');
INSERT INTO `t_dictionary` VALUES ('18', 'dog_breed_type', '15', '边境牧羊犬', '2018-08-20 10:18:56');
INSERT INTO `t_dictionary` VALUES ('19', 'dog_breed_type', '16', '德国牧羊犬', '2018-08-20 10:19:07');
INSERT INTO `t_dictionary` VALUES ('20', 'dog_breed_type', '17', '秋田犬', '2018-08-20 10:19:25');
INSERT INTO `t_dictionary` VALUES ('21', 'dog_breed_type', '18', '阿拉斯加犬', '2018-08-20 10:19:41');
INSERT INTO `t_dictionary` VALUES ('22', 'dog_breed_type', '19', '高加索犬', '2018-08-20 10:19:51');
INSERT INTO `t_dictionary` VALUES ('23', 'dog_breed_type', '20', '大白熊犬', '2018-08-20 10:20:07');
INSERT INTO `t_dictionary` VALUES ('24', 'dog_breed_type', '21', '圣伯纳犬', '2018-08-20 10:20:24');
INSERT INTO `t_dictionary` VALUES ('25', 'dog_breed_type', '22', '苏格兰牧羊犬', '2018-08-20 10:20:38');
INSERT INTO `t_dictionary` VALUES ('26', 'dog_color_type', '0', '白色', '2018-09-14 03:41:53');
INSERT INTO `t_dictionary` VALUES ('27', 'dog_color_type', '1', '黑色', '2018-08-20 11:03:20');
INSERT INTO `t_dictionary` VALUES ('28', 'dog_color_type', '2', '褐色', '2018-08-20 11:03:26');
INSERT INTO `t_dictionary` VALUES ('29', 'dog_color_type', '3', '青色', '2018-08-20 11:03:33');
INSERT INTO `t_dictionary` VALUES ('30', 'dog_color_type', '4', '黑褐色', '2018-08-20 11:03:44');
INSERT INTO `t_dictionary` VALUES ('31', 'dog_color_type', '5', '青色', '2018-08-20 11:03:54');
INSERT INTO `t_dictionary` VALUES ('32', 'dog_color_type', '6', '黑褐色', '2018-08-20 11:04:03');
INSERT INTO `t_dictionary` VALUES ('33', 'dog_color_type', '7', '铁灰色', '2018-08-20 11:04:13');
INSERT INTO `t_dictionary` VALUES ('34', 'dog_color_type', '8', '灰褐色', '2018-08-20 11:04:22');
INSERT INTO `t_dictionary` VALUES ('35', 'dog_color_type', '9', '黄褐色', '2018-08-20 11:04:36');
INSERT INTO `t_dictionary` VALUES ('36', 'dog_color_type', '10', '灰白色', '2018-08-20 11:04:53');
INSERT INTO `t_dictionary` VALUES ('37', 'dog_color_type', '11', '灰白色', '2018-08-20 11:05:04');
INSERT INTO `t_dictionary` VALUES ('38', 'dog_color_type', '12', '黄红色', '2018-08-20 11:05:18');
INSERT INTO `t_dictionary` VALUES ('39', 'dog_color_type', '13', '淡红色', '2018-08-20 11:05:29');
INSERT INTO `t_dictionary` VALUES ('40', 'dog_owner_type', '0', '个人', '2018-08-20 11:06:19');
INSERT INTO `t_dictionary` VALUES ('41', 'dog_owner_type', '1', '企业', '2018-08-20 11:06:16');
INSERT INTO `t_dictionary` VALUES ('42', 'card_type', '0', '身份证', '2018-08-20 11:07:17');
INSERT INTO `t_dictionary` VALUES ('43', 'card_type', '1', '房产证', '2018-08-20 11:07:27');
INSERT INTO `t_dictionary` VALUES ('44', 'card_type', '2', '营业执照', '2018-08-20 11:07:35');
INSERT INTO `t_dictionary` VALUES ('45', 'card_type', '3', '其他', '2018-08-20 11:07:43');
INSERT INTO `t_dictionary` VALUES ('46', 'violation_type', '9', '溜犬不及时清理犬只粪便', '2019-07-03 17:36:43');
INSERT INTO `t_dictionary` VALUES ('47', 'violation_type', '1', '不牵犬绳', '2018-08-20 11:15:45');
INSERT INTO `t_dictionary` VALUES ('48', 'violation_type', '2', '犬只扰民', '2018-08-20 11:57:54');
INSERT INTO `t_dictionary` VALUES ('49', 'punish_type', '0', '扣分', '2018-08-20 12:02:01');
INSERT INTO `t_dictionary` VALUES ('51', 'punish_type', '1', '收容', '2018-08-29 09:29:22');
INSERT INTO `t_dictionary` VALUES ('52', 'punish_type', '2', '其他', '2018-08-29 09:29:25');
INSERT INTO `t_dictionary` VALUES ('53', 'coll_dog_type', '0', '依法收缴犬', '2018-08-20 12:03:30');
INSERT INTO `t_dictionary` VALUES ('55', 'coll_dog_type', '1', '无主流浪犬', '2018-08-29 09:28:40');
INSERT INTO `t_dictionary` VALUES ('56', 'coll_dog_type', '2', '其他', '2018-08-29 09:28:45');
INSERT INTO `t_dictionary` VALUES ('61', 'biz_type', '0', '看病', '2018-08-21 18:45:16');
INSERT INTO `t_dictionary` VALUES ('62', 'biz_type', '1', '办证', '2018-08-21 18:45:44');
INSERT INTO `t_dictionary` VALUES ('63', 'biz_type', '2', '年检', '2018-08-21 18:46:15');
INSERT INTO `t_dictionary` VALUES ('64', 'biz_type', '3', '免疫', '2018-08-21 18:46:41');
INSERT INTO `t_dictionary` VALUES ('65', 'appointment_channel', '0', '电话预约', '2018-08-23 15:15:01');
INSERT INTO `t_dictionary` VALUES ('66', 'appointment_channel', '1', '网上预约', '2019-03-07 01:47:04');
INSERT INTO `t_dictionary` VALUES ('67', 'appointment_channel', '2', '门面预约', '2018-08-23 07:15:57');
INSERT INTO `t_dictionary` VALUES ('68', 'appointment_channel', '3', '其他', '2018-08-23 07:16:07');
INSERT INTO `t_dictionary` VALUES ('69', 'org_type', '0', '犬只办', '2018-08-23 07:56:43');
INSERT INTO `t_dictionary` VALUES ('70', 'org_type', '1', '收容所', '2018-08-23 07:57:35');
INSERT INTO `t_dictionary` VALUES ('71', 'org_type', '2', '宠物医院', '2018-08-23 07:57:43');
INSERT INTO `t_dictionary` VALUES ('72', 'district_type', '0', '省级', '2018-08-23 12:57:23');
INSERT INTO `t_dictionary` VALUES ('73', 'district_type', '1', '市级', '2018-08-23 12:57:35');
INSERT INTO `t_dictionary` VALUES ('74', 'district_type', '2', '区级', '2018-08-23 12:57:50');
INSERT INTO `t_dictionary` VALUES ('80', 'pet_h_card_type', '0', '动物诊疗许可证', '2018-08-24 07:50:48');
INSERT INTO `t_dictionary` VALUES ('81', 'pet_h_card_type', '1', '动物防疫合格证', '2018-08-24 07:51:07');
INSERT INTO `t_dictionary` VALUES ('82', 'pet_h_card_type', '2', '工商营业执照', '2018-08-24 07:51:21');
INSERT INTO `t_dictionary` VALUES ('83', 'pet_h_card_type', '3', '兽医资格证', '2018-08-24 07:51:33');
INSERT INTO `t_dictionary` VALUES ('84', 'information_type', '0', '政策法规', '2018-08-24 12:00:16');
INSERT INTO `t_dictionary` VALUES ('85', 'information_type', '1', '动态资讯', '2018-08-24 12:01:04');
INSERT INTO `t_dictionary` VALUES ('86', 'information_type', '2', '养犬宣传', '2018-08-24 12:01:17');
INSERT INTO `t_dictionary` VALUES ('87', 'information_type', '3', '服务指南', '2018-08-24 12:01:26');
INSERT INTO `t_dictionary` VALUES ('92', 'logout_reason', '0', '死亡', '2018-09-09 11:13:30');
INSERT INTO `t_dictionary` VALUES ('93', 'logout_reason', '1', '丢失', '2018-09-09 11:13:42');
INSERT INTO `t_dictionary` VALUES ('94', 'logout_reason', '2', '异地送葬', '2018-09-09 11:13:51');
INSERT INTO `t_dictionary` VALUES ('95', 'picture_type', '1', '资讯图片', '2018-09-21 01:49:18');
INSERT INTO `t_dictionary` VALUES ('96', 'picture_type', '2', '有证犬只正面图片', '2018-11-19 14:36:32');
INSERT INTO `t_dictionary` VALUES ('97', 'picture_type', '3', '收容犬图片', '2018-09-21 01:49:21');
INSERT INTO `t_dictionary` VALUES ('98', 'picture_type', '4', '投诉与建议图片', '2018-09-21 01:49:23');
INSERT INTO `t_dictionary` VALUES ('99', 'picture_type', '5', '病历图片', '2018-09-21 01:49:24');
INSERT INTO `t_dictionary` VALUES ('100', 'picture_type', '6', '违法记录图片', '2018-09-21 01:49:28');
INSERT INTO `t_dictionary` VALUES ('101', 'picture_type', '7', '禁养犬种图片', '2018-09-21 01:49:30');
INSERT INTO `t_dictionary` VALUES ('102', 'picture_type', '8', '医院证书图片', '2018-09-21 01:49:32');
INSERT INTO `t_dictionary` VALUES ('103', 'picture_type', '9', '医院logo', '2018-09-21 01:49:34');
INSERT INTO `t_dictionary` VALUES ('104', 'violation_type', '3', '无人看管', '2018-09-26 01:51:58');
INSERT INTO `t_dictionary` VALUES ('105', 'violation_type', '4', '随地大小便', '2018-09-26 01:52:57');
INSERT INTO `t_dictionary` VALUES ('106', 'violation_type', '5', '进入禁养区域', '2018-09-26 01:53:26');
INSERT INTO `t_dictionary` VALUES ('107', 'violation_type', '6', '伤人', '2018-09-26 01:53:39');
INSERT INTO `t_dictionary` VALUES ('108', 'violation_type', '7', '犬吠', '2018-09-26 01:54:01');
INSERT INTO `t_dictionary` VALUES ('109', 'violation_type', '8', '其他', '2018-09-26 01:54:08');
INSERT INTO `t_dictionary` VALUES ('112', 'dog_breed_type', '23', '牛头梗犬', '2019-07-11 10:23:51');
INSERT INTO `t_dictionary` VALUES ('113', 'dog_breed_type', '24', '大丹犬', '2019-07-11 10:23:24');
INSERT INTO `t_dictionary` VALUES ('114', 'picture_type', '10', '有证犬只侧面图片', '2018-11-19 14:37:39');
INSERT INTO `t_dictionary` VALUES ('115', 'picture_type', '11', '犬主手持省份证半生照', '2018-11-19 14:38:36');
INSERT INTO `t_dictionary` VALUES ('116', 'picture_type', '12', '犬主身份证正面照', '2018-11-19 14:39:20');
INSERT INTO `t_dictionary` VALUES ('117', 'picture_type', '13', '犬主身份证反面照', '2018-11-19 14:40:24');
INSERT INTO `t_dictionary` VALUES ('118', 'org_type', '3', '运营方', '2018-11-30 21:08:06');
INSERT INTO `t_dictionary` VALUES ('119', 'picture_type', '14', '病历记录图片', '2018-12-10 15:47:17');
INSERT INTO `t_dictionary` VALUES ('121', 'dog_breed_type', '25', '西藏敖犬', '2018-12-14 17:38:16');
INSERT INTO `t_dictionary` VALUES ('122', 'dog_breed_type', '26', '中亚牧羊犬', '2018-12-14 17:44:02');
INSERT INTO `t_dictionary` VALUES ('123', 'dog_breed_type', '27', '纽波利顿犬', '2018-12-14 17:50:04');
INSERT INTO `t_dictionary` VALUES ('124', 'dog_breed_type', '28', '罗德西亚背脊犬', '2018-12-14 17:52:40');
INSERT INTO `t_dictionary` VALUES ('125', 'dog_breed_type', '29', '法国波尔多獒犬', '2018-12-14 17:53:34');
INSERT INTO `t_dictionary` VALUES ('126', 'dog_breed_type', '30', '斗牛犬', '2018-12-14 17:54:19');
INSERT INTO `t_dictionary` VALUES ('127', 'dog_breed_type', '31', '伯思山犬', '2018-12-14 17:55:28');
INSERT INTO `t_dictionary` VALUES ('128', 'dog_breed_type', '32', '比特犬', '2018-12-14 17:56:08');
INSERT INTO `t_dictionary` VALUES ('129', 'notif_type', '1', '免疫通知', '2019-03-05 19:53:47');
INSERT INTO `t_dictionary` VALUES ('130', 'notif_type', '2', '预约通知', '2019-03-05 19:53:47');
INSERT INTO `t_dictionary` VALUES ('131', 'notif_type', '3', '喂药通知', '2019-03-05 19:53:47');
INSERT INTO `t_dictionary` VALUES ('132', 'notif_type', '4', '领养通知', '2019-03-05 19:53:47');
INSERT INTO `t_dictionary` VALUES ('133', 'notif_type', '5', '处罚通知', '2019-03-05 19:53:47');
INSERT INTO `t_dictionary` VALUES ('134', 'notif_type', '6', '低电告警', '2019-03-05 19:53:47');
INSERT INTO `t_dictionary` VALUES ('135', 'notif_type', '7', '进入禁养区通知', '2019-03-05 19:53:47');
INSERT INTO `t_dictionary` VALUES ('136', 'notif_type', '8', '审核通知', '2019-03-05 19:53:47');
INSERT INTO `t_dictionary` VALUES ('137', 'notif_type', '9', '黑名单通知', '2019-03-05 19:53:47');
INSERT INTO `t_dictionary` VALUES ('138', 'notif_type', '10', '其它', '2019-03-05 19:53:47');
INSERT INTO `t_dictionary` VALUES ('139', 'Immune_num_pre', '0', '宿A', '2019-07-09 21:11:52');
INSERT INTO `t_dictionary` VALUES ('140', 'complaint_result', '0', '收容', '2019-06-18 14:43:30');
INSERT INTO `t_dictionary` VALUES ('141', 'complaint_result', '3', '认领', '2019-06-18 14:46:47');
INSERT INTO `t_dictionary` VALUES ('142', 'complaint_result', '2', '捕杀', '2019-06-18 14:43:49');
INSERT INTO `t_dictionary` VALUES ('143', 'complaint_result', '4', '其他', '2019-06-18 14:46:44');
INSERT INTO `t_dictionary` VALUES ('144', 'dog_breed_type', '33', '京巴犬', '2019-07-11 09:19:39');
INSERT INTO `t_dictionary` VALUES ('145', 'dog_color_type', '14', '奶油色', '2019-07-11 10:24:48');
INSERT INTO `t_dictionary` VALUES ('146', 'dog_color_type', '15', '虎斑色', '2019-07-11 10:34:26');
INSERT INTO `t_dictionary` VALUES ('147', 'dog_color_type', '16', '三花', '2019-07-11 10:34:51');
INSERT INTO `t_dictionary` VALUES ('148', 'dog_color_type', '17', '黑白', '2019-07-11 10:35:33');
INSERT INTO `t_dictionary` VALUES ('149', 'dog_color_type', '18', '黄白', '2019-07-11 10:35:57');
INSERT INTO `t_dictionary` VALUES ('150', 'dog_color_type', '19', '黄色', '2019-07-11 10:36:15');
INSERT INTO `t_dictionary` VALUES ('151', 'picture_type', '16', '犬只无说明图片1', '2019-07-11 15:33:48');
INSERT INTO `t_dictionary` VALUES ('152', 'picture_type', '17', '犬只无说明图片2', '2019-07-11 15:34:13');
INSERT INTO `t_dictionary` VALUES ('153', 'dog_color_type', '20', '红棕色', '2019-07-13 11:09:50');
INSERT INTO `t_dictionary` VALUES ('154', 'dog_color_type', '21', '棕色', '2019-07-13 11:10:15');
INSERT INTO `t_dictionary` VALUES ('155', 'dog_color_type', '22', '土黄色', '2019-07-13 11:10:30');
INSERT INTO `t_dictionary` VALUES ('156', 'dog_color_type', '23', '灰色', '2019-07-13 11:20:19');
INSERT INTO `t_dictionary` VALUES ('157', 'dog_color_type', '24', '巧克力色', '2019-07-13 11:20:57');
INSERT INTO `t_dictionary` VALUES ('158', 'dog_color_type', '25', '香槟色', '2019-07-13 11:21:29');
INSERT INTO `t_dictionary` VALUES ('159', 'dog_color_type', '26', '花色', '2019-07-13 11:22:21');
INSERT INTO `t_dictionary` VALUES ('160', 'dog_color_type', '27', '黄色', '2019-07-13 11:22:30');
INSERT INTO `t_dictionary` VALUES ('161', 'dog_color_type', '28', '金黄色', '2019-07-13 11:22:49');
INSERT INTO `t_dictionary` VALUES ('162', 'dog_color_type', '29', '其他', '2019-07-13 19:33:22');
INSERT INTO `t_dictionary` VALUES ('163', 'dog_breed_type', '34', '凯恩梗犬', '2019-07-13 19:37:09');
INSERT INTO `t_dictionary` VALUES ('165', 'dog_breed_type', '35', '澳大利亚梗', '2019-07-13 19:41:31');
INSERT INTO `t_dictionary` VALUES ('166', 'dog_breed_type', '36', '法国狼犬', '2019-07-13 19:42:14');
INSERT INTO `t_dictionary` VALUES ('167', 'dog_breed_type', '37', '美国斯塔福郡梗', '2019-07-13 19:43:43');
INSERT INTO `t_dictionary` VALUES ('168', 'dog_breed_type', '38', '其他', '2019-07-13 20:28:36');
