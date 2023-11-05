package com.demo.entity.pojo;

import com.demo.base.ToStringBase;
import com.github.pagehelper.Page;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "分页详情")
public class PageInfo<T> extends ToStringBase {

    /**
     * 总页数(从1开始)
     */
    @Schema(description = "总页数(从1开始)")
    private final int pages;
    /**
     * 当前页码(从1开始)
     */
    @Schema(description = "当前页码(从1开始)")
    private final int page;
    /**
     * 总条数
     */
    @Schema(description = "总条数")
    private final long total;
    /**
     * 每页条数
     */
    @Schema(description = "每页条数")
    private final int rows;
    /**
     * 当前页条数
     */
    @Schema(description = "当前页条数")
    private final int row;
    /**
     * 数据列表
     */
    @Schema(description = "数据列表")
    private final List<T> list;

    /**
     * 构造函数(未分页)
     *
     * @param data 数据列表
     */
    public PageInfo(List<T> data) {
        // 总页数(从1开始)
        pages = 1;
        // 当前页码(从1开始)
        page = 1;
        // 当前页条数
        row = data.size();
        // 总条数
        total = row;
        // 每页条数
        rows = row;
        // 数据列表
        list = data;
    }

    /**
     * 构造函数(PageHelper分页)
     *
     * @param data PageHelper分页
     */
    public PageInfo(Page<T> data) {
        // 当前页码(从1开始)
        page = data.getPageNum();
        // 总条数
        total = data.getTotal();
        // 每页条数
        rows = data.getPageSize();
        // 总页数(从1开始)=总条数/每页条数
        pages = total == 0 ? 1 : ((int) ((total - 1) / rows) + 1);
        // 当前页条数
        row = data.size();
        // 数据列表
        list = data;
    }

    /**
     * 构造函数(MongoDB分页)
     *
     * @param data MongoDB分页
     */
    public PageInfo(cn.z.mongo.entity.Page<T> data) {
        // 总页数(从1开始)
        pages = data.getPages();
        // 当前页码(从1开始)
        page = data.getPage();
        // 总条数
        total = data.getTotal();
        // 每页条数
        rows = data.getRows();
        // 当前页条数
        row = data.getRow();
        // 数据列表
        list = data.getList();
    }

}
