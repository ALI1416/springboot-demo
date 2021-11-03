package com.demo.entity.pojo;

import com.demo.base.ToStringBase;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>批量返回结果实体类</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
public class ResultBatch<T> extends ToStringBase {

    /**
     * 为true的列表
     */
    private final List<T> listTrue = new ArrayList<>();
    /**
     * 为false的列表
     */
    private final List<T> listFalse = new ArrayList<>();
    /**
     * 为true的列表的状态信息
     */
    private final List<String> listTrueMsg = new ArrayList<>();
    /**
     * 为false的列表的状态信息
     */
    private final List<String> listFalseMsg = new ArrayList<>();
    /**
     * 成功(全部都为true)
     */
    private boolean ok = true;
    /**
     * 全部的个数
     */
    private int total = 0;
    /**
     * 为true的个数
     */
    private int totalTrue = 0;
    /**
     * 为false的个数
     */
    private int totalFalse = 0;

    /**
     * 新增一个对象
     *
     * @param status  true/false
     * @param t   对象
     * @param msg 状态信息
     */
    public void add(boolean status, T t, String msg) {
        if (status) {
            listTrue.add(t);
            listTrueMsg.add(msg);
            totalTrue += 1;
        } else {
            listFalse.add(t);
            listFalseMsg.add(msg);
            totalFalse += 1;
            ok = false;
        }
        total += 1;
    }

    /**
     * 合并多个ResultBatch
     *
     * @param resultBatchList 多个ResultBatch(需要相同泛型)
     */
    @SafeVarargs
    public static <T> ResultBatch<T> merge(ResultBatch<T>... resultBatchList) {
        ResultBatch<T> result = new ResultBatch<>();
        for (ResultBatch<T> resultBatch : resultBatchList) {
            if (!resultBatch.ok) {
                result.ok = false;
            }
            result.total += resultBatch.total;
            result.totalTrue += resultBatch.totalTrue;
            result.totalFalse += resultBatch.totalFalse;
            result.listTrue.addAll(resultBatch.listTrue);
            result.listTrueMsg.addAll(resultBatch.listTrueMsg);
            result.listFalse.addAll(resultBatch.listFalse);
            result.listFalseMsg.addAll(resultBatch.listFalseMsg);
        }
        return result;
    }
}
