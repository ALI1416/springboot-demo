# demo-base

## 目录结构

- **`src.main.java`** : 项目代码
- **`src.main.resources`** : 项目资源文件
- **`src.test.java`** : 测试代码
- `pom.xml` : 项目pom
- `README.md` : 说明

### pom.xml

- `parent` : 父pom : `com.demo:springboot-demo`
- `dependencies` : 依赖
- `build` : 构建插件

### src.main.java.com.demo

- **`base`** : 基类文件夹
  - `ToStringBase` : ToString格式化基类
  - `DaoBase` : Dao层基类
  - `EntityBase` : 实体层基类
  - `ServiceBase` : 服务层基类
  - `ControllerBase` : 控制层基类
- **`config`** : 配置文件夹
  - `RedisConfig` : Redis配置类
  - `TomcatConfig` : Tomcat配置类
  - `WebMvcConfig` : WebMvc配置类
- **`constant`** : 常数文件夹
  - `FormatConstant` : 格式常量类
  - `ResultCodeEnum` : 返回结果状态枚举类
  - `Constant` : 常量
  - `RedisConstant` : Redis常量
- **`controller`** : 控制层文件夹
- **`dao`** : Dao层文件夹
  - **`mysql`** : MysqlDao层文件夹
- **`entity`** : 实体层文件夹
  - **`po`** : 数据库实体层
  - **`pojo`** : 普通实体层
  - **`vo`** : 值对象实体层
- **`handler`** : 处理层文件夹
  - `GlobalExceptionHandler` : 全局异常处理类(404异常除外)
  - `NotFoundExceptionHandler` : 404异常处理类
- **`interceptor`** : 拦截器文件夹
  - `RouteInterceptor` : 路由拦截
- **`mapper`** : MySQL的Mapper文件夹
- **`service`** : 服务层
- **`tool`** : 工具类(无后缀Utils)
- **`util`** : 工具类(有后缀Utils)
- `DemoSaTokenApp` : 启动类

### src.main.resources

- **`config`** : 配置文件
  - `application.yml` : 总配置
  - `application-dev.yml` : 开发环境配置
  - `application-prd.yml` : 生产环境配置
  - `application-qas.yml` : 测试环境配置
- **`mapper`** : MySQL的mapper文件
- **`sql`** : mysql文件
- **`static`** : 静态文件
  - `favicon.ico` : 图标
- `404z.cn.jks` : ssl证书
- `banner.txt` : 横幅

#### application.yml

- `spring.profiles.active` : 使用的配置文件后缀
- `spring.servlet.multipart` : 传输文件
  - `max-file-size` : 最大文件大小
  - `max-request-size` : 最大请求大小
- `spring.datasource` : 数据库
  - `driver-class-name` : 驱动名
  - `hikari.max-lifetime` : 连接超时时间
- `server.compression.enable` : 是否启用响应压缩
- `server.servlet.content-path` : 前缀(例如/api)
- `server.port` : 端口号
- `server.ssl` : ssl证书
  - `key-store` : 路径(例如classpath:404z.cn.jks)
  - `key-store-password` : 密码
  - `key-store-type` : 类型
- `logging.file.name` : 日志文件(可带路径)
- `mybatis` : mybatis
  - `mapper-locations` : mapper文件位置(例如classpath:mapper/*.xml)
  - `type-aliases-package` : 用于别名的包
  - `configuration.map-underscore-to-camel-case` : mapper文件中大写转下划线
- `id` : 雪花ID生成器
  - `machine-id` : 机器码
  - `machine-bits` : 机器码位数
  - `sequence-bits` : 序列号位数
- `sa-token` : sa-token
  - `is-print` : 是否在初始化配置时打印版本字符画
  - `is-share` : 在多人登录同一账号时，是否共用一个token

#### application-xxx.yml

- `spring.devtools.restart.enabled` : 启用热部署
- `spring.datasource` : 数据库
  - `url` : url(例如jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true)
  - `username` : 用户名
  - `password` : 密码
- `spring.redis` : redis配置
  - `database` : 数据库索引
  - `host` : 地址
  - `port` : 端口
  - `password` : 密码
  - `timeout` : 超时时间
- `logging.level.root` : 记录日志等级，值如下
  - `trace` : 跟踪
  - `debug` : 调试
  - `info` : 信息
  - `warn` : 警告
  - `error` : 错误
  - `fatal` : 致命
  - `off` : 关闭
- `sa-token` : sa-token
  - `is-log` : 是否打印操作日志

### src.test.java.com.demo

- `AppTest` : SpringBoot测试
- `MainTest` : 普通测试
