package com.demo.hutool.lang;

import cn.hutool.core.codec.BCD;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.text.StrFormatter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <h1>语言特性</h1>
 *
 * <p>
 * createDate 2022/03/08 17:24:31
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        dict();
        singleton();
        assert1();
        bcd();
        console();
        validator();
        strFormatter();
        treeUtil();
    }

    /**
     * HashMap扩展
     */
    private static void dict() {
        log.info("---------- HashMap扩展 ----------");
        // 创建
        Dict dict = Dict.create()
                //int
                .set("key1", 1)
                //long
                .set("key2", 1000L)
                //Date
                .set("key3", new Date());
        // 获取指定类型的值
        log.info("getInt:" + dict.getInt("key1"));
        log.info("getLong:" + dict.getLong("key2"));
        log.info("getDate:" + dict.getDate("key3"));
    }

    /**
     * 单例工具
     */
    private static void singleton() {
        log.info("---------- 单例工具 ----------");
        Animal dog = Singleton.get(Dog.class);
        Animal cat = Singleton.get(Cat.class);
        log.info("单例Dog:" + (dog == Singleton.get(Dog.class)));
        log.info("单例Cat:" + (cat == Singleton.get(Cat.class)));
        dog.say();
        cat.say();
    }

    /**
     * 动物接口
     */
    public interface Animal {
        /**
         * say
         */
        void say();
    }

    /**
     * 狗实现
     */
    public static class Dog implements Animal {
        @Override
        public void say() {
            log.info("Dog:汪汪");
        }
    }

    /**
     * 猫实现
     */
    public static class Cat implements Animal {
        @Override
        public void say() {
            log.info("Cat:喵喵");
        }
    }

    /**
     * 断言
     */
    private static void assert1() {
        log.info("---------- 断言 ----------");
        cn.hutool.core.lang.Assert.isNull(null);
        try {
            cn.hutool.core.lang.Assert.isTrue(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 二进码十进数
     */
    private static void bcd() {
        log.info("---------- 二进码十进数 ----------");
        byte[] bcd = BCD.strToBcd("123456ABCDEF");
        log.info("BCD:" + Arrays.toString(bcd));
        log.info("10:" + BCD.bcdToStr(bcd));
    }

    /**
     * 控制台打印封装
     */
    private static void console() {
        log.info("---------- 控制台打印封装 ----------");
        String[] a = {"abc", "bcd", "def"};
        Console.log(a);
        Console.error("This is Console log for {}.", "test");
    }

    /**
     * 字段验证器
     */
    private static void validator() {
        log.info("---------- 字段验证器 ----------");
        log.info("验证是否为可用邮箱地址:" + Validator.isEmail("loolly@gmail.com"));
        try {
            log.info("验证是否为汉字:" + Validator.validateChinese("我是一段zhongwen", "内容中包含非中文"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 字符串格式化
     * StrFormatter.format的快捷使用方式为StrUtil.format，推荐使用后者
     */
    private static void strFormatter() {
        log.info("---------- 字符串格式化 ----------");
        log.info("通常使用:" + StrFormatter.format("this is {} for {}", "a", "b"));
        log.info("转义{}:" + StrFormatter.format("this is \\{} for {}", "a", "b"));
        log.info("转义\\:" + StrFormatter.format("this is \\\\{} for {}", "a", "b"));
    }

    /**
     * 树结构工具
     */
    private static void treeUtil() {
        log.info("---------- 树结构工具 ----------");
        /*构建Tree*/
        // 构建node列表
        // TreeNode表示一个抽象的节点，也表示数据库中一行数据。 如果有其它数据，可以调用setExtra添加扩展字段。
        List<TreeNode<String>> nodeList = new ArrayList<>();
        nodeList.add(new TreeNode<>("1", "0", "系统管理", 5));
        nodeList.add(new TreeNode<>("11", "1", "用户管理", 222222));
        nodeList.add(new TreeNode<>("111", "11", "用户添加", 0));
        nodeList.add(new TreeNode<>("2", "0", "店铺管理", 1));
        nodeList.add(new TreeNode<>("21", "2", "商品管理", 44));
        nodeList.add(new TreeNode<>("221", "2", "商品管理2", 2));
        // 0表示最顶层的id是0
        List<Tree<String>> treeList = TreeUtil.build(nodeList, "0");
        log.info("树构建:" + treeList);

        /*自定义字段名*/
        // 配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("order");
        treeNodeConfig.setIdKey("rid");
        // 最大递归深度
        treeNodeConfig.setDeep(3);
        // 转换器
        List<Tree<String>> treeNodes = TreeUtil.build(nodeList, "0", treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentId());
            tree.setWeight(treeNode.getWeight());
            tree.setName(treeNode.getName());
            // 扩展属性 ...
            tree.putExtra("extraField", 666);
            tree.putExtra("other", new Object());
        });
        log.info("自定义字段名:" + treeNodes);
    }

}
