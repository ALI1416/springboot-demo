package com.demo.init;

import cn.hutool.core.io.FileUtil;
import com.demo.property.Ip2RegionProperty;
import com.demo.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.InputStream;

/**
 * <h1>Ip2Region初始化</h1>
 *
 * <p>
 * createDate 2020/11/20 19:42:59
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Ip2RegionInit {

    public static void init() {
        log.info("Ip2Region初始化开始...");
        copyFile();
        IpUtils.ip2RegionInitial();
        start();
        log.info("Ip2Region初始化结束。");
    }

    private static void copyFile() {
        try {
            log.info("Ip2Region检查并创建文件...");
            /*文件夹*/
            File libraryPath = new File(Ip2RegionProperty.REFERENCE_PATH.substring(0,
                    Ip2RegionProperty.REFERENCE_PATH.lastIndexOf("/") + 1));
            if (!libraryPath.exists()) {
                if (libraryPath.mkdirs()) {
                    log.info("Ip2Region文件夹缺失，创建在{}", libraryPath.getPath());
                } else {
                    log.error("Ip2Region文件夹缺失，在{}创建失败！", libraryPath.getPath());
                    return;
                }
            } else {
                log.info("Ip2Region文件夹已存在，在{}", libraryPath.getPath());
            }
            /*数据文件*/
            File libraryDefaultFile = new File(Ip2RegionProperty.REFERENCE_PATH);
            if (!libraryDefaultFile.exists()) {
                ClassPathResource classPathResource = new ClassPathResource(Ip2RegionProperty.RESOURCE_PATH);
                InputStream inputStream = classPathResource.getInputStream();
                FileUtil.writeFromStream(inputStream, libraryDefaultFile);
                log.info("Ip2Region数据文件缺失，创建在{}", libraryDefaultFile.getPath());
            } else {
                log.info("Ip2Region数据文件已存在，在{}", libraryDefaultFile.getPath());
            }
            log.info("Ip2Region检查并创建文件结束。");
        } catch (Exception e) {
            log.error("复制文件时发生了错误！", e);
        }
    }

    private static void start() {
        log.info("Ip2Region开始加载...");
        log.info("Ip2Region测试：" + IpUtils.getIpInfo("202.108.22.5"));
        log.info("Ip2Region加载结束。");
    }

}
