package com.demo.tool.entity.minio;

import com.demo.tool.MinioTemp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>储存桶</h1>
 *
 * <p>
 * createDate 2022/03/28 15:39:35
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Bucket {

    /**
     * 名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Timestamp createDate;

    public Bucket() {
    }

    public Bucket(io.minio.messages.Bucket bucket) {
        this.name = bucket.name();
        this.createDate = MinioTemp.zonedDateTime2Timestamp(bucket.creationDate());
    }

    /**
     * io.minio.messages.Bucket转Bucket
     *
     * @param bucketList List io.minio.messages.Bucket
     * @return List Bucket
     */
    public static List<Bucket> toList(List<io.minio.messages.Bucket> bucketList) {
        List<Bucket> list = new ArrayList<>();
        for (io.minio.messages.Bucket bucket : bucketList) {
            list.add(new Bucket(bucket));
        }
        return list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Bucket{" +
                "name='" + name + '\'' +
                ", createDate=" + createDate +
                '}';
    }

}
