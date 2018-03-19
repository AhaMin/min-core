
CREATE DATABASE `ahamin`  DEFAULT CHARACTER SET utf8;

-- 启用独立表空间参数
SET GLOBAL innodb_file_per_table=1;

SET GLOBAL innodb_file_format=Barracuda;

USE `ahamin`;


-- 商品表goods
create table goods(
	id bigint(20) unsigned not null auto_increment,
	store_id bigint(20) unsigned not null,
	price double unsigned not null,
	data varchar(8192),
	PRIMARY key (id),
	KEY idx_store_id(store_id)
) Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;

-- 库存表storage
create table storage(
	id bigint(20) unsigned not null auto_increment,
	goods_id bigint(20) unsigned not null,
	price double unsigned not null,
	color varchar(22) not null,
	size varchar(22) not null,
	PRIMARY key (id),
	KEY idx_goods_id(goods_id)
) Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;

-- 店主表store_keeper
create table store_keeper(
	id bigint(20) unsigned not null auto_increment,
	user_id bigint(20) unsigned not null,
	data varchar(1024) not null,
	PRIMARY key (id),
	KEY idx_user_id(user_id)
) Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;

-- 第三方支付存票pay_ticket
create table pay_ticket(
	id bigint(20) unsigned not null auto_increment,
	order_id bigint(20) unsigned not null,
	user_id bigint(20) unsigned not null,
	to_user_id bigint(20) unsigned not null,
	price double unsigned not null,
	finish int not null,
	PRIMARY key (id)
) Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;

-- 店铺store
create table store(
	id bigint(20) unsigned not null auto_increment,
	keeper_id bigint(20) unsigned not null,
	data varchar(1024) not null,
	PRIMARY key (id),
	KEY idx_keeper_id(keeper_id)
) Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;

-- 订单order
create table `order`(
	id bigint(20) unsigned not null auto_increment,
	fake_id bigint(20) unsigned not null,
	address_id bigint(20) unsigned not null,
	user_id bigint(20) unsigned not null,
	goods_id bigint(20) unsigned not null,
	store_id bigint(20) unsigned not null,
	status varchar(22) not null,
	pay_time datetime not null,
	pay_type int not null,
	data varchar(1024) not null,
	PRIMARY key (id)
) Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;

-- 用户地址address
create table address(
	id bigint(20) unsigned not null auto_increment,
	user_id bigint(20) unsigned not null,
	address varchar(22) not null,
	PRIMARY key (id),
	KEY idx_user_id(user_id)
)Engine=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;