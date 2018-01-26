DROP TABLE IF EXISTS `ip_pool`;

CREATE TABLE `ip_pool` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `port` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `foot_odds`;

CREATE TABLE `foot_odds` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vs` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `win` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `flat` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `loss` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `screen` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '场次',
  `matchTime` TIMESTAMP ,
  `createTime` TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `foot_data`;

CREATE TABLE `foot_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `winData` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `flatData` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lossData` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `result` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '结果',
  `type` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '类型',
  `createTime` TIMESTAMP COMMENT '创建时间',
  `footName` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '结果',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `foot_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `footName` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `info` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `chance` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `result` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '结果',
  `type` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '类型',
  `createTime` TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;