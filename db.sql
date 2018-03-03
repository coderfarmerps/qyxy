CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(128) NOT NULL COMMENT '账号',
  `password` varchar(64) NOT NULL COMMENT '密码，未加密',
  `account_type_id` bigint(20) NOT NULL COMMENT '账号类型 ',
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号表';


CREATE TABLE `account_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '账号类型名称 qq 微信 知乎 12306 微博',
  `code` varchar(64) NOT NULL COMMENT '账号类型code  qq weixin zhihu 12306 weibo',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号类型表';

CREATE TABLE `remind_date` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `important_date` bigint(20) NOT NULL COMMENT '时间',
  `remark` varchar(512) NOT NULL COMMENT '提醒内容',
  `remind_times` int(11) NOT NULL DEFAULT '0' COMMENT '提醒次数',
  `is_finished` int(11) NOT NULL DEFAULT '0' COMMENT '是否完成，1. 是 0. 否',
  `next_remind_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '下次通知时间，自动生成',
  `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '删除标志',
  `title` varchar(64) NOT NULL COMMENT '标题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='日期记录表，有提醒功能';
