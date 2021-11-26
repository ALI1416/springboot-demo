package com.demo.util;

import com.demo.entity.vo.RouteVo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>RouteUtils</h1>
 *
 * <p>
 * createDate 2021/11/26 09:44:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class RouteUtils {

    public static void main(String[] args) {
        List<RouteVo> routeList = new ArrayList<>();
        // 0,1,2,3 /*
        routeList.add(new RouteVo(0L, "/", 0, 0L));
        // 10,11,12,13,14 /a/*
        routeList.add(new RouteVo(1L, "a", 1, 0L));
        // 21,22 /b/*
        routeList.add(new RouteVo(2L, "b", 2, 0L));
        // 31 /c/*
        routeList.add(new RouteVo(3L, "c", 3, 0L));
        // /d
        routeList.add(new RouteVo(4L, "d", 4, 0L));
        // /e
        routeList.add(new RouteVo(5L, "e", 5, 0L));
        // /a
        routeList.add(new RouteVo(10L, "", 0, 1L));
        // 110,111,112 /a/aa/*
        routeList.add(new RouteVo(11L, "aa", 1, 1L));
        // /a/ab
        routeList.add(new RouteVo(12L, "ab", 2, 1L));
        // /a/ac
        routeList.add(new RouteVo(13L, "ac", 3, 1L));
        // /a/ad
        routeList.add(new RouteVo(14L, "ad", 4, 1L));
        // /a/ad
        routeList.add(new RouteVo(21L, "ba", 1, 2L));
        // /b/bb
        routeList.add(new RouteVo(22L, "bb", 2, 2L));
        // /c/ca
        routeList.add(new RouteVo(23L, "ca", 3, 3L));
        // /a/aa
        routeList.add(new RouteVo(110L, "", 0, 11L));
        // /a/aa/aaa
        routeList.add(new RouteVo(111L, "aaa", 1, 11L));
        // 1121,1122 /a/aa/aab/*
        routeList.add(new RouteVo(112L, "aab", 2, 11L));
        // /a/aa/aab/aaba
        routeList.add(new RouteVo(1121L, "aaba", 1, 112L));
        // 11221 /a/aa/aab/aabb/*
        routeList.add(new RouteVo(1122L, "aabb", 2, 112L));
        // /a/aa/aab/aabb/aabba
        routeList.add(new RouteVo(11221L, "aabba", 1, 1122L));

        System.out.println(routeList);

        RouteVo tree = list2Tree(routeList);
        System.out.println(tree);
        System.out.println(routeList);

        RouteVo expandedList = tree2ExpandedList(tree);
        System.out.println(expandedList);
        System.out.println(tree);

    }

    /**
     * 列表转树
     *
     * @param routeList 路由表
     * @return 树；如果不存在id=0的根节点，返回new RouteVo()
     */
    public static RouteVo list2Tree(List<RouteVo> routeList) {
        Long parentId;
        String path;
        // 找到并重置根节点
        RouteVo tree;
        Optional<RouteVo> first = routeList.stream().filter(s -> s.getId() == 0).findFirst();
        if (first.isPresent()) {
            tree = first.get();
            parentId = tree.getParentId();
            path = tree.getPath();
            tree.setParentId(-1L);
            tree.setPath("/");
        } else {
            return new RouteVo();
        }
        // 按parentId分组
        Map<Long, List<RouteVo>> map = routeList.stream().collect(Collectors.groupingBy(RouteVo::getParentId));
        // 生成树
        makeTree(tree, map);
        // 还原routeList
        RouteVo route = first.get();
        route.setParentId(parentId);
        route.setPath(path);
        return tree;
    }

    /**
     * 生成树
     *
     * @param tree 接收的树
     * @param map  按parentId分组的map
     */
    private static void makeTree(RouteVo tree, Map<Long, List<RouteVo>> map) {
        // 找到子节点
        List<RouteVo> children = map.get(tree.getId());
        // 如果有子节点
        if (children != null) {
            // 对子节点进行排序
            List<RouteVo> childrenOrder =
                    children.stream().sorted(Comparator.comparing(RouteVo::getSeq)).collect(Collectors.toList());
            children.clear();
            children.addAll(childrenOrder);
            // 子节点生成树
            for (RouteVo child : children) {
                makeTree(child, map);
            }
            // 插入子节点
            tree.setChildren(children);
        }
    }

    /**
     * 树转展开列表
     *
     * @param tree 树
     */
    public static RouteVo tree2ExpandedList(RouteVo tree) {
        RouteVo route = new RouteVo();
        List<String> matcherPath = new ArrayList<>();
        List<String> directPath = new ArrayList<>();
        route.setMatcherPath(matcherPath);
        route.setDirectPath(directPath);
        // 根节点加入路径
        matcherPath.add("/");
        // 找到子节点
        List<RouteVo> children = tree.getChildren();
        if (children != null) {
            for (RouteVo child : children) {
                // 子节点加入路径
                if (child.getChildren() != null) {
                    route.getMatcherPath().add("/" + child.getPath());
                } else {
                    route.getDirectPath().add("/" + child.getPath());
                }
                // 子节点去展开
                makeExpandedList(route, child, "");
            }
        }
        // 排序
        sort(route.getMatcherPath());
        sort(route.getDirectPath());
        return route;
    }

    /**
     * 生成展开列表
     *
     * @param route  接收的列表
     * @param tree   树
     * @param prefix 前缀
     */
    private static void makeExpandedList(RouteVo route, RouteVo tree, String prefix) {
        // 新的前缀
        if (!tree.getPath().isEmpty()) {
            prefix = prefix + "/" + tree.getPath();
        }
        // 找到子节点
        List<RouteVo> children = tree.getChildren();
        if (children != null) {
            for (RouteVo child : children) {
                // 子节点加入路径
                if (child.getChildren() != null) {
                    route.getMatcherPath().add(prefix + "/" + child.getPath());
                } else {
                    if (!child.getPath().isEmpty()) {
                        route.getDirectPath().add(prefix + "/" + child.getPath());
                    } else {
                        route.getDirectPath().add(prefix);
                    }
                }
                // 子节点去展开
                makeExpandedList(route, child, prefix);
            }
        }
    }

    /**
     * 列表排序
     *
     * @param list 列表
     */
    private static void sort(List<String> list) {
        Map<Integer, List<String>> map = new TreeMap<>();
        for (String s : list) {
            int count = charCount(s);
            if (!map.containsKey(count)) {
                map.put(count, new ArrayList<>());
            }
            map.get(count).add(s);
        }
        list.clear();
        for (List<String> value : map.values()) {
            list.addAll(value);
        }
    }

    /**
     * 计算字符串中出现/字符的次数
     *
     * @param str 字符串
     * @return 出现次数
     */
    private static int charCount(String str) {
        int count = 0;
        char[] chars = str.toCharArray();
        for (char a : chars) {
            if (a == '/') {
                count++;
            }
        }
        return count;
    }

}
