CREATE DATABASE lunar;

USE lunar;

-- 用户表
CREATE TABLE if NOT EXISTS `user`(
		`user_id` INT(6) ZEROFILL AUTO_INCREMENT COMMENT '用户id(主键)',
		`user_account` VARCHAR(12) NOT NULL UNIQUE COMMENT '用户手机号',
		`user_password` VARCHAR(255) NOT NULL COMMENT '用户账号密码',
		`user_name` VARCHAR(40) NOT NULL COMMENT '用户昵称',
		`user_birthday`  TIMESTAMP COMMENT '用户出生时间',
		`user_signature` VARCHAR(255) COMMENT '用户个性签名', 
		`user_follow_number` INT NOT NULL DEFAULT(0) COMMENT '用户关注的人数',
		`user_fans_number` INT NOT NULL DEFAULT(0) COMMENT '用户的粉丝数(关注该用户的人数)',
		`user_article_number` INT NOT NULL DEFAULT(0) COMMENT '用户的文章数',
		`user_limit` INT(1) DEFAULT(1)COMMENT '用户类别(权限) 0 - 管理员/1 - 普通注册用户 /2 - 游客',
		`user_profile` VARCHAR(1024) COMMENT '用户简介',
		`user_area` VARCHAR(1024) COMMENT '用户所在地址',
		CONSTRAINT `PK_USER` PRIMARY KEY(`user_id`)
);

-- 博客表  
CREATE TABLE IF NOT EXISTS `blog`(
		`blog_id`  INT(6) ZEROFILL AUTO_INCREMENT COMMENT '博客id(主键)',
		`blog_author_id` INT(6) ZEROFILL NOT NULL COMMENT '博客的作者用户id(外键)',
		`blog_resource` VARCHAR(255) NOT NULL COMMENT '博客内容的实际存储位置',
		`blog_title` VARCHAR(255) NOT NULL COMMENT '博客的标题',
		`blog_digest` VARCHAR(1024) NOT NULL COMMENT '博客的摘要(150字以内的摘要)',
		`blog_create_time` TIMESTAMP NOT NULL DEFAULT(CURRENT_TIMESTAMP) COMMENT '博客的创建时间戳',
		`blog_visit_number` INT NOT NULL DEFAULT(0) COMMENT '博客被查看的次数',
		`blog_like_number` INT NOT NULL DEFAULT(0) COMMENT '博客被点赞👍的次数',
		`blog_dislike_number` INT NOT NULL DEFAULT(0) COMMENT '博客被点踩👎的次数',
		`blog_collect_number` INT NOT NULL DEFAULT(0) COMMENT '博客被收藏的次数',
		`blog_share_number` INT NOT NULL DEFAULT(0) COMMENT '博客被分享的次数',
		`blog_form`	INT(1) NOT NULL DEFAULT(0) COMMENT '博客的可见性:0 - 公开/1 - 粉丝可见/2 - 私有',
		`blog_type`	INT(1) NOT NULL DEFAULT(0) COMMENT '博客的类型:0 - 原创/1 - 转载',
		CONSTRAINT `PK_BLOG` PRIMARY KEY(`blog_id`),
		CONSTRAINT `FK_BLOG_USER` FOREIGN KEY(`blog_author_id`) REFERENCES `user`(`user_id`)
);

