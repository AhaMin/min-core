
CREATE DATABASE `design`  DEFAULT CHARACTER SET utf8;

-- 启用独立表空间参数
SET GLOBAL innodb_file_per_table=1;

SET GLOBAL innodb_file_format=Barracuda;

USE `design`;

-- TODO 建表
create table design(
  id bigint unsigned not null auto_increment,
  owner_id bigint unsigned not null,
  create_time datetime not null,
  primary key(id),
  key idx_user(owner_id)
)Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;

create table design_preview(
  id bigint unsigned not null auto_increment,
  design_id bigint unsigned not null,
  data varchar(256) not null,
  side tinyint unsigned not null,
  create_time datetime not null,
  primary key(id),
  key idx_design(design_id)
)Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;

create table design_order(
  id bigint unsigned not null auto_increment,
  design_id bigint unsigned not null,
  user_id bigint unsigned not null,
  address_id bigint unsigned not null,
  status tinyint unsigned not null,
  price double unsigned not null,
  `size` tinyint unsigned not null,
  create_time datetime not null,
  primary key(id),
  key idx_design(design_id),
  key idx_user(user_id),
  key idx_address(address_id)
)Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;