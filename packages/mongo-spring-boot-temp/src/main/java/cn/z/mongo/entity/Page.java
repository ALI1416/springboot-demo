package cn.z.mongo.entity;

import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * <h1>MongoDB分页</h1>
 *
 * <p>
 * createDate 2023/11/02 17:08:31
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Page<T> {

    /**
     * 总页数(从1开始)
     */
    private final int pages;
    /**
     * 当前页码(从1开始)
     */
    private final int page;
    /**
     * 总条数
     */
    private final long total;
    /**
     * 每页条数
     */
    private final int rows;
    /**
     * 当前页条数
     */
    private final int row;
    /**
     * 数据列表
     */
    private final List<T> list;

    /**
     * 构造函数
     *
     * @param query Query
     * @param data  数据列表
     * @param count 总条数
     */
    public Page(Query query, List<T> data, Long count) {
        // 当前页条数
        row = data.size();
        // 数据列表
        list = data;
        if (count == null) {
            /* 全部查询 */
            // 总页数(从1开始)
            pages = 1;
            // 当前页码(从1开始)
            page = 1;
            // 总条数
            total = row;
            // 每页条数
            rows = row;
        } else {
            /* 分页查询 */
            // 每页条数
            rows = query.getLimit();
            // 当前页码(从1开始)=跳过条数/每页条数
            page = (int) (query.getSkip() / rows) + 1;
            // 总条数
            total = count;
            // 总页数(从1开始)=总条数/每页条数
            pages = total == 0 ? 1 : ((int) ((total - 1) / rows) + 1);
        }
    }

    public int getPages() {
        return pages;
    }

    public int getPage() {
        return page;
    }

    public long getTotal() {
        return total;
    }

    public int getRows() {
        return rows;
    }

    public int getRow() {
        return row;
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pages=" + pages +
                ", page=" + page +
                ", total=" + total +
                ", rows=" + rows +
                ", row=" + row +
                ", list=" + list +
                '}';
    }

}
