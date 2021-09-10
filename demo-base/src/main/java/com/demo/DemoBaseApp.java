package com.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
@Slf4j
public class DemoBaseApp {

    private static final Class<?> APP = DemoBaseApp.class;
    private static String[] argList;
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        argList = args;
        context = SpringApplication.run(APP, args);
    }

    /**
     * 关闭服务
     */
    public static void shutdown() {
        log.error("SpringBoot服务被强制关闭！");
        context.close();
    }

    /**
     * 重启服务
     */
    public static void restart() {
        log.error("SpringBoot服务被强制重启！");
        ExecutorService threadPool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        threadPool.execute(() -> {
            context.close();
            context = SpringApplication.run(APP, argList);
        });
        threadPool.shutdown();
    }

    /**
     * 防止Tomcat报400错误(设置可接收参数|{}[]\)
     */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}[]\\"));
        return factory;
    }

}
