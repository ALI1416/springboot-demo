# demo-redis

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
  - `ToStringBase` : ToString基类
- **`config`** : 配置文件夹
  - `TomcatConfig` : Tomcat配置类
  - `WebMvcConfig` : WebMvc配置
- **`constant`** : 常数文件夹
  - `FormatConstant` : 格式常量
  - `ResultCodeEnum` : 返回结果状态枚举类
- **`controller`** : 控制层文件夹
  - `HashController` : 哈希
  - `IndexController` : 通用
  - `ListController` : 列表
  - `SetController` : 集合
  - `StringController` : 字符串
- **`entity`** : 实体层文件夹
  - **`po`** : 数据库实体层
    - `User` : 用户
  - **`pojo`** : 普通实体层
    - `Result` : 返回结果实体类
- **`handler`** : 处理层文件夹
  - `GlobalExceptionHandler` : 全局异常处理类(404异常除外)
  - `NotFoundExceptionHandler` : 404异常处理类
- **`util`** : 工具
  - `RedisUtils` : Redis工具类
- `DemoRedisApp` : 启动类

### src.main.resources

- **`config`** : 配置文件
  - `application.yml` : 总配置
  - `application-dev.yml` : 开发环境配置
  - `application-prd.yml` : 生产环境配置
  - `application-qas.yml` : 测试环境配置
- **`static`** : 静态文件
  - `favicon.ico` : 图标
- `404z.cn.jks` : ssl证书
- `banner.txt` : 横幅

#### application.yml

- `spring.profiles.active` : 使用的配置文件后缀
- `spring.servlet.multipart` : 传输文件
  - `max-file-size` : 最大文件大小
  - `max-request-size` : 最大请求大小
- `server.compression.enable` : 是否启用响应压缩
- `server.servlet.content-path` : 前缀(例如/api)
- `server.port` : 端口号
- `server.ssl` : ssl证书
  - `key-store` : 路径(例如classpath:404z.cn.jks)
  - `key-store-password` : 密码
  - `key-store-type` : 类型
- `logging.file.name` : 日志文件(可带路径)

#### application-xxx.yml

- `spring.devtools.restart.enabled` : 启用热部署
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

### src.test.java.com.demo

- `AppTest` : SpringBoot测试
- `MainTest` : 普通测试
