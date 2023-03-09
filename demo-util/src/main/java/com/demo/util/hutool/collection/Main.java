package com.demo.util.hutool.collection;

import cn.hutool.core.collection.*;
import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * <h1>集合</h1>
 *
 * <p>
 * createDate 2022/03/09 16:06:15
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        collUtil();
        listUtil();
        iterUtil();
        boundedPriorityQueue();
        concurrentHashSet();
        collStreamUtil();
        lineIter();
    }

    /**
     * 集合工具
     */
    private static void collUtil() {
        log.info("---------- 集合工具 ----------");
        String[] col = new String[]{"a", "b", "c", "d", "e"};
        List<String> colList = CollUtil.newArrayList(col);
        log.info("CollUtil.join:" + CollUtil.join(colList, "#"));
    }

    /**
     * 列表工具
     */
    private static void listUtil() {
        log.info("---------- 列表工具 ----------");
        /*获取满足指定规则所有的元素的位置*/
        List<String> list = ListUtil.toList("1", "2", "3", "4", "3", "2", "1");
        log.info("获取满足指定规则所有的元素的位置:" + Arrays.toString(ListUtil.indexOfAll(list, "2"::equals)));
        /*拆分*/
        log.info("拆分:" + ListUtil.split(list, 2));
        log.info("平均拆分:" + ListUtil.splitAvg(list, 5));
        /*列表截取*/
        log.info("列表截取:" + ListUtil.sub(list, 2, 4));
        /*元素交换*/
        log.info("交换前:" + list);
        ListUtil.swapTo(list, "2", 0);
        log.info("交换后:" + list);
    }

    /**
     * Iterator工具
     */
    private static void iterUtil() {
        log.info("---------- Iterator工具 ----------");
        List<String> list = new ArrayList<>();
        list.add("s");
        list.add(null);
        log.info("Iterable是否为空:" + IterUtil.isEmpty(list));
        log.info("是否包含null元素:" + IterUtil.hasNull(list));
        log.info("获得Iterable对象的元素类型:" + IterUtil.getElementType(list));
    }

    /**
     * 有界优先队列
     */
    private static void boundedPriorityQueue() {
        log.info("---------- 有界优先队列 ----------");
        //初始化队列，设置队列的容量为5（只能容纳5个元素），元素类型为integer使用默认比较器，在队列内部将按照从小到大排序
        BoundedPriorityQueue<Integer> queue = new BoundedPriorityQueue<>(5, Integer::compareTo);
        //定义了6个元素，当元素加入到队列中，会按照从小到大排序，当加入第6个元素的时候，队列末尾（最大的元素）将会被抛弃
        int[] array = new int[]{5, 7, 9, 2, 3, 8};
        for (int i : array) {
            queue.offer(i);
        }
        log.info("有界优先队列:" + queue.toList());
    }

    /**
     * 线程安全的HashSet
     */
    private static void concurrentHashSet() {
        log.info("---------- 线程安全的HashSet ----------");
        Set<String> set = new ConcurrentHashSet<>();
        set.add("a");
        set.add("b");
        log.info("线程安全的HashSet:" + set);
    }

    /**
     * 集合串行流工具
     */
    private static void collStreamUtil() {
        log.info("---------- 集合串行流工具 ----------");
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, 1, 1, "张三"));
        list.add(new Student(1, 2, 2, "李四"));
        list.add(new Student(2, 1, 1, "擎天柱"));
        list.add(new Student(2, 2, 2, "威震天"));
        list.add(new Student(2, 3, 2, "霸天虎"));
        /*集合转Map*/
        log.info("集合转Map:" + CollStreamUtil.toIdentityMap(list, Student::getStudentId));
        /*分组*/
        log.info("分组:" + CollStreamUtil.groupByKey(list, Student::getClassId));
        /*转换提取*/
        log.info("转换提取:" + CollStreamUtil.toList(list, Student::getName));
        /*合并*/
        Map<Long, Student> map1 = new HashMap<>();
        map1.put(1L, new Student(1, 1, 1, "张三"));
        Map<Long, Student> map2 = new HashMap<>();
        map2.put(1L, new Student(2, 1, 1, "李四"));
        log.info("合并:" + CollStreamUtil.merge(map1, map2, Main::merge));
    }

    private static String merge(Student student1, Student student2) {
        if (student1 == null && student2 == null) {
            return null;
        } else if (student1 == null) {
            return student2.getName();
        } else if (student2 == null) {
            return student1.getName();
        } else {
            return student1.getName() + student2.getName();
        }
    }

    /**
     * 行遍历器
     */
    private static void lineIter() {
        log.info("---------- 行遍历器 ----------");
        LineIter lineIter = new LineIter(ResourceUtil.getUtf8Reader("library.properties"));
        for (String s : lineIter) {
            log.info(s);
        }
    }

}
