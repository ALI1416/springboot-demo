package com.demo.tool.entity.minio;

import com.demo.tool.MinioTemp;
import io.minio.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h1>项</h1>
 *
 * <p>
 * createDate 2022/03/28 16:24:35
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Item {

    private static final Logger log = LoggerFactory.getLogger(Item.class);

    /**
     * ETag
     */
    private String etag;
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
        if (item.etag() != null) {
            this.etag = item.etag().substring(1, item.etag().length() - 1);
        }
        this.name = item.objectName();
        if (!item.isDir()) {
            this.lastModifiedDate = MinioTemp.zonedDateTime2Timestamp(item.lastModified());
        }
        if (item.owner() != null) {
            this.owner = new Owner(item.owner());
        }
        this.size = item.size();
        this.storageClass = item.storageClass();
        this.isLatest = item.isLatest();
        this.versionId = item.versionId();
        this.userMetadata = item.userMetadata();
        this.isDir = item.isDir();
    }

    /**
     * io.minio.messages.Item转Item
     *
     * @param itemList Iterable Result io.minio.messages.Item
     * @return List Item
     */
    public static List<Item> toList(Iterable<Result<io.minio.messages.Item>> itemList) {
        List<Item> list = new ArrayList<>();
        for (Result<io.minio.messages.Item> item : itemList) {
            try {
                list.add(new Item(item.get()));
            } catch (Exception e) {
                log.error("[io.minio.messages.Item转Item]异常！", e);
            }
        }
        return list;
    }

    public String getEtag() {
        return this.etag;
    }

    public String getName() {
        return this.name;
    }

    public Timestamp getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public long getSize() {
        return this.size;
    }

    public String getStorageClass() {
        return this.storageClass;
    }

    public boolean isLatest() {
        return this.isLatest;
    }

    public String getVersionId() {
        return this.versionId;
    }

    public Map<String, String> getUserMetadata() {
        return this.userMetadata;
    }

    public boolean isDir() {
        return this.isDir;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }

    public void setLatest(boolean isLatest) {
        this.isLatest = isLatest;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }

    public void setDir(boolean isDir) {
        this.isDir = isDir;
    }

    @Override
    public String toString() {
        return "Item{" +
                "etag='" + etag + '\'' +
                ", name='" + name + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", owner=" + owner +
                ", size=" + size +
                ", storageClass='" + storageClass + '\'' +
                ", isLatest=" + isLatest +
                ", versionId='" + versionId + '\'' +
                ", userMetadata=" + userMetadata +
                ", isDir=" + isDir +
                '}';
    }

}
