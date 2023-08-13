# demo-mail

## 代码

- **`base`** : 基类文件夹
  - `ToStringBase` : ToString基类
- **`config`** : 配置文件夹
  - `TomcatConfig` : Tomcat配置
  - `WebMvcConfig` : WebMvc配置
- **`constant`** : 常量文件夹
  - `FormatConstant` : 格式常量
  - `ResultEnum` : 统一返回状态枚举
- **`controller`** : 控制层文件夹
  - `IndexController` : 首页
- **`entity`** : 实体层文件夹
  - **`po`** : 数据库实体层文件夹
    - `User` : 用户
  - **`pojo`** : 普通实体层文件夹
    - `GlobalException` : 全局异常
    - `Result` : 统一返回
- **`handler`** : 处理层文件夹
  - `GlobalExceptionHandler` : 全局异常处理类(404异常除外)
  - `PageNotFoundExceptionHandler` : 404异常处理类
- `DemoMailApp` : 启动类

## 资源

- **`config`** : 配置文件夹
  - `application.yml` : 总配置
  - `application-dev.yml` : 开发环境配置
  - `application-prd.yml` : 生产环境配置
  - `application-qas.yml` : 测试环境配置
- **`static`** : 静态资源文件夹
  - `favicon.ico` : 图标
- `404z.cn.jks` : SSL证书
- `banner.txt` : 横幅

## 测试

- `AppTest` : SpringBoot测试
- `MainTest` : 普通测试

## application.yml

- `spring.profiles.active` : 使用哪个配置
- `spring.servlet.multipart` : 传输文件
  - `max-file-size` : 最大文件大小
  - `max-request-size` : 最大请求大小
- `server.compression.enable` : 是否启用响应压缩
- `server.servlet.content-path` : 前缀(例如`/api`)
- `server.port` : 端口号
- `server.ssl` : SSL证书
  - `key-store` : 路径(例如`classpath:404z.cn.jks`)
  - `key-store-password` : 密码
  - `key-store-type` : 类型
- `logging.file.name` : 日志文件名称(可带路径)

## application-xxx.yml

- `spring.devtools.restart.enabled` : 是否启用热部署
- `logging.level.root` : 日志级别，例如
  - `trace` : 跟踪
  - `debug` : 调试
  - `info` : 信息
  - `warn` : 警告
  - `error` : 错误
  - `fatal` : 致命
  - `off` : 关闭
