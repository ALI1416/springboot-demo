package com.demo.hutool.map;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.BiMap;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.map.TableMap;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>Map工具</h1>
 *
 * <p>
 * createDate 2022/03/09 17:26:25
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        mapUtil();
        biMap();
        tableMap();
    }

    /**
     * Map工具
     */
    private static void mapUtil() {
        log.info("---------- Map工具 ----------");
        /*创建Map*/
        Map<Object, Object> colorMap = MapUtil.of(new String[][]{{"RED", "#FF0000"}, {"GREEN", "#00FF00"}, {"BLUE",
                "#0000FF"}});
        log.info("创建Map:" + colorMap);
        Map<String, List<Integer>> map = new HashMap<>(3);
        map.put("a", ListUtil.of(1, 2, 3, 4));
        map.put("b", ListUtil.of(1, 2, 3));
        map.put("c", ListUtil.of(1));
        log.info("map:" + map);
        List<Map<String, Integer>> maps = MapUtil.toMapList(map);
        log.info("列转行:" + maps);
        log.info("行转列:" + MapUtil.toListMap(maps));
    }

    /**
     * 双向查找Map
     */
    private static void biMap() {
        log.info("---------- 双向查找Map ----------");
        BiMap<String, Integer> biMap = new BiMap<>(new HashMap<>(2));
        biMap.put("aaa", 111);
        biMap.put("bbb", 222);
        log.info("key:aaa,value:" + biMap.get("aaa"));
        log.info("key:bbb,value:" + biMap.get("bbb"));
        log.info("value:111,key:" + biMap.getKey(111));
        log.info("value:222,key:" + biMap.getKey(222));
    }

    /**
     * 可重复键值Map
     */
    private static void tableMap() {
        log.info("---------- 可重复键值Map ----------");
        TableMap<String, Integer> tableMap = new TableMap<>(4);
        tableMap.put("aaa", 111);
        tableMap.put("bbb", 222);
        tableMap.put("bbb", 333);
        tableMap.put("ccc", 222);
        log.info("key:aaa,value:" + tableMap.get("aaa"));
        log.info("value:111,key:" + tableMap.getKey(111));
        log.info("key:bbb,values:" + tableMap.getValues("bbb"));
        log.info("value:222,keys:" + tableMap.getKeys(222));
    }

}
