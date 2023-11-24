package com.demo.base;

import com.alibaba.fastjson2.JSON;
import com.demo.constant.FormatConstant;

import java.io.Serializable;

/**
 * <h1>ToString基类</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ToStringBase implements Serializable {

    /**
     * JSON字符串
     */
    @Override
    public String toString() {
        return JSON.toJSONString(this, FormatConstant.DATE);
    }

    /**
     * Web格式JSON字符串
     */
    public String toWebString() {
        return JSON.toJSONString(this, FormatConstant.DATE, FormatConstant.JSON_WRITER_FEATURE);
    }

    /**
     * JSON byte[]
     */
    public byte[] toBytes() {
        return JSON.toJSONBytes(this, FormatConstant.DATE);
    }

    /**
     * Web格式JSON byte[]
     */
    public byte[] toWebBytes() {
        return JSON.toJSONBytes(this, FormatConstant.DATE, FormatConstant.JSON_WRITER_FEATURE);
    }

}
