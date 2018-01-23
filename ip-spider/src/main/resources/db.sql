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
  `time` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create` TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;