CREATE DATABASE tankFarm DEFAULT CHARACTER SET utf8;

use tankFarm;

-- 工作人员信息(工作号,姓名,身份证号,员工类型,密码)
CREATE TABLE staff(
    work_id bigint unsigned NOT NULL,
    name VARCHAR(16) NOT NULL ,
    id_num bigint unsigned NOT NULL,
    staff_type VARCHAR(16) NOT NULL,
    password VARCHAR(32) NOT NULL,
    PRIMARY KEY (work_id),
    UNIQUE (id_num)
);

-- 油库信息(油库编号,名称,地点,容量)
CREATE TABLE farm_info(
    id INT unsigned NOT NULL auto_increment,
    name VARCHAR(16) NOT NULL,
    position VARCHAR(64) NOT NULL,
    capacity DOUBLE unsigned NOT NULL,
    PRIMARY KEY (id)
);

-- 油品类别(油品编号,名称,价格)
CREATE TABLE tank(
    id INT unsigned NOT NULL auto_increment,
    name VARCHAR(16) NOT NULL,
    price DOUBLE unsigned NOT NULL,
    PRIMARY KEY (id)
);

-- 油品储量信息(油品编号,储量,所属油库)
CREATE TABLE tank_storage(
    tank_id INT unsigned NOT NULL,
    storage_capacity DOUBLE unsigned NOT NULL,
    farm_id INT unsigned NOT NULL,
    FOREIGN KEY (tank_id) REFERENCES tank(id),
    FOREIGN KEY (farm_id) REFERENCES farm_info(id),
    CHECK (storage_capacity<farm_info.capacity)
);

-- 会员等级信息(会员等级编号,会员等级名称,折扣,最低消费金额)
CREATE TABLE member_level(
    id INT unsigned NOT NULL auto_increment,
    name VARCHAR(16) NOT NULL,
    discount DOUBLE unsigned NOT NULL,
    min_consumption DOUBLE unsigned NOT NULL,
    PRIMARY KEY (id),
    CHECK (discount<10)
);

-- 会员信息(会员编号,姓名,身份证号,会员等级编号,车牌号,总消费额)
CREATE TABLE member(
    member_id bigint unsigned NOT NULL auto_increment,
    name VARCHAR(16) NOT NULL,
    id_num bigint unsigned NOT NULL,
    level_id INT unsigned NOT NULL,
    car_num VARCHAR(16) NOT NULL,
    total_consumption DOUBLE unsigned NOT NULL,
    PRIMARY KEY (member_id),
    UNIQUE (id_num),
    FOREIGN KEY (level_id) REFERENCES member_level(id)
);

-- 进货信息(进货时间,进货油品,存储油库,进货人员,进货量,单价)
CREATE TABLE supply_info(
    id bigint unsigned NOT NULL auto_increment,
    create_time datetime NOT NULL,
    tank_id INT unsigned NOT NULL,
    farm_id INT unsigned NOT NULL,
    oprate_staff_id bigint unsigned NOT NULL,
    supply_capacity DOUBLE unsigned NOT NULL,
    unit_price DOUBLE unsigned NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (tank_id) REFERENCES tank(id),
    FOREIGN KEY (farm_id) REFERENCES farm_info(id),
    FOREIGN KEY (oprate_staff_id) REFERENCES staff(work_id)
);

-- 销售信息(销售时间,销售油品,提货油库,销售量,单价,销售人员,客户,折扣)
CREATE TABLE sale_info(
    id bigint unsigned NOT NULL auto_increment,
    create_time datetime NOT NULL,
    tank_id INT unsigned NOT NULL,
    farm_id INT unsigned NOT NULL,
    sale_capacity DOUBLE unsigned NOT NULL,
    unit_price DOUBLE unsigned NOT NULL,
    oprate_staff_id bigint unsigned NOT NULL,
    member_id bigint unsigned DEFAULT NULL,
    discount DOUBLE unsigned NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (tank_id) REFERENCES tank(id),
    FOREIGN KEY (farm_id) REFERENCES farm_info(id),
    FOREIGN KEY (oprate_staff_id) REFERENCES staff(work_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id),
    CHECK (discount<10),
    CHECK (sale_capacity<tank_storage.storage_capacity)
);

/*
将工作人员信息表中名称员工类型的数据类型修改为枚举类型，取值限定为销售、财务、前台、经理；

将会员等级信息表中会员等级名称类型的数据类型修改为枚举类型，取值限定为金卡、银卡、铜卡、普通；

在工作人员信息、会员信息表中各增加电话属性，自定义数据类型；

会员等级信息表中折扣默认为1；

工作人员信息表中，员工类型默认为销售

会员等级信息中的折扣数据类型修改为numeric（3,2）；

在会员信息表中车牌号添加唯一索引；

因客户总查询他的消费记录，所以在销售信息表中，添加客户属性列上的索引，请用sql定义此索引。

请自行模仿上题，提出需求，创建索引。
 */

use tankFarm;

ALTER TABLE staff modify staff_type ENUM('销售','财务','前台','经理');

ALTER TABLE member_level modify `name` ENUM('金卡','银卡','铜卡','普通');

ALTER TABLE staff ADD COLUMN phone_num VARCHAR(16);

ALTER TABLE member_level ALTER discount SET DEFAULT 1;

ALTER TABLE staff ALTER staff_type SET DEFAULT '销售';

ALTER TABLE member_level modify discount NUMERIC(3,2) DEFAULT 1;

ALTER TABLE member ADD UNIQUE INDEX idx_car(car_num);

ALTER TABLE sale_info ADD INDEX idx_member(member_id);

