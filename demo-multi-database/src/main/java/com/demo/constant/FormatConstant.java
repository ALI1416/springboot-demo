package com.demo.constant;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;

/**
 * <h1>格式常量</h1>
 *
 * <p>
 * createDate 2023/03/03 16:15:43
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class FormatConstant {

    private FormatConstant() {
    }

    /**
     * 日期
     */
    public static final String DATE = "yyyy-MM-dd HH:mm:ss";
    /**
     * JSON读特性
     */
    public static final JSONReader.Feature[] JSON_READER_FEATURE = {
            // 智能匹配
            JSONReader.Feature.SupportSmartMatch
    };
    /**
     * JSON写特性
     */
    public static final JSONWriter.Feature[] JSON_WRITER_FEATURE = {
            // Long转String
            JSONWriter.Feature.WriteLongAsString,
            // MapKey转String
            JSONWriter.Feature.WriteNonStringKeyAsString
    };

}
