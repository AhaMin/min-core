
CREATE DATABASE `session`  DEFAULT CHARACTER SET utf8;

-- 启用独立表空间参数
SET GLOBAL innodb_file_per_table=1;

SET GLOBAL innodb_file_format=Barracuda;

USE `session`;

CREATE TABLE session(
  id bigint unsigned NOT NULL auto_increment,
  user_id bigint unsigned NOT NULL,
  to_user_id bigint unsigned NOT NULL,
  create_time DATETIME NOT NULL,
  update_time DATETIME NOT NULL,
  unread int unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  KEY idx_user(user_id),
  KEY idx_to_user(to_user_id)
)Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;

CREATE TABLE message(
  id bigint unsigned NOT NULL auto_increment,
  user_id bigint unsigned NOT NULL,
  to_user_id bigint unsigned NOT NULL,
  content VARCHAR(1024) NOT NULL,
  session_id bigint unsigned NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  KEY isx_user(user_id),
  KEY idx_to_user(to_user_id),
  KEY idx_session(session_id)
)Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;