-- 因客户姓名可能会存在重复，需要根据身份证号做更精确的查找，所以在会员信息表中，添加身份证上的索引
ALTER TABLE member ADD INDEX idx_id(id_num);

ALTER TABLE staff modify id_num VARCHAR(32);

ALTER TABLE member modify id_num VARCHAR(32);

ALTER TABLE supply_info ADD COLUMN tank_id INT unsigned NOT NULL;

ALTER TABLE supply_info ADD FOREIGN KEY (tank_id) REFERENCES tank(id);


/**
1.  查询所有油品的信息；

2.  查询工作人员王红的电话；
insert into staff(work_id,name,id_num,staff_type,password) values(122221,'王红','210403196605173324','销售','123456');

3.  查询储量低于1000升的油品信息。
insert into tank(name,price) values('92号汽油',6.88);
insert into tank(name,price) values('93号汽油',6.88);
insert into tank(name,price) values('97号汽油',7.33);
insert into tank(name,price) values('柴油',6.54);

insert into farm_info(name,position,capacity) values('一号油库','厂房东北角',80000);
insert into farm_info(name,position,capacity) values('二号油库','厂房东南角',70000);
insert into farm_info(name,position,capacity) values('三号油库','厂房西北角',88000);
insert into farm_info(name,position,capacity) values('三号油库','厂房西南角',100000);

insert into tank_storage(tank_id,storage_capacity,farm_id) values(1,999,1);
insert into tank_storage(tank_id,storage_capacity,farm_id) values(2,2000,2);
insert into tank_storage(tank_id,storage_capacity,farm_id) values(3,3000,3);
insert into tank_storage(tank_id,storage_capacity,farm_id) values(4,888,4);

4.  查询车牌包含“京B”的会员信息。
insert into member_level(name,discount,min_consumption) values('普通',0.95,3000);
insert into member_level(name,discount,min_consumption) values('铜卡',0.9,5000);
insert into member_level(name,discount,min_consumption) values('银卡',0.85,8000);
insert into member_level(name,discount,min_consumption) values('金卡',0.8,20000);

insert into member(name,id_num,level_id,car_num,total_consumption) values('王苗',1,'京B1234',3200);
insert into member(name,id_num,level_id,car_num,total_consumption) values('张三',2,'京B4321',5600);
insert into member(name,id_num,level_id,car_num,total_consumption) values('王麻子',3,'京A4444',12000);
insert into member(name,id_num,level_id,car_num,total_consumption) values('李四',3,'京A6666',8800);


5.  查询93号汽油历史进货最高价格。
insert into supply_info(create_time,farm_id,oprate_staff_id,supply_capacity,unit_price,tank_id) values(
'2018-01-01',2,122221,8000,6,2);

insert into supply_info(create_time,farm_id,oprate_staff_id,supply_capacity,unit_price,tank_id) values(
'2018-03-01',2,122221,8000,6.3,2);

insert into supply_info(create_time,farm_id,oprate_staff_id,supply_capacity,unit_price,tank_id) values(
'2018-03-10',1,122221,8000,6.4,1);

6.  查询会员信息，按总消费额降序排列。

7.  查询银卡会员，且总消费额在10000元以上的会员编号和姓名。

8.  查询“王苗”加“97号汽油”的次数。
insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,oprate_staff_id,member_id,discount) values(
'2018-01-01',3,3,500,7.33,122221,1,0.95);

insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,oprate_staff_id,member_id,discount) values(
'2018-03-10',3,3,500,7.33,122221,1,0.95);

insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,oprate_staff_id,member_id,discount) values(
'2018-04-01',2,2,500,6.88,122221,1,0.95);

insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,oprate_staff_id,member_id,discount) values(
'2018-04-02',2,2,500,6.88,122221,2,0.9);

9.  查询各级会员的人数。

10.查询各种油品的储量，并将总储量低于1000升的油品选出。

11.因93号汽油油品有问题，需查询在近一周内加过“93号汽油”的会员信息（使用嵌套查询和连接查询两种查询方式，自行查找相应函数）。

12.查询工作人员王红于2018年3月10日进货92号汽油的相关信息（使用嵌套查询和连接查询两种方式完成）。

13.查询会员王苗在那些油库加过油（要求油库名称）。

14.查询会员王苗没在那些油库加过油（要求油库名称）。

15.查询全部销售量最高的油品名称。

16.查询2017年度，一号油库每个油品的销售额。
insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,oprate_staff_id,member_id,discount) values(
'2017-12-10',3,1,500,7.33,122221,1,0.95);

insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,oprate_staff_id,member_id,discount) values(
'2017-12-11',2,1,500,6.88,122221,2,0.9);

17.查询购买过“93号汽油”和“97号汽油”的会员信息。

18.查询没购买过油的会员信息。（用内连接和外连接两种方式查询）

19.查询，2017年度，柴油的净利润（2017年柴油的销售总价减去柴油的进货总价，提示：select 3-2 结果为1）。
insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,oprate_staff_id,member_id,discount) values(
'2017-12-10',4,1,500,6.54,122221,2,0.9);

insert into supply_info(create_time,farm_id,oprate_staff_id,supply_capacity,unit_price,tank_id) values(
'2017-12-01',2,122221,500,6,4);

20.查询，2017年度，各种油的净利润，按照利润降序排序。
insert into supply_info(create_time,farm_id,oprate_staff_id,supply_capacity,unit_price,tank_id) values(
'2017-12-01',1,122221,500,6.8,3);

insert into supply_info(create_time,farm_id,oprate_staff_id,supply_capacity,unit_price,tank_id) values(
'2017-12-01',1,122221,500,6,2);

 */


