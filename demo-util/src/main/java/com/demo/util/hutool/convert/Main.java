package com.demo.util.hutool.convert;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.Converter;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <h1>类型转换</h1>
 *
 * <p>
 * createDate 2021/08/27 16:24:55
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        /*转换为字符串*/
        log.info("---------- 转换为字符串 ----------");
        int n = 1;
        log.info("int转String:" + Convert.toStr(n));
        long[] nArr = {1, 2, 3, 4, 5};
        log.info("long[]转String:" + Convert.toStr(nArr));

        /*转换为指定类型数组*/
        log.info("---------- 转换为指定类型数组 ----------");
        String[] sArr = {"1", "2", "3", "4"};
        log.info("String[]转Integer[]:" + Arrays.toString(Convert.toIntArray(sArr)));
        long[] nArr2 = {1, 2, 3, 4, 5};
        log.info("long[]转Integer[]:" + Arrays.toString(Convert.toIntArray(nArr2)));

        /*转换为日期对象*/
        log.info("---------- 转换为日期对象 ----------");
        log.info("String转Date:" + Convert.toDate("2017-05-06"));

        /*转换为集合*/
        log.info("---------- 转换为集合 ----------");
        Object[] a = {"a", "你", "好", "", 1};
        log.info("Object[]转List<?>:" + Convert.convert(List.class, a));
        log.info("Object[]转List<?>:" + Convert.toList(a));

        /*半角和全角转换*/
        log.info("---------- 半角和全角转换 ----------");
        String bjStr = "123456789qwertyuiopasdfghjklzxcvbnm`~!@#$%^&*()-_=+[{]}\\|:;\"'<,>.?/ ";
        log.info("半角转全角:" + Convert.toSBC(bjStr));
        String qjStr2 = "１２３４５６７８９ｑｗｅｒｔｙｕｉｏｐａｓｄｆｇｈｊｋｌｚｘｃｖｂｎｍ｀～！＠＃＄％＾＆＊（）－＿＝＋［｛］｝＼｜：；＂＇＜，＞．？／　";
        log.info("全角转半角:" + Convert.toDBC(qjStr2));

        /*16进制和字符串转换*/
        log.info("---------- 16进制和字符串转换 ----------");
        String str = "我是一个小小的可爱的字符串";
        log.info("字符串转换成十六进制字符串，结果为小写:" + Convert.toHex(str, CharsetUtil.CHARSET_UTF_8));
        String hex = "e68891e698afe4b880e4b8aae5b08fe5b08fe79a84e58fafe788b1e79a84e5ad97e7aca6e4b8b2";
        log.info("十六进制转换字符串:" + Convert.hexToStr(hex, CharsetUtil.CHARSET_UTF_8));

        /*Unicode和字符串转换*/
        log.info("---------- Unicode和字符串转换 ----------");
        String raw = "我是一个小小的可爱的字符串";
        log.info("String的字符串转换成unicode的String:" + Convert.strToUnicode(raw));
        String unicode = "\\u6211\\u662f\\u4e00\\u4e2a\\u5c0f\\u5c0f\\u7684\\u53ef\\u7231\\u7684\\u5b57\\u7b26\\u4e32";
        log.info("unicode的String转换成String的字符串:" + Convert.unicodeToStr(unicode));

        /*编码转换*/
        log.info("---------- 编码转换 ----------");
        String raw2 = "我是一个小小的可爱的字符串";
        String unicode2 = "æ\u0088\u0091æ\u0098¯ä¸\u0080ä¸ªå°\u008Få°\u008Fç\u009A\u0084å\u008F¯ç\u0088±ç\u009A\u0084"
                + "å\u00AD\u0097ç¬¦ä¸²";
        log.info("UTF_8转ISO_8859_1:" + Convert.convertCharset(raw2, CharsetUtil.UTF_8, CharsetUtil.ISO_8859_1));
        log.info("ISO_8859_1转UTF_8:" + Convert.convertCharset(unicode2, CharsetUtil.ISO_8859_1, CharsetUtil.UTF_8));

        /*时间单位转换*/
        log.info("---------- 时间单位转换 ----------");
        log.info("毫秒转为秒:" + Convert.convertTime(4535345, TimeUnit.MILLISECONDS, TimeUnit.MINUTES));

        /*金额大小写转换*/
        log.info("---------- 金额大小写转换 ----------");
        log.info("金额转为中文形式:" + Convert.digitToChinese(67556.32));

        /*数字转为英文表达*/
        log.info("---------- 数字转为英文表达 ----------");
        log.info("将阿拉伯数字转为英文表达方式:" + Convert.numberToWord(100.23));

        /*数字简化*/
        log.info("---------- 数字简化 ----------");
        log.info("将阿拉伯数字转为精简表示形式:" + Convert.numberToSimple(1200));

        /*数字转中文*/
        log.info("---------- 数字转中文 ----------");
        log.info("将阿拉伯数字转为中文表达方式:" + Convert.numberToChinese(10889.72356, false));
        log.info("将阿拉伯数字转为中文表达方式(大写):" + Convert.numberToChinese(12653, true));

        /*数字中文表示转换为数字*/
        log.info("---------- 数字中文表示转换为数字 ----------");
        log.info("数字中文表示形式转数字:" + Convert.chineseToNumber("一千零一十二"));

        /*原始类和包装类转换*/
        log.info("---------- 原始类和包装类转换 ----------");
        log.info("包装类转为原始类，非包装类返回原类:" + Convert.unWrap(Integer.class));
        log.info("原始类转为包装类，非原始类返回原类:" + Convert.wrap(long.class));

        /*自定义类型转换*/
        log.info("---------- 自定义类型转换 ----------");
        // 获取转换器登记中心单例
        ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
        log.info("默认转换器:" + converterRegistry.convert(String.class, 3423));
        // 登记自定义转换器
        // 此处做为示例自定义String转换，因为Hutool中已经提供String转换，请尽量不要替换
        // 替换可能引发关联转换异常（例如覆盖String转换会影响全局）
        converterRegistry.putCustom(String.class, CustomConverter.class);
        log.info("自定义转换器:" + converterRegistry.convert(String.class, 2323));

    }

    /**
     * 自定义转换器
     */
    public static class CustomConverter implements Converter<String> {

        @Override
        public String convert(Object value, String defaultValue) throws IllegalArgumentException {
            return "Custom: " + value.toString();
        }

    }

}
