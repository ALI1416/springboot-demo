package com.demo.hutool.io;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.io.file.*;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.io.watch.watchers.DelayWatcher;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Arrays;
import java.util.Properties;

/**
 * <h1>IO流</h1>
 *
 * <p>
 * createDate 2022/03/08 11:35:28
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        ioUtil();
        fileUtil();
        fileTypeUtil();
        watchMonitor();
        fileWriter();
        fileReader();
        fileAppender();
        tailer();
        fileNameUtil();
        resourceUtil();
        classPathResource();
    }

    /**
     * IO工具类
     */
    private static void ioUtil() {
        log.info("---------- IO工具类 ----------");
        // 输入流
        BufferedInputStream in = FileUtil.getInputStream("E:/log/platform.log");
        // 输出流
        BufferedOutputStream out = FileUtil.getOutputStream("E:/log/platform2.log");
        // 拷贝
        long copySize = IoUtil.copy(in, out);
        log.info("传输的byte数:" + copySize);
        // 关闭
        IoUtil.close(in);
        IoUtil.close(out);
    }

    /**
     * 文件工具类
     */
    private static void fileUtil() {
        log.info("---------- 文件工具类 ----------");
        // 拷贝文件
        FileUtil.copy("E:/log/platform2.log", "E:/log/platform3.log", false);
        // 删除文件
        FileUtil.del("E:/log/platform2.log");
        // 创建文件
        FileUtil.touch("E:/log/platform4.log");
        // 创建文件夹
        FileUtil.mkdir("E:/log/platform");
        // 列出文件
        File[] ls = FileUtil.ls("E:/log");
        log.info(Arrays.toString(Arrays.stream(ls).map(File::getName).toArray(String[]::new)));
    }

    /**
     * 文件类型判断
     */
    private static void fileTypeUtil() {
        log.info("---------- 文件类型判断 ----------");
        log.info("文件类型:" + FileTypeUtil.getType(new File("C:/Windows/regedit.exe")));
    }

    /**
     * 文件监听
     */
    private static void watchMonitor() {
        log.info("---------- 文件监听 ----------");
        /*监听指定事件*/
        WatchMonitor watchMonitor = WatchMonitor.create(new File("E:/log/platform4.log"), WatchMonitor.EVENTS_ALL);
        watchMonitor.setWatcher(new Watcher() {
            // 需要实现全部接口
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
                log.info("创建:{}-> {}", currentPath, event.context());
            }

            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                log.info("修改:{}-> {}", currentPath, event.context());
            }

            @Override
            public void onDelete(WatchEvent<?> event, Path currentPath) {
                log.info("删除:{}-> {}", currentPath, event.context());
            }

            @Override
            public void onOverflow(WatchEvent<?> event, Path currentPath) {
                log.info("丢失:{}-> {}", currentPath, event.context());
            }
        });
        //设置监听目录的最大深入，目录层级大于指定层级的变更将不被监听，默认只监听当前层级目录
        watchMonitor.setMaxDepth(3);
        //启动监听
        watchMonitor.start();

        /*监听全部事件*/
        WatchMonitor.createAll(new File("E:/log/platform4.log"), new SimpleWatcher() {
            // 实现部分接口
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                log.info("监听全部事件，仅修改{}:{}->{}", event.kind(), currentPath, event.context());
            }
        }).start();

        /*延迟处理监听事件*/
        WatchMonitor.createAll("E:/log/platform4.log", new DelayWatcher(new SimpleWatcher() {
            // 实现部分接口
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                log.info("延迟处理监听事件，仅修改{}:{}->{}", event.kind(), currentPath, event.context());
            }
        }, 500)).start();

    }

    /**
     * 文件写入
     */
    private static void fileWriter() {
        log.info("---------- 文件写入 ----------");
        FileWriter writer = new FileWriter("E:/log/platform5.log");
        writer.write("platform5.log");
        writer.append("\nappend");
    }

    /**
     * 文件读取
     */
    private static void fileReader() {
        log.info("---------- 文件读取 ----------");
        FileReader fileReader = new FileReader("E:/log/platform5.log");
        log.info("读取文件内容:" + fileReader.readString());
    }

    /**
     * 文件追加
     */
    private static void fileAppender() {
        log.info("---------- 文件追加 ----------");
        FileAppender appender = new FileAppender(new File("E:/log/platform5.log"), 16, true);
        appender.append("123");
        appender.append("abc");
        appender.append("xyz");
        appender.flush();
    }

    /**
     * 文件跟随
     */
    private static void tailer() {
        log.info("---------- 文件跟随 ----------");
        // 会阻塞当前线程
        Thread thread = new Thread(() -> {
            Tailer tailer = new Tailer(FileUtil.file("E:/log/platform5.log"), new TailerHandler(), 2);
            tailer.start();
        });
        thread.start();
    }

    /**
     * 文件跟随回调
     */
    public static class TailerHandler implements LineHandler {
        @Override
        public void handle(String line) {
            log.info("文件跟随回调:" + line);
        }
    }

    /**
     * 文件名工具
     */
    private static void fileNameUtil() {
        log.info("---------- 文件名工具 ----------");
        File file = new File("E:/log/platform5.log");
        log.info("文件名:" + FileNameUtil.getName(file));
        log.info("前缀:" + FileNameUtil.getPrefix(file));
        log.info("后缀:" + FileNameUtil.getSuffix(file));
    }

    /**
     * 资源工具
     */
    private static void resourceUtil() {
        log.info("---------- 资源工具 ----------");
        log.info("读取Classpath下的资源为字符串，使用UTF-8编码:" + ResourceUtil.readUtf8Str("library.properties"));
    }

    /**
     * ClassPath资源访问
     */
    private static void classPathResource() {
        log.info("---------- ClassPath资源访问 ----------");
        ClassPathResource resource = new ClassPathResource("library.properties");
        Properties properties = new Properties();
        try {
            properties.load(resource.getStream());
            log.info("ClassPath读取:" + properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
