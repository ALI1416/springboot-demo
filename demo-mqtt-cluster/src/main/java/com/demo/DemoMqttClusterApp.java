package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h1>启动类</h1>
 *
 * <h2>打包后运行方式</h2>
 * <p>
 * java -jar 文件名.jar<br>
 * 如果中文乱码，请用以下命令运行<br>
 * javaw -Dfile.encoding=utf-8 -jar 文件名.jar
 * </p>
 *
 * <h2>配置文件可放置目录(优先级从高到低)</h2>
 * <p>
 * ./config/ (当前项目路径config目录下)<br>
 * ./ (当前项目路径下)<br>
 * classpath:/config/ (类路径config目录下)<br>
 * classpath:/ (类路径下)<br>
 * </p>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@SpringBootApplication
public class DemoMqttClusterApp {

    public static void main(String[] args) {
        SpringApplication.run(DemoMqttClusterApp.class, args);
    }

}
