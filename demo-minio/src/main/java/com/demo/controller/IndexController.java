package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.tool.MinioTemp;
import com.demo.tool.entity.minio.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
@AllArgsConstructor
@Slf4j
public class IndexController {

    private final MinioTemp minioTemp;

    /**
     * 所有储存桶
     * http://localhost:8080/bucketAll
     */
    @GetMapping("bucketAll")
    public Result<List<Bucket>> bucketAll() {
        return Result.o(minioTemp.bucketAll());
    }

    /**
     * 是否存在储存桶
     * http://localhost:8080/bucketExist?bucket=test
     */
    @GetMapping("bucketExist")
    public Result<Boolean> existBucket(String bucket) {
        return Result.o(minioTemp.bucketExist(bucket));
    }

    /**
     * 创建储存桶
     * http://localhost:8080/bucketCreate?bucket=test
     */
    @GetMapping("bucketCreate")
    public Result<Boolean> createBucket(String bucket) {
        return Result.o(minioTemp.bucketCreate(bucket));
    }

    /**
     * 删除储存桶
     * http://localhost:8080/bucketDelete?bucket=test
     */
    @GetMapping("bucketDelete")
    public Result<Boolean> bucketDelete(String bucket) {
        return Result.o(minioTemp.bucketDelete(bucket));
    }

    /**
     * 获取储存桶的标签
     * http://localhost:8080/bucketTagGet?bucket=test
     */
    @GetMapping("bucketTagGet")
    public Result<Map<String, String>> bucketTagGet(String bucket) {
        return Result.o(minioTemp.bucketTagGet(bucket));
    }

    /**
     * 设置储存桶的标签
     * http://localhost:8080/bucketTagSet?bucket=test
     */
    @GetMapping("bucketTagSet")
    public Result<Boolean> bucketTagSet(String bucket) {
        Map<String, String> tags = new HashMap<>(2);
        tags.put("a", "aa");
        tags.put("b", "bb");
        return Result.o(minioTemp.bucketTagSet(bucket, tags));
    }

    /**
     * 删除储存桶的全部标签
     * http://localhost:8080/bucketTagDelete?bucket=test
     */
    @GetMapping("bucketTagDelete")
    public Result<Boolean> bucketTagDelete(String bucket) {
        return Result.o(minioTemp.bucketTagDelete(bucket));
    }

    /**
     * 所有对象(根目录)
     * http://localhost:8080/objectAll?bucket=test
     */
    @GetMapping("objectAll")
    public Result<List<Item>> objectAll(String bucket) {
        return Result.o(minioTemp.objectAll(bucket));
    }

    /**
     * 所有对象
     * http://localhost:8080/objectAll2?bucket=test&path=folder
     */
    @GetMapping("objectAll2")
    public Result<List<Item>> objectAll(String bucket, String path) {
        return Result.o(minioTemp.objectAll(bucket, path));
    }

    /**
     * 对象状态
     * http://localhost:8080/objectStat?bucket=test&path=README.md
     */
    @GetMapping("objectStat")
    public Result<StatObjectResponse> objectStat(String bucket, String path) {
        return Result.o(minioTemp.objectStatus(bucket, path));
    }

    /**
     * 获取对象
     * http://localhost:8080/objectGet?bucket=test&path=README.md
     */
    @GetMapping("objectGet")
    public void objectGet(String bucket, String path, HttpServletResponse response) {
        GetObjectResponse getObjectResponse = minioTemp.objectGet(bucket, path);
        if (getObjectResponse != null) {
            log.info(getObjectResponse.toString());
            if (getObjectResponse.getFile() != null) {
                try {
                    MinioTemp.inputStream2HttpServletResponse(
                            getObjectResponse.getFile(),
                            response,
                            getObjectResponse.getResponse().getName(),
                            getObjectResponse.getResponse().getHeaders().get("Content-Type")
                    );
                } catch (Exception e) {
                    log.info("[InputStream转HttpServletResponse]异常！", e);
                }
            }
        }
    }

    /**
     * 获取对象并下载
     * http://localhost:8080/objectDownload2?bucket=test&path=README.md&name=1.md
     */
    @GetMapping("objectDownload2")
    public void objectDownload(String bucket, String path, HttpServletResponse response, String name) {
        minioTemp.objectDownload(bucket, path, response, name);
    }

    /**
     * 获取对象并下载
     * http://localhost:8080/objectDownload?bucket=test&path=README.md
     */
    @GetMapping("objectDownload")
    public void objectDownload(String bucket, String path, HttpServletResponse response) {
        minioTemp.objectDownload(bucket, path, response);
    }

    /**
     * 下载对象到本地
     * http://localhost:8080/objectDownloadLocal?bucket=test&path=README.md&fileName=/1.md
     */
    @GetMapping("objectDownloadLocal")
    public void objectDownloadLocal(String bucket, String path, String fileName) {
        minioTemp.objectDownloadLocal(bucket, path, fileName);
    }

    /**
     * 复制对象
     * http://localhost:8080/objectCopy?bucket=test&path=README.md&newBucket=test2&newPath=1.md
     */
    @GetMapping("objectCopy")
    public Result<ObjectWriteResponse> objectCopy(String bucket, String path, String newBucket, String newPath) {
        return Result.o(minioTemp.objectCopy(bucket, path, newBucket, newPath));
    }

