use shogaku;
drop table if exists `head_line`;
CREATE TABLE `head_line`
(
    `id`             INT(100) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`           VARCHAR(100) DEFAULT NULL COMMENT '头条名称',
    `link`           VARCHAR(200)         NOT NULL COMMENT '头条链接',
    `img_url`        VARCHAR(2000)        NOT NULL COMMENT '头条图片地址',
    `priority`       INT(2)       DEFAULT NULL COMMENT '展示优先级',
    `enable_status`  INT(2)               NOT NULL COMMENT '可用状态',
    `create_time`    DATETIME     DEFAULT NULL COMMENT '创建时间',
    `last_edit_time` DATETIME     DEFAULT NULL COMMENT '最近修改时间'
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = UTF8;
drop table if exists `shop_category`;
create table `shop_category`
(
    `id`             int(11) primary key not null auto_increment comment '店铺类别id',
    `name`           varchar(100)        not null default '' comment '店铺类别名称',
    `desc`           varchar(1000)                default '' comment '店铺类别描述',
    `imgUrl`         varchar(2000)                default null comment '店铺类别图片url',
    `priority`       int(2)              not null default 0 comment '店铺类别展示优先级',
    `create_time`    datetime                     default null comment '创建时间',
    `last_edit_time` datetime                     default null comment '最近修改时间',
    `parent_id`      int(11)                      default null comment '店铺类别的父类id',
    KEY `fk_shop_category_self` (`parent_id`),
    constraint `fk_shop_category_self` foreign key (`parent_id`)
        references `shop_category` (`id`)
) engine = InnoDB
  auto_increment = 1
  default charset = utf8;