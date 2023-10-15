package com.demo.entity.pojo;

import com.demo.base.ToStringBase;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <h1>分页详情</h1>
 *
 * <p>
 * createDate 2023/10/15 14:20:55
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
public class PageInfo<T> extends ToStringBase {

    /**
     * 总页数
     */
    private final int pages;
    /**
     * 每页条数
     */
    private final int rows;
    /**
     * 当前页码
     */
    private final int page;
    /**
     * 当前页条数
     */
    private final int row;
    /**
     * 总条数
     */
    private final long total;
    /**
     * 数据
     */
    private final List<T> data;

    public PageInfo(List<T> list) {
        data = list;
        row = list.size();
        rows = row;
        total = row;
        page = 1;
        pages = row == 0 ? 0 : 1;
    }

    public PageInfo(Page<T> p) {
        this(p.getContent(), p.getPageable(), p.getTotalElements());
    }

    public PageInfo(List<T> data, Pageable pageable, long total) {
        this.data = data;
        this.total = total;
        row = data.size();
        rows = pageable.getPageSize();
        // 当前页码修正(从1开始)
        page = pageable.getPageNumber() + 1;
        // 总页数=总条数/每页条数
        pages = total == 0 ? 0 : ((int) ((total - 1) / rows) + 1);
    }

}
