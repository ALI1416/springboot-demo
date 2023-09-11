package com.demo.tool.entity.elasticsearch;

import co.elastic.clients.json.JsonData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h1>错误原因</h1>
 *
 * <p>
 * createDate 2023/09/11 10:52:02
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ErrorCause {

    /**
     * 元信息
     */
    private Map<String, JsonData> metadata;
    /**
     * 类型
     */
    private String type;
    /**
     * 原因
     */
    private String reason;
    /**
     * 堆栈跟踪
     */
    private String stackTrace;
    /**
     * 是因为
     */
    private ErrorCause causedBy;
    /**
     * 根原因
     */
    private List<ErrorCause> rootCause;
    /**
     * 抑制
     */
    private List<ErrorCause> suppressed;

    public ErrorCause() {
    }

    public ErrorCause(co.elastic.clients.elasticsearch._types.ErrorCause cause) {
        this.metadata = cause.metadata();
        this.type = cause.type();
        this.stackTrace = cause.stackTrace();
        if (cause.causedBy() != null) {
            this.causedBy = new ErrorCause(cause.causedBy());
        }
        this.rootCause = ErrorCause.toList(cause.rootCause());
        this.suppressed = ErrorCause.toList(cause.suppressed());
    }

    public static List<ErrorCause> toList(List<co.elastic.clients.elasticsearch._types.ErrorCause> causeList) {
        List<ErrorCause> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch._types.ErrorCause cause : causeList) {
            list.add(new ErrorCause(cause));
        }
        return list;
    }

    public Map<String, JsonData> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, JsonData> metadata) {
        this.metadata = metadata;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public ErrorCause getCausedBy() {
        return causedBy;
    }

    public void setCausedBy(ErrorCause causedBy) {
        this.causedBy = causedBy;
    }

    public List<ErrorCause> getRootCause() {
        return rootCause;
    }

    public void setRootCause(List<ErrorCause> rootCause) {
        this.rootCause = rootCause;
    }

    public List<ErrorCause> getSuppressed() {
        return suppressed;
    }

    public void setSuppressed(List<ErrorCause> suppressed) {
        this.suppressed = suppressed;
    }

    @Override
    public String toString() {
        return "ErrorCause{" +
                "metadata=" + metadata +
                ", type='" + type + '\'' +
                ", reason='" + reason + '\'' +
                ", stackTrace='" + stackTrace + '\'' +
                ", causedBy=" + causedBy +
                ", rootCause=" + rootCause +
                ", suppressed=" + suppressed +
                '}';
    }

}
