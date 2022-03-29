package com.demo.util.pojo.minio;

import com.demo.base.ToStringBase;
import com.demo.util.MinioUtils;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

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
@Getter
@Setter
public class Bucket extends ToStringBase {

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
        this.createDate = MinioUtils.zonedDateTime2Timestamp(bucket.creationDate());
    }

    /**
     * Bucket转Bean
     *
     * @param buckets List&lt;io.minio.messages.Bucket>
     * @return List&lt;Bucket>
     */
    public static List<Bucket> getList(List<io.minio.messages.Bucket> buckets) {
        return buckets.stream().map(Bucket::new).collect(Collectors.toList());
    }

}
