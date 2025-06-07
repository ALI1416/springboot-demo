# springboot-demo SpringBoot示例(3.x版)

[![License](https://img.shields.io/github/license/ALI1416/springboot-demo?label=License)](https://opensource.org/licenses/BSD-3-Clause)
[![Java Support](https://img.shields.io/badge/Java-17+-green)](https://openjdk.org/)
[![Repo Size](https://img.shields.io/github/repo-size/ALI1416/springboot-demo?label=Repo%20Size&color=success)](https://github.com/ALI1416/springboot-demo/archive/refs/heads/v3.zip)

## 简介

SpringBoot示例(3.x版)

## 项目

- **`demo-base`** : 基框架
  - SpringBoot Web
  - FastJson
- **`demo-config`** : 配置
  - SpringBoot Web
  - FastJson
  - 配置
- **`demo-dynamic-route`** : 动态路由
  - SpringBoot Web
  - FastJson
  - Redis
  - 轻量级权限认证SpringBoot启动器
  - MySQL
  - MyBatis
  - 雪花ID生成器
- **`demo-elastic-search`** : ElasticSearch
  - SpringBoot Web
  - FastJson
  - ElasticSearch
- **`demo-influxdb`** : InfluxDB
  - SpringBoot Web
  - FastJson
  - InfluxDB
- **`demo-just-auth`** : JustAuth第三方登录
  - SpringBoot Web
  - FastJson
  - JustAuth第三方登录
- **`demo-knife4j`** : Knife4j
  - SpringBoot Web
  - FastJson
  - Knife4j
- **`demo-mail`** : 邮件
  - SpringBoot Web
  - FastJson
  - 邮件
- **`demo-minio`** : Minio
  - SpringBoot Web
  - FastJson
  - Minio
- **`demo-mongodb`** : MongoDB
  - SpringBoot Web
  - FastJson
  - MongoDB
  - 雪花ID生成器
- **`demo-mqtt`** : MQTT
  - SpringBoot Web
  - FastJson
  - MQTT
  - 加密算法
- **`demo-mqtt-cluster`** : MQTT集群
  - SpringBoot
  - FastJson
  - MQTT
  - Redis
- **`demo-mysql`** : MySQL
  - SpringBoot Web
  - FastJson
  - MySQL
  - MyBatis
  - PageHelper分页排序查询
  - IP解析
  - 雪花ID生成器
  - IP地址转区域
- **`demo-mysql`** : 多数据库
  - SpringBoot Web
  - FastJson
  - MySQL
  - MyBatis
  - 雪花ID生成器
- **`demo-rabbitmq`** : RabbitMQ
  - SpringBoot Web
  - FastJson
  - Protocol Buffers
  - RabbitMQ
  - 雪花ID生成器
- **`demo-redis`** : Redis
  - SpringBoot Web
  - FastJson
  - Redis
  - 雪花ID生成器
- **`demo-util`** : 工具
  - SpringBoot Web
  - FastJson
  - FastExcel
  - 雪花ID生成器
  - IP地址转区域
  - 手机号码转区域
  - 二维码生成器
  - HuTool工具包
  - Tika
  - Ansj分词
  - 二维码
- **`demo-websocket`** : WebSocket
  - SpringBoot Web
  - SpringBoot WebSocket
- **`demo-websocket-cluster`** : WebSocket集群
  - SpringBoot Web
  - SpringBoot WebSocket
  - FastJson
  - Redis

## 版本号

- 父依赖 :
  - `SpringBoot Parent` : `org.springframework.boot:spring-boot-starter-parent`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-parent?label=Maven%20Central)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-parent)
- 继承父依赖不可修改版本号 :
  - `SpringBoot` : `org.springframework.boot:spring-boot-starter`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter?label=Maven%20Central)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter)
  - `SpringBoot Web` : `org.springframework.boot:spring-boot-starter-web`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-web?label=Maven%20Central)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
  - `SpringBoot WebSocket` : `org.springframework.boot:spring-boot-starter-websocket`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-websocket?label=Maven%20Central)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-websocket)
  - `邮件` : `org.springframework.boot:spring-boot-starter-mail`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-mail?label=Maven%20Central)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-mail)
  - `测试` : `org.springframework.boot:spring-boot-starter-test`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-test?label=Maven%20Central)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test)
  - `热部署` : `org.springframework.boot:spring-boot-devtools`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-devtools?label=Maven%20Central)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools)
  - `SpringBoot打包插件` : `org.springframework.boot:spring-boot-maven-plugin`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-maven-plugin?label=Maven%20Central)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-maven-plugin)
  - `自动装配` : `org.springframework.boot:spring-boot-autoconfigure-processor`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-autoconfigure-processor?label=Maven%20Central)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-autoconfigure-processor)
  - `配置` : `org.springframework.boot:spring-boot-configuration-processor`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-configuration-processor?label=Maven%20Central)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-configuration-processor)
- 继承父依赖可修改版本号 :
  - `实体层注解` : `org.projectlombok:lombok`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.projectlombok/lombok?label=Maven%20Central)](https://mvnrepository.com/artifact/org.projectlombok/lombok)
  - `日志` : `ch.qos.logback:logback-classic`  
    [![Maven Central](https://img.shields.io/maven-central/v/ch.qos.logback/logback-classic?label=Maven%20Central)](https://mvnrepository.com/artifact/ch.qos.logback/logback-classic)
  - `Maven编译插件` : `org.apache.maven.plugins:maven-compiler-plugin`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.apache.maven.plugins/maven-compiler-plugin?label=Maven%20Central)](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-compiler-plugin)
  - `MySQL` : `mysql:mysql-connector-java`  
    [![Maven Central](https://img.shields.io/maven-central/v/mysql/mysql-connector-java?label=Maven%20Central)](https://mvnrepository.com/artifact/com.mysql/mysql-connector-j)
  - `MongoDB` : `org.springframework.boot:spring-boot-starter-data-mongodb`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-data-mongodb?label=Maven%20Central)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-mongodb)
  - `Redis` : `org.springframework.boot:spring-boot-starter-data-redis`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-data-redis?label=Maven%20Central)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis)
  - `RabbitMQ` : `org.springframework.boot:spring-boot-starter-amqp`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-amqp?label=Maven%20Central)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-amqp)
- 父依赖不含 :
  - `FastJson` : `com.alibaba.fastjson2:fastjson2`  
    [![Maven Central](https://img.shields.io/maven-central/v/com.alibaba.fastjson2/fastjson2?label=Maven%20Central)](https://mvnrepository.com/artifact/com.alibaba.fastjson2/fastjson2)
  - `FastExcel` : `cn.idev.excel:fastexcel`  
    [![Maven Central](https://img.shields.io/maven-central/v/cn.idev.excel/fastexcel?label=Maven%20Central)](https://mvnrepository.com/artifact/cn.idev.excel/fastexcel)
  - `ElasticSearch(需要与服务器版本号相同)` : `co.elastic.clients:elasticsearch-java`  
    [![Maven Central](https://img.shields.io/maven-central/v/co.elastic.clients/elasticsearch-java?label=Maven%20Central)](https://mvnrepository.com/artifact/co.elastic.clients/elasticsearch-java)
  - `MQTT` : `org.eclipse.paho:org.eclipse.paho.client.mqttv3`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.eclipse.paho/org.eclipse.paho.client.mqttv3?label=Maven%20Central)](https://mvnrepository.com/artifact/org.eclipse.paho/org.eclipse.paho.client.mqttv3)
  - `加密算法` : `org.bouncycastle:bcpkix-jdk18on`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.bouncycastle/bcpkix-jdk18on?label=Maven%20Central)](https://mvnrepository.com/artifact/org.bouncycastle/bcpkix-jdk18on)
  - `Minio` : `io.minio:minio`  
    [![Maven Central](https://img.shields.io/maven-central/v/io.minio/minio?label=Maven%20Central)](https://mvnrepository.com/artifact/io.minio/minio)
  - `InfluxDB` : `com.influxdb:influxdb-client-java`  
    [![Maven Central](https://img.shields.io/maven-central/v/com.influxdb/influxdb-client-java?label=Maven%20Central)](https://mvnrepository.com/artifact/io.minio/minio)
  - `MyBatis` : `org.mybatis.spring.boot:mybatis-spring-boot-starter`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.mybatis.spring.boot/mybatis-spring-boot-starter?label=Maven%20Central)](https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter)
  - `PageHelper分页排序查询` : `com.github.pagehelper:pagehelper-spring-boot-starter`  
    [![Maven Central](https://img.shields.io/maven-central/v/com.github.pagehelper/pagehelper-spring-boot-starter?label=Maven%20Central)](https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper-spring-boot-starter)
  - `雪花ID生成器` : `cn.404z:id-spring-boot-autoconfigure`  
    [![Maven Central](https://img.shields.io/maven-central/v/cn.404z/id-spring-boot-autoconfigure?label=Maven%20Central)](https://mvnrepository.com/artifact/cn.404z/id-spring-boot-autoconfigure)
  - `IP地址转区域` : `cn.404z:ip2region-spring-boot-autoconfigure`  
    [![Maven Central](https://img.shields.io/maven-central/v/cn.404z/ip2region-spring-boot-autoconfigure?label=Maven%20Central)](https://mvnrepository.com/artifact/cn.404z/ip2region-spring-boot-autoconfigure)
  - `手机号码区域` : `cn.404z:phone2region-spring-boot-autoconfigure`  
    [![Maven Central](https://img.shields.io/maven-central/v/cn.404z/phone2region-spring-boot-autoconfigure?label=Maven%20Central)](https://mvnrepository.com/artifact/cn.404z/phone2region-spring-boot-autoconfigure)
  - `二维码生成器` : `cn.404z:qrcode-encoder`  
    [![Maven Central](https://img.shields.io/maven-central/v/cn.404z/qrcode-encoder?label=Maven%20Central)](https://mvnrepository.com/artifact/cn.404z/qrcode-encoder)
  - `轻量级集群任务SpringBoot启动器` : `cn.404z:tiny-task-spring-boot-starter`  
    [![Maven Central](https://img.shields.io/maven-central/v/cn.404z/tiny-task-spring-boot-starter?label=Maven%20Central)](https://mvnrepository.com/artifact/cn.404z/tiny-task-spring-boot-starter)
  - `轻量级权限认证SpringBoot启动器` : `cn.404z:tiny-token-spring-boot-starter`  
    [![Maven Central](https://img.shields.io/maven-central/v/cn.404z/tiny-token-spring-boot-starter?label=Maven%20Central)](https://mvnrepository.com/artifact/cn.404z/tiny-token-spring-boot-starter)
  - `Protocol Buffers` : `com.google.protobuf:protobuf-java`  
    [![Maven Central](https://img.shields.io/maven-central/v/com.google.protobuf/protobuf-java?label=Maven%20Central)](https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java)
  - `Protocol Buffers Util` : `com.google.protobuf:protobuf-java-util`  
    [![Maven Central](https://img.shields.io/maven-central/v/com.google.protobuf/protobuf-java-util?label=Maven%20Central)](https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java-util)
  - `HuTool工具包` : `cn.hutool:hutool-all`  
    [![Maven Central](https://img.shields.io/maven-central/v/cn.hutool/hutool-all?label=Maven%20Central)](https://mvnrepository.com/artifact/cn.hutool/hutool-all)
  - `JustAuth第三方登录` : `me.zhyd.oauth:JustAuth`  
    [![Maven Central](https://img.shields.io/maven-central/v/me.zhyd.oauth/JustAuth?label=Maven%20Central)](https://mvnrepository.com/artifact/me.zhyd.oauth/JustAuth)
  - `Tika语言、编码、类型检测` : `org.apache.tika:tika-core`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.apache.tika/tika-core?label=Maven%20Central)](https://mvnrepository.com/artifact/org.apache.tika/tika-core)
  - `Tika语言、编码、类型检测` : `org.apache.tika:tika-parsers-standard-package`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.apache.tika/tika-parsers-standard-package?label=Maven%20Central)](https://mvnrepository.com/artifact/org.apache.tika/tika-parsers-standard-package)
  - `Tika语言、编码、类型检测` : `org.apache.tika:tika-langdetect-tika`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.apache.tika/tika-langdetect-tika?label=Maven%20Central)](https://mvnrepository.com/artifact/org.apache.tika/tika-langdetect-tika)
  - `Ansj分词` : `org.ansj:ansj_seg`  
    [![Maven Central](https://img.shields.io/maven-central/v/org.ansj/ansj_seg?label=Maven%20Central)](https://mvnrepository.com/artifact/org.ansj/ansj_seg)
  - `二维码` : `com.google.zxing:core`  
    [![Maven Central](https://img.shields.io/maven-central/v/com.google.zxing/core?label=Maven%20Central)](https://mvnrepository.com/artifact/com.google.zxing/core)
  - `Knife4j` : `com.github.xiaoymin:knife4j-openapi3-jakarta-spring-boot-starter`  
    [![Maven Central](https://img.shields.io/maven-central/v/com.github.xiaoymin/knife4j-openapi3-jakarta-spring-boot-starter?label=Maven%20Central)](https://mvnrepository.com/artifact/com.github.xiaoymin/knife4j-openapi3-jakarta-spring-boot-starter)

## 关于

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://www.404z.cn/images/about.dark.svg">
  <img alt="About" src="https://www.404z.cn/images/about.light.svg">
</picture>
