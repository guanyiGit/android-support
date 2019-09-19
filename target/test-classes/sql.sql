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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `t_dog_card`;
CREATE TABLE `t_dog_card` (
  `seq_num` bigint(20) NOT NULL AUTO_INCREMENT,
  `dog_id` bigint(20) NOT NULL,
  `dog_owner_id` bigint(20) NOT NULL,
  `member_card_id` bigint(20) DEFAULT NULL,
  `dog_card_num` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `lssue_time` datetime DEFAULT NULL COMMENT '发证时间 （审批时间）',
  `lssue_org_id` int(11) DEFAULT NULL,
  `lssuer_id` bigint(20) DEFAULT NULL,
  `status` int(11) NOT NULL COMMENT '0已禁用，1启用/审核通过，2,已过期，3注销，4待审核，5审核不通过',
  `creation_time` datetime NOT NULL,
  `cause` varchar(255) DEFAULT NULL COMMENT '审核不通过原因',
  `isAudit` int(1) DEFAULT NULL COMMENT '1:小程序申办的犬证',
  `del_type` int(11) DEFAULT NULL COMMENT '注销类型0丢失1死亡2送养3违法',
  PRIMARY KEY (`seq_num`),
  KEY `idx_dog_id` (`dog_id`),
  KEY `idx_dog_owner_id` (`dog_owner_id`),
  KEY `idx_member_card_id` (`member_card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `t_dog_info`;
CREATE TABLE `t_dog_info` (
  `dog_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dog_owner_id` bigint(20) NOT NULL,
  `dog_breed_type` int(11) DEFAULT NULL,
  `dog_color_type` int(11) DEFAULT NULL,
  `dog_name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `birth_time` datetime DEFAULT NULL,
  `dog_gender` int(11) DEFAULT NULL COMMENT '0雄，1雌',
  `dog_character` varchar(50) DEFAULT NULL,
  `dog_remarks` text,
  `weight` double DEFAULT NULL,
  `dog_status` int(11) NOT NULL DEFAULT '0' COMMENT '0正常，1待领养，2收容犬，3已被处理,4犬只注销',
  `cancellation_reason` varchar(200) DEFAULT NULL COMMENT '注销原因',
  `cancellation_note` varchar(200) DEFAULT NULL COMMENT '注销备注',
  `cancellation_time` datetime DEFAULT NULL COMMENT '注销时间',
  `deduction_sum` double DEFAULT NULL,
  `father_id` bigint(50) DEFAULT NULL,
  `mother_id` bigint(50) DEFAULT NULL,
  `health_status` int(255) DEFAULT '0' COMMENT '0健康，1不健康',
  `operation_id` bigint(20) DEFAULT NULL,
  `creation_time` datetime DEFAULT NULL,
  `dogAge` double(11,1) DEFAULT NULL,
  PRIMARY KEY (`dog_id`),
  KEY `idx_dog_owner_id` (`dog_owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_dog_owner
-- ----------------------------
DROP TABLE IF EXISTS `t_dog_owner`;
CREATE TABLE `t_dog_owner` (
  `dog_owner_id` bigint(50) NOT NULL AUTO_INCREMENT,
  `district_id` bigint(50) DEFAULT NULL,
  `community` varchar(100) DEFAULT NULL,
  `dog_owner_type` int(11) DEFAULT '0' COMMENT '0个体户，1企业',
  `dog_owner_name` varchar(30) DEFAULT NULL,
  `dog_owner_phone` varchar(20) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL COMMENT '0男，1女',
  `e_mail` varchar(30) DEFAULT NULL,
  `postal_code` varchar(50) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `creation_time` datetime NOT NULL,
  `birth_date` datetime DEFAULT NULL,
  `ethnic` varchar(20) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`dog_owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_dog_owner_card
-- ----------------------------
DROP TABLE IF EXISTS `t_dog_owner_card`;
CREATE TABLE `t_dog_owner_card` (
  `dog_owner_card_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dog_owner_id` bigint(20) NOT NULL,
  `card_type` int(11) DEFAULT NULL,
  `card_num` varchar(100) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `creation_time` datetime NOT NULL,
  PRIMARY KEY (`dog_owner_card_id`),
  KEY `idx_dog_owner_id` (`dog_owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `t_vaccine_register`;
CREATE TABLE `t_vaccine_register` (
  `vaccine_register_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dog_id` bigint(20) NOT NULL,
  `org_id` int(11) NOT NULL,
  `injection_type` int(11) DEFAULT NULL,
  `ref_id` bigint(20) DEFAULT NULL,
  `vaccine_name` varchar(50) DEFAULT NULL,
  `vaccine_producer` varchar(50) DEFAULT NULL,
  `vaccine_num` varchar(50) DEFAULT NULL,
  `vaccine_count` int(11) DEFAULT NULL,
  `injection_interval_days` int(11) DEFAULT NULL,
  `first_injection_date` datetime NOT NULL,
  `schedule` int(11) NOT NULL,
  `creation_time` datetime NOT NULL,
  PRIMARY KEY (`vaccine_register_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `t_vaccine_injection`;
CREATE TABLE `t_vaccine_injection` (
  `vaccine_injection_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '疫苗注射记录id',
  `vaccine_register_id` bigint(20) DEFAULT NULL COMMENT '疫苗注射登记id',
  `vaccine_injection_count` int(11) DEFAULT NULL COMMENT '当前注射次数',
  `injection_date` datetime DEFAULT NULL COMMENT '本次计划注射时间',
  `fact_injection_date` datetime DEFAULT NULL COMMENT '实际注射时间',
  `next_injection_date` datetime DEFAULT NULL COMMENT 'next_injection_date',
  `injection_status` bigint(20) NOT NULL COMMENT '注射状态0.已注射 1.未注射(默认1)',
  `operator_id` bigint(20) NOT NULL COMMENT '注射人id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `vaccine_name` varchar(50) DEFAULT NULL COMMENT '疫苗名称',
  `vaccine_producer` varchar(50) DEFAULT NULL COMMENT '疫苗厂家',
  `vaccine_num` varchar(50) DEFAULT NULL COMMENT '疫苗编号',
  `send` int(11) DEFAULT '0' COMMENT '0未发送; 1已发送。',
  `vac_org` int(11) DEFAULT NULL COMMENT '免疫机构',
  `dog_id` bigint(20) NOT NULL,
  PRIMARY KEY (`vaccine_injection_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE t_vaccine_injection ADD COLUMN veterinarian VARCHAR(255) DEFAULT NULL COMMENT '注射人（兽医)';


DROP TABLE IF EXISTS `t_immune_card`;
CREATE TABLE `t_immune_card` (
  `seq_id` int(20) NOT NULL AUTO_INCREMENT,
  `dog_owner_id` int(20) NOT NULL,
  `dog_id` int(20) NOT NULL,
  `member_card_id` bigint(20) DEFAULT NULL,
  `immune_card_num` varchar(100) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `lssue_time` datetime DEFAULT NULL,
  `lssue_org_id` int(20) DEFAULT NULL,
  `lssuer_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `creation_time` datetime DEFAULT NULL,
  PRIMARY KEY (`seq_id`),
  UNIQUE KEY `uqe_immune_card_num` (`immune_card_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'device_type', '0', 'RFID', '2018-08-21 02:02:16');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'device_type', '1', '项圈', '2018-08-21 02:02:32');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '0', '中华田园犬', '2018-09-14 11:41:49');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '1', '泰迪熊犬', '2018-08-21 02:09:30');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '2', '比熊犬', '2018-08-21 02:09:55');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '3', '博美犬', '2018-08-21 02:10:15');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '4', '吉娃娃犬', '2018-08-20 18:15:17');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '5', '巴哥犬', '2018-08-20 18:15:31');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '6', '雪纳瑞犬', '2018-08-20 18:15:58');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '7', '法国斗牛犬', '2018-08-20 18:16:26');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '8', '英国斗牛犬', '2018-08-20 18:16:45');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '9', '柯基犬', '2018-08-20 18:17:02');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '10', '比格犬', '2018-08-20 18:17:13');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '11', '金毛犬', '2018-08-20 18:17:56');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '12', '拉布拉多犬', '2018-08-20 18:18:14');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '13', '萨摩耶犬', '2018-08-20 18:18:28');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '14', '哈士奇犬', '2018-08-20 18:18:40');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '15', '边境牧羊犬', '2018-08-20 18:18:56');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '16', '德国牧羊犬', '2018-08-20 18:19:07');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '17', '秋田犬', '2018-08-20 18:19:25');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '18', '阿拉斯加犬', '2018-08-20 18:19:41');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '19', '高加索犬', '2018-08-20 18:19:51');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '20', '大白熊犬', '2018-08-20 18:20:07');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '21', '圣伯纳犬', '2018-08-20 18:20:24');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_breed_type', '22', '苏格兰牧羊犬', '2018-08-20 18:20:38');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_color_type', '0', '白色', '2018-09-14 11:41:53');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_color_type', '1', '黑色', '2018-08-20 19:03:20');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_color_type', '2', '黄色', '2019-06-10 11:47:11');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_color_type', '3', '黑白', '2019-06-10 11:47:16');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_color_type', '4', '黑黄', '2019-06-10 11:47:21');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_color_type', '5', '黄白', '2019-06-10 11:47:27');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_color_type', '6', '黑褐色', '2018-08-20 19:04:03');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_color_type', '7', '铁灰色', '2018-08-20 19:04:13');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_color_type', '8', '灰褐色', '2018-08-20 19:04:22');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_color_type', '9', '黄褐色', '2018-08-20 19:04:36');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_color_type', '10', '灰白色', '2018-08-20 19:04:53');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_color_type', '11', '灰白色', '2018-08-20 19:05:04');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_color_type', '12', '黄红色', '2018-08-20 19:05:18');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_color_type', '13', '淡红色', '2018-08-20 19:05:29');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_owner_type', '0', '个人', '2018-08-20 19:06:19');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'dog_owner_type', '1', '企业', '2018-08-20 19:06:16');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'card_type', '0', '身份证', '2018-08-20 19:07:17');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'card_type', '1', '房产证', '2018-08-20 19:07:27');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'card_type', '2', '营业执照', '2018-08-20 19:07:35');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'card_type', '3', '其他', '2018-08-20 19:07:43');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'violation_type', '1', '不牵犬绳', '2018-08-20 19:15:45');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'violation_type', '2', '犬只扰民', '2018-08-20 19:57:54');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'punish_type', '0', '扣分', '2018-08-20 20:02:01');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'punish_type', '1', '收容', '2018-08-29 17:29:22');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'punish_type', '2', '其他', '2018-08-29 17:29:25');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'coll_dog_type', '0', '依法收缴犬', '2018-08-20 20:03:30');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'coll_dog_type', '1', '无主流浪犬', '2018-08-29 17:28:40');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'coll_dog_type', '2', '其他', '2018-08-29 17:28:45');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'biz_type', '0', '看病', '2018-08-22 02:45:16');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'biz_type', '1', '办证', '2018-08-22 02:45:44');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'biz_type', '2', '年检', '2018-08-22 02:46:15');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'biz_type', '3', '免疫', '2018-08-22 02:46:41');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'appointment_channel', '0', '电话预约', '2018-08-23 23:15:01');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'appointment_channel', '1', '网上预约', '2019-03-07 09:47:04');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'appointment_channel', '2', '门面预约', '2018-08-23 15:15:57');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'appointment_channel', '3', '其他', '2018-08-23 15:16:07');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'org_type', '0', '犬只办', '2018-08-23 15:56:43');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'org_type', '1', '收容所', '2018-08-23 15:57:35');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'org_type', '2', '宠物医院', '2018-08-23 15:57:43');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'district_type', '0', '省级', '2018-08-23 20:57:23');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'district_type', '1', '市级', '2018-08-23 20:57:35');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'district_type', '2', '区级', '2018-08-23 20:57:50');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'district_type', '3', '街道', '2019-06-11 17:35:14');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'district_type', '4', '社区', '2019-06-11 17:35:17');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'pet_h_card_type', '0', '动物诊疗许可证', '2018-08-24 15:50:48');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'pet_h_card_type', '1', '动物防疫合格证', '2018-08-24 15:51:07');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'pet_h_card_type', '2', '工商营业执照', '2018-08-24 15:51:21');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'pet_h_card_type', '3', '兽医资格证', '2018-08-24 15:51:33');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'information_type', '0', '政策法规', '2018-08-24 20:00:16');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'information_type', '1', '动态资讯', '2018-08-24 20:01:04');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'information_type', '2', '养犬宣传', '2018-08-24 20:01:17');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'information_type', '3', '服务指南', '2018-08-24 20:01:26');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'logout_reason', '0', '死亡', '2018-09-09 19:13:30');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'logout_reason', '1', '丢失', '2018-09-09 19:13:42');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'logout_reason', '2', '异地送葬', '2018-09-09 19:13:51');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'picture_type', '1', '资讯图片', '2018-09-21 09:49:18');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'picture_type', '2', '有证犬只正面图片', '2018-11-20 12:06:35');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'picture_type', '3', '收容犬图片', '2018-09-21 09:49:21');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'picture_type', '4', '投诉与建议图片', '2018-09-21 09:49:23');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES ( 'picture_type', '5', '病历图片', '2018-09-21 09:49:24');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'picture_type', '6', '违法记录图片', '2018-09-21 09:49:28');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'picture_type', '7', '禁养犬种图片', '2018-09-21 09:49:30');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'picture_type', '8', '医院证书图片', '2018-09-21 09:49:32');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'picture_type', '9', '医院logo', '2018-09-21 09:49:34');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'violation_type', '3', '无人看管', '2018-09-26 09:51:58');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'violation_type', '4', '随地大小便', '2018-09-26 09:52:57');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'violation_type', '5', '进入禁养区域', '2018-09-26 09:53:26');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'violation_type', '6', '伤人', '2018-09-26 09:53:39');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'violation_type', '7', '犬吠', '2018-09-26 09:54:01');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'violation_type', '8', '其他', '2018-09-26 09:54:08');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'health_status', '0', '健康', '2018-09-26 19:55:13');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'health_status', '1', '不健康', '2018-09-26 19:55:21');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'health_status', '2', '其他', '2018-09-26 19:55:30');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '23', '测试犬种', '2018-11-05 22:21:26');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '24', '测试2', '2018-11-05 22:24:56');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '25', '测试00000', '2018-11-05 22:39:47');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '26', '测试', '2018-11-05 22:53:32');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '27', '测试444444444', '2018-11-05 23:43:12');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '28', '测试55555555555555', '2018-11-06 00:00:40');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '29', '测试狗狗', '2018-11-09 23:49:04');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '30', '狗狗2', '2018-11-09 23:50:16');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '31', 'aaa', '2018-11-09 23:56:41');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '32', '犬种信息', '2018-11-10 00:01:08');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '33', 'werwr', '2018-11-10 00:51:03');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '34', 'rttet', '2018-11-10 01:00:14');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'org_type', '3', '运营方', '2018-11-10 01:55:20');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '35', '好大的狗', '2018-11-12 18:02:10');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '36', 'sasdsasa', '2018-11-13 01:15:42');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '37', 'daa', '2018-11-13 01:16:54');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '38', 's', '2018-11-13 01:17:38');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '39', 'dwadad', '2018-11-13 01:18:24');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '40', '达瓦达', '2018-11-14 17:53:47');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '41', '凶犬', '2018-11-14 19:55:51');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '42', 'vg', '2018-11-15 22:24:49');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'picture_type', '10', '有证犬只侧面图片', '2018-11-20 12:07:35');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'picture_type', '11', '犬主手持省份证半生照', '2018-11-20 12:08:04');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'picture_type', '12', '犬主身份证正面照', '2018-11-20 12:08:25');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'picture_type', '13', '犬主身份证反面照', '2018-11-20 12:08:50');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '43', '666', '2018-12-10 23:12:21');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '44', '555', '2018-12-10 23:27:29');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'picture_type', '14', '病历记录图片', '2018-12-10 15:47:50');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '45', 'ceddd', '2018-12-17 23:27:10');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '46', 'cegougou', '2018-12-17 23:32:10');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '47', 'ceddded', '2018-12-17 23:34:50');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '48', '201ddd', '2018-12-17 23:37:02');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'notif_type', '1', '免疫通知', '2019-03-05 18:38:19');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'notif_type', '2', '预约通知', '2019-03-05 18:38:19');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'notif_type', '3', '喂药通知', '2019-03-05 18:38:19');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'notif_type', '4', '领养通知', '2019-03-05 18:38:19');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'notif_type', '5', '处罚通知', '2019-03-05 18:38:19');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'notif_type', '6', '低电告警', '2019-03-05 18:38:19');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'notif_type', '7', '进入禁养区通知', '2019-03-05 18:38:19');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'notif_type', '8', '审核通知', '2019-03-05 18:38:19');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'notif_type', '9', '黑名单通知', '2019-03-05 18:38:19');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'notif_type', '10', '其它', '2019-03-05 18:38:19');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'org_type', '4', '犬主', '2019-03-07 02:10:51');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '49', 'ceshigou', '2019-03-07 18:25:05');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '50', 'ceshigou1', '2019-03-07 18:25:26');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '51', 'ceshi', '2019-03-07 22:21:32');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '52', 'ceshi2', '2019-03-07 22:44:29');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'Immune_num_pre', '0', '青A', '2019-03-12 11:57:27');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'org_type', '5', '电信', '2019-03-25 16:42:24');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '53', '测试犬', '2019-05-30 11:55:40');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '54', '测试犬3', '2019-05-30 11:56:13');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '55', '测试犬2', '2019-05-30 11:56:43');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '56', '测试犬4', '2019-05-30 11:57:01');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '57', '测试犬5', '2019-05-30 11:57:09');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '58', '测试犬6', '2019-05-30 11:57:19');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '59', '测试犬7', '2019-05-30 11:57:42');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '60', '测试犬8', '2019-05-30 11:57:51');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'complaint_result', '0', '收容', '2019-06-10 11:52:19');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'complaint_result', '1', '认领', '2019-06-10 11:52:55');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'complaint_result', '2', '捕杀', '2019-06-10 11:53:21');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'complaint_result', '3', '其他', '2019-06-10 11:53:36');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'picture_type', '15', '流浪犬图片', '2019-06-11 10:07:06');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'org_type', '6', '城管', '2019-06-11 18:03:21');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'org_type', '7', '卫健委', '2019-06-11 18:03:47');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'org_type', '8', '社区', '2019-06-11 18:03:58');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '61', '测试犬45', '2019-06-17 14:04:52');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '62', '二二', '2019-06-17 14:05:21');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '63', '哥反对', '2019-06-17 14:11:39');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '64', '大夫十分', '2019-06-17 14:12:01');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'book_num', '200', '预约次数', '2019-07-10 14:37:25');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'org_type', '9', '办证点', '2019-07-09 11:26:23');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '65', '范文芳', '2019-07-10 10:21:36');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'dog_breed_type', '33', '京巴犬', '2019-07-11 09:19:39');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'picture_type', '16', '犬只无说明图片1', '2019-07-11 10:33:16');
INSERT INTO `t_dictionary` (dictionary_field,dictionary_value,dictionary_describe,creation_time) VALUES (  'picture_type', '17', '犬只无说明图片2', '2019-07-11 10:33:20');

