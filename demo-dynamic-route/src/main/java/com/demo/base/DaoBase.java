package com.demo.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

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
@Slf4j
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
                if (inconformity) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            log.error("tryEq1", e);
            if (exception) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return false;
        }
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
            if (!function.getAsBoolean()) {
                if (inconformity) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            log.error("tryEqTrue", e);
            if (exception) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return false;
        }
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
            return true;
        } catch (Exception e) {
            log.error("tryAny", e);
            if (exception) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return false;
        }
    }

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
