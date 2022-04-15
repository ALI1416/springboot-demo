# demo

## 目录结构
- **`demo-base`** : springboot项目模板
  - SpringBoot Web
  - FastJson
- **`demo-mysql`** : 整合mysql
  - SpringBoot Web
  - FastJson
  - MySQL
  - MyBatis
  - IP解析
  - 雪花ID生成器
  - IP地址转区域
  - HuTool工具包
- **`demo-mysql2`** : 整合mysql2
  - SpringBoot Web
  - FastJson
  - MySQL
  - MyBatis
  - PageHelper分页排序查询
  - IP解析
  - 雪花ID生成器
  - IP地址转区域
  - HuTool工具包
- **`demo-redis`** : 整合redis
  - SpringBoot Web
  - FastJson
  - Redis
  - 雪花ID生成器
- **`mongodb`** : 整合mongodb
  - SpringBoot Web
  - FastJson
  - MongoDB
  - 雪花ID生成器
  - HuTool工具包
- **`rabbitmq`** : 整合rabbitmq
  - SpringBoot Web
  - FastJson
  - Protocol Buffers
  - Protocol Buffers Util
  - RabbitMQ
  - 雪花ID生成器
- **`sa-token`** : 整合sa-token
  - SpringBoot Web
  - FastJson
  - Redis
  - SaToken权限认证
  - SaToken整合Redis(使用jackson序列化)
  - MySQL
  - MyBatis
  - PageHelper分页排序查询
  - 雪花ID生成器
- `.gitignore` : git忽略
- `LICENSE` : 许可证
- `pom.xml` : 父pom
- `README.md` : 说明

### pom.xml
- `parent` : 父pom : `org.springframework.boot:spring-boot-starter-parent`
  <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.springframework.boot/spring-boot-starter-parent/badge.svg)
- `modules` : 模块，新增时添加`module`子标签
- `properties` : 属性
  - `java.version` : 指定java版本，最低1.8
  - `maven.compiler.source`和`maven.compiler.target` : 打包成jar时指定java版本
  - `maven.compiler.encoding`、`project.build.sourceEncoding`和`project.reporting.outputEncoding` : 项目编码
  - 继承父pom版本号 :
    - `SpringBootWeb` : `org.springframework.boot:spring-boot-starter-web`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.springframework.boot/spring-boot-starter-web/badge.svg)
    - `邮件` : `org.springframework.boot:spring-boot-starter-mail`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.springframework.boot/spring-boot-starter-mail/badge.svg)
    - `热部署` : `org.springframework.boot:spring-boot-devtools`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.springframework.boot/spring-boot-devtools/badge.svg)
    - `配置` : `org.springframework.boot:spring-boot-configuration-processor`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.springframework.boot/spring-boot-configuration-processor/badge.svg)
    - `测试` : `org.springframework.boot:spring-boot-starter-test`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.springframework.boot/spring-boot-starter-test/badge.svg)
    - `SpringBoot整合Maven插件` : `org.springframework.boot:spring-boot-maven-plugin`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.springframework.boot/spring-boot-maven-plugin/badge.svg)
    - `Maven打包插件` : `org.apache.maven.plugins:maven-compiler-plugin`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.apache.maven.plugins/maven-compiler-plugin/badge.svg)
    - `MySQL(与服务器版本号对应，不需一致)` : `mysql:mysql-connector-java`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/mysql/mysql-connector-java/badge.svg)
    - `MongoDB(与服务器版本号对应，不需一致)` : `org.springframework.boot:spring-boot-starter-data-mongodb`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.springframework.boot/spring-boot-starter-data-mongodb/badge.svg)
    - `Redis(与服务器版本号不对应，不需一致)` : `org.springframework.boot:spring-boot-starter-data-redis`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.springframework.boot/spring-boot-starter-data-redis/badge.svg)
    - `RabbitMQ(与服务器版本号不对应，不需一致)` : `org.springframework.boot:spring-boot-starter-amqp`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.springframework.boot/spring-boot-starter-amqp/badge.svg)
    - `实体层注解(与IDE安装的版本号对应，不需一致)` : `org.projectlombok:lombok`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.projectlombok/lombok/badge.svg)
  - 父pom版本号需要修改 :
    - `ElasticSearch(与服务器版本号对应，需要一致)` : `org.springframework.boot:spring-boot-starter-data-elasticsearch`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.springframework.boot/spring-boot-starter-data-elasticsearch/badge.svg)
  - 父pom不含依赖 :
    - `Minio(与服务器版本号不对应，不需一致)` : `io.minio:minio`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.minio/minio/badge.svg)
    - `MyBatis` : `org.mybatis.spring.boot:mybatis-spring-boot-starter`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.mybatis.spring.boot/mybatis-spring-boot-starter/badge.svg)
    - `PageHelper分页排序查询` : `com.github.pagehelper:pagehelper-spring-boot-starter`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.pagehelper/pagehelper-spring-boot-starter/badge.svg)
    - `SaToken权限认证` : `cn.dev33:sa-token-spring-boot-starter`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.dev33/sa-token-spring-boot-starter/badge.svg)
    - `SaToken整合Redis(使用jackson序列化)` : `cn.dev33:sa-token-dao-redis-jackson`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.dev33/sa-token-dao-redis-jackson/badge.svg)
    - `雪花ID生成器` : `cn.404z:id-spring-boot-autoconfigure`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.404z/id-spring-boot-autoconfigure/badge.svg)
    - `IP地址转区域` : `cn.404z:ip2region-spring-boot-autoconfigure`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.404z/ip2region-spring-boot-autoconfigure/badge.svg)
    - `IP解析` : `org.lionsoul:ip2region`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.lionsoul/ip2region/badge.svg)
    - `FastJson` : `com.alibaba:fastjson`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.alibaba/fastjson/badge.svg)
    - `EasyExcel` : `com.alibaba:easyexcel`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.alibaba/easyexcel/badge.svg)
    - `Protocol Buffers` : `com.google.protobuf:protobuf-java`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.google.protobuf/protobuf-java/badge.svg)
    - `Protocol Buffers Util` : `com.google.protobuf:protobuf-java-util`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.google.protobuf/protobuf-java-util/badge.svg)
    - `HuTool工具包` : `cn.hutool:hutool-all`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.hutool/hutool-all/badge.svg)
    - `JustAuth第三方登录` : `me.zhyd.oauth:JustAuth`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/me.zhyd.oauth/JustAuth/badge.svg)
    - `手机号码归属地查询` : `me.ihxq.projects:phone-number-geo`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/me.ihxq.projects/phone-number-geo/badge.svg)
    - `Tika语言、编码、类型检测` : `org.apache.tika:tika-parsers`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.apache.tika/tika-parsers/badge.svg)
    - `Ansj分词` : `org.ansj:ansj_seg`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.ansj/ansj_seg/badge.svg)
    - `二维码` : `com.google.zxing:core`
      <br>![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.google.zxing/core/badge.svg)
- `dependencyManagement` : 依赖管理，不用写版本号
- `profiles` : 配置
  - `profile.id:install` : 打包配置
    - `activation.activeByDefault:true` : 默认配置
    - `build.plugins.plugin.configuration` : 插件配置，编译插件
      - `source`和`target` : 指定java版本
