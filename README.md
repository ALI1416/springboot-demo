# demo

## 目录结构

- **demo-base** : springboot项目模板
- **demo-mysql** : springboot整合mysql
- **demo-util** : 工具测试
- .gitignore : git忽略
- LICENSE : 许可证
- pom.xml : 父pom
- README.md : 说明

### pom.xml

- `parent`父pom : `org.springframework.boot:spring-boot-starter-parent`
- `modules`模块 : 新增时添加`module`子标签
- `properties`属性 :
    - `jdk.version` : java使用版本，最低1.8
    - `maven.compiler.source`和`maven.compiler.target` : 打包成jar时java版本号
    - `maven.compiler.encoding`、`project.build.sourceEncoding`和`project.reporting.outputEncoding` : 项目编码
    - 继承依赖版本号 :
      - org.springframework.boot:spring-boot-starter-web
      - 