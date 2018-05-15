
CREATE DATABASE `image`  DEFAULT CHARACTER SET utf8;

-- 启用独立表空间参数
SET GLOBAL innodb_file_per_table=1;

SET GLOBAL innodb_file_format=Barracuda;

USE `image`;

-- 用户
create table image(
  id bigint unsigned not null auto_increment,
  data VARCHAR(4096) not null,
  create_time datetime not null,
  primary key (id)
) Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;
