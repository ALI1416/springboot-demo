package com.demo.datasource;

/**
 * <h1>数据源上下文助手</h1>
 *
 * <p>
 * createDate 2020/11/11 11:11:11
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 */
public class DataSourceContextHolder {

    private DataSourceContextHolder() {
    }

    /**
     * 本地线程数据源
     */
    private static final ThreadLocal<DataSourceType> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置当前数据源
     */
    public static void setDataSource(DataSourceType dataSourceType) {
        CONTEXT_HOLDER.set(dataSourceType);
    }

    /**
     * 获取当前数据源
     */
    public static DataSourceType getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除当前数据源
     */
    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }

}