-- 评论表
CREATE TABLE IF NOT EXISTS `comment`(
		`comment_id` INT(6) ZEROFILL AUTO_INCREMENT COMMENT '评论id(主键)',
		`comment_author_id` INT(6) ZEROFILL NOT NULL COMMENT '评论的所属者用户id(外键)',
		`comment_blog_id` INT(6) ZEROFILL NOT NULL COMMENT '评论的所属博客id(外键)',
		`comment_content` TEXT NOT NULL COMMENT '评论的具体内容', 
		`comment_create_time` TIMESTAMP NOT NULL DEFAULT(CURRENT_TIMESTAMP) COMMENT '评论的创建时间戳',
		`comment_like_number` INT NOT NULL DEFAULT(0) COMMENT '评论被点赞的数量',
		`comment_dislike_number` INT NOT NULL DEFAULT(0) COMMENT '评论被点踩的数量',
		`comment_parent_id` INT(6) ZEROFILL COMMENT'该条评论的父级评论id(本表外键)',
		CONSTRAINT `PK_COMMENT` PRIMARY KEY(`comment_id`),
		CONSTRAINT `FK_COMMENT_USER` FOREIGN KEY(`comment_author_id`) REFERENCES `user`(`user_id`),
		CONSTRAINT `FK_COMMENT_BLOG` FOREIGN KEY(`comment_blog_id`) REFERENCES `blog`(`blog_id`),
		CONSTRAINT `FK_COMMENT_COMMENT` FOREIGN KEY(`comment_parent_id`) REFERENCES `comment`(`comment_id`)
);

-- 标签表
CREATE TABLE IF NOT EXISTS `tag`(
		`tag_id` INT(6) ZEROFILL AUTO_INCREMENT COMMENT '标签id(主键)',
		`tag_content` VARCHAR(128) NOT NULL DEFAULT('默认') COMMENT '标签的名字',
		CONSTRAINT `PK_TAG` PRIMARY KEY(`tag_id`)
);

-- 标签与博客的关系表
CREATE TABLE IF NOT EXISTS `has_tag`(
		`blog_id` INT(6) ZEROFILL NOT NULL COMMENT '博客id(外键)',
		`tag_id` INT(6) ZEROFILL NOT NULL COMMENT '标签id(外键) 两个属性联合作为本表主码',
		CONSTRAINT `PK_HAS_TAG` PRIMARY KEY(`blog_id`,`tag_id`),
		CONSTRAINT `FK_HAS_TAG_BLOG` FOREIGN KEY(`blog_id`) REFERENCES `blog`(`blog_id`),
		CONSTRAINT `FK_HAS_TAG_TAG` FOREIGN KEY(`tag_id`) REFERENCES `tag`(`tag_id`)
);

-- 消息表
CREATE TABLE IF NOT EXISTS `message`(
		`message_id` INT(6) ZEROFILL AUTO_INCREMENT COMMENT '消息id(主键)',
		`message_sender_id` INT(6) ZEROFILL NOT NULL COMMENT '消息的发送者用户id(外键)',
		`message_content` VARCHAR(512) NOT NULL COMMENT '消息内容',
		`message_create_time` TIMESTAMP NOT NULL DEFAULT(CURRENT_TIMESTAMP) COMMENT '消息的创建时间戳',
		`message_receiver_id` INT(6) ZEROFILL NOT NULL COMMENT '消息的接收者用户id(外键)',
		`message_if_read` INT(1) NOT NULL DEFAULT(0) COMMENT '消息是否已读: 0 - 未读/ 1 - 已读',
		CONSTRAINT `PK_MESSAGE` PRIMARY KEY(`message_id`),
		CONSTRAINT `FK_MESSAGE_USER_SENDER` FOREIGN KEY(`message_sender_id`) REFERENCES `user`(`user_id`),
		CONSTRAINT `FK_MESSAGE_USER_RECEIVER` FOREIGN KEY(`message_receiver_id`) REFERENCES `user`(`user_id`)
);

-- 用户的关注信息表
CREATE TABLE IF NOT EXISTS `user_follow`(
		`author_id` INT(6) ZEROFILL NOT NULL COMMENT '用户的id(外键)',
		`to_id` INT(6) ZEROFILL NOT NULL COMMENT '被关注的用户id(外键) /例: author_id 关注了 to_id ',
		`follow_time` TIMESTAMP NOT NULL DEFAULT(CURRENT_TIMESTAMP) COMMENT '关注的时间戳',
		CONSTRAINT `PK_USER_FOLLOW` PRIMARY KEY(`author_id`,`to_id`),
		CONSTRAINT `FK_USER_FOLLOW_USER_AUTHOR_ID` FOREIGN KEY(`author_id`) REFERENCES `user`(`user_id`),
		CONSTRAINT `FK_USER_FOLLOW_USER_TO_ID` FOREIGN KEY(`to_id`) REFERENCES `user`(`user_id`)
);

