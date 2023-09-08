package com.demo.entity.po;

import com.demo.base.ToStringBase;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>文章</h1>
 *
 * <p>
 * createDate 2023/09/06 16:46:34
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class Article  extends ToStringBase {

    /**
     * 内容
     */
    private String content;

}
