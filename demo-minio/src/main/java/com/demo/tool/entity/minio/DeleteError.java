package com.demo.tool.entity.minio;

import io.minio.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>删除错误结果</h1>
 *
 * <p>
 * createDate 2022/04/01 17:38:58
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class DeleteError extends ErrorResponse {

    private static final Logger log = LoggerFactory.getLogger(DeleteError.class);

    public DeleteError() {
    }

    public DeleteError(io.minio.messages.DeleteError error) {
        super(error);
    }

    /**
     * io.minio.messages.DeleteError转DeleteError
     *
     * @param errorList Iterable Result io.minio.messages.DeleteError
     * @return List DeleteError
     */
    public static List<DeleteError> toList(Iterable<Result<io.minio.messages.DeleteError>> errorList) {
        List<DeleteError> list = new ArrayList<>();
        for (Result<io.minio.messages.DeleteError> deleteError : errorList) {
            try {
                list.add(new DeleteError(deleteError.get()));
            } catch (Exception e) {
                log.error("[io.minio.messages.DeleteError转DeleteError]异常！", e);
            }
        }
        return list;
    }

}
