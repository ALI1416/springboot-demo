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
     * 尝试执行：捕获到异常/不符合条件回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function 函数(返回结果<code>1</code>符合条件)
     * @return 是否成功
     */
    public static boolean tryif(IntSupplier function) {
        return tryif(function, true, true);
    }

    /**
     * 尝试执行：捕获到异常回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     函数(返回结果<code>1</code>符合条件)
     * @param inconformity 不符合条件回滚
     * @return 是否成功
     */
    public static boolean tryif(IntSupplier function, boolean inconformity) {
        return tryif(function, true, inconformity);
    }

    /**
     * 尝试执行<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     函数(返回结果<code>1</code>符合条件)
     * @param exception    捕获到异常回滚
     * @param inconformity 不符合条件回滚
     * @return 是否成功
     */
    public static boolean tryif(IntSupplier function, boolean exception, boolean inconformity) {
        try {
            if (function.getAsInt() != 1) {
                // 不符合条件
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
     * 尝试执行：捕获到异常/不符合条件回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function 函数(返回结果<code>true</code>符合条件)
     * @return 是否成功
     */
    public static boolean tryif(BooleanSupplier function) {
        return tryif(function, true, true);
    }

    /**
     * 尝试执行：捕获到异常回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     函数(返回结果<code>true</code>符合条件)
     * @param inconformity 不符合条件回滚
     * @return 是否成功
     */
    public static boolean tryif(BooleanSupplier function, boolean inconformity) {
        return tryif(function, true, inconformity);
    }

    /**
     * 尝试执行<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     函数(返回结果<code>true</code>符合条件)
     * @param exception    捕获到异常回滚
     * @param inconformity 不符合条件回滚
     * @return 是否成功
     */
    public static boolean tryif(BooleanSupplier function, boolean exception, boolean inconformity) {
        try {
            if (function.getAsBoolean()) {
                // 不符合条件
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
    public static boolean tryif(Runnable function) {
        return tryif(function, true);
    }

    /**
     * 尝试执行<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function  函数
     * @param exception 捕获到异常回滚
     * @return 是否成功
     */
    public static boolean tryif(Runnable function, boolean exception) {
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
