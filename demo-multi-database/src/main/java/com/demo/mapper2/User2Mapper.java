package com.demo.mapper2;

import com.demo.datasource.DataSourceType;
import com.demo.datasource.TargetDataSource;
import com.demo.entity.vo.User2Vo;

/**
 * <h1>用户</h1>
 *
 * <p>
 * createDate 2021/10/26 14:04:27
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@TargetDataSource(DataSourceType.DB2)
public interface User2Mapper {

    /**
     * 插入
     *
     * @param user id,account,pwd
     * @return 执行成功数量
     */
    int insert(User2Vo user);

    /**
     * 更新
     *
     * @param user id(必须),account,pwd,name,comment(至少1个)
     * @return 执行成功数量
     */
    int update(User2Vo user);

    /**
     * 删除唯一键
     *
     * @param user id,account(至少1个)
     * @return 执行成功数量
     */
    int deleteOne(User2Vo user);

    /**
     * 查询唯一键
     *
     * @param user id,account(至少1个)
     * @return User2Vo
     */
    User2Vo findOne(User2Vo user);

}
