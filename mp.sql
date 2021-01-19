create table user(
                     id bigint(20) primary key not null comment '主键',
                     name varchar(20) default null comment '姓名',
                     age int(11) default null comment '年龄',
                     email varchar(50) default null comment '邮箱',
                     manager_id bigint(20) default null comment '上级id',
                     create_time datetime default null comment '创建时间'
) ENGINE=INNODB CHARSET=UTF8;

-- 外键参照本表的ID
insert into user(id,name,age,email,manager_id,create_time)
VALUES
(1,'老板',30,'dd1@163.com',NULL,'2020-08-12 00:00:12'),
(2,'小碗',30,'dd2@163.com',NULL,'2020-08-12 10:00:12'),
(3,'小胖',30,'dd3@163.com',NULL,'2020-08-12 11:00:12'),
(4,'小米',30,'dd4@163.com',NULL,'2020-08-12 13:00:12');