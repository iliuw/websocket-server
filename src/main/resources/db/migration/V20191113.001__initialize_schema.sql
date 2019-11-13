-- Initialize database schema

-- im_session
CREATE TABLE `im_session` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `session_id` char(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '会话ID',
  `creator` char(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者SocketIOClient-ID',
  `participant` mediumtext COLLATE utf8mb4_general_ci COMMENT '参与者SocketIOClient-ID及在线标志数组，json串。不包括创建者。示例：[{clientId:xxxx, online:true}, {clientId:xxxx, online:false},...]',
  `type` enum('single','multi') COLLATE utf8mb4_general_ci NOT NULL COMMENT '会话类型，single=点对点；multi=多方对话',
  `state` enum('alive','dead') COLLATE utf8mb4_general_ci NOT NULL COMMENT '会话状态，alive=活动；dead=死亡',
  `resource` mediumtext COLLATE utf8mb4_general_ci COMMENT '关联的系统资源，一个Json对象。{id:xxx,type:xxx,link:xxx}',
  `last_message` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最近一条会话消息ID',
  `total` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '消息总数量',
  `unread` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '未读消息总量',
  `last_update` datetime NOT NULL COMMENT '最近更新时间',
  `version` int(4) unsigned NOT NULL DEFAULT '1' COMMENT 'version-locker',
  PRIMARY KEY (`id`),
  UNIQUE KEY `udx_imsess_id` (`session_id`),
  KEY `idx_imsess_creator` (`creator`),
  KEY `idx_imsess_type` (`type`),
  KEY `idx_imsess_state` (`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='IM会话表';

-- im_message
CREATE TABLE `im_message` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '记录时间',
  `message_id` char(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息ID',
  `creator` char(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '发布者ID',
  `session_id` char(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属会话ID，将决定谁接收',
  `content` tinytext COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息正文，json格式',
  `type` enum('text','media','audio','vedio','doc','monitor') COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息类型，text=文本；media=图片；audio=音频；vedio=视频；doc=文件；monitor=摄像头视频',
  `state` enum('pushing','pushed','timeout','repeal') COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息状态，pushing=发送中；pushed=已发送；timeout=发送超时；repeal=已撤销',
  `version` int(4) unsigned NOT NULL DEFAULT '1' COMMENT 'version-lock',
  PRIMARY KEY (`id`,`created`),
  UNIQUE KEY `udx_immsg_id` (`message_id`),
  KEY `idx_immsg_creator` (`creator`),
  KEY `idx_immsg_session` (`session_id`),
  KEY `idx_immsg_type` (`type`),
  KEY `idx_immsg_state` (`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='IM消息表';

-- attachment
CREATE TABLE `attachment` (
  `id` char(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '附件ID',
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '记录时间',
  `origin_name` varchar(240) COLLATE utf8mb4_general_ci NOT NULL COMMENT '附件原始名称',
  `dest_name` varchar(240) COLLATE utf8mb4_general_ci NOT NULL COMMENT '附件存储名称',
  `save_path` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '存储路径',
  `extension` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件扩展名',
  `length` bigint(20) NOT NULL COMMENT '文件长度',
  `type` enum('file','image','audio','vedio') COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件类型',
  `updated` datetime NOT NULL COMMENT '更新时间',
  `version` int(4) unsigned NOT NULL DEFAULT '1' COMMENT 'version-lock',
  PRIMARY KEY (`id`,`created`),
  KEY `idx_attach_origin` (`origin_name`),
  KEY `idx_attach_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='附件表';

-- notice_info
CREATE TABLE `notice_info` (
  `id` char(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知ID',
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '发布时间',
  `label` varchar(245) COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知标题',
  `content` tinytext COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知正文，一个Json对象',
  `creator` char(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '发布者ID, system表示是系统自动发布的通知',
  `receiver` mediumtext COLLATE utf8mb4_general_ci COMMENT '接收者ID列表，一个Json对象，为空表示系统全部用户都接收',
  `system_id` char(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '归属系统ID，允许为空',
  `resource` mediumtext COLLATE utf8mb4_general_ci COMMENT '关联资源，一个Json对象。{id:xxx,type:xxx,link:xxx}',
  `type` enum('text','media','audio','vedio','doc') COLLATE utf8mb4_general_ci NOT NULL COMMENT '正文类型，text=文本；media=图片；audio=音频；vedio=视频；doc=文件',
  `state` enum('unpush','pushed','repeal') COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知状态，unpush=未推送；pushed=已推送；repeal=已撤销',
  `updated` datetime NOT NULL COMMENT '最近更新时间',
  `version` int(4) unsigned NOT NULL DEFAULT '1' COMMENT 'version-lock',
  PRIMARY KEY (`id`,`created`),
  KEY `idx_notice_creator` (`creator`),
  KEY `idx_notice_system` (`system_id`),
  KEY `idx_notice_type` (`type`),
  KEY `idx_notice_state` (`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='通知消息表';

-- notice_push_log
CREATE TABLE `notice_push_log` (
  `id` char(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '记录ID',
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '记录时间',
  `notice_id` char(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知ID',
  `receiver` char(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '接收者ID',
  `notice` mediumtext COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知实体',
  `has_read` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '已读标识，0=未读，1=已读',
  `read_time` datetime DEFAULT NULL COMMENT '读取时间',
  `version` int(4) unsigned NOT NULL DEFAULT '1' COMMENT 'version-lock',
  PRIMARY KEY (`id`,`created`),
  KEY `idx_pushed_notice` (`notice_id`),
  KEY `idx_pushed_receiver` (`receiver`),
  KEY `idx_pushed_readed` (`has_read`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='通知消息推送表';