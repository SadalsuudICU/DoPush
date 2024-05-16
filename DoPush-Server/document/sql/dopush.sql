SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

drop database if exists `dopush`;
create database `dopush`;
use `dopush`;



drop table IF EXISTS message_template;
CREATE TABLE `message_template`
(
    `id`               bigint(20)                               NOT NULL AUTO_INCREMENT,
    `name`             varchar(100) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '标题',
    `audit_status`     tinyint(4)                               NOT NULL DEFAULT '0' COMMENT '当前消息审核状态： 10.待审核 20.审核成功 30.被拒绝',
    `flow_id`          varchar(50) COLLATE utf8mb4_unicode_ci COMMENT '工单ID',
    `msg_status`       tinyint(4)                               NOT NULL DEFAULT '0' COMMENT '当前消息状态：10.新建 20.停用 30.启用 40.等待发送 50.发送中 60.发送成功 70.发送失败',
    `cron_task_id`     bigint(20) COMMENT '定时任务Id (xxl-job-admin返回)',
    `cron_crowd_path`  varchar(500) COMMENT '定时发送人群的文件路径',
    `expect_push_time` varchar(100) COLLATE utf8mb4_unicode_ci COMMENT '期望发送时间：0:立即发送 定时任务以及周期任务:cron表达式',
    `id_type`          tinyint(4)                               NOT NULL DEFAULT '0' COMMENT '消息的发送ID类型：10. userId 20.did 30.手机号 40.openId 50.email 60.企业微信userId',
    `send_channel`     int(10)                                  NOT NULL DEFAULT '0' COMMENT '消息发送渠道：10.IM 20.Push 30.短信 40.Email 50.公众号 60.小程序 70.企业微信 80.钉钉机器人 90.钉钉工作通知 100.企业微信机器人 110.飞书机器人 110. 飞书应用消息 ',
    `template_type`    tinyint(4)                               NOT NULL DEFAULT '0' COMMENT '10.运营类 20.技术类接口调用',
    `msg_type`         tinyint(4)                               NOT NULL DEFAULT '0' COMMENT '10.通知类消息 20.营销类消息 30.验证码类消息',
    `shield_type`      tinyint(4)                               NOT NULL DEFAULT '0' COMMENT '10.夜间不屏蔽 20.夜间屏蔽 30.夜间屏蔽(次日早上9点发送)',
    `msg_content`      varchar(4096) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '消息内容 占位符用{$var}表示',
    `send_account`     int(10)                                  NOT NULL DEFAULT '0' COMMENT '发送账号 一个渠道下可存在多个账号',
    `creator`          varchar(45) COLLATE utf8mb4_unicode_ci   NOT NULL DEFAULT '' COMMENT '创建者',
    `updater`          varchar(45) COLLATE utf8mb4_unicode_ci   NOT NULL DEFAULT '' COMMENT '更新者',
    `auditor`          varchar(45) COLLATE utf8mb4_unicode_ci   NOT NULL DEFAULT '' COMMENT '审核人',
    `team`             varchar(45) COLLATE utf8mb4_unicode_ci   NOT NULL DEFAULT '' COMMENT '业务方团队',
    `proposer`         varchar(45) COLLATE utf8mb4_unicode_ci   NOT NULL DEFAULT '' COMMENT '业务方',
    `is_deleted`       tinyint(4)                               NOT NULL DEFAULT '0' COMMENT '是否删除：0.不删除 1.删除',
    `created`          int(11)                                  NOT NULL DEFAULT '0' COMMENT '创建时间',
    `updated`          int(11)                                  NOT NULL DEFAULT '0' COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_channel` (`send_channel`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='消息模板信息';



drop table IF EXISTS `sms_record`;
CREATE TABLE `sms_record`
(
    `id`                  bigint(20)                              NOT NULL AUTO_INCREMENT,
    `message_template_id` bigint(20)                              NOT NULL DEFAULT '0' COMMENT '消息模板ID',
    `phone`               bigint(20)                              NOT NULL DEFAULT '0' COMMENT '手机号',
    `supplier_id`         tinyint(4)                              NOT NULL DEFAULT '0' COMMENT '发送短信渠道商的ID',
    `supplier_name`       varchar(40) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '发送短信渠道商的名称',
    `msg_content`         varchar(600) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '短信发送的内容',
    `series_id`           varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '下发批次的ID',
    `charging_num`        tinyint(4)                              NOT NULL DEFAULT '0' COMMENT '计费条数',
    `report_content`      varchar(50)                             NOT NULL DEFAULT '' COMMENT '回执内容',
    `status`              tinyint(4)                              NOT NULL DEFAULT '0' COMMENT '短信状态： 10.发送 20.成功 30.失败',
    `send_date`           int(11)                                 NOT NULL DEFAULT '0' COMMENT '发送日期：20211112',
    `created`             int(11)                                 NOT NULL DEFAULT '0' COMMENT '创建时间',
    `updated`             int(11)                                 NOT NULL DEFAULT '0' COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_send_date` (`send_date`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='短信记录信息';



drop table IF EXISTS channel_account;
CREATE TABLE `channel_account`
(
    `id`             bigint(20)                               NOT NULL AUTO_INCREMENT,
    `name`           varchar(100) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' COMMENT '账号名称',
    `send_channel`   tinyint(4)                               NOT NULL DEFAULT '0' COMMENT '消息发送渠道：10.IM 20.Push 30.短信 40.Email 50.公众号 60.小程序 70.企业微信 80.钉钉机器人 90.钉钉工作通知 100.企业微信机器人 110.飞书机器人 110. 飞书应用消息 ',
    `account_config` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '账号配置',
    `creator`        varchar(128) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT 'Java3y' COMMENT '拥有者',
    `created`        int(11)                                  NOT NULL DEFAULT '0' COMMENT '创建时间',
    `updated`        int(11)                                  NOT NULL DEFAULT '0' COMMENT '更新时间',
    `is_deleted`     tinyint(4)                               NOT NULL DEFAULT '0' COMMENT '是否删除：0.不删除 1.删除',
    PRIMARY KEY (`id`),
    KEY `idx_send_channel` (`send_channel`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='渠道账号信息';


drop table if exists `request_log`;
CREATE TABLE `request_log`
(
    `id`            varchar(128)                             NOT NULL,
    `uri`           varchar(64)                              NOT NULL COMMENT '接口url',
    `method`        varchar(32)                              NOT NULL COMMENT '请求方法',
    `args`          varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求参数',
    `auth`          tinyint(2)                                        DEFAULT 0 COMMENT '是否需要认证',
    `token`         varchar(128) COLLATE utf8mb4_unicode_ci COMMENT '身份令牌',
    `login_account` varchar(128) COLLATE utf8mb4_unicode_ci COMMENT '登录账号信息',
    `product`       varchar(128) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '产品名称',
    `path`          varchar(128) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '请求路径：类名+方法',
    `referer`       varchar(128) COLLATE utf8mb4_unicode_ci COMMENT '引用',
    `remote_addr`   varchar(128) COLLATE utf8mb4_unicode_ci COMMENT '远程调用地址',
    `user_agent`    varchar(128) COLLATE utf8mb4_unicode_ci COMMENT '用户客户端',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='请求日志';



drop table if exists `user`;
CREATE TABLE `user`
(
    `id`       bigint(20)  NOT NULL AUTO_INCREMENT,
    `username` varchar(64) NOT NULL COMMENT '用户名',
    `password` varchar(64) NOT NULL default 'dopush123456' COMMENT '密码',
    `role`  int         NOT NULL COMMENT '角色: 0->管理 1->审查 2->运营',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户表';
insert into `user` values (1, 'admin', 'admin', 0);
insert into `user` values (2, 'examiner', 'examiner', 1);
insert into `user` values (3, 'salesperson', 'salesperson', 2);


# drop table if exists `role`;
# CREATE TABLE `role`
# (
#     `id`        bigint(20)  NOT NULL AUTO_INCREMENT,
#     description varchar(64) NOT NULL COMMENT '角色名称',
#     PRIMARY KEY (`id`)
# ) ENGINE = InnoDB
#   AUTO_INCREMENT = 1
#   DEFAULT CHARSET = utf8mb4
#   COLLATE = utf8mb4_unicode_ci COMMENT ='角色表';


# drop table if exists `dept`;
# CREATE TABLE `dept`
# (
#     `id`          bigint(20)  NOT NULL AUTO_INCREMENT,
#     `description` varchar(64) NOT NULL COMMENT '部门描述',
#     PRIMARY KEY (`id`)
# ) ENGINE = InnoDB
#   AUTO_INCREMENT = 1
#   DEFAULT CHARSET = utf8mb4
#   COLLATE = utf8mb4_unicode_ci COMMENT ='部门表';


drop table if exists `user_dimension_data`;
CREATE TABLE `user_dimension_data`
(
    `id`          int(11)                                 NOT NULL DEFAULT '0' COMMENT '产出时间',
    `receiver`    varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户',
    `anchor_info` json COMMENT '点位信息',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户维度数据';


drop table if exists `template_dimension_data`;
CREATE TABLE `template_dimension_data`
(
    `id`          int(11)                                 NOT NULL DEFAULT '0' COMMENT '产出时间',
    `business_id` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务id',
    `anchor_info` json COMMENT '点位信息',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='每日消息模板维度数据';


drop table if exists `message_dimension_data`;
CREATE TABLE `message_dimension_data`
(
    `id`          int(11)                                 NOT NULL DEFAULT '0' COMMENT '产出时间',
    `message_key` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '构建messageId维度的链路信息key',
    `anchor_info` json COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='messageId维度的链路信息';

drop table if exists `material`;
CREATE TABLE `material`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `type`        int        NOT NULL default 0 COMMENT '素材类型',
    `creator`     varchar(128)        NOT NULL COMMENT '上传者',
    `name`        varchar(128) COMMENT '素材文件名',
    `path`        varchar(256) COMMENT '素材文件存储地址',
    `create_time` int COMMENT '素材创建时间',
    `is_deleted`  tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除：0.不删除 1.删除',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    AUTO_INCREMENT = 1
    COLLATE = utf8mb4_unicode_ci COMMENT ='素材';


drop table if exists `failed_task`;
CREATE TABLE `failed_task`
(
    `messageId`         varchar(200) NOT NULL COMMENT '消息唯一id',
    `bizId`             varchar(200) NOT NULL COMMENT '业务消息发送id',
    `messageTemplateId` varchar(200) NOT NULL COMMENT '消息模板id',
    `businessId`        bigint default 0 COMMENT '业务Id(数据追踪使用)',
    `receiver`          text         NOT NULL COMMENT '接受人群',
    `time`              int COMMENT '录入时间',
    primary key (`messageId`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT '失败任务信息';


