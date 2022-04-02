package com.demo.util.pojo.minio;

import com.demo.base.ToStringBase;
import com.demo.util.MinioUtils;
import io.minio.Result;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    private Long size;
    /**
     * 储存种类
     */
    private String storageClass;
    /**
     * 是最新的
     */
    private Boolean isLatest;
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
    private Boolean isDir;

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
        if (!this.isDir) {
            this.lastModifiedDate = MinioUtils.zonedDateTime2Timestamp(item.lastModified());
            this.owner = new Owner(item.owner());
        }
    }

    /**
     * Item转Bean
     *
     * @param items Iterable&lt;Result&lt;Item>>
     * @return List&lt;Item>
     */
    public static List<Item> getList(Iterable<Result<io.minio.messages.Item>> items) {
        return StreamSupport.stream(items.spliterator(), true).map(item -> {
            try {
                return new Item(item.get());
            } catch (Exception e) {
                log.error("Result<Item>转Item失败", e);
                return new Item();
            }
        }).collect(Collectors.toList());
    }

}
