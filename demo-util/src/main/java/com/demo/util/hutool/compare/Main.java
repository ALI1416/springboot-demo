package com.demo.util.hutool.compare;

import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.comparator.VersionComparator;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>比较器</h1>
 *
 * <p>
 * createDate 2022/03/10 11:00:30
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        /*比较工具-CompareUtil*/
        log.info("当isNullGreater为true时，null始终最大:" + CompareUtil.compare(null, "a", true));
        log.info("当isNullGreater为false时，null始终最小:" + CompareUtil.compare("a", null, false));
        /*版本比较器-VersionComparator*/
        log.info("比较两个版本:" + VersionComparator.INSTANCE.compare("1.12.1", "1.12.1c"));
        log.info("比较两个版本:" + VersionComparator.INSTANCE.compare("V0.0.20170102", "V0.0.20170101"));
    }

}
