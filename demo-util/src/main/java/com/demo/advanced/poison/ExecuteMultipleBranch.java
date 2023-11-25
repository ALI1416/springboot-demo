package com.demo.advanced.poison;

import lombok.extern.java.Log;

/**
 * <h1>同时执行多个分支</h1>
 *
 * <p>
 * createDate 2022/09/13 11:28:39
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Log
public class ExecuteMultipleBranch {

    public static void judge(String param) {
        if (
            // 当第一次调用judge()方法时，不满足`或运算`中的第一个条件，因此执行第二个条件
                param == null ||
                        // 执行`匿名内部类`内的`实例化初始块代码`，再次执行judge()方法，此时满足if条件，因此执行第一句打印语句
                        new ExecuteMultipleBranch() {{
                            ExecuteMultipleBranch.judge(null);
                        }}
                                // 而实例化的新对象不满足后面的equals()方法中的条件，所以不满足if中的任意一个条件，因此会执行else中的语句，执行第二句打印语句
                                .equals(null)) {
            log.info("step one");
        } else {
            log.info("step two");
        }
    }

    public static void main(String[] args) {
        judge("Hydra");
    }

}
