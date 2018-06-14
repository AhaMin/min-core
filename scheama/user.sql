
CREATE DATABASE `user`  DEFAULT CHARACTER SET utf8;

-- 启用独立表空间参数
SET GLOBAL innodb_file_per_table=1;

SET GLOBAL innodb_file_format=Barracuda;

USE `user`;

-- 用户
create table user(
  id bigint unsigned not null auto_increment,
  account varchar(64) not null,
  data VARCHAR(4096) not null,
  create_time datetime not null,
  primary key (id),
  key idx_account(account)
)Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;

create table user_password(
  user_id bigint unsigned not null,
  password varchar(128) not null,
  create_time datetime not null,
  update_time bigint unsigned not null,
  primary key idx_user(user_id)
)Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;

-- 用户地址address
create table user_address(
  id bigint unsigned not null auto_increment,
	user_id bigint unsigned not null,
	data varchar(4096) not null,
	create_time datetime not null,
	PRIMARY key (id)
)Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;