package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.util.MinioUtils;
import com.demo.util.pojo.minio.GetObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>首页</h1>
 *
 * <p>
 * createDate 2021/09/09 10:35:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@Slf4j
public class IndexController {

    /**
     * 所有储存桶
     * http://localhost:8080/bucketAll
     */
    @GetMapping("bucketAll")
    public Result bucketAll() {
        return Result.o(MinioUtils.bucketAll());
    }

    /**
     * 是否存在储存桶
     * http://localhost:8080/bucketExist?bucket=test
     */
    @GetMapping("bucketExist")
    public Result existBucket(String bucket) {
        return Result.o(MinioUtils.bucketExist(bucket));
    }

    /**
     * 创建储存桶
     * http://localhost:8080/bucketCreate?bucket=test
     */
    @GetMapping("bucketCreate")
    public Result createBucket(String bucket) {
        return Result.o(MinioUtils.bucketCreate(bucket));
    }

    /**
     * 删除储存桶
     * http://localhost:8080/bucketDelete?bucket=test
     */
    @GetMapping("bucketDelete")
    public Result bucketDelete(String bucket) {
        return Result.o(MinioUtils.bucketDelete(bucket));
    }

    /**
     * 获取储存桶的标签
     * http://localhost:8080/bucketTagGet?bucket=test
     */
    @GetMapping("bucketTagGet")
    public Result bucketTagGet(String bucket) {
        return Result.o(MinioUtils.bucketTagGet(bucket));
    }

    /**
     * 设置储存桶的标签
     * http://localhost:8080/bucketTagSet?bucket=test
     */
    @GetMapping("bucketTagSet")
    public Result bucketTagSet(String bucket) {
        Map<String, String> tags = new HashMap<>(2);
        tags.put("a", "aa");
        tags.put("b", "bb");
        return Result.o(MinioUtils.bucketTagSet(bucket, tags));
    }

    /**
     * 删除储存桶的全部标签
     * http://localhost:8080/bucketTagDelete?bucket=test
     */
    @GetMapping("bucketTagDelete")
    public Result bucketTagDelete(String bucket) {
        return Result.o(MinioUtils.bucketTagDelete(bucket));
    }

    /**
     * 所有对象(根目录)
     * http://localhost:8080/objectAll?bucket=asiatrip
     */
    @GetMapping("objectAll")
    public Result objectAll(String bucket) {
        return Result.o(MinioUtils.objectAll(bucket));
    }

    /**
     * 所有对象
     * http://localhost:8080/objectAll2?bucket=asiatrip&path=folder
     */
    @GetMapping("objectAll2")
    public Result objectAll(String bucket, String path) {
        return Result.o(MinioUtils.objectAll(bucket, path));
    }

    /**
     * 对象状态
     * http://localhost:8080/objectStat?bucket=asiatrip&path=putty.exe
     */
    @GetMapping("objectStat")
    public Result objectStat(String bucket, String path) {
        return Result.o(MinioUtils.objectStat(bucket, path));
    }

    /**
     * 获取对象
     * http://localhost:8080/objectGet?bucket=asiatrip&path=putty.exe
     */
    @GetMapping("objectGet")
    public void objectGet(String bucket, String path, HttpServletResponse response) {
        GetObjectResponse getObjectResponse = MinioUtils.objectGet(bucket, path);
        log.info(getObjectResponse.getResponse().toString());
        try {
            MinioUtils.inputStream2HttpServletResponse(getObjectResponse.getFile(), response,
                    getObjectResponse.getResponse().getHeaders().get("Content-Type"),
                    getObjectResponse.getResponse().getName());
        } catch (Exception e) {
            log.info("InputStream转HttpServletResponse失败", e);
        }
    }

    /**
     * 获取对象并下载
     * http://localhost:8080/objectDownload2?bucket=asiatrip&path=putty.exe&fileName=a.exe
     */
    @GetMapping("objectDownload2")
    public void objectDownload(String bucket, String path, HttpServletResponse response, String fileName) {
        MinioUtils.objectDownload(bucket, path, response, fileName);
    }

    /**
     * 获取对象并下载
     * http://localhost:8080/objectDownload?bucket=asiatrip&path=putty.exe
     */
    @GetMapping("objectDownload")
    public void objectDownload(String bucket, String path, HttpServletResponse response) {
        MinioUtils.objectDownload(bucket, path, response);
    }

    /**
     * 下载对象到本地
     * http://localhost:8080/objectDownloadLocal?bucket=asiatrip&path=putty.exe&fileName=/a.exe
     */
    @GetMapping("objectDownloadLocal")
    public void objectDownloadLocal(String bucket, String path, String fileName) {
        MinioUtils.objectDownloadLocal(bucket, path, fileName);
    }

    /**
     * 复制对象
     * http://localhost:8080/objectCopy?bucket=asiatrip&path=putty.exe&newBucket=test&newPath=putty.exe
     */
    @GetMapping("objectCopy")
    public Result objectCopy(String bucket, String path, String newBucket, String newPath) {
        return Result.o(MinioUtils.objectCopy(bucket, path, newBucket, newPath));
    }

