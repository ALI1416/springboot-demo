package com.demo.base;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

/**
 * <h1>Dao层基类</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class DaoBase {

    /**
     * 尝试执行：捕获到异常、结果不为1回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function 函数
     * @return 是否成功
     */
    public static boolean tryEq1(IntSupplier function) {
        return tryEq1(function, true, true);
    }

    /**
     * 尝试执行：捕获到异常回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     函数
     * @param inconformity 结果不为1回滚
     * @return 是否成功
     */
    public static boolean tryEq1(IntSupplier function, boolean inconformity) {
        return tryEq1(function, true, inconformity);
    }

    /**
     * 尝试执行<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     函数
     * @param exception    捕获到异常回滚
     * @param inconformity 结果不为1回滚
     * @return 是否成功
     */
    public static boolean tryEq1(IntSupplier function, boolean exception, boolean inconformity) {
        try {
            if (function.getAsInt() != 1) {
                // 结果不为1
                if (inconformity) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                return false;
            }
        } catch (Exception e) {
            // 捕获到异常
            if (exception) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return false;
        }
        return true;
    }

    /**
     * 尝试执行：捕获到异常、结果不为true回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function 函数
     * @return 是否成功
     */
    public static boolean tryEqTrue(BooleanSupplier function) {
        return tryEqTrue(function, true, true);
    }

    /**
     * 尝试执行：捕获到异常回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     函数
     * @param inconformity 结果不为true回滚
     * @return 是否成功
     */
    public static boolean tryEqTrue(BooleanSupplier function, boolean inconformity) {
        return tryEqTrue(function, true, inconformity);
    }

    /**
     * 尝试执行<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     函数
     * @param exception    捕获到异常回滚
     * @param inconformity 结果不为true回滚
     * @return 是否成功
     */
    public static boolean tryEqTrue(BooleanSupplier function, boolean exception, boolean inconformity) {
        try {
            if (function.getAsBoolean()) {
                // 结果不为true
                if (inconformity) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                return false;
            }
        } catch (Exception e) {
            // 捕获到异常
            if (exception) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return false;
        }
        return true;
    }

    /**
     * 尝试执行：捕获到异常回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function 函数
     * @return 是否成功
     */
    public static boolean tryAny(Runnable function) {
        return tryAny(function, true);
    }

    /**
     * 尝试执行<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function  函数
     * @param exception 捕获到异常回滚
     * @return 是否成功
     */
    public static boolean tryAny(Runnable function, boolean exception) {
        try {
            function.run();
        } catch (Exception e) {
            // 捕获到异常
            if (exception) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return false;
        }
        return true;
    }

}
