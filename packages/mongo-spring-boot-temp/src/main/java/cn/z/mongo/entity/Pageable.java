package cn.z.mongo.entity;

import org.springframework.data.domain.Sort;

/**
 * <h1>MongoDB分页器</h1>
 *
 * <p>
 * createDate 2023/11/03 17:12:41
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Pageable {

    /**
     * 分页器
     */
    private final org.springframework.data.domain.Pageable pageable;
    /**
     * 排序
     */
    private final Sort sort;

    /**
     * 构造函数
     *
     * @param pageable Pageable
     * @param sort     Sort
     */
    public Pageable(org.springframework.data.domain.Pageable pageable, Sort sort) {
        this.pageable = pageable;
        this.sort = sort;
    }

    public org.springframework.data.domain.Pageable getPageable() {
        return pageable;
    }

    public Sort getSort() {
        return sort;
    }

    @Override
    public String toString() {
        return "Pageable{" +
                "pageable=" + pageable +
                ", sort=" + sort +
                '}';
    }

}
