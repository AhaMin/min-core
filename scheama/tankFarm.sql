CREATE DATABASE tankFarm DEFAULT CHARACTER SET utf8;

use tankFarm;

-- 工作人员信息(工作号,姓名,身份证号,员工类型,密码)
CREATE TABLE staff(
    work_id bigint unsigned NOT NULL,
    name VARCHAR(16) NOT NULL ,
    id_num VARCHAR(32) NOT NULL,
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
    id_num VARCHAR(32) NOT NULL,
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
    operate_staff_id bigint unsigned NOT NULL,
    supply_capacity DOUBLE unsigned NOT NULL,
    unit_price DOUBLE unsigned NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (tank_id) REFERENCES tank(id),
    FOREIGN KEY (farm_id) REFERENCES farm_info(id),
    FOREIGN KEY (operate_staff_id) REFERENCES staff(work_id)
);

-- 销售信息(销售时间,销售油品,提货油库,销售量,单价,销售人员,客户,折扣)
CREATE TABLE sale_info(
    id bigint unsigned NOT NULL auto_increment,
    create_time datetime NOT NULL,
    tank_id INT unsigned NOT NULL,
    farm_id INT unsigned NOT NULL,
    sale_capacity DOUBLE unsigned NOT NULL,
    unit_price DOUBLE unsigned NOT NULL,
    operate_staff_id bigint unsigned NOT NULL,
    member_id bigint unsigned DEFAULT NULL,
    discount DOUBLE unsigned NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (tank_id) REFERENCES tank(id),
    FOREIGN KEY (farm_id) REFERENCES farm_info(id),
    FOREIGN KEY (operate_staff_id) REFERENCES staff(work_id),
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

-- *****************************************************************************************************
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
-- *****************************************************************************************************

-- 更改原DB信息
ALTER TABLE staff modify id_num VARCHAR(32);

ALTER TABLE member modify id_num VARCHAR(32);

ALTER TABLE supply_info ADD COLUMN tank_id INT unsigned NOT NULL;

ALTER TABLE supply_info ADD FOREIGN KEY (tank_id) REFERENCES tank(id);
-- *****************************************************************************************************


/**
1.  查询所有油品的信息；

2.  查询工作人员王红的电话；
insert into staff(work_id,name,id_num,staff_type,password,phone_num) values(122221,'王红','210403196605173324','销售','123456','15812341234');

3.  查询储量低于1000升的油品信息。
insert into tank(name,price) values('92号汽油',6.88);
insert into tank(name,price) values('93号汽油',6.88);
insert into tank(name,price) values('97号汽油',7.33);
insert into tank(name,price) values('柴油',6.54);

insert into farm_info(name,position,capacity) values('一号油库','厂房东北角',80000);
insert into farm_info(name,position,capacity) values('二号油库','厂房东南角',70000);
insert into farm_info(name,position,capacity) values('三号油库','厂房西北角',88000);
insert into farm_info(name,position,capacity) values('四号油库','厂房西南角',100000);

insert into tank_storage(tank_id,storage_capacity,farm_id) values(1,999,1);
insert into tank_storage(tank_id,storage_capacity,farm_id) values(2,2000,2);
insert into tank_storage(tank_id,storage_capacity,farm_id) values(3,3000,3);
insert into tank_storage(tank_id,storage_capacity,farm_id) values(4,888,4);

4.  查询车牌包含“京B”的会员信息。
insert into member_level(name,discount,min_consumption) values('普通',0.95,3000);
insert into member_level(name,discount,min_consumption) values('铜卡',0.9,5000);
insert into member_level(name,discount,min_consumption) values('银卡',0.85,8000);
insert into member_level(name,discount,min_consumption) values('金卡',0.8,20000);

insert into member(name,id_num,level_id,car_num,total_consumption) values('王苗','654023197706036226',1,'京B1234',3200);
insert into member(name,id_num,level_id,car_num,total_consumption) values('张三','140801199409025425',2,'京B4321',5600);
insert into member(name,id_num,level_id,car_num,total_consumption) values('王麻子','450305199209111100',3,'京A4444',12000);
insert into member(name,id_num,level_id,car_num,total_consumption) values('李四','32058419770626873X',3,'京A6666',8800);


5.  查询93号汽油历史进货最高价格。
insert into supply_info(create_time,tank_id,farm_id,operate_staff_id,supply_capacity,unit_price) values(
'2018-01-01',2,2,122221,8000,6);

insert into supply_info(create_time,tank_id,farm_id,operate_staff_id,supply_capacity,unit_price) values(
'2018-03-01',2,2,122221,8000,6.3);

insert into supply_info(create_time,tank_id,farm_id,operate_staff_id,supply_capacity,unit_price) values(
'2018-03-10',1,1,122221,8000,6.4);

6.  查询会员信息，按总消费额降序排列。

7.  查询银卡会员，且总消费额在10000元以上的会员编号和姓名。

8.  查询“王苗”加“97号汽油”的次数。
insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,operate_staff_id,member_id,discount) values(
'2018-01-01',3,3,500,7.33,122221,1,0.95);

insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,operate_staff_id,member_id,discount) values(
'2018-03-10',3,3,500,7.33,122221,1,0.95);

insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,operate_staff_id,member_id,discount) values(
'2018-04-01',2,2,500,6.88,122221,1,0.95);

insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,operate_staff_id,member_id,discount) values(
'2018-04-02',2,2,500,6.88,122221,2,0.9);

9.  查询各级会员的人数。

10.查询各种油品的储量，并将总储量低于1000升的油品选出。

11.因93号汽油油品有问题，需查询在近一周内加过“93号汽油”的会员信息（使用嵌套查询和连接查询两种查询方式，自行查找相应函数）。

12.查询工作人员王红于2018年3月10日进货92号汽油的相关信息（使用嵌套查询和连接查询两种方式完成）。

13.查询会员王苗在那些油库加过油（要求油库名称）。

14.查询会员王苗没在那些油库加过油（要求油库名称）。

15.查询全部销售量最高的油品名称。

16.查询2017年度，一号油库每个油品的销售额。
insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,operate_staff_id,member_id,discount) values(
'2017-12-10',3,1,500,7.33,122221,1,0.95);

insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,operate_staff_id,member_id,discount) values(
'2017-12-11',2,1,500,6.88,122221,2,0.9);

insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,operate_staff_id,member_id,discount) values(
'2017-12-21',2,1,500,6.88,122221,2,0.9);

17.查询购买过“93号汽油”和“97号汽油”的会员信息。

18.查询没购买过油的会员信息。（用内连接和外连接两种方式查询）

19.查询，2017年度，柴油的净利润（2017年柴油的销售总价减去柴油的进货总价，提示：select 3-2 结果为1）。
insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,operate_staff_id,member_id,discount) values(
'2017-12-10',4,1,500,6.54,122221,2,0.9);

insert into supply_info(create_time,tank_id,farm_id,operate_staff_id,supply_capacity,unit_price) values(
'2017-12-01',4,2,122221,500,6);

20.查询，2017年度，各种油的净利润，按照利润降序排序。
insert into supply_info(create_time,tank_id,farm_id,operate_staff_id,supply_capacity,unit_price) values(
'2017-12-01',3,1,122221,500,6.8);

insert into supply_info(create_time,tank_id,farm_id,operate_staff_id,supply_capacity,unit_price) values(
'2017-12-01',2,1,122221,500,6);

insert into supply_info(create_time,tank_id,farm_id,operate_staff_id,supply_capacity,unit_price) values(
'2017-12-11',2,1,122221,500,6);

 */
 -- *****************************************************************************************************
-- 1.查询所有油品的信息
SELECT id,name,price,storage_capacity,farm_id FROM tank LEFT JOIN tank_storage ON tank.id = tank_storage.tank_id;


-- 2.查询工作人员王红的电话
SELECT staff.phone_num FROM staff WHERE name = '王红';


-- 3.查询储量低于1000升的油品信息
SELECT tank.id,tank.name,price,storage_capacity,farm_info.name FROM tank JOIN tank_storage
ON tank.id = tank_storage.tank_id JOIN farm_info ON tank_storage.farm_id = farm_info.id
WHERE storage_capacity<1000;


-- 4.查询车牌包含“京B”的会员信息
SELECT member_id,name,id_num,level_id,car_num,total_consumption FROM member WHERE car_num LIKE '%京B%';


-- 5.查询93号汽油历史进货最高价格
SELECT MAX(price.unit_price) AS 93号汽油历史进货最高价格 FROM
(SELECT si.unit_price FROM supply_info si WHERE si.tank_id=(SELECT t.id FROM tank t WHERE t.name='93号汽油')) price;


-- 6.查询会员信息，按总消费额降序排列
SELECT member_id,name,id_num,level_id,car_num,total_consumption FROM member ORDER BY total_consumption DESC;


-- 7.查询银卡会员，且总消费额在10000元以上的会员编号和姓名
SELECT member_id,name FROM member WHERE level_id=(SELECT id FROM member_level WHERE member_level.name='银卡')
AND total_consumption>10000;


-- 8.查询“王苗”加“97号汽油”的次数
SELECT COUNT(*) AS 王苗加97号汽油次数 FROM sale_info WHERE tank_id=(SELECT id FROM tank WHERE name='97号汽油')
AND member_id=(SELECT member_id FROM member WHERE name='王苗');


-- 9.查询各级会员的人数
SELECT ml.id AS 等级编号,ml.name AS 等级名称,COUNT(*) AS 人数 FROM member_level ml JOIN member m ON m.level_id = ml.id GROUP BY ml.id;


-- 10.查询各种油品的储量，并将总储量低于1000升的油品选出
SELECT t.id,t.name,t.price,ts.storage_capacity FROM tank_storage ts JOIN tank t ON t.id = ts.tank_id
WHERE ts.storage_capacity<1000;


-- 11.查询在近一周内加过“93号汽油”的会员信息（嵌套和连接）
-- 嵌套
SELECT * FROM member WHERE member_id in (SELECT member_id FROM sale_info
WHERE tank_id=(SELECT tank.id FROM tank WHERE tank.name='93号汽油') AND create_time >= date_sub(now(),INTERVAL 1 week));
-- 连接
SELECT m.member_id,m.name,m.id_num,ml.name,m.car_num,m.total_consumption FROM sale_info si JOIN member m ON si.member_id = m.member_id LEFT JOIN member_level
ml ON m.level_id = ml.id JOIN tank t ON si.tank_id=t.id WHERE t.name='93号汽油' AND si.create_time >= date_sub(now(),INTERVAL 1 week);


-- 12.查询工作人员王红于2018年3月10日进货92号汽油的相关信息(嵌套和连接)
-- 嵌套
SELECT * FROM supply_info WHERE operate_staff_id=(SELECT work_id FROM staff WHERE name='王红')
AND date(create_time) ='2018-03-10'
AND tank_id=(SELECT id FROM tank WHERE name='92号汽油');
-- 连接
SELECT si.id,si.create_time,t.name AS 油品,si.farm_id,s.name AS 员工,si.supply_capacity,si.unit_price FROM supply_info si
JOIN staff s ON si.operate_staff_id = s.work_id JOIN tank t ON si.tank_id = t.id
WHERE s.name='王红' AND t.name='92号汽油' AND si.create_time ='2018-3-10';


-- 13.查询会员王苗在那些油库加过油（要求油库名称）
SELECT name FROM farm_info WHERE id IN (SELECT farm_id FROM sale_info WHERE member_id=
(SELECT member_id FROM member WHERE name='王苗'));


-- 14.查询会员王苗没在那些油库加过油（要求油库名称）
SELECT name FROM farm_info WHERE id NOT IN (SELECT farm_id FROM sale_info WHERE member_id=
(SELECT member_id FROM member WHERE name='王苗'));


-- 15.查询全部销售量最高的油品名称。
SELECT si.tank_id,t.name,SUM(sale_capacity) FROM sale_info si JOIN tank t ON si.tank_id = t.id GROUP BY si.tank_id
HAVING SUM(sale_capacity)=
(SELECT MAX(sale.total_sale) FROM (SELECT SUM(sale_capacity) AS total_sale FROM sale_info GROUP BY tank_id) AS sale);


-- 16.查询2017年度，一号油库每个油品的销售额。
SELECT si.tank_id,SUM(si.sale_capacity*si.unit_price) FROM farm_info fi JOIN sale_info si ON fi.id = si.farm_id
WHERE fi.name='一号油库' GROUP BY si.tank_id;


-- 17.查询购买过“93号汽油”和“97号汽油”的会员信息。
SELECT m.member_id,m.name,m.id_num,m.level_id,m.car_num,m.total_consumption
FROM sale_info si JOIN member m ON si.member_id = m.member_id
WHERE si.tank_id=(SELECT id FROM tank WHERE name='93号汽油') AND si.member_id IN
(SELECT member_id FROM sale_info WHERE tank_id=(SELECT id FROM tank WHERE name='97号汽油'));


-- 18.查询没购买过油的会员信息。（用内连接和外连接两种方式查询）
-- 外连接
SELECT m.member_id,m.name,m.id_num,m.level_id,m.car_num,m.total_consumption
FROM member m LEFT JOIN sale_info si ON m.member_id = si.member_id
WHERE id IS NULL;
-- 内连接
SELECT * FROM member WHERE member_id NOT IN
(SELECT m.member_id FROM member m INNER JOIN sale_info si ON m.member_id = si.member_id);


-- 19.查询，2017年度，柴油的净利润（2017年柴油的销售总价减去柴油的进货总价，提示：select 3-2 结果为1）。
SELECT (
(SELECT SUM(sa.sale_capacity*sa.unit_price) FROM sale_info sa WHERE sa.tank_id=(SELECT id FROM tank WHERE name='柴油')
AND YEAR(sa.create_time)='2017')
-
(SELECT SUM(su.supply_capacity*su.unit_price) FROM supply_info su WHERE su.tank_id=(SELECT id FROM tank WHERE name='柴油')
AND YEAR(su.create_time)='2017')
) AS 柴油净利润;


-- 20.查询，2017年度，各种油的净利润，按照利润降序排序
SELECT total_sale.id,sale_price-supply_price FROM
(SELECT sa.tank_id AS id,SUM(sa.sale_capacity*sa.unit_price) AS sale_price FROM sale_info sa WHERE YEAR(sa.create_time)='2017' GROUP BY sa.tank_id) total_sale JOIN
(SELECT su.tank_id AS id,SUM(su.supply_capacity*su.unit_price) AS supply_price FROM supply_info su WHERE YEAR(su.create_time)='2017' GROUP BY su.tank_id) total_supply
ON total_sale.id=total_supply.id;

 -- *****************************************************************************************************

/*
用insert语句给每个表插入两条记录,注意符合完整性约束要求。

2018年3月10日，会员王苗给车加97号汽油，请在库中插入记录，描述这件事情。

更新工作人员王红的电话为13500020001。

更新王苗的会员等级为金卡。

删除柴油的信息，如果删除时系统提示错误，请提出解决办法。

删除2018年3月10日，会员王苗给车加97号汽油的信息。

创建油品2017年的销量表，表中属性包含（排名，油品编号，销量），其中排名是自动增长，表中的数据根据销售表查询得到

定义视图，查询每个会员在2017年的消费金额。

在前一题的基础上定义视图，查询每个银卡会员在2017年的消费金额。

在第9题的视图中查询，银卡会员2017年消费金额最高的客户名称。
 */

-- 添加注释
-- 1.用insert语句给每个表插入两条记录,注意符合完整性约束要求。
insert into staff(work_id,name,id_num,staff_type,password,phone_num) values(233333,'李梅','210403196605172433','财务','123456','15843214321');
insert into staff(work_id,name,id_num,staff_type,password,phone_num) values(666666,'马云','210403196605170038','经理','123456','15866668888');

insert into farm_info(name,position,capacity) values('五号油库','中央厂房',80000);
insert into farm_info(name,position,capacity) values('六号油库','中央厂房',80000);

insert into tank(name,price) values('90号汽油',5.54);
insert into tank(name,price) values('95号汽油',7.37);

insert into tank_storage(tank_id,storage_capacity,farm_id) values(5,1000,5);
insert into tank_storage(tank_id,storage_capacity,farm_id) values(6,1000,6);

insert into member(name,id_num,level_id,car_num,total_consumption) values('张雯','450305199209111122',3,'京A4433',12000);
insert into member(name,id_num,level_id,car_num,total_consumption) values('鳄汪','53262519770626873X',4,'京A2333',28000);

insert into supply_info(create_time,tank_id,farm_id,operate_staff_id,supply_capacity,unit_price) values(
'2018-03-01',5,5,122221,8000,6.3);

insert into supply_info(create_time,tank_id,farm_id,operate_staff_id,supply_capacity,unit_price) values(
'2018-03-10',6,6,122221,8000,6.4);

insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,operate_staff_id,member_id,discount) values(
'2018-04-01',5,5,500,6.88,122221,7,0.85);

insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,operate_staff_id,member_id,discount) values(
'2018-04-02',6,6,500,6.88,122221,8,0.8);

-- 2. 2018年3月10日，会员王苗给车加97号汽油，请在库中插入记录，描述这件事情。
insert into sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,operate_staff_id,member_id,discount)
values(
'2018-3-10',
(SELECT tank.id FROM tank WHERE tank.name='97号汽油'),
3,500,6.88,122221,
(SELECT member.member_id FROM member WHERE member.name='王苗'),
0.95);

-- 3. 更新工作人员王红的电话为13500020001。
UPDATE staff SET phone_num='13500020001' WHERE name='王红';

-- 4. 更新王苗的会员等级为金卡。
UPDATE member SET level_id=(SELECT member_level.id FROM member_level WHERE member_level.name='金卡')
WHERE name='王苗';

-- 5. 删除柴油的信息，如果删除时系统提示错误，请提出解决办法。
ALTER TABLE tank_storage DROP FOREIGN KEY tank_storage_ibfk_1;
ALTER TABLE tank_storage ADD CONSTRAINT ts_fk_tank FOREIGN KEY(tank_id) REFERENCES tank(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE supply_info DROP FOREIGN KEY supply_info_ibfk_1;
ALTER TABLE supply_info ADD CONSTRAINT supply_fk_tank FOREIGN KEY(tank_id) REFERENCES tank(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE sale_info DROP FOREIGN KEY sale_info_ibfk_1;
ALTER TABLE sale_info ADD CONSTRAINT sale_fk_tank FOREIGN KEY(tank_id) REFERENCES tank(id) ON DELETE CASCADE ON UPDATE CASCADE;

DELETE FROM tank WHERE name='柴油';

-- 6. 删除2018年3月10日，会员王苗给车加97号汽油的信息。
DELETE FROM sale_info WHERE create_time='2018-3-10' AND
member_id=(SELECT member.member_id FROM member WHERE member.name='王苗') AND
tank_id=(SELECT tank.id FROM tank WHERE tank.name='97号汽油');

-- 7. 创建油品2017年的销量表，表中属性包含（排名，油品编号，销量），其中排名是自动增长，表中的数据根据销售表查询得到
CREATE TABLE sales_2017(
  id INT unsigned NOT NULL auto_increment,
  tank_id INT unsigned NOT NULL,
  sales DOUBLE unsigned NOT NULL,
  PRIMARY KEY (id)
);
INSERT INTO sales_2017(tank_id,sales)
SELECT tank_id,SUM(sale_capacity) AS sales FROM sale_info GROUP BY tank_id;

-- 8. 定义视图，查询每个会员在2017年的消费金额。
CREATE VIEW consumption_2017(member_id,consumption)
AS
SELECT si.member_id,SUM(si.unit_price*si.sale_capacity) FROM sale_info si
WHERE YEAR(si.create_time)='2017' GROUP BY si.member_id;

-- 9. 在前一题的基础上定义视图，查询每个银卡会员在2017年的消费金额。
CREATE VIEW consumption_2017_silver(member_id,consumption)
AS
SELECT c.member_id,c.consumption FROM consumption_2017 c WHERE c.member_id IN
(SELECT m.member_id FROM member m WHERE m.level_id=(SELECT ml.id FROM member_level ml WHERE ml.name='银卡'));

-- 9.在第9题的视图中查询，银卡会员2017年消费金额最高的客户名称。
SELECT name FROM member WHERE member.member_id=
(SELECT member_id FROM consumption_2017_silver WHERE consumption=
(SELECT MAX(consumption) FROM consumption_2017_silver));

-- *****************************************************************************************************

-- 1.先删除进货信息表中中定义的外码约束，再重新定义外码约束（给约束命名），违约处理采用级联操作。

ALTER TABLE supply_info DROP FOREIGN KEY supply_info_ibfk_1;
ALTER TABLE supply_info ADD CONSTRAINT supply_fk_tank FOREIGN KEY(tank_id) REFERENCES tank(id) ON DELETE CASCADE ON UPDATE CASCADE ;

ALTER TABLE supply_info DROP FOREIGN KEY supply_info_ibfk_2;
ALTER TABLE supply_info ADD CONSTRAINT supply_fk_farm FOREIGN KEY(farm_id) REFERENCES farm_info(id) ON DELETE CASCADE ON UPDATE CASCADE ;

ALTER TABLE supply_info DROP FOREIGN KEY supply_info_ibfk_3;
ALTER TABLE supply_info ADD CONSTRAINT supply_fk_staff FOREIGN KEY(operate_staff_id) REFERENCES staff(work_id) ON DELETE CASCADE ON UPDATE CASCADE ;


-- 2.创建触发器，当在进货信息表中添加一条记录，对应修改油品储量信息表中的储量信息。
delimiter $$
CREATE trigger tri_after_supply_insert
after INSERT ON supply_info FOR each row
BEGIN
UPDATE tank_storage SET storage_capacity=(storage_capacity+new.supply_capacity) WHERE tank_id=new.tank_id;
END$$
delimiter ;

-- 3.创建触发器，当在销售信息添加一条记录，对应修改油品储量信息表中的储量信息。
delimiter $$
CREATE trigger tri_after_sale_insert
after INSERT ON sale_info FOR each row
BEGIN
UPDATE tank_storage SET storage_capacity=(storage_capacity-new.sale_capacity) WHERE tank_id=new.tank_id;
END$$
delimiter ;

-- 4.创建触发器，当在销售信息添加一条记录,对应修改会员信息表中的总消费额,修改会员信息中的总消费额后，如果总消费额超过10000，修改其为金卡会员。
UPDATE member_level SET min_consumption=10000 WHERE name='金卡';

delimiter $$
CREATE trigger tri_after_sale_insert_2
after INSERT ON sale_info FOR each row
BEGIN
UPDATE member SET total_consumption=(total_consumption+new.sale_capacity*new.unit_price*new.discount) WHERE member_id=new.member_id;
if((SELECT total_consumption FROM member WHERE member_id=new.member_id)>10000)
THEN
UPDATE member SET level_id=(SELECT id FROM member_level WHERE name='金卡') WHERE member_id=new.member_id;
END if;
END$$
delimiter ;

 -- *****************************************************************************************************
-- 1.定义存储过程，功能是往销售表中插入一行数据，参数为：油品编号、油库编号、工作人员编号和会员编号，此记录中时间为当前时间，折扣通过会员编号从会员信息表中查询得到，单价从油品类别中得到。
delimiter $$
CREATE PROCEDURE insert_sale_info (IN tank_id INT,IN farm_id INT,IN operate_staff_id bigint,IN member_id bigint)
BEGIN
DECLARE discount DOUBLE;
DECLARE unit_price DOUBLE;
SET discount=(SELECT member_level.discount FROM member LEFT JOIN member_level ON member.level_id=member_level.id WHERE member.member_id=member_id);
SET unit_price=(SELECT price FROM tank WHERE id=tank_id);
INSERT INTO sale_info(create_time,tank_id,farm_id,sale_capacity,unit_price,operate_staff_id,member_id,discount)
VALUES (now(),tank_id,farm_id,300,unit_price,operate_staff_id,member_id,discount);
END$$
delimiter ;

-- 2.定义存储过程，参数为年份，统计每种油品本年的利润。
CREATE TABLE tank_profit_year(
  id bigint unsigned NOT NULL auto_increment,
  tank_id INT unsigned NOT NULL,
  profit DOUBLE unsigned NOT NULL,
  `year` INT unsigned NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (tank_id) REFERENCES tank(id),
  KEY idx_year(`year`)
);

delimiter $$
CREATE PROCEDURE tank_profit (IN paramYear INT)
BEGIN
INSERT INTO tank_profit_year(tank_id,profit,`year`)
SELECT total_sale.id,sale_price - supply_price,paramYear FROM
(SELECT sa.tank_id AS id,SUM(sa.sale_capacity*sa.unit_price) AS sale_price FROM sale_info sa WHERE YEAR(sa.create_time)=paramYear GROUP BY sa.tank_id) total_sale JOIN
(SELECT su.tank_id AS id,SUM(su.supply_capacity*su.unit_price) AS supply_price FROM supply_info su WHERE YEAR(su.create_time)=paramYear GROUP BY su.tank_id) total_supply
ON total_sale.id=total_supply.id;
END$$
delimiter ;

call tank_profit(2017);
SELECT * FROM tank_profit_year WHERE `year`=2017;

-- 3.在存储过程中定义游标，输出每个客户的编号，姓名，等级，消费额。
delimiter $$
CREATE PROCEDURE member_info()
BEGIN
DECLARE member_id bigint;
DECLARE member_name VARCHAR(16);
DECLARE level_name VARCHAR(16);
DECLARE total_consumption DOUBLE;
DECLARE member_cursor CURSOR FOR
SELECT member.member_id,member.name,member_level.name,member.total_consumption FROM member
LEFT JOIN member_level ON member.level_id=member_level.id;
OPEN member_cursor;
FETCH member_cursor INTO member_id,member_name,level_name,total_consumption;
repeat
SELECT concat('客户编号：',member_id,' 姓名:',member_name,' 等级:',level_name,' 消费额:',total_consumption);
FETCH member_cursor INTO member_id,member_name,level_name,total_consumption;
until 0 end repeat;
CLOSE member_cursor;
END$$
delimiter ;

-- 4.员工的工资与其销售额有关，请定义函数，参数为工作号，返回值为该员工本月的销售额。
delimiter $$
CREATE FUNCTION staff_total_sale(work_id bigint)
returns DOUBLE
BEGIN
DECLARE x DOUBLE DEFAULT 0;
SET x=(SELECT SUM(sale_capacity*unit_price) FROM sale_info WHERE MONTH(create_time)=MONTH(now())
GROUP BY operate_staff_id HAVING operate_staff_id=work_id);
return x;
END$$
delimiter ;

-- 5.请定义函数，参数为会员编号，返回值为该会员本年的消费金额
delimiter $$
CREATE FUNCTION member_consumption(id bigint)
returns DOUBLE
BEGIN
DECLARE x DOUBLE DEFAULT 0;
SET x=(SELECT SUM(sale_capacity*unit_price*discount) FROM sale_info WHERE YEAR(create_time)=YEAR(now())
GROUP BY member_id HAVING member_id=id);
return x;
END$$
delimiter ;

 -- *****************************************************************************************************
-- 一、授权。在MYSQL中建立多个用户，给他们赋予不同的权限，然后查看是否真正拥有被授予的权限了。
-- 在授权之后验证用户是否拥有了相应的权限。在执行完上面语句之后，我们可以分别以不同用户的身份登录数据库，进行相关操作，检查系统是否许可。

-- 1.  将访问整个服务器的所有权限授予用户U1;
GRANT ALL PRIVILEGES ON *.* TO 'U1'@'localhost' IDENTIFIED BY '0000';

-- 2.  把查询油库的权限授给用户 U2。
GRANT SELECT ON tankFarm.tank TO 'U2'@'localhost' IDENTIFIED BY '0000';

-- 3.  把对进货信息表的查询、修改和删除操作权限授予用户 U3。
GRANT SELECT,UPDATE,DELETE ON tankFarm.supply_info TO 'U3'@'localhost' IDENTIFIED BY '0000';

-- 4.  把查询 会员信息表和修改会员信息表中会员等级编号的权限授给用户 U4, 并允许 U4 将此权限再授予其他用户。
GRANT SELECT,UPDATE(level_id) ON tankFarm.member TO 'U4'@'localhost' IDENTIFIED BY '0000' WITH GRANT OPTION;

-- 5.  授予用户U5 对数据库的 create view，show view 权限。然后u5登录，创建视图，要求视图显示的信息为2017年每个油品的销量。
GRANT CREATE VIEW,SHOW VIEW ON tankFarm.* TO 'U5'@'localhost' IDENTIFIED BY '0000';
GRANT SELECT ON tankFarm.sale_info TO 'U5'@'localhost';
GRANT SELECT ON tankFarm.2017_tank_sale TO 'U5'@'localhost';

CREATE VIEW 2017_tank_sale AS SELECT tank_id,SUM(sale_capacity) AS sales FROM sale_info GROUP BY tank_id;


-- 二、回收权限。将授予的权限部分收回，检查回收后，该用户是否真正丧失了对数据的相应权限。

-- 收回用户 U4 修改会员等级编号的权限。
REVOKE UPDATE(level_id) ON tankFarm.member FROM 'U4'@'localhost';
-- 在回收权限之后验证用户是否真正丧失了该权限。
UPDATE member SET level_id=1 WHERE member_id=8;