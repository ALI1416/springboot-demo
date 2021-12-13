package com.demo.util;

import com.demo.entity.vo.Route2Vo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>Route2Utils</h1>
 *
 * <p>
 * createDate 2021/11/26 09:44:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Route2Utils {

    /**
     * 列表转树
     *
     * @param route2List 路由表
     * @return 树；如果不存在id=0的根节点，返回new Route2Vo()
     */
    public static Route2Vo list2Tree(List<Route2Vo> route2List) {
        // 拷贝原始数据
        List<Route2Vo> list = deepCopy(route2List);
        // 找到并重置根节点
        Route2Vo root;
        Optional<Route2Vo> first = list.stream().filter(s -> s.getId() == 0).findFirst();
        if (first.isPresent()) {
            root = first.get();
            root.setParentId(-1L);
            root.setPath("/");
        } else {
            return new Route2Vo();
        }
        // 按parentId分组
        Map<Long, List<Route2Vo>> map = list.stream().collect(Collectors.groupingBy(Route2Vo::getParentId));
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
    private static void makeTree(Route2Vo tree, Map<Long, List<Route2Vo>> map) {
        // 找到子节点
        List<Route2Vo> children = map.get(tree.getId());
        // 如果有子节点
        if (children != null) {
            // 对子节点进行排序
            children = children.stream().sorted(Comparator.comparing(Route2Vo::getSeq)).collect(Collectors.toList());
            // 子节点生成树
            for (Route2Vo child : children) {
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
    public static Route2Vo tree2ExpandedList(Route2Vo root) {
        // 创建新的路由表
        Route2Vo route2 = new Route2Vo();
        List<Route2Vo> matcher = new ArrayList<>();
        List<Route2Vo> direct = new ArrayList<>();
        route2.setMatcher(matcher);
        route2.setDirect(direct);
        // 拷贝原始数据
        Route2Vo tree = deepCopy(root);
        // 找到子节点
        List<Route2Vo> children = tree.getChildren();
        tree.setChildren(null);
        // 根节点加入路径
        matcher.add(tree);
        if (children != null) {
            for (Route2Vo child : children) {
                // 子节点去展开
                makeExpandedList(route2, child, "");
            }
        }
        // 排序
        route2.setMatcher(route2.getMatcher().stream().sorted(Comparator.comparing(Route2Vo::getSeq)).sorted(Comparator.comparing(Route2Vo::getParentId)).collect(Collectors.toList()));
        route2.setDirect(route2.getDirect().stream().sorted(Comparator.comparing(Route2Vo::getSeq)).sorted(Comparator.comparing(Route2Vo::getParentId)).collect(Collectors.toList()));
        return route2;
    }

    /**
     * 生成展开列表
     *
     * @param route2 接收的列表
     * @param tree   树
     * @param prefix 前缀
     */
    private static void makeExpandedList(Route2Vo route2, Route2Vo tree, String prefix) {
        // 找到子节点
        List<Route2Vo> children = tree.getChildren();
        tree.setChildren(null);
        // 节点加入路径
        if (children != null) {
            // 存在子节点
            prefix = prefix + "/" + tree.getPath();
            tree.setPath(prefix);
            route2.getMatcher().add(tree);
        } else {
            // 不存在子节点，且路径不为空
            if (!tree.getPath().isEmpty()) {
                prefix = prefix + "/" + tree.getPath();
            }
            tree.setPath(prefix);
            route2.getDirect().add(tree);
        }
        if (children != null) {
            for (Route2Vo child : children) {
                // 子节点去展开
                makeExpandedList(route2, child, prefix);
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
