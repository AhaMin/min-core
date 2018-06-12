
CREATE DATABASE `shop`  DEFAULT CHARACTER SET utf8;

USE `shop`;

-- 用户表 user
create table user(
  id bigint unsigned not null auto_increment,
  username varchar(128) not null,
  password varchar(128) not null,
  PRIMARY KEY (id)
);

-- 用户收货地址 user_address
create table user_address(
  id bigint unsigned not null auto_increment,
  user_id bigint unsigned not null,
  address varchar(128) not null,
  phone varchar(16) not null,
  name varchar(32) not null,
  PRIMARY KEY (id),
  CONSTRAINT idx_user_address_user FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 商店表 store
create table store(
  id bigint unsigned not null auto_increment,
  owner_id bigint unsigned not null,
  name varchar(64) not null,
  detail varchar(128) not null,
  PRIMARY KEY (id),
  CONSTRAINT idx_store_user FOREIGN KEY(owner_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
  KEY idx_store_name(name(20))
);

-- 商品表 goods
create table goods(
	id bigint unsigned not null auto_increment,
	store_id bigint unsigned not null,
	name varchar(64) not null,
	detail varchar(128) not null,
	primaryImagePath varchar(128) not null,
	PRIMARY KEY (id),
	CONSTRAINT idx_goods_store FOREIGN KEY(store_id) REFERENCES store(id) ON DELETE CASCADE ON UPDATE CASCADE,
	KEY idx_goods_name(name(20))
);

-- 商品样式表 goods_attr
create table goods_attr(
  id bigint unsigned not null auto_increment,
  goods_id bigint unsigned not null,
  stock int not null,
  price decimal(10,2) not null,
  imagePath varchar(128) not null,
  PRIMARY KEY (id),
  CONSTRAINT idx_goods_attr_goods FOREIGN KEY(goods_id) REFERENCES goods(id) ON DELETE CASCADE ON UPDATE CASCADE,
  check (stock>=0 and price>0)
);

-- 订单表 order
create table `order`(
  id bigint unsigned not null auto_increment,
  user_id bigint unsigned not null,
  address_id bigint unsigned not null,
  status Enum('准备中', '已发货', '已完成', '售后中') not null,
  create_time datetime not null,
  total_price decimal(10,2) not null,
  PRIMARY KEY (id),
  CONSTRAINT idx_order_user FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT idx_order_address FOREIGN KEY (address_id) REFERENCES user_address(id) ON DELETE CASCADE ON UPDATE CASCADE,
  check (total_price>0)
);

-- 订单样式表 order_attr
create table order_attr(
  order_id bigint unsigned not null,
  goods_attr_id bigint unsigned not null,
  amount int not null,
  price decimal(10,2) not null,
  PRIMARY KEY (order_id),
  CONSTRAINT idx_order_attr_order FOREIGN KEY(order_id) REFERENCES `order`(id),
  CONSTRAINT idx_order_attr_goods_attr FOREIGN KEY(goods_attr_id) REFERENCES goods_attr(id) ON DELETE CASCADE ON UPDATE CASCADE ,
  check (price>=0 and amount>0)
);

-- 收藏夹
create table user_favorite(
  id bigint unsigned not null auto_increment,
  goods_id bigint unsigned not null,
  user_id bigint unsigned not null,
  PRIMARY KEY (id),
  CONSTRAINT idx_user_favorite_goods FOREIGN KEY(goods_id) REFERENCES goods(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT idx_user_favorite_user FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 订单物流
create table order_delivery(
  order_id bigint unsigned not null,
  delivery_company varchar(16) not null,
  delivery_num varchar(64) not null,
  lastest_going varchar(512) not null,
  PRIMARY KEY (order_id),
  CONSTRAINT idx_order_delivery_order FOREIGN KEY(order_id) REFERENCES `order`(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 建立存储过程，在批量插入订单和订单样式数据、修改订单总价时提高效率
delimiter $$
CREATE PROCEDURE insert_order_attr (IN goods_attr_id bigint,IN amount INT)
BEGIN
DECLARE unit_price DECIMAL(10,2);
SET unit_price=(SELECT goods_attr.price FROM goods_attr WHERE id=goods_attr_id);
INSERT INTO order_attr(order_id,goods_attr_id,amount,price)
VALUES (0,goods_attr_id,amount,unit_price*order_attr.amount);
END$$
delimiter ;

delimiter $$
CREATE PROCEDURE insert_order (IN user_id INT,IN address_id bigint)
BEGIN
DECLARE total_price DECIMAL(10,2);
DECLARE order_id bigint;
SET total_price=(SELECT SUM(price) FROM order_attr GROUP BY order_id HAVING order_id=0);
INSERT INTO `order`(user_id,address_id,status,create_time,total_price)
VALUES (user_id,address_id,'准备中',now(),total_price);
SET order_id=(SELECT id FROM `order` WHERE user_id=user_id ORDER BY id DESC limit 1);
UPDATE order_attr SET order_id=order_id where order_id=0;
END$$
delimiter ;
