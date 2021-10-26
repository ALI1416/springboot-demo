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
     * try-if简化，不符合function条件的回滚
     *
     * @param function 要执行的函数，返回结果为1时才算符合条件
     */
    public static boolean tryif(Function<Integer> function) {
        return tryif(true, function);
    }

    /**
     * try-if简化
     *
     * @param rollbackIf 不符合function条件的是否回滚
     * @param function   要执行的函数，返回结果为1时才算符合条件
     */
    public static boolean tryif(boolean rollbackIf, Function<Integer> function) {
        try {
            if (function.run() != 1) {
                if (rollbackIf) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                return false;
            }
        } catch (Exception e) {
            if (rollbackIf) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            log.error("try-if捕获到异常", e);
            return false;
        }
        return true;
    }

    /**
     * try-if2简化，不符合function条件的回滚
     *
     * @param function 要执行的函数
     */
    public static boolean tryif2(Function<Boolean> function) {
        return tryif2(true, function);
    }

    /**
     * try-if2简化
     *
     * @param rollbackIf 不符合function条件的是否回滚
     * @param function   要执行的函数
     */
    public static boolean tryif2(boolean rollbackIf, Function<Boolean> function) {
        try {
            if (!function.run()) {
                if (rollbackIf) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                return false;
            }
        } catch (Exception e) {
            if (rollbackIf) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            log.error("try-if2捕获到异常", e);
            return false;
        }
        return true;
    }

}
