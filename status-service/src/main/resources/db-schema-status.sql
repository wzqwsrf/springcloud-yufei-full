-- 状态表结构定义
-- 表名: status

CREATE TABLE IF NOT EXISTS `status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `retweet_count` bigint(20) DEFAULT '0' COMMENT '点赞数',
  `fav_count` bigint(20) DEFAULT '0' COMMENT '点赞数',
  `like_count` bigint(20) DEFAULT '0' COMMENT '点赞数',
  `reply_count` bigint(20) DEFAULT '0' COMMENT '点赞数',
  `create_at` bigint(20) NOT NULL COMMENT '创建时间戳',
  `update_at` bigint(20) DEFAULT '0' COMMENT '更新时间戳',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标记(0:未删除,1:已删除)',
  `version` int(11) DEFAULT '0' COMMENT '版本号(乐观锁)',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COLLATE=utf8mb4_unicode_ci COMMENT='状态表';
