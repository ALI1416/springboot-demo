package com.demo.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

/**
 * <h1>ToString格式化基类</h1>
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
     * 重写toString成JSON格式
     */
    @Override
    public String toString() {
        return JSON.toJSONStringWithDateFormat(//
                this, //
                "yyyy-MM-dd HH:mm:ss", // 日期格式化样式
                SerializerFeature.DisableCircularReferenceDetect, // 禁用对象循环引用：避免$ref
                SerializerFeature.WriteNonStringValueAsString// 非String转为String：防止long丢失精度
        );
    }

}
