package com.demo.tool.entity.minio;

import com.demo.base.ToStringBase;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>所有者</h1>
 *
 * <p>
 * createDate 2022/03/28 16:32:20
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class Owner extends ToStringBase {

    /**
     * id
     */
    private String id;
    /**
     * 姓名
     */
    private String name;

    public Owner() {
    }

    public Owner(io.minio.messages.Owner owner) {
        this.id = owner.id();
        this.name = owner.displayName();
    }

}
