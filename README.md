# 数据导入并简易分析Demo

------------

#### 环境
- JDK 1.8
- Maven 3+
- Mysql 8.0
---
#### 初始化SQL
```
create schema demo;

create table data
(
id              int auto_increment,
day_part        varchar(20)    null comment 'The time period when the ad aired',
local_air_date  date           null comment 'The local date when the ad aired',
local_air_time  time           null comment 'The local time when the ad aired',
local_air_day   int(1)         null comment 'The day of week when the ad aired',
station         varchar(30)    null comment 'The TV station where the ad aired',
mod_program     varchar(255)   null comment 'The TV program when the ad aired',
cpc             decimal(10, 2) null comment 'The cost per website click resulted from the ad airing',
cpo             decimal(10, 2) null comment 'The cost per website purchase resulted from the ad airing; a lower CPO indicates a better ad performance',
cost            decimal(10, 6) null comment 'The cost for the ad airing',
web_revenue     decimal(10, 2) null comment 'The revenue from the ads aired',
web_order       decimal(10, 6) null comment 'The number of orders resulted from the ad airing; a higher WebOrder indicates a more statistically significant data',
constraint data_pk
primary key (id)
);
```
---

#### 服务启动
- 本地IDE run 或 mvn package打包执行java -jar demo-0.0.1-SNAPSHOT.jar
- Maven 3
- Mysql 8.0 默认账号/密码:root/123456 端口3306
---

####访问地址
[http://localhost:8080/demo/web/index.html](http://localhost:8080/demo/web/index.html "http://localhost:8080/demo/web/index.html")
---
![index](https://github.com/knsv/mermaid#flowchart "index")