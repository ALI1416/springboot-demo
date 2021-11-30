package com.demo.util;

import com.demo.entity.po.Route;
import com.demo.entity.vo.RouteVo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        routeList.add(new RouteVo(21L, "ba", 0, 2L));
        // /b/bb
        routeList.add(new RouteVo(22L, "bb", 1, 2L));
        // /c/ca
        routeList.add(new RouteVo(23L, "ca", 0, 3L));
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
        routeList.add(new RouteVo(11221L, "aabba", 0, 1122L));

        System.out.println(routeList);

        RouteVo tree = list2Tree(routeList);
        System.out.println(tree);

        RouteVo expandedList = tree2ExpandedList(tree);
        System.out.println(expandedList);

    }

    /**
     * 列表转树
     *
     * @param routeList 路由表
     * @return 树；如果不存在id=0的根节点，返回new RouteVo()
     */
    public static RouteVo list2Tree(List<RouteVo> routeList) {
        // 拷贝原始数据
        List<RouteVo> list = deepCopy(routeList);
        // 找到并重置根节点
        RouteVo root;
        Optional<RouteVo> first = list.stream().filter(s -> s.getId() == 0).findFirst();
        if (first.isPresent()) {
            root = first.get();
            root.setParentId(-1L);
            root.setPath("/");
        } else {
            return new RouteVo();
        }
        // 按parentId分组
        Map<Long, List<RouteVo>> map = list.stream().collect(Collectors.groupingBy(RouteVo::getParentId));
        // 生成树
        makeTree(root, map);
        return root;
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
            children = children.stream().sorted(Comparator.comparing(RouteVo::getSeq)).collect(Collectors.toList());
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
     * @param root 树
     * @return 列表
     */
    public static RouteVo tree2ExpandedList(RouteVo root) {
        // 创建新的路由表
        RouteVo route = new RouteVo();
        List<RouteVo> matcher = new ArrayList<>();
        List<RouteVo> direct = new ArrayList<>();
        route.setMatcher(matcher);
        route.setDirect(direct);
        // 拷贝原始数据
        RouteVo tree = deepCopy(root);
        // 找到子节点
        List<RouteVo> children = tree.getChildren();
        tree.setChildren(null);
        // 根节点加入路径
        matcher.add(tree);
        if (children != null) {
            for (RouteVo child : children) {
                // 子节点去展开
                makeExpandedList(route, child, "");
            }
        }
        // 排序
        route.setMatcher(route.getMatcher().stream().sorted(Comparator.comparing(Route::getSeq)).sorted(Comparator.comparing(Route::getParentId)).collect(Collectors.toList()));
        route.setDirect(route.getDirect().stream().sorted(Comparator.comparing(Route::getSeq)).sorted(Comparator.comparing(Route::getParentId)).collect(Collectors.toList()));
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
        // 找到子节点
        List<RouteVo> children = tree.getChildren();
        tree.setChildren(null);
        // 节点加入路径
        if (children != null) {
            // 存在子节点
            prefix = prefix + "/" + tree.getPath();
            tree.setPath(prefix);
            route.getMatcher().add(tree);
        } else {
            // 不存在子节点，且路径不为空
            if (!tree.getPath().isEmpty()) {
                prefix = prefix + "/" + tree.getPath();
            }
            tree.setPath(prefix);
            route.getDirect().add(tree);
        }
        if (children != null) {
            for (RouteVo child : children) {
                // 子节点去展开
                makeExpandedList(route, child, prefix);
            }
        }
    }

    /**
     * 深度拷贝
     *
     * @param src T
     * @return T
     */
    @SuppressWarnings("unchecked")
    private static <T> T deepCopy(T src) {
        T dest = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            dest = (T) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dest;
    }

}
