package com.demo.base;

import com.demo.tool.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
     * 执行function：捕获到异常/条件不符合回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function 要执行的function(返回结果为1时才算符合条件)
     * @return true:符合条件;false:条件不符合/捕获到异常
     */
    public static boolean tryif(Function<Integer> function) {
        return tryif(function, true, true);
    }

    /**
     * 执行function：捕获到异常回滚，并根据参数判断条件不符合是否回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     要执行的function(返回结果为1时才算符合条件)
     * @param inconformity 条件不符合是否回滚
     * @return true:符合条件;false:条件不符合/捕获到异常
     */
    public static boolean tryif(Function<Integer> function, boolean inconformity) {
        return tryif(function, true, inconformity);
    }

    /**
     * 执行function：并根据参数判断是否回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     要执行的function(返回结果为true时才算符合条件)
     * @param exception    捕获到异常是否回滚
     * @param inconformity 条件不符合是否回滚
     * @return true:符合条件;false:条件不符合/捕获到异常
     */
    public static boolean tryif(Function<Integer> function, boolean exception, boolean inconformity) {
        try {
            if (function.run() != 1) {
                if (inconformity) {
                    log.warn("tryif:条件不符合，已回滚");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                } else {
                    log.info("tryif:条件不符合，未回滚");
                }
                return false;
            }
        } catch (Exception e) {
            if (exception) {
                log.error("tryif:捕获到异常，已回滚", e);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } else {
                log.info("tryif:捕获到异常，未回滚", e);
            }
            return false;
        }
        return true;
    }

    /**
     * 执行function：捕获到异常/条件不符合回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function 要执行的function(返回结果为true时才算符合条件)
     * @return true:符合条件;false:条件不符合/捕获到异常
     */
    public static boolean tryif2(Function<Boolean> function) {
        return tryif2(function, true, true);
    }

    /**
     * 执行function：捕获到异常回滚，并根据参数判断条件不符合是否回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     要执行的function(返回结果为true时才算符合条件)
     * @param inconformity 条件不符合是否回滚
     * @return true:符合条件;false:条件不符合/捕获到异常
     */
    public static boolean tryif2(Function<Boolean> function, boolean inconformity) {
        return tryif2(function, true, inconformity);
    }

    /**
     * 执行function：并根据参数判断是否回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function     要执行的function(返回结果为1时才算符合条件)
     * @param exception    捕获到异常是否回滚
     * @param inconformity 条件不符合是否回滚
     * @return true:符合条件;false:条件不符合/捕获到异常
     */
    public static boolean tryif2(Function<Boolean> function, boolean exception, boolean inconformity) {
        try {
            if (!function.run()) {
                if (inconformity) {
                    log.info("tryif2:条件不符合，已回滚");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                } else {
                    log.info("tryif2:条件不符合，未回滚");
                }
                return false;
            }
        } catch (Exception e) {
            if (exception) {
                log.error("tryif2:捕获到异常，已回滚", e);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } else {
                log.error("tryif2:捕获到异常，未回滚", e);
            }
            return false;
        }
        return true;
    }

    /**
     * 执行function：捕获到异常回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function 要执行的function
     * @return true:执行成功;false:捕获到异常
     */
    public static boolean tryif3(Function<?> function) {
        return tryif3(function, true);
    }

    /**
     * 执行function：并根据参数判断是否回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function  要执行的function
     * @param exception 捕获到异常是否回滚
     * @return true:执行成功;false:捕获到异常
     */
    public static boolean tryif3(Function<?> function, boolean exception) {
        try {
            function.run();
        } catch (Exception e) {
            if (exception) {
                log.error("tryif3:捕获到异常，已回滚", e);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } else {
                log.error("tryif3:捕获到异常，未回滚", e);
            }
            return false;
        }
        return true;
    }

}