    /**
     * 复制对象
     * http://localhost:8080/objectCopy2?bucket=asiatrip&path=putty.exe&newPath=a.exe
     */
    @GetMapping("objectCopy2")
    public Result objectCopy(String bucket, String path, String newPath) {
        return Result.o(MinioUtils.objectCopy(bucket, path, newPath));
    }

    /**
     * 合并分片对象
     * http://localhost:8080/objectCompose?bucket=asiatrip&paths=a.txt&paths=b.txt&paths=c.txt&newPath=0.txt
     */
    @GetMapping("objectCompose")
    public Result objectCompose(String bucket, String[] paths, String newPath) {
        return Result.o(MinioUtils.objectCompose(bucket, Arrays.asList(paths), newPath));
    }

    /**
     * 删除对象
     * http://localhost:8080/objectDelete?bucket=asiatrip&path=a.txt
     */
    @GetMapping("objectDelete")
    public Result objectDelete(String bucket, String path) {
        return Result.o(MinioUtils.objectDelete(bucket, path));
    }

    /**
     * 删除对象
     * http://localhost:8080/objectDelete2?bucket=asiatrip&paths=a.txt&paths=b.txt&paths=
     */
    @GetMapping("objectDelete2")
    public Result objectDelete(String bucket, String[] paths) {
        return Result.o(MinioUtils.objectDelete(bucket, Arrays.asList(paths)));
    }

    /**
     * 上传对象
     * http://localhost:8080/objectUpload?bucket=asiatrip&path=aa/
     */
    @GetMapping("objectUpload")
    public Result objectUpload(String bucket, String path, MultipartFile file) {
        return Result.o(MinioUtils.objectUpload(bucket, path, file));
    }

    /**
     * 上传对象
     * http://localhost:8080/objectUpload2?bucket=asiatrip&path=aa/
     */
    @GetMapping("objectUpload2")
    public Result objectUpload(String bucket, String path, MultipartFile[] files) {
        return Result.o(MinioUtils.objectUpload(bucket, path, files));
    }

    /**
     * 创建文件夹
     * http://localhost:8080/folderCreate?bucket=asiatrip&path=ab/
     */
    @GetMapping("folderCreate")
    public Result folderCreate(String bucket, String path) {
        return Result.o(MinioUtils.folderCreate(bucket, path));
    }

    /**
     * 从本地上传对象
     * http://localhost:8080/objectUploadLocal?bucket=asiatrip&path=ab/&localPath=D:\Pictures\头像壁纸\苹果ISO11壁纸.JPG
     */
    @GetMapping("objectUploadLocal")
    public Result objectUploadLocal(String bucket, String path, String localPath) {
        return Result.o(MinioUtils.objectUploadLocal(bucket, path, localPath));
    }

    /**
     * 获取对象的标签
     * http://localhost:8080/objectTagGet?bucket=asiatrip&path=a.txt
     */
    @GetMapping("objectTagGet")
    public Result objectTagGet(String bucket, String path) {
        return Result.o(MinioUtils.objectTagGet(bucket, path));
    }

    /**
     * 设置对象的标签
     * http://localhost:8080/objectTagSet?bucket=asiatrip&path=a.txt
     */
    @GetMapping("objectTagSet")
    public Result objectTagSet(String bucket, String path) {
        Map<String, String> tags = new HashMap<>(2);
        tags.put("a", "aa");
        tags.put("b", "bb");
        return Result.o(MinioUtils.objectTagSet(bucket, path, tags));
    }

    /**
     * 删除对象的全部标签
     * http://localhost:8080/objectTagDelete?bucket=asiatrip&path=a.txt
     */
    @GetMapping("objectTagDelete")
    public Result objectTagDelete(String bucket, String path) {
        return Result.o(MinioUtils.objectTagDelete(bucket, path));
    }

    /**
     * 通过访问URL删除对象
     * http://localhost:8080/urlDelete?bucket=asiatrip&path=a.txt&expiry=600
     */
    @GetMapping("urlDelete")
    public Result urlDelete(String bucket, String path, int expiry) {
        return Result.o(MinioUtils.urlDelete(bucket, path, expiry));
    }

    /**
     * 通过访问URL修改对象
     * http://localhost:8080/urlUpdate?bucket=asiatrip&path=a.txt&expiry=600
     */
    @GetMapping("urlUpdate")
    public Result urlUpdate(String bucket, String path, int expiry) {
        Map<String, String> queryParams = new HashMap<>(1);
        queryParams.put("response-content-type", "application/json");
        return Result.o(MinioUtils.urlUpdate(bucket, path, expiry, queryParams));
    }

    /**
     * 通过访问URL下载对象
     * http://localhost:8080/urlDownload?bucket=asiatrip&path=a.txt&expiry=600
     */
    @GetMapping("urlDownload")
    public Result urlDownload(String bucket, String path, int expiry) {
        return Result.o(MinioUtils.urlDownload(bucket, path, expiry));
    }


}
