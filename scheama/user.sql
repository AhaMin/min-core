
CREATE DATABASE `user`  DEFAULT CHARACTER SET utf8;

-- 启用独立表空间参数
SET GLOBAL innodb_file_per_table=1;

SET GLOBAL innodb_file_format=Barracuda;

USE `user`;

-- 用户
create table user(
  id bigint unsigned not null auto_increment,
  account varchar(64) not null,
  username varchar(128) not null,
  password varchar(128) not null,
  data json not null,
  create_time datetime not null,
  primary key (id),
  key idx_account(account),
  key idx_username(username)
)Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;

-- 用户地址address
create table address(
	id bigint unsigned not null auto_increment,
	user_id bigint unsigned not null,
	address varchar(4096) not null,
	PRIMARY key (id),
	KEY idx_user_id(user_id)
)Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;