# springboot-demo SpringBoot示例

[![License](https://img.shields.io/github/license/ALI1416/springboot-demo?label=License)](https://opensource.org/licenses/BSD-3-Clause)
[![Java Support](https://img.shields.io/badge/Java-8+-green)](https://openjdk.org/)
[![Repo Size](https://img.shields.io/github/repo-size/ALI1416/springboot-demo?label=Repo%20Size&color=success)](https://github.com/ALI1416/springboot-demo/archive/refs/heads/master.zip)

## 简介

SpringBoot示例

## 项目

- **`demo-base`** : 基框架
  - SpringBoot Web
  - FastJson
- **`demo-config`** : 配置
  - SpringBoot Web
  - FastJson
  - 配置
- **`demo-easy-excel`** : EasyExcel
  - SpringBoot Web
  - FastJson
  - EasyExcel
- **`demo-elastic-search`** : ElasticSearch
  - SpringBoot Web
  - FastJson
  - ElasticSearch
- **`demo-java-advanced`** : Java进阶
  - HuTool工具包
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
  - HuTool工具包
- **`demo-mqtt`** : MQTT
  - SpringBoot Web
  - FastJson
  - MQTT
- **`demo-mysql`** : MySQL
  - SpringBoot Web
  - FastJson
  - MySQL
  - MyBatis
  - IP解析
  - 雪花ID生成器
  - IP地址转区域
  - HuTool工具包
- **`demo-mysql2`** : MySQL2
  - SpringBoot Web
  - FastJson
  - MySQL
  - MyBatis
  - PageHelper分页排序查询
  - IP解析
  - 雪花ID生成器
  - HuTool工具包
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
- **`demo-tiny-token`** : 轻量级权限认证SpringBoot启动器
  - SpringBoot Web
  - FastJson
  - Redis
  - 轻量级权限认证SpringBoot启动器
  - MySQL
  - MyBatis
  - 雪花ID生成器
  - HuTool工具包
- **`demo-util`** : 工具
  - SpringBoot Web
  - FastJson
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
  - FastJson

### 版本号

- 父依赖 :
  - `SpringBoot Parent` : `org.springframework.boot:spring-boot-starter-parent`  
  ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-parent?label=Maven%20Central)
- 继承父依赖 :
  - `SpringBoot Web` : `org.springframework.boot:spring-boot-starter-web`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-web?label=Maven%20Central)
  - `邮件` : `org.springframework.boot:spring-boot-starter-mail`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-mail?label=Maven%20Central)
  - `热部署` : `org.springframework.boot:spring-boot-devtools`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-devtools?label=Maven%20Central)
  - `配置` : `org.springframework.boot:spring-boot-configuration-processor`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-configuration-processor?label=Maven%20Central)
  - `测试` : `org.springframework.boot:spring-boot-starter-test`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-test?label=Maven%20Central)
  - `SpringBoot整合Maven插件` : `org.springframework.boot:spring-boot-maven-plugin`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-maven-plugin?label=Maven%20Central)
  - `Maven打包插件` : `org.apache.maven.plugins:maven-compiler-plugin`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.apache.maven.plugins/maven-compiler-plugin?label=Maven%20Central)
  - `MySQL(与服务器版本号对应，不需一致)` : `mysql:mysql-connector-java`  
    ![Maven Central](https://img.shields.io/maven-central/v/mysql/mysql-connector-java?label=Maven%20Central)
  - `MongoDB(与服务器版本号对应，不需一致)` : `org.springframework.boot:spring-boot-starter-data-mongodb`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-data-mongodb?label=Maven%20Central)
  - `Redis(与服务器版本号不对应，不需一致)` : `org.springframework.boot:spring-boot-starter-data-redis`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-data-redis?label=Maven%20Central)
  - `RabbitMQ(与服务器版本号不对应，不需一致)` : `org.springframework.boot:spring-boot-starter-amqp`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-amqp?label=Maven%20Central)
  - `MQTT(无法修改版本号)` : `org.springframework.boot:spring-boot-starter-integration`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-integration?label=Maven%20Central)
  - `MQTT(无法修改版本号)` : `org.springframework.integration:spring-integration-stream`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.integration/spring-integration-stream?label=Maven%20Central)
  - `MQTT(无法修改版本号)` : `org.springframework.integration:spring-integration-mqtt`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.integration/spring-integration-mqtt?label=Maven%20Central)
  - `实体层注解(与IDE安装的版本号对应，不需一致)` : `org.projectlombok:lombok`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.projectlombok/lombok?label=Maven%20Central)
- 需要修改父依赖版本号 :
  - `ElasticSearch(与服务器版本号对应，需要一致)` : `org.springframework.boot:spring-boot-starter-data-elasticsearch`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-data-elasticsearch?label=Maven%20Central)
