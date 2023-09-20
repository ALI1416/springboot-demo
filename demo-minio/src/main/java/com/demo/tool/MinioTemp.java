package com.demo.tool;

import com.demo.tool.entity.minio.GetObjectResponse;
import com.demo.tool.entity.minio.ObjectWriteResponse;
import com.demo.tool.entity.minio.StatObjectResponse;
import com.demo.tool.entity.minio.*;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.DeleteObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * <h1>Minio模板</h1>
 *
 * <p>
 * createDate 2022/03/28 11:13:22
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Component
public class MinioTemp {

    private static final Logger log = LoggerFactory.getLogger(MinioTemp.class);
    private final MinioClient minioClient;

    /**
     * 构造函数(自动注入)
     *
     * @param minioClient MinioClient
     */
    public MinioTemp(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    /* ==================== 桶基本操作 ==================== */

    /**
     * 获取所有储存桶
     *
     * @return 所有储存桶
     */
    public List<Bucket> bucketAll() {
        try {
            return Bucket.toList(minioClient.listBuckets());
        } catch (Exception e) {
            log.error("[获取所有储存桶]异常！", e);
            return new ArrayList<>(0);
        }
    }

    /**
     * 是否存在储存桶
     *
     * @param bucket 储存桶
     * @return 是否存在
     */
    public boolean bucketExist(String bucket) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        } catch (Exception e) {
            log.error("[是否存在储存桶]异常！", e);
            return false;
        }
    }

    /**
     * 创建储存桶
     *
     * @param bucket 储存桶
     * @return 是否成功
     */
    public boolean bucketCreate(String bucket) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            return true;
        } catch (Exception e) {
            log.error("[创建储存桶]异常！", e);
            return false;
        }
    }

    /**
     * 删除储存桶
     *
     * @param bucket 储存桶
     * @return 是否成功
     */
    public boolean bucketDelete(String bucket) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
            return true;
        } catch (Exception e) {
            log.error("[删除储存桶]异常！", e);
            return false;
        }
    }

    /* ==================== 桶标签操作 ==================== */

    /**
     * 获取储存桶的标签
     *
     * @param bucket 储存桶
     * @return Map
     */
    public Map<String, String> bucketTagGet(String bucket) {
        try {
            return minioClient.getBucketTags(GetBucketTagsArgs.builder().bucket(bucket).build()).get();
        } catch (Exception e) {
            log.error("[获取储存桶的标签]异常！", e);
            return new HashMap<>(0);
        }
    }

    /**
     * 设置储存桶的标签
     *
     * @param bucket 储存桶
     * @param tagMap Map
     * @return 是否成功
     */
    public boolean bucketTagSet(String bucket, Map<String, String> tagMap) {
        try {
            minioClient.setBucketTags(SetBucketTagsArgs.builder().bucket(bucket).tags(tagMap).build());
            return true;
        } catch (Exception e) {
            log.error("[设置储存桶的标签]异常！", e);
            return false;
        }
    }

    /**
     * 删除储存桶的全部标签
     *
     * @param bucket 储存桶
     * @return 是否成功
     */
    public boolean bucketTagDelete(String bucket) {
        try {
            minioClient.deleteBucketTags(DeleteBucketTagsArgs.builder().bucket(bucket).build());
            return true;
        } catch (Exception e) {
            log.error("[删除储存桶的全部标签]异常！", e);
            return false;
        }
    }

    /* ==================== 桶版本操作 ==================== */
    /* ==================== 桶策略操作 ==================== */
    /* ==================== 桶生命周期操作 ==================== */
    /* ==================== 桶通知操作 ==================== */
    /* ==================== 桶主从复制操作 ==================== */
    /* ==================== 桶加密操作 ==================== */
    /* ==================== 桶对象默认保留操作 ==================== */


    /* ==================== 对象操作 ==================== */

    /**
     * 获取根路径的所有对象
     *
     * @param bucket 储存桶
     * @return 所有对象
     */
    public List<Item> objectAll(String bucket) {
        try {
            return Item.toList(minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).build()));
        } catch (Exception e) {
            log.error("[获取根路径的所有对象]异常！", e);
            return new ArrayList<>(0);
        }
    }

    /**
     * 获取指定路径的所有对象
     *
     * @param bucket 储存桶
     * @param path   指定路径
     * @return 所有对象
     */
    public List<Item> objectAll(String bucket, String path) {
        try {
            return Item.toList(minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).prefix(path + "/").build()));
        } catch (Exception e) {
            log.error("[获取指定路径的所有对象]异常！", e);
            return new ArrayList<>(0);
        }
    }

    /**
     * 获取对象状态
     *
     * @param bucket 储存桶
     * @param path   路径
     * @return 对象状态
     */
    public StatObjectResponse objectStatus(String bucket, String path) {
        try {
            return new StatObjectResponse(minioClient.statObject(StatObjectArgs.builder().bucket(bucket).object(path).build()));
        } catch (Exception e) {
            log.error("[获取对象状态]异常！", e);
            return null;
        }
    }

    /**
     * 获取对象<br>
     * 请手动关闭getFile()
     *
     * @param bucket 储存桶
     * @param path   路径
     * @return GetObjectResponse
     */
    public GetObjectResponse objectGet(String bucket, String path) {
        try {
            return new GetObjectResponse(minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(path).build()));
        } catch (Exception e) {
            log.error("[获取对象]异常！", e);
            return null;
        }
    }

    /**
     * 下载对象
     *
     * @param bucket   储存桶
     * @param path     路径
     * @param response HttpServletResponse
     * @return 是否成功
     */
    public boolean objectDownload(String bucket, String path, HttpServletResponse response) {
        return objectDownload(bucket, path, response, null);
    }

    /**
     * 下载对象并指定文件名
     *
     * @param bucket   储存桶
     * @param path     路径
     * @param response HttpServletResponse
     * @param name     文件名(null为默认文件名)
     * @return 是否成功
     */
    public boolean objectDownload(String bucket, String path, HttpServletResponse response, String name) {
        GetObjectResponse getObjectResponse = objectGet(bucket, path);
        if (getObjectResponse == null || getObjectResponse.getFile() == null) {
            return false;
        }
        try {
            MinioTemp.inputStream2HttpServletResponse(
                    getObjectResponse.getFile(),
                    response,
                    name == null ? getObjectResponse.getResponse().getName() : name,
                    getObjectResponse.getResponse().getHeaders().get("Content-Type")
            );
            return true;
        } catch (Exception e) {
            log.info("[下载对象并指定文件名]异常！", e);
            return false;
        }
    }

    /**
     * 下载对象到本地
     *
     * @param bucket    储存桶
     * @param path      路径
     * @param localPath 本地路径
     * @return 是否成功
     */
    public boolean objectDownloadLocal(String bucket, String path, String localPath) {
        try {
            minioClient.downloadObject(DownloadObjectArgs.builder().bucket(bucket).object(path).filename(localPath).build());
            return true;
        } catch (Exception e) {
            log.error("[下载对象到本地]异常！", e);
            return false;
        }
    }

    /**
     * 复制对象
     *
     * @param bucket    储存桶
     * @param path      路径
     * @param newBucket 新储存桶
     * @param newPath   新路径
     * @return ObjectWriteResponse
     */
    public ObjectWriteResponse objectCopy(String bucket, String path, String newBucket, String newPath) {
        try {
            return new ObjectWriteResponse(minioClient.copyObject(CopyObjectArgs.builder() //
                    .bucket(newBucket).object(newPath) //
                    .source(CopySource.builder().bucket(bucket).object(path).build()).build()));
        } catch (Exception e) {
            log.error("[复制对象]异常！", e);
            return null;
        }
    }

    /**
     * 复制对象
     *
     * @param bucket  储存桶
     * @param path    路径
     * @param newPath 新路径
     * @return ObjectWriteResponse
     */
    public ObjectWriteResponse objectCopy(String bucket, String path, String newPath) {
        return objectCopy(bucket, path, bucket, newPath);
    }

    /**
     * 合并分片对象
     *
     * @param bucket   储存桶
     * @param pathList 路径列表
     * @param newPath  新路径
     * @return ObjectWriteResponse
     */
    public ObjectWriteResponse objectCompose(String bucket, List<String> pathList, String newPath) {
        List<ComposeSource> list = new ArrayList<>();
        for (String path : pathList) {
            list.add(ComposeSource.builder().bucket(bucket).object(path).build());
        }
        try {
            return new ObjectWriteResponse(minioClient.composeObject(ComposeObjectArgs.builder().bucket(bucket).object(newPath).sources(list).build()));
        } catch (Exception e) {
            log.error("[合并分片对象]异常！", e);
            return null;
        }
    }

    /**
     * 删除对象
     *
     * @param bucket 储存桶
     * @param path   路径
     * @return 是否成功
     */
    public boolean objectDelete(String bucket, String path) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(path).build());
            return true;
        } catch (Exception e) {
            log.error("[删除对象]异常！", e);
            return false;
        }
    }

    /**
     * 删除对象列表
     *
     * @param bucket   储存桶
     * @param pathList 路径列表
     * @return 删除错误结果
     */
    public List<DeleteError> objectDelete(String bucket, List<String> pathList) {
        List<DeleteObject> list = new LinkedList<>();
        for (String path : pathList) {
            list.add(new DeleteObject(path));
        }
        try {
            return DeleteError.toList(minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucket).objects(list).build()));
        } catch (Exception e) {
            log.error("[删除对象列表]异常！", e);
            return new ArrayList<>(0);
        }
    }

    /**
     * 上传对象
     *
     * @param bucket        储存桶
     * @param path          路径
     * @param multipartFile MultipartFile
     * @return 对象写入结果
     */
    public ObjectWriteResponse objectUpload(String bucket, String path, MultipartFile multipartFile) {
        try {
            return new ObjectWriteResponse(minioClient.putObject(PutObjectArgs.builder().bucket(bucket) //
                    .object(path + "/" + multipartFile.getOriginalFilename()) //
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1) //
                    .contentType(multipartFile.getContentType()).build()));
        } catch (Exception e) {
            log.error("[上传对象]异常！", e);
            return null;
        }
    }

    /**
     * 上传对象列表
     *
     * @param bucket             储存桶
     * @param path               路径
     * @param multipartFileArray MultipartFile数组
     * @return 对象写入结果
     */
    public ObjectWriteResponse objectUpload(String bucket, String path, MultipartFile[] multipartFileArray) {
        List<SnowballObject> list = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : multipartFileArray) {
                list.add(new SnowballObject( //
                        path + "/" + multipartFile.getOriginalFilename(), //
                        multipartFile.getInputStream(),  //
                        multipartFile.getSize(),  //
                        null //
                ));
            }
            return new ObjectWriteResponse(minioClient.uploadSnowballObjects(UploadSnowballObjectsArgs.builder().bucket(bucket).objects(list).build()));
        } catch (Exception e) {
            log.error("[上传对象列表]异常！", e);
            return null;
        }
    }

    /**
     * 创建文件夹
     *
     * @param bucket 储存桶
     * @param path   路径
     * @return 对象写入结果
     */
    public ObjectWriteResponse folderCreate(String bucket, String path) {
        try {
            return new ObjectWriteResponse(minioClient.putObject(PutObjectArgs.builder().bucket(bucket) //
                    .object(path + "/") //
                    .stream(new ByteArrayInputStream(new byte[]{}), 0, -1).build()));
        } catch (Exception e) {
            log.error("[创建文件夹]异常！", e);
            return null;
        }
    }

    /**
     * 从本地上传对象
     *
     * @param bucket    储存桶
     * @param path      路径
     * @param localPath 本地路径
     * @return 对象写入结果
     */
    public ObjectWriteResponse objectUploadLocal(String bucket, String path, String localPath) {
        try {
            return new ObjectWriteResponse(minioClient.uploadObject(UploadObjectArgs.builder().bucket(bucket) //
                    .object(path + "/" + Paths.get(localPath).getFileName()) //
                    .filename(localPath).build()));
        } catch (Exception e) {
            log.error("[从本地上传对象]异常！", e);
            return null;
        }
    }

    /* ==================== 对象标签操作 ==================== */

    /**
     * 获取对象的标签
     *
     * @param bucket 储存桶
     * @param path   路径
     * @return 对象的标签
     */
    public Map<String, String> objectTagGet(String bucket, String path) {
        try {
            return minioClient.getObjectTags(GetObjectTagsArgs.builder().bucket(bucket).object(path).build()).get();
        } catch (Exception e) {
            log.error("[获取对象的标签]异常！", e);
            return new HashMap<>(0);
        }
    }

    /**
     * 设置对象的标签
     *
     * @param bucket 储存桶
     * @param path   路径
     * @return 是否成功
     */
    public boolean objectTagSet(String bucket, String path, Map<String, String> tags) {
        try {
            minioClient.setObjectTags(SetObjectTagsArgs.builder().bucket(bucket).object(path).tags(tags).build());
            return true;
        } catch (Exception e) {
            log.error("[设置对象的标签]异常！", e);
            return false;
        }
    }

    /**
     * 删除对象的全部标签
     *
     * @param bucket 储存桶
     * @param path   路径
     * @return 是否成功
     */
    public boolean objectTagDelete(String bucket, String path) {
        try {
            minioClient.deleteObjectTags(DeleteObjectTagsArgs.builder().bucket(bucket).object(path).build());
            return true;
        } catch (Exception e) {
            log.error("[删除对象的全部标签]异常！", e);
            return false;
        }
    }

    /* ==================== URL ==================== */

    /**
     * 获取删除对象的URL
     *
     * @param bucket 储存桶
     * @param path   路径
     * @param expire 失效时间(秒)
     * @return URL(需要使用DELETE方法访问)
     */
    public String urlDelete(String bucket, String path, int expire) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.DELETE).bucket(bucket).object(path).expiry(expire).build());
        } catch (Exception e) {
            log.error("[获取删除对象的URL]异常！", e);
            return "";
        }
    }

    /**
     * 获取修改对象的URL
     *
     * @param bucket        储存桶
     * @param path          路径
     * @param expire        失效时间(秒)
     * @param queryParamMap 修改内容
     * @return URL(需要使用PUT方法访问)
     */
    public String urlUpdate(String bucket, String path, int expire, Map<String, String> queryParamMap) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.PUT).bucket(bucket).object(path).expiry(expire).extraQueryParams(queryParamMap).build());
        } catch (Exception e) {
            log.error("[获取修改对象的URL]异常！", e);
            return "";
        }
    }

    /**
     * 获取下载对象的URL
     *
     * @param bucket 储存桶
     * @param path   路径
     * @param expire 失效时间(秒)
     * @return URL(需要使用GET方法访问)
     */
    public String urlDownload(String bucket, String path, int expire) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucket).object(path).expiry(expire).build());
        } catch (Exception e) {
            log.error("[获取下载对象的URL]异常！", e);
            return "";
        }
    }

    /* ==================== 对象保留操作 ==================== */
    /* ==================== 对象合法保留操作 ==================== */
    /* ==================== SQL表达式选取对象 ==================== */
    /* ==================== 获取对象的PostPolicy的表单数据，以使用POST方法上传其数据 ==================== */

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

    /**
     * InputStream转HttpServletResponse
     *
     * @param input       InputStream
     * @param response    HttpServletResponse
     * @param name        文件名
     * @param contentType 内容类型
     */
    public static void inputStream2HttpServletResponse(InputStream input, HttpServletResponse response, String name, String contentType) throws IOException {
        name = URLEncoder.encode(name, "UTF-8").replace("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + name);
        response.setContentType(contentType);
        OutputStream output = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        input.close();
    }

}
