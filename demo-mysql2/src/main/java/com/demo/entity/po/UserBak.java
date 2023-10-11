package com.demo.entity.po;

import cn.z.id.Id;

/**
 * <h1>用户备份实体类</h1>
 *
 * <p>
 * createDate 2021/10/28 14:45:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class UserBak extends User {

    /**
     * 构造函数
     */
    public UserBak() {
    }

    /**
     * 构造函数(自动生成id)
     *
     * @param refId refId
     */
    public UserBak(Long refId) {
        setId(Id.next());
        setRefId(refId);
    }

}