- 父依赖不含 :
  - `Minio(与服务器版本号不对应，不需一致)` : `io.minio:minio`  
    ![Maven Central](https://img.shields.io/maven-central/v/io.minio/minio?label=Maven%20Central)
  - `MyBatis` : `org.mybatis.spring.boot:mybatis-spring-boot-starter`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.mybatis.spring.boot/mybatis-spring-boot-starter?label=Maven%20Central)
  - `PageHelper分页排序查询` : `com.github.pagehelper:pagehelper-spring-boot-starter`  
    ![Maven Central](https://img.shields.io/maven-central/v/com.github.pagehelper/pagehelper-spring-boot-starter?label=Maven%20Central)
  - `雪花ID生成器` : `cn.404z:id-spring-boot-autoconfigure`  
    ![Maven Central](https://img.shields.io/maven-central/v/cn.404z/id-spring-boot-autoconfigure?label=Maven%20Central)
  - `IP地址转区域` : `cn.404z:ip2region-spring-boot-autoconfigure`  
    ![Maven Central](https://img.shields.io/maven-central/v/cn.404z/ip2region-spring-boot-autoconfigure?label=Maven%20Central)
  - `手机号码区域` : `cn.404z:phone2region-spring-boot-autoconfigure`  
    ![Maven Central](https://img.shields.io/maven-central/v/cn.404z/phone2region-spring-boot-autoconfigure?label=Maven%20Central)
  - `二维码生成器` : `cn.404z:qrcode-encoder`  
    ![Maven Central](https://img.shields.io/maven-central/v/cn.404z/qrcode-encoder?label=Maven%20Central)
  - `轻量级权限认证SpringBoot启动器` : `cn.404z:tiny-token-spring-boot-starter`  
    ![Maven Central](https://img.shields.io/maven-central/v/cn.404z/tiny-token-spring-boot-starter?label=Maven%20Central)
  - `FastJson` : `com.alibaba.fastjson2:fastjson2`  
    ![Maven Central](https://img.shields.io/maven-central/v/com.alibaba.fastjson2/fastjson2?label=Maven%20Central)
  - `FastJson Extension Spring5` : `com.alibaba.fastjson2:fastjson2-extension-spring5`  
    ![Maven Central](https://img.shields.io/maven-central/v/com.alibaba.fastjson2/fastjson2-extension-spring5?label=Maven%20Central)
  - `EasyExcel` : `com.alibaba:easyexcel`  
    ![Maven Central](https://img.shields.io/maven-central/v/com.alibaba/easyexcel?label=Maven%20Central)
  - `Protocol Buffers` : `com.google.protobuf:protobuf-java`  
    ![Maven Central](https://img.shields.io/maven-central/v/com.google.protobuf/protobuf-java?label=Maven%20Central)
  - `Protocol Buffers Util` : `com.google.protobuf:protobuf-java-util`  
    ![Maven Central](https://img.shields.io/maven-central/v/com.google.protobuf/protobuf-java-util?label=Maven%20Central)
  - `HuTool工具包` : `cn.hutool:hutool-all`  
    ![Maven Central](https://img.shields.io/maven-central/v/cn.hutool/hutool-all?label=Maven%20Central)
  - `JustAuth第三方登录` : `me.zhyd.oauth:JustAuth`  
    ![Maven Central](https://img.shields.io/maven-central/v/me.zhyd.oauth/JustAuth?label=Maven%20Central)
  - `Tika语言、编码、类型检测` : `org.apache.tika:tika-core`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.apache.tika/tika-core?label=Maven%20Central)
  - `Tika语言、编码、类型检测` : `org.apache.tika:tika-parsers-standard-package`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.apache.tika/tika-parsers-standard-package?label=Maven%20Central)
  - `Tika语言、编码、类型检测` : `org.apache.tika:tika-langdetect-tika`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.apache.tika/tika-langdetect-tika?label=Maven%20Central)
  - `Ansj分词` : `org.ansj:ansj_seg`  
    ![Maven Central](https://img.shields.io/maven-central/v/org.ansj/ansj_seg?label=Maven%20Central)
  - `二维码` : `com.google.zxing:core`  
    ![Maven Central](https://img.shields.io/maven-central/v/com.google.zxing/core?label=Maven%20Central)
  - `Knife4j` : `com.github.xiaoymin:knife4j-openapi3-spring-boot-starter`  
    ![Maven Central](https://img.shields.io/maven-central/v/com.github.xiaoymin/knife4j-openapi3-spring-boot-starter?label=Maven%20Central)

## 关于

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://www.404z.cn/images/about.dark.svg">
  <img alt="About" src="https://www.404z.cn/images/about.light.svg">
</picture>
