package com.demo.tool.entity.minio;

import com.demo.base.ToStringBase;
import com.demo.tool.MinioTemp;
import io.minio.Result;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h1>对象</h1>
 *
 * <p>
 * createDate 2022/03/28 16:24:35
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@Slf4j
public class Item extends ToStringBase {

    /**
     * ETag
     */
    private String eTag;
    /**
     * 名称
     */
    private String name;
    /**
     * 上次修改时间
     */
    private Timestamp lastModifiedDate;
    /**
     * 所有者
     */
    private Owner owner;
    /**
     * 文件大小(字节)
     */
    private long size;
    /**
     * 储存种类
     */
    private String storageClass;
    /**
     * 是最新的
     */
    private boolean isLatest;
    /**
     * 版本Id
     */
    private String versionId;
    /**
     * 用户元信息
     */
    private Map<String, String> userMetadata;
    /**
     * 是目录
     */
    private boolean isDir;

    public Item() {
    }

    public Item(io.minio.messages.Item item) {
        this.eTag = item.etag();
        this.name = item.objectName();
        this.size = item.size();
        this.storageClass = item.storageClass();
        this.isLatest = item.isLatest();
        this.versionId = item.versionId();
        this.userMetadata = item.userMetadata();
        this.isDir = item.isDir();
        if (!item.isDir()) {
            this.lastModifiedDate = MinioTemp.zonedDateTime2Timestamp(item.lastModified());
        }
        if (item.owner() != null) {
            this.owner = new Owner(item.owner());
        }
    }

    /**
     * io.minio.messages.Item转Item
     *
     * @param itemList Iterable Result io.minio.messages.Item
     * @return List Item
     */
    public static List<Item> toList(Iterable<Result<io.minio.messages.Item>> itemList) {
        List<Item> list = new ArrayList<>();
        for (Result<io.minio.messages.Item> result : itemList) {
            try {
                list.add(new Item(result.get()));
            } catch (Exception e) {
                log.info("io.minio.messages.Item转Item", e);
                list.add(new Item());
            }
        }
        return list;
    }

}
