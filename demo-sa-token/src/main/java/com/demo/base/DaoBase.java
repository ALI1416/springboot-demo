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
     * function条件不符合、捕获到异常的回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function 要执行的function(function返回结果为1时才算符合条件)
     * @return true:符合function条件并且未捕获到异常;false:其他情况
     */
    public static boolean tryif(Function<Integer> function) {
        return tryif(true, function);
    }

    /**
     * 捕获到异常的回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param rollbackIf function条件不符合的是否回滚
     * @param function   要执行的function(function返回结果为1时才算符合条件)
     * @return true:符合function条件并且未捕获到异常;false:其他情况
     */
    public static boolean tryif(boolean rollbackIf, Function<Integer> function) {
        try {
            if (function.run() != 1) {
                if (rollbackIf) {
                    log.warn("try-if:function条件不符合，已回滚");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                } else {
                    log.info("try-if:function条件不符合，未回滚");
                }
                return false;
            }
        } catch (Exception e) {
            if (rollbackIf) {
                log.error("try-if:捕获到异常，已回滚", e);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } else {
                log.info("try-if:捕获到异常，未回滚", e);
            }
            return false;
        }
        return true;
    }

    /**
     * function条件不符合、捕获到异常的回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function 要执行的function
     * @return true:符合function条件并且未捕获到异常;false:其他情况
     */
    public static boolean tryif2(Function<Boolean> function) {
        return tryif2(true, function);
    }

    /**
     * try-if简化2<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param rollbackIf 不符合function条件的是否回滚
     * @param function   要执行的function
     * @return true:符合function条件并且未捕获到异常;false:其他情况
     */
    public static boolean tryif2(boolean rollbackIf, Function<Boolean> function) {
        try {
            if (!function.run()) {
                if (rollbackIf) {
                    log.info("try-if2:function条件不符合，已回滚");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                } else {
                    log.info("try-if2:function条件不符合，未回滚");
                }
                return false;
            }
        } catch (Exception e) {
            if (rollbackIf) {
                log.error("try-if2:捕获到异常，已回滚", e);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } else {
                log.error("try-if2:捕获到异常，未回滚", e);
            }
            return false;
        }
        return true;
    }

    /**
     * try-if简化3，不符合function条件的回滚<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param function 要执行的function
     * @return function返回结果是否为true
     */
    public static boolean tryif3(Function<?> function) {
        return tryif3(true, function);
    }

    /**
     * try-if简化2<br>
     * 注意：回滚后，后面的语句还会继续执行
     *
     * @param rollbackIf 不符合function条件的是否回滚
     * @param function   要执行的function
     * @return function返回结果是否为true
     */
    public static boolean tryif3(boolean rollbackIf, Function<?> function) {
        try {
            function.run();
        } catch (Exception e) {
            if (rollbackIf) {
                log.error("try-if3:捕获到异常，已回滚", e);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } else {
                log.error("try-if3:捕获到异常，未回滚", e);
            }
            return false;
        }
        return true;
    }

}
