package com.demo.base;

import lombok.extern.slf4j.Slf4j;

import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * <h1>Dao层基类</h1>
 *
 * <p>
 * createDate 2022/01/04 17:17:26
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class DaoBase {

    /**
     * 尝试执行(无事务)：必须无异常、结果必须为1
     *
     * @param function 函数
     * @return 是否成功
     */
    public static boolean tryEq1NoTransaction(IntSupplier function) {
        try {
            return function.getAsInt() == 1;
        } catch (Exception e) {
            log.error("tryEq1NoTransaction", e);
            return false;
        }
    }

    /**
     * 尝试执行(无事务)：必须无异常、结果必须为true
     *
     * @param function 函数
     * @return 是否成功
     */
    public static boolean tryEqTrueNoTransaction(BooleanSupplier function) {
        try {
            return function.getAsBoolean();
        } catch (Exception e) {
            log.error("tryEqTrueNoTransaction", e);
            return false;
        }
    }

    /**
     * 尝试执行(无事务)：必须无异常
     *
     * @param function 函数
     * @return 是否成功
     */
    public static boolean tryAnyNoTransaction(Runnable function) {
        try {
            function.run();
            return true;
        } catch (Exception e) {
            log.error("tryAnyNoTransaction", e);
            return false;
        }
    }

    /**
     * 尝试执行(无事务)：必须无异常
     *
     * @param function 函数
     * @return T(异常返回null)
     */
    public static <T> T tryAnyNoTransactionReturnT(Supplier<T> function) {
        try {
            return function.get();
        } catch (Exception e) {
            log.error("tryAnyNoTransactionReturnT", e);
            return null;
        }
    }

}
