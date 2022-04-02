package com.demo.util.pojo.minio;

import io.minio.Result;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * <h1>删除错误</h1>
 *
 * <p>
 * createDate 2022/04/01 17:38:58
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class DeleteError extends ErrorResponse {

    public DeleteError() {
    }

    public DeleteError(io.minio.messages.DeleteError deleteError) {
        super(deleteError);
    }

    /**
     * DeleteError转Bean
     *
     * @param deleteErrors Iterable&lt;Result&lt;DeleteError>>
     * @return List&lt;DeleteError>
     */
    public static List<DeleteError> getList(Iterable<Result<io.minio.messages.DeleteError>> deleteErrors) {
        return StreamSupport.stream(deleteErrors.spliterator(), true).map(deleteError -> {
            try {
                return new DeleteError(deleteError.get());
            } catch (Exception e) {
                log.error("Result<DeleteError>转DeleteError失败", e);
                return new DeleteError();
            }
        }).collect(Collectors.toList());
    }

}
