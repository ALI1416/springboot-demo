package com.demo.entity.pojo;

import com.demo.base.ToStringBase;
import com.github.pagehelper.Page;
import lombok.Getter;

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
        pages = p.getPages();
        rows = p.getPageSize();
        page = p.getPageNum();
        row = p.size();
        total = p.getTotal();
        data = p;
    }

}
