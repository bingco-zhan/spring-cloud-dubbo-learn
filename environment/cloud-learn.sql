drop database if exists `cloud-learn`;
create database `cloud-learn` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `cloud-learn`;

create table `order` (
  id int(18) primary key auto_increment,
  fn varchar(32),
  `name` varchar(255),
  count int(18) default 0,
  money decimal(12,4) default 0.00,
);

create table store(
  id int(18) primary key auto_increment,
  `name` varchar(32),
  cnt int(12) default 0
);

insert into store(`name`, cnt) values('樱桃', 100),('凤梨', 50);