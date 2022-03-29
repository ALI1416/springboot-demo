package com.demo.util;

import com.demo.util.pojo.minio.Bucket;
import com.demo.util.pojo.minio.Item;
import com.demo.util.pojo.minio.StatObjectResponse;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>Minio工具</h1>
 *
 * <p>
 * createDate 2022/03/28 11:13:22
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
@Component
public class MinioUtils {

    private static MinioClient minioClient;

    @Autowired
    public MinioUtils(MinioClient minioClient) {
        MinioUtils.minioClient = minioClient;
    }

    /* ==================== 桶基本操作 ==================== */

    /**
     * 所有储存桶
     *
     * @return 所有储存桶
     */
    public static List<Bucket> bucketAll() {
        try {
            return Bucket.getList(minioClient.listBuckets());
        } catch (Exception e) {
            log.info("所有储存桶", e);
            return new ArrayList<>();
        }
    }

    /**
     * 是否存在储存桶
     *
     * @param bucket 储存桶
     * @return 是否存在
     */
    public static boolean bucketExist(String bucket) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        } catch (Exception e) {
            log.info("是否存在储存桶", e);
            return false;
        }
    }

    /**
     * 创建储存桶
     *
     * @param bucket 储存桶
     * @return 是否成功
     */
    public static boolean bucketCreate(String bucket) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            return true;
        } catch (Exception e) {
            log.info("创建储存桶", e);
            return false;
        }
    }

    /**
     * 删除储存桶
     *
     * @param bucket 储存桶
     * @return 是否成功
     */
    public static boolean bucketDelete(String bucket) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
            return true;
        } catch (Exception e) {
            log.info("删除储存桶", e);
            return false;
        }
    }

    /* ==================== 桶版本操作 ==================== */
    /* ==================== 桶策略操作 ==================== */
    /* ==================== 桶生命周期操作 ==================== */
    /* ==================== 桶通知操作 ==================== */
    /* ==================== 桶主从复制操作 ==================== */
    /* ==================== 桶加密操作 ==================== */

    /* ==================== 桶标签操作 ==================== */

    /**
     * 获取储存桶的标签
     *
     * @param bucket 储存桶
     * @return 储存桶的标签
     */
    public static Map<String, String> bucketTagGet(String bucket) {
        try {
            return minioClient.getBucketTags(GetBucketTagsArgs.builder().bucket(bucket).build()).get();
        } catch (Exception e) {
            log.info("获取储存桶的标签", e);
            return new HashMap<>(0);
        }
    }

    /**
     * 设置储存桶的标签
     *
     * @param bucket 储存桶
     * @return 是否成功
     */
    public static boolean bucketTagSet(String bucket, Map<String, String> tags) {
        try {
            minioClient.setBucketTags(SetBucketTagsArgs.builder().bucket(bucket).tags(tags).build());
            return true;
        } catch (Exception e) {
            log.info("设置储存桶的标签", e);
            return false;
        }
    }

    /**
     * 删除储存桶的全部标签
     *
     * @param bucket 储存桶
     * @return 是否成功
     */
    public static boolean bucketTagDelete(String bucket) {
        try {
            minioClient.deleteBucketTags(DeleteBucketTagsArgs.builder().bucket(bucket).build());
            return true;
        } catch (Exception e) {
            log.info("删除储存桶的标签", e);
            return false;
        }
    }

    /* ==================== 对象操作 ==================== */

    /**
     * 所有对象(根目录)
     *
     * @param bucket 储存桶
     * @return 所有对象
     */
    public static List<Item> objectAll(String bucket) {
        try {
            return Item.getList(minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).build()));
        } catch (Exception e) {
            log.info("所有对象(根目录)", e);
            return new ArrayList<>();
        }
    }

    /**
     * 所有对象(指定路径)
     *
     * @param bucket 储存桶
     * @param path   路径
     * @return 所有对象
     */
    public static List<Item> objectAll(String bucket, String path) {
        try {
            return Item.getList(minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).prefix(path).build()));
        } catch (Exception e) {
            log.info("所有对象(指定路径)", e);
            return new ArrayList<>();
        }
    }

    /**
     * 对象的元数据
     *
     * @param bucket 储存桶
     * @param path   路径
     * @return 元数据
     */
    public static StatObjectResponse objectMetadata(String bucket, String path) {
        try {
            return new StatObjectResponse(minioClient.statObject(StatObjectArgs.builder().bucket(bucket).object(path).build()));
        } catch (Exception e) {
            log.info("对象的元数据", e);
            return new StatObjectResponse();
        }
    }

    /* ==================== 工具 ==================== */

    /**
     * ZonedDateTime转Timestamp
     *
     * @param zonedDateTime ZonedDateTime
     * @return Timestamp
     */
    public static Timestamp zonedDateTime2Timestamp(ZonedDateTime zonedDateTime) {
        return new Timestamp(zonedDateTime.toEpochSecond() * 1000 // 秒
                + zonedDateTime.getNano() / 1000000); // 毫秒
    }

}
