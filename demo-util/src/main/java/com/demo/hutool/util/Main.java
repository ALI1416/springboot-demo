package com.demo.hutool.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.swing.clipboard.ClipboardUtil;
import cn.hutool.core.util.*;
import cn.z.util.AnsjUtils;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * <h1>工具</h1>
 *
 * <p>
 * createDate 2022/03/04 11:24:26
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        strUtil();
        hexUtil();
        escapeUtil();
        hashUtil();
        urlUtil();
        xmlUtil();
        objectUtil();
        reflectUtil();
        typeUtil();
        pageUtil();
        clipboardUtil();
        classUtil();
        classLoaderUtil();
        enumUtil();
        runtimeUtil();
        numberUtil();
        arrayUtil();
        randomUtil();
        idUtil();
        zipUtil();
        referenceUtil();
        reUtil();
        idcardUtil();
        desensitizedUtil();
        creditCodeUtil();
        serviceLoaderUtil();
    }

    /**
     * 字符串工具
     */
    private static void strUtil() {
        log.info("---------- 字符串工具 ----------");
        log.info("是否包含空字符串(仅null和空字符串):" + StrUtil.hasEmpty(""));
        log.info("是否包含空字符串(包括不可见字符):" + StrUtil.hasBlank(StrUtil.TAB));
        log.info("去除后缀:" + StrUtil.removeSuffix("123.jpg", ".jpg"));
        log.info("去除前缀:" + StrUtil.removeSuffix("123.jpg", "123"));
        log.info("字符串拼接:" + StrUtil.format("{}爱{}，就像老鼠爱大米", "我", "你"));
    }

    /**
     * 16进制工具
     */
    private static void hexUtil() {
        log.info("---------- 16进制工具 ----------");
        String s = "字符串abc123";
        String hex = HexUtil.encodeHexStr(s);
        log.info("hex:" + hex);
        log.info("hex解码:" + HexUtil.decodeHexStr(hex));
    }

    /**
     * 转义和反转义工具类
     */
    private static void escapeUtil() {
        log.info("---------- 转义和反转义工具类 ----------");
        String s = "字符串abc123 这些不转义*@-_+./这些不转义 %^&*(){}[]";
        String escape = EscapeUtil.escape(s);
        log.info("转义:" + escape);
        log.info("反转义:" + EscapeUtil.unescape(escape));
    }

    /**
     * Hash算法
     */
    private static void hashUtil() {
        log.info("---------- Hash算法 ----------");
        String s = "字符串abc123";
        log.info("加法hash:" + HashUtil.additiveHash(s, 11));
    }

    /**
     * URL工具
     */
    private static void urlUtil() {
        log.info("---------- URL工具 ----------");
        String s = "https://www.hutool.cn/docs/#/core/工具类/URL工具-URLUtil";
        String url = URLUtil.encode(s);
        log.info("编码:" + url);
        log.info("解码:" + URLUtil.decode(url));
    }

    /**
     * XML工具
     */
    private static void xmlUtil() {
        log.info("---------- XML工具 ----------");
        String s = "<html>Hello, World !</html>";
        Document xml = XmlUtil.parseXml(s);
        Node node = xml.getFirstChild();
        log.info("name:" + node.getNodeName());
        log.info("value:" + node.getFirstChild().getNodeValue());
    }

    /**
     * 对象工具
     */
    private static void objectUtil() {
        log.info("---------- 对象工具 ----------");
        log.info("是否为基本类型，包括包装类型和非包装类型:" + ObjectUtil.isBasicType(123));
    }

    /**
     * 反射工具
     */
    private static void reflectUtil() {
        log.info("---------- 反射工具 ----------");
        log.info("获得一个类中所有方法列表，包括其父类中的方法:" + Arrays.toString(ReflectUtil.getMethods(AnsjUtils.class)));
    }

    /**
     * 泛型类型工具
     */
    private static void typeUtil() {
        log.info("---------- 泛型类型工具 ----------");
        Method intTest = ReflectUtil.getMethod(TestClass.class, "intTest", Integer.class);
        log.info("查找指定方法:" + intTest);
        log.info("获取方法的参数类型:" + TypeUtil.getParamType(intTest, 0));
        log.info("获取方法的返回值类型:" + TypeUtil.getReturnType(intTest));
        Method getList = ReflectUtil.getMethod(TestClass.class, "getList");
        Type type = TypeUtil.getReturnType(getList);
        log.info("获得给定类的第一个泛型参数:" + TypeUtil.getTypeArgument(type));
    }

    /**
     * 分页工具
     */
    private static void pageUtil() {
        log.info("---------- 分页工具 ----------");
        log.info("第0页，每页10条:" + Arrays.toString(PageUtil.transToStartEnd(0, 10)));
        log.info("第1页，每页10条:" + Arrays.toString(PageUtil.transToStartEnd(1, 10)));
        log.info("根据总数计算总页数：" + PageUtil.totalPage(20, 3));
        log.info("分页彩虹算法:" + Arrays.toString(PageUtil.rainbow(5, 20, 6)));
    }

    /**
     * 剪贴板工具
     */
    private static void clipboardUtil() {
        log.info("---------- 剪贴板工具 ----------");
        log.info("获取系统剪贴板:" + ClipboardUtil.getClipboard());
        ClipboardUtil.setStr("Hello, World !");
        log.info("从剪贴板获取文本:" + ClipboardUtil.getStr());
    }

    /**
     * 类工具
     */
    private static void classUtil() {
        log.info("---------- 类工具 ----------");
        log.info("获取完整类名的短格式如:" + ClassUtil.getShortClassName(Main.class.getName()));
        log.info("扫描该包路径下所有class文件:" + ClassUtil.scanPackage("com.demo.ansj"));
    }

    /**
     * 类加载工具
     */
    private static void classLoaderUtil() {
        log.info("---------- 类加载工具 ----------");
        log.info("获取当前线程的ClassLoader:" + ClassLoaderUtil.getContextClassLoader());
    }

    /**
     * 枚举工具
     */
    private static void enumUtil() {
        log.info("---------- 枚举工具 ----------");
        log.info("枚举类中所有枚举对象的name列表:" + EnumUtil.getNames(TestEnum.class));
        log.info("获得枚举类中各枚举对象下指定字段的值:" + EnumUtil.getFieldValues(TestEnum.class, "type"));
    }

    /**
     * 命令行工具
     */
    private static void runtimeUtil() {
        log.info("---------- 命令行工具 ----------");
        log.info("执行系统命令:" + RuntimeUtil.execForStr("ipconfig"));
    }

    /**
     * 数字工具
     */
    private static void numberUtil() {
        log.info("---------- 数字工具 ----------");
        log.info("加法运算:" + NumberUtil.add(1, 2.4, 3.666666));
        log.info("保留小数:" + NumberUtil.round(123.456, 2));
    }

    /**
     * 数组工具
     */
    private static void arrayUtil() {
        log.info("---------- 数组工具 ----------");
        int[] a = {};
        int[] b = null;
        log.info("数组是否为空:" + ArrayUtil.isEmpty(a));
        log.info("数组是否为非空:" + ArrayUtil.isNotEmpty(b));
        Boolean[] bs = ArrayUtil.newArray(Boolean.class, 3);
        bs[0] = false;
        bs[1] = true;
        log.info("新建一个空数组:" + Arrays.toString(bs));
        log.info("生成一个数字列表:" + Arrays.toString(ArrayUtil.range(1, 13, 2)));
        String[] keys = {"a", "b", "c"};
        Integer[] values = {1, 2, 3};
        log.info("映射键值:" + ArrayUtil.zip(keys, values, true));
    }

    /**
     * 随机工具
     */
    private static void randomUtil() {
        log.info("---------- 随机工具 ----------");
        log.info("获得指定范围内的随机数:" + RandomUtil.randomInt(10, 100));
        log.info("获得一个随机的字符串（只包含数字和字符）:" + RandomUtil.randomString(5));
    }

    /**
     * 唯一ID工具
     */
    private static void idUtil() {
        log.info("---------- 唯一ID工具 ----------");
        log.info("UUID:" + IdUtil.randomUUID());
        log.info("简化UUID:" + IdUtil.fastSimpleUUID());
        log.info("ObjectId:" + IdUtil.objectId());
        log.info("NanoId:" + IdUtil.nanoId());
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        log.info("雪花ID:" + snowflake.nextId());
    }

    /**
     * 压缩工具
     */
    private static void zipUtil() {
        log.info("---------- 压缩工具 ----------");
        // // 打包到当前目录（可以打包文件，也可以打包文件夹，根据路径自动判断）
        // ZipUtil.zip("E:/log");
        // // 指定打包后保存的目的地，自动判断目标是文件还是文件夹
        // ZipUtil.zip("E:/log", "E:/zip.zip");
        // // 解压
        // ZipUtil.unzip("E:/zip.zip", "E:/zip");
        // gzip压缩
        byte[] gzip = ZipUtil.gzip("字符串", "UTF-8");
        log.info("gzip压缩:" + StrUtil.str(gzip, StandardCharsets.UTF_8));
        // gzip解压
        byte[] unGzip = ZipUtil.unGzip(gzip);
        log.info("gzip解压:" + StrUtil.str(unGzip, StandardCharsets.UTF_8));
    }

    /**
     * 引用工具
     */
    private static void referenceUtil() {
        // ReferenceUtil.create();
        log.info("---------- 引用工具 ----------");
    }

    /**
     * 正则工具
     */
    private static void reUtil() {
        log.info("---------- 正则工具 ----------");
        String content = "ZZZaaabbbccc中文1234";
        String extractMulti = ReUtil.extractMulti("(\\w)aa(\\w)", content, "$1-$2");
        log.info("抽取多个分组然后把它们拼接起来:" + extractMulti);
        String delFirst = ReUtil.delFirst("(\\w)aa(\\w)", content);
        log.info("删除第一个匹配到的内容:" + delFirst);
        ArrayList<String> findAll = ReUtil.findAll("\\w{2}", content, 0, new ArrayList<>());
        log.info("查找所有匹配文本:" + findAll);
        Integer firstNumber = ReUtil.getFirstNumber(content);
        log.info("找到匹配的第一个数字:" + firstNumber);
        boolean match = ReUtil.isMatch("\\w+[\u4E00-\u9FFF]+\\d+", content);
        log.info("给定字符串是否匹配给定正则:" + match);
        String replaceAll = ReUtil.replaceAll(content, "(\\d+)", "->$1<-");
        log.info("通过正则查找到字符串，然后把匹配到的字符串加入到replacementTemplate中，$1表示分组1的字符串:" + replaceAll);
        String escape = ReUtil.escape("我有个$符号{}");
        log.info("转义给定字符串，为正则相关的特殊符号转义:" + escape);
    }

    /**
     * 身份证工具
     */
    private static void idcardUtil() {
        log.info("---------- 身份证工具 ----------");
        String id = "321083197812162119";
        log.info("是否有效:" + IdcardUtil.isValidCard(id));
        log.info("省份:" + IdcardUtil.getProvinceByIdCard(id));
    }

    /**
     * 信息脱敏工具
     */
    private static void desensitizedUtil() {
        log.info("---------- 信息脱敏工具 ----------");
        log.info("【身份证号】前1位后2位:" + DesensitizedUtil.idCardNum("51343620000320711X", 1, 2));
        log.info("【手机号码】前3位后4位:" + DesensitizedUtil.mobilePhone("18049531999"));
        log.info("【密码】密码的全部字符都用*代替:" + DesensitizedUtil.password("1234567890"));
    }

    /**
     * 社会信用代码工具
     */
    private static void creditCodeUtil() {
        log.info("---------- 社会信用代码工具 ----------");
        log.info("是否是有效的统一社会信用代码:" + CreditCodeUtil.isCreditCode("91310110666007217T"));
        log.info("随机的统一社会信用代码:" + CreditCodeUtil.randomCreditCode());
    }

    /**
     * SPI加载工具
     */
    private static void serviceLoaderUtil() {
        log.info("---------- SPI加载工具 ----------");
        // ServiceLoaderUtil.loadFirstAvailable();
    }

}
