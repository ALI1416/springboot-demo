# springboot-demo SpringBoot示例

[![License](https://img.shields.io/github/license/ali1416/springboot-demo?label=License)](https://opensource.org/licenses/BSD-3-Clause)
[![Java Support](https://img.shields.io/badge/Java-8+-green)](https://openjdk.org/)
[![Repo Size](https://img.shields.io/github/repo-size/ali1416/springboot-demo?label=Repo%20Size&color=success)](https://github.com/ALI1416/springboot-demo/archive/refs/heads/master.zip)

## 简介

SpringBoot示例

## 目录结构

- **`demo-base`** : 基础框架
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
- **`demo-mail`** : 邮件
  - SpringBoot Web
  - FastJson
  - 邮件
- **`demo-minio`** : minio
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
  - IP地址转区域
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
- **`demo-sa-token`** : SaToken权限认证
  - SpringBoot Web
  - FastJson
  - Redis
  - SaToken权限认证
  - MySQL
  - MyBatis
  - 雪花ID生成器
  - HuTool工具包
- **`demo-socket`** :WebSocket
  - SpringBoot Web
  - SpringBoot WebSocket
  - FastJson
- **`demo-util`** :工具
  - SpringBoot Web
  - FastJson
  - 雪花ID生成器
  - IP地址转区域
  - 手机号码转区域
  - HuTool工具包
  - Tika
  - Ansj分词
  - 二维码
- `.gitignore` : git忽略
- `LICENSE` : 许可证
- `pom.xml` : 项目父pom
- `README.md` : 自述文件

### pom.xml

- `parent` : `SpringBoot Parent` : `org.springframework.boot:spring-boot-starter-parent`  
  ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-parent?label=Maven%20Central)
- `modules` : 模块，新增时添加`module`子标签
- `properties` : 属性
  - `java.version` : 指定java版本，最低1.8
  - `maven.compiler.source`和`maven.compiler.target` : 打包成jar时指定java版本
  - `maven.compiler.encoding`、`project.build.sourceEncoding`和`project.reporting.outputEncoding` : 项目编码
  - 继承父pom版本号 :
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
    - `实体层注解(与IDE安装的版本号对应，不需一致)` : `org.projectlombok:lombok`  
      ![Maven Central](https://img.shields.io/maven-central/v/org.projectlombok/lombok?label=Maven%20Central)
  - 父pom版本号需要修改 :
    - `ElasticSearch(与服务器版本号对应，需要一致)` : `org.springframework.boot:spring-boot-starter-data-elasticsearch`  
      ![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-data-elasticsearch?label=Maven%20Central)
  - 父pom不含依赖 :
    - `Minio(与服务器版本号不对应，不需一致)` : `io.minio:minio`  
      ![Maven Central](https://img.shields.io/maven-central/v/io.minio/minio?label=Maven%20Central)
    - `MyBatis` : `org.mybatis.spring.boot:mybatis-spring-boot-starter`  
      ![Maven Central](https://img.shields.io/maven-central/v/org.mybatis.spring.boot/mybatis-spring-boot-starter?label=Maven%20Central)
    - `PageHelper分页排序查询` : `com.github.pagehelper:pagehelper-spring-boot-starter`  
      ![Maven Central](https://img.shields.io/maven-central/v/com.github.pagehelper/pagehelper-spring-boot-starter?label=Maven%20Central)
    - `SaToken权限认证` : `cn.dev33:sa-token-spring-boot-starter`  
      ![Maven Central](https://img.shields.io/maven-central/v/cn.dev33/sa-token-spring-boot-starter?label=Maven%20Central)
    - `SaToken整合Redis(使用jackson序列化)` : `cn.dev33:sa-token-dao-redis-jackson`  
      ![Maven Central](https://img.shields.io/maven-central/v/cn.dev33/sa-token-dao-redis-jackson?label=Maven%20Central)
    - `雪花ID生成器` : `cn.404z:id-spring-boot-autoconfigure`  
      ![Maven Central](https://img.shields.io/maven-central/v/cn.404z/id-spring-boot-autoconfigure?label=Maven%20Central)
    - `IP地址转区域` : `cn.404z:ip2region-spring-boot-autoconfigure`  
      ![Maven Central](https://img.shields.io/maven-central/v/cn.404z/ip2region-spring-boot-autoconfigure?label=Maven%20Central)
    - `手机号码区域` : `cn.404z:phone2region-spring-boot-autoconfigure`  
      ![Maven Central](https://img.shields.io/maven-central/v/cn.404z/phone2region-spring-boot-autoconfigure?label=Maven%20Central)
    - `FastJson` : `com.alibaba:fastjson`  
      ![Maven Central](https://img.shields.io/maven-central/v/com.alibaba/fastjson?label=Maven%20Central)
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
    - `Tika语言、编码、类型检测` : `org.apache.tika:tika-parsers`  
      ![Maven Central](https://img.shields.io/maven-central/v/org.apache.tika/tika-parsers?label=Maven%20Central)
    - `Ansj分词` : `org.ansj:ansj_seg`  
      ![Maven Central](https://img.shields.io/maven-central/v/org.ansj/ansj_seg?label=Maven%20Central)
    - `二维码` : `com.google.zxing:core`  
      ![Maven Central](https://img.shields.io/maven-central/v/com.google.zxing/core?label=Maven%20Central)
- `dependencyManagement` : 依赖管理，不用写版本号
- `profiles` : 配置
  - `profile.id:install` : 打包配置
    - `activation.activeByDefault:true` : 默认配置
    - `build.plugins.plugin.configuration` : 插件配置，编译插件
      - `source`和`target` : 指定java版本

## 交流

- [x] QQ：`1416978277`
- [x] 微信：`1416978277`
- [x] 支付宝：`1416978277@qq.com`

![交流](https://cdn.jsdelivr.net/gh/ALI1416/ALI1416/image/contact.png)

## 赞助

![赞助](https://cdn.jsdelivr.net/gh/ALI1416/ALI1416/image/donate.png)