    /**
     * 复制对象
     * http://localhost:8080/objectCopy2?bucket=test&path=README.md&newPath=1.md
     */
    @GetMapping("objectCopy2")
    public Result<ObjectWriteResponse> objectCopy(String bucket, String path, String newPath) {
        return Result.o(minioTemp.objectCopy(bucket, path, newPath));
    }

    /**
     * 合并分片对象
     * http://localhost:8080/objectCompose?bucket=test&paths=minio.exe&paths=mc.exe&newPath=1.dat
     */
    @GetMapping("objectCompose")
    public Result<ObjectWriteResponse> objectCompose(String bucket, String[] paths, String newPath) {
        return Result.o(minioTemp.objectCompose(bucket, Arrays.asList(paths), newPath));
    }

    /**
     * 删除对象
     * http://localhost:8080/objectDelete?bucket=test&path=a.txt
     */
    @GetMapping("objectDelete")
    public Result<Boolean> objectDelete(String bucket, String path) {
        return Result.o(minioTemp.objectDelete(bucket, path));
    }

    /**
     * 删除对象
     * http://localhost:8080/objectDelete2?bucket=test&paths=a.txt&paths=b.txt
     */
    @GetMapping("objectDelete2")
    public Result<List<DeleteError>> objectDelete(String bucket, String[] paths) {
        return Result.o(minioTemp.objectDelete(bucket, Arrays.asList(paths)));
    }

    /**
     * 上传对象
     * http://localhost:8080/objectUpload?bucket=test&path=folder
     */
    @GetMapping("objectUpload")
    public Result<ObjectWriteResponse> objectUpload(String bucket, String path, MultipartFile file) {
        return Result.o(minioTemp.objectUpload(bucket, path, file));
    }

    /**
     * 上传对象
     * http://localhost:8080/objectUpload2?bucket=test&path=folder
     */
    @GetMapping("objectUpload2")
    public Result<ObjectWriteResponse> objectUpload(String bucket, String path, MultipartFile[] files) {
        return Result.o(minioTemp.objectUpload(bucket, path, files));
    }

    /**
     * 创建文件夹
     * http://localhost:8080/folderCreate?bucket=test&path=ab
     */
    @GetMapping("folderCreate")
    public Result<ObjectWriteResponse> folderCreate(String bucket, String path) {
        return Result.o(minioTemp.folderCreate(bucket, path));
    }

    /**
     * 从本地上传对象
     * http://localhost:8080/objectUploadLocal?bucket=test&path=ab&localPath=E:\Pictures\头像壁纸\苹果ISO11壁纸.JPG
     */
    @GetMapping("objectUploadLocal")
    public Result<ObjectWriteResponse> objectUploadLocal(String bucket, String path, String localPath) {
        return Result.o(minioTemp.objectUploadLocal(bucket, path, localPath));
    }

    /**
     * 获取对象的标签
     * http://localhost:8080/objectTagGet?bucket=test&path=a.txt
     */
    @GetMapping("objectTagGet")
    public Result<Map<String, String>> objectTagGet(String bucket, String path) {
        return Result.o(minioTemp.objectTagGet(bucket, path));
    }

    /**
     * 设置对象的标签
     * http://localhost:8080/objectTagSet?bucket=test&path=a.txt
     */
    @GetMapping("objectTagSet")
    public Result<Boolean> objectTagSet(String bucket, String path) {
        Map<String, String> tags = new HashMap<>(2);
        tags.put("a", "aa");
        tags.put("b", "bb");
        return Result.o(minioTemp.objectTagSet(bucket, path, tags));
    }

    /**
     * 删除对象的全部标签
     * http://localhost:8080/objectTagDelete?bucket=test&path=a.txt
     */
    @GetMapping("objectTagDelete")
    public Result<Boolean> objectTagDelete(String bucket, String path) {
        return Result.o(minioTemp.objectTagDelete(bucket, path));
    }

    /**
     * 获取删除对象的URL
     * http://localhost:8080/urlDelete?bucket=test&path=a.txt&expiry=600
     */
    @GetMapping("urlDelete")
    public Result<String> urlDelete(String bucket, String path, int expiry) {
        return Result.o(minioTemp.urlDelete(bucket, path, expiry));
    }

    /**
     * 获取修改对象的URL
     * http://localhost:8080/urlUpdate?bucket=test&path=a.txt&expiry=600
     */
    @GetMapping("urlUpdate")
    public Result<String> urlUpdate(String bucket, String path, int expiry) {
        Map<String, String> queryParams = new HashMap<>(1);
        queryParams.put("response-content-type", "application/json");
        return Result.o(minioTemp.urlUpdate(bucket, path, expiry, queryParams));
    }

    /**
     * 获取下载对象的URL
     * http://localhost:8080/urlDownload?bucket=test&path=a.txt&expiry=600
     */
    @GetMapping("urlDownload")
    public Result<String> urlDownload(String bucket, String path, int expiry) {
        return Result.o(minioTemp.urlDownload(bucket, path, expiry));
    }

}
