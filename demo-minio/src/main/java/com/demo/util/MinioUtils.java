package com.demo.util;

import com.demo.util.pojo.minio.GetObjectResponse;
import com.demo.util.pojo.minio.ObjectWriteResponse;
import com.demo.util.pojo.minio.StatObjectResponse;
import com.demo.util.pojo.minio.*;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.*;

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
            log.error("所有储存桶", e);
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
            log.error("是否存在储存桶", e);
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
            log.error("创建储存桶", e);
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
            log.error("删除储存桶", e);
            return false;
        }
    }


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
            log.error("获取储存桶的标签", e);
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
            log.error("设置储存桶的标签", e);
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
            log.error("删除储存桶的全部标签", e);
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
     * 所有对象(根目录)
     *
     * @param bucket 储存桶
     * @return 所有对象
     */
    public static List<Item> objectAll(String bucket) {
        try {
            return Item.getList(minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).build()));
        } catch (Exception e) {
            log.error("所有对象(根目录)", e);
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
            log.error("所有对象(指定路径)", e);
            return new ArrayList<>();
        }
    }

    /**
     * 对象状态
     *
     * @param bucket 储存桶
     * @param path   路径
     * @return GetObjectResponse
     */
    public static StatObjectResponse objectStat(String bucket, String path) {
        try {
            return new StatObjectResponse(minioClient.statObject(StatObjectArgs.builder().bucket(bucket).object(path).build()));
        } catch (Exception e) {
            log.error("对象状态", e);
            return new StatObjectResponse();
        }
    }

    /**
     * 获取对象<br>
     * GetObjectResponse.getFile()请手动关闭
     *
     * @param bucket 储存桶
     * @param path   路径
     * @return GetObjectResponse
     */
    public static GetObjectResponse objectGet(String bucket, String path) {
        try {
            return new GetObjectResponse(minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(path).build()));
        } catch (Exception e) {
            log.error("获取对象", e);
            return new GetObjectResponse();
        }
    }

    /**
     * 下载对象
     *
     * @param bucket   储存桶
     * @param path     路径
     * @param response HttpServletResponse
     * @param fileName 文件名(null为默认文件名)
     * @return 是否成功
     */
    public static boolean objectDownload(String bucket, String path, HttpServletResponse response, String fileName) {
        GetObjectResponse getObjectResponse = objectGet(bucket, path);
        if (getObjectResponse.getFile() == null) {
            return false;
        }
        try {
            MinioUtils.inputStream2HttpServletResponse( //
                    getObjectResponse.getFile(), //
                    response, //
                    getObjectResponse.getResponse().getHeaders().get("Content-Type"), //
                    fileName == null ? getObjectResponse.getResponse().getName() : fileName //
            );
            return true;
        } catch (Exception e) {
            log.info("InputStream转HttpServletResponse失败", e);
            return false;
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
    public static boolean objectDownload(String bucket, String path, HttpServletResponse response) {
        return objectDownload(bucket, path, response, null);
    }

    /**
     * 下载对象到本地
     *
     * @param bucket    储存桶
     * @param path      路径
     * @param localPath 本地路径
     * @return 是否成功
     */
    public static boolean objectDownloadLocal(String bucket, String path, String localPath) {
        try {
            minioClient.downloadObject(DownloadObjectArgs.builder().bucket(bucket).object(path).filename(localPath).build());
            return true;
        } catch (Exception e) {
            log.error("下载对象到本地", e);
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
    public static ObjectWriteResponse objectCopy(String bucket, String path, String newBucket, String newPath) {
        try {
            return new ObjectWriteResponse(minioClient.copyObject( //
                    CopyObjectArgs.builder().bucket(newBucket).object(newPath) //
                            .source(CopySource.builder().bucket(bucket).object(path).build()) //
                            .build()));
        } catch (Exception e) {
            log.error("复制对象", e);
            return new ObjectWriteResponse();
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
    public static ObjectWriteResponse objectCopy(String bucket, String path, String newPath) {
        return objectCopy(bucket, path, bucket, newPath);
    }

    /**
     * 合并分片对象
     *
     * @param bucket  储存桶
     * @param paths   路径列表
     * @param newPath 新路径
     * @return ObjectWriteResponse
     */
    public static ObjectWriteResponse objectCompose(String bucket, List<String> paths, String newPath) {
        List<ComposeSource> sources = new ArrayList<>();
        for (String path : paths) {
            sources.add(ComposeSource.builder().bucket(bucket).object(path).build());
        }
        try {
            return new ObjectWriteResponse(minioClient.composeObject(ComposeObjectArgs.builder().bucket(bucket).object(newPath).sources(sources).build()));
        } catch (Exception e) {
            log.error("合并对象", e);
            return new ObjectWriteResponse();
        }
    }

    /**
     * 删除对象
     *
     * @param bucket 储存桶
     * @param path   路径
     * @return 是否成功
     */
    public static boolean objectDelete(String bucket, String path) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(path).build());
            return true;
        } catch (Exception e) {
            log.error("删除对象", e);
            return false;
        }
    }

    /**
     * 删除对象
     *
     * @param bucket 储存桶
     * @param paths  路径列表
     * @return 错误原因
     */
    public static List<DeleteError> objectDelete(String bucket, List<String> paths) {
        List<DeleteObject> objects = new LinkedList<>();
        for (String path : paths) {
            objects.add(new DeleteObject(path));
        }
        try {
            return DeleteError.getList(minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucket).objects(objects).build()));
        } catch (Exception e) {
            log.error("删除对象", e);
            return new ArrayList<>();
        }
    }

    /**
     * 上传对象
     *
     * @param bucket        储存桶
     * @param path          路径(以/结尾)
     * @param multipartFile MultipartFile
     * @return ObjectWriteResponse
     */
    public static ObjectWriteResponse objectUpload(String bucket, String path, MultipartFile multipartFile) {
        try {
            return new ObjectWriteResponse(minioClient.putObject(PutObjectArgs.builder() //
                    .bucket(bucket) //
                    .object(path + multipartFile.getOriginalFilename()) //
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1) //
                    .contentType(multipartFile.getContentType()) //
                    .build()));
        } catch (Exception e) {
            log.error("上传对象", e);
            return new ObjectWriteResponse();
        }
    }

    /**
     * 上传对象
     *
     * @param bucket         储存桶
     * @param path           路径(以/结尾)
     * @param multipartFiles MultipartFile列表
     * @return ObjectWriteResponse
     */
    public static ObjectWriteResponse objectUpload(String bucket, String path, MultipartFile[] multipartFiles) {
        List<SnowballObject> objects = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : multipartFiles) {
                objects.add(new SnowballObject(path + multipartFile.getOriginalFilename(),
                        multipartFile.getInputStream(), multipartFile.getSize(), null));
            }
            return new ObjectWriteResponse(minioClient.uploadSnowballObjects(UploadSnowballObjectsArgs.builder().bucket(bucket).objects(objects).build()));
        } catch (Exception e) {
            log.error("上传对象", e);
            return new ObjectWriteResponse();
        }
    }

    /**
     * 创建文件夹
     *
     * @param bucket 储存桶
     * @param path   路径(以/结尾)
     * @return ObjectWriteResponse
     */
    public static ObjectWriteResponse folderCreate(String bucket, String path) {
        try {
            return new ObjectWriteResponse(minioClient.putObject(PutObjectArgs.builder() //
                    .bucket(bucket) //
                    .object(path) //
                    .stream(new ByteArrayInputStream(new byte[]{}), 0, -1) //
                    .build()));
        } catch (Exception e) {
            log.error("创建文件夹", e);
            return new ObjectWriteResponse();
        }
    }

    /**
     * 从本地上传对象
     *
     * @param bucket    储存桶
     * @param path      路径(以/结尾)
     * @param localPath 本地路径
     * @return ObjectWriteResponse
     */
    public static ObjectWriteResponse objectUploadLocal(String bucket, String path, String localPath) {
        try {
            return new ObjectWriteResponse(minioClient.uploadObject(UploadObjectArgs.builder() //
                    .bucket(bucket) //
                    .object(path + Paths.get(localPath).getFileName()) //
                    .filename(localPath) //
                    .build()));
        } catch (Exception e) {
            log.error("从本地上传对象", e);
            return new ObjectWriteResponse();
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
    public static Map<String, String> objectTagGet(String bucket, String path) {
        try {
            return minioClient.getObjectTags(GetObjectTagsArgs.builder().bucket(bucket).object(path).build()).get();
        } catch (Exception e) {
            log.error("获取对象的标签", e);
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
    public static boolean objectTagSet(String bucket, String path, Map<String, String> tags) {
        try {
            minioClient.setObjectTags(SetObjectTagsArgs.builder().bucket(bucket).object(path).tags(tags).build());
            return true;
        } catch (Exception e) {
            log.error("设置对象的标签", e);
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
    public static boolean objectTagDelete(String bucket, String path) {
        try {
            minioClient.deleteObjectTags(DeleteObjectTagsArgs.builder().bucket(bucket).object(path).build());
            return true;
        } catch (Exception e) {
            log.error("删除对象的全部标签", e);
            return false;
        }
    }


    /* ==================== URL ==================== */

    /**
     * 通过访问URL删除对象
     *
     * @param bucket 储存桶
     * @param path   路径
     * @param expiry 失效时间(秒)
     * @return URL(需要使用DELETE方法访问)
     */
    public static String urlDelete(String bucket, String path, int expiry) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.DELETE).bucket(bucket).object(path).expiry(expiry).build());
        } catch (Exception e) {
            log.error("通过访问URL删除对象", e);
            return "";
        }
    }

    /**
     * 通过访问URL修改对象
     *
     * @param bucket      储存桶
     * @param path        路径
     * @param expiry      失效时间(秒)
     * @param queryParams 修改内容
     * @return URL(需要使用PUT方法访问)
     */
    public static String urlUpdate(String bucket, String path, int expiry, Map<String, String> queryParams) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.PUT).bucket(bucket).object(path).expiry(expiry).extraQueryParams(queryParams).build());
        } catch (Exception e) {
            log.error("通过访问URL修改对象", e);
            return "";
        }
    }

    /**
     * 通过访问URL下载对象
     *
     * @param bucket 储存桶
     * @param path   路径
     * @param expiry 失效时间(秒)
     * @return URL(需要使用GET方法访问)
     */
    public static String urlDownload(String bucket, String path, int expiry) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucket).object(path).expiry(expiry).build());
        } catch (Exception e) {
            log.error("通过访问URL下载对象", e);
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
     * @param in          InputStream
     * @param response    HttpServletResponse
     * @param contentType 内容类型
     * @param fileName    文件名
     */
    public static void inputStream2HttpServletResponse(InputStream in, HttpServletResponse response,
                                                       String contentType, String fileName) throws Exception {
        response.setContentType(contentType);
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
        int len;
        byte[] buffer = new byte[1024];
        OutputStream out = response.getOutputStream();
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        try {
            in.close();
        } catch (Exception ignored) {
        }
        if (out != null) {
            try {
                out.close();
            } catch (Exception ignored) {
            }
        }
    }

}
