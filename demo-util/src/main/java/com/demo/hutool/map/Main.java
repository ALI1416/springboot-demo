package com.demo.hutool.map;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
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
    }

    /**
     * Map工具
     */
    private static void mapUtil() {
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

}
