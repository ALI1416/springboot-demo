package com.demo.hutool.math;

import cn.hutool.core.math.MathUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * <h1>数学相关</h1>
 *
 * <p>
 * createDate 2022/03/10 11:08:40
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("排列:" + MathUtil.arrangementCount(6));
        List<String[]> arrangementSelect = MathUtil.arrangementSelect(new String[]{"a", "b", "c"});
        for (String[] strings : arrangementSelect) {
            log.info("全排列选择:" + Arrays.toString(strings));
        }
        log.info("组合:" + MathUtil.combinationCount(8, 3));
        List<String[]> combinationSelect = MathUtil.combinationSelect(new String[]{"a", "b", "c"}, 2);
        for (String[] strings : combinationSelect) {
            log.info("组合选择:" + Arrays.toString(strings));
        }
    }

}