-- 收藏夹表
CREATE TABLE IF NOT EXISTS `folder`(
		`folder_id` INT(6) ZEROFILL AUTO_INCREMENT COMMENT '收藏夹id(主键)',
		`folder_author_id` INT(6) ZEROFILL NOT NULL COMMENT '收藏夹所属用户id(外键)',
		`folder_name` VARCHAR(20) NOT NULL COMMENT '收藏夹名称',
		`folder_create_time` TIMESTAMP NOT NULL DEFAULT(CURRENT_TIMESTAMP) COMMENT '收藏夹创建时间戳',
		CONSTRAINT `PK_FOLDER` PRIMARY KEY(`folder_id`),
		CONSTRAINT `FK_FOLDER_USER` FOREIGN KEY(`folder_author_id`) REFERENCES `user`(`user_id`)
);

-- 收藏夹收藏博客信息表
CREATE TABLE IF NOT EXISTS `folder_collect`(
		`folder_id` INT(6) ZEROFILL NOT NULL COMMENT '收藏夹id(外键)',
		`blog_id` INT(6) ZEROFILL NOT NULL COMMENT '博客id(外键) /两个属性联合做主键',
		`collect_time` TIMESTAMP NOT NULL DEFAULT(CURRENT_TIMESTAMP) COMMENT '收藏博客到该收藏夹的时间戳',
		CONSTRAINT `PK_FOLDER_COLLECT` PRIMARY KEY(`folder_id`,`blog_id`),
		CONSTRAINT `FK_FOLDER_COLLECT_FOLDER` FOREIGN KEY(`folder_id`) REFERENCES `folder`(`folder_id`),
		CONSTRAINT `FK_FOLDER_COLLECT_BLOG` FOREIGN KEY(`blog_id`) REFERENCES `blog`(`blog_id`)
);

-- 点赞博客信息
CREATE TABLE IF NOT EXISTS `blog_like`(
		`user_id` INT(6) ZEROFILL NOT NULL COMMENT '用户id(外键)',
		`blog_id` INT(6) ZEROFILL NOT NULL COMMENT '博客id(外键) /两个属性联合做主键',
        `like_type` INT(1) NOT NULL DEFAULT(1) COMMENT '点赞类型 0 - 点踩/1 - 点赞',
        `blog_like_time` TIMESTAMP NOT NULL DEFAULT(CURRENT_TIMESTAMP) COMMENT '点赞博客的时间戳',
		CONSTRAINT `PK_BLOG_LIKE` PRIMARY KEY(`user_id`,`blog_id`),
		CONSTRAINT `FK_BLOG_LIKE_USER` FOREIGN KEY(`user_id`) REFERENCES `user`(`user_id`),
		CONSTRAINT `FK_BLOG_LIKE_BLOG` FOREIGN KEY(`blog_id`) REFERENCES `blog`(`blog_id`)
);

-- 点赞评论信息
CREATE TABLE IF NOT EXISTS `comment_like`(
		`user_id` INT(6) ZEROFILL NOT NULL COMMENT '用户id(外键)',
		`comment_id` INT(6) ZEROFILL NOT NULL COMMENT '评论id(外键) /两个属性联合做主键',
        `like_type` INT(1) NOT NULL DEFAULT(1) COMMENT '点赞类型 0 - 点踩/1 - 点赞',
        `comment_like_time` TIMESTAMP NOT NULL DEFAULT(CURRENT_TIMESTAMP) COMMENT '点赞评论的时间戳',
		CONSTRAINT `PK_COMMENT_LIKE` PRIMARY KEY(`user_id`,`comment_id`),
		CONSTRAINT `FK_COMMENT_LIKE_USER` FOREIGN KEY(`user_id`) REFERENCES `user`(`user_id`),
		CONSTRAINT `FK_COMMENT_LIKE_COMMENT` FOREIGN KEY(`comment_id`) REFERENCES `comment`(`comment_id`)
);












