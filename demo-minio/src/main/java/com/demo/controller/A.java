package com.demo.controller;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;

/**
 * <h1>A</h1>
 *
 * <p>
 * createDate 2022/03/25 16:11:13
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class A {
    public static void main(String[] args) {
        try {
            MinioClient minioClient = MinioClient.builder().endpoint("http://127.0.0.1:9000/").credentials(
                    "minioadmin", "minioadmin").build();
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("asiatrip").build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("asiatrip").build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
