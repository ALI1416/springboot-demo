package com.demo.dao.mysql;

import com.demo.base.DaoBase;
import com.demo.entity.vo.UserProfileVo;
import com.demo.mapper.UserProfileMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>UserProfileDao</h1>
 *
 * <p>
 * createDate 2021/09/13 10:46:29
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Service
@AllArgsConstructor
public class UserProfileDao extends DaoBase {

    private final UserProfileMapper userProfileMapper;

    /* ==================== 通用方法 ==================== */
    //region

    /**
     * 存在id
     */
    public boolean existId(long id) {
        UserProfileVo userProfile = new UserProfileVo();
        userProfile.setId(id);
        return userProfileMapper.existUniqueKey(userProfile);
    }

    /**
     * 存在account
     */
    public boolean existAccount(String account) {
        UserProfileVo userProfile = new UserProfileVo();
        userProfile.setAccount(account);
        return userProfileMapper.existUniqueKey(userProfile);
    }

    /**
     * 查找，通过id
     */
    public UserProfileVo findById(long id) {
        UserProfileVo userProfile = new UserProfileVo();
        userProfile.setId(id);
        return userProfileMapper.findByUniqueKey(userProfile);
    }

    /**
     * 查找，通过account
     */
    public UserProfileVo findByAccount(String account) {
        UserProfileVo userProfile = new UserProfileVo();
        userProfile.setAccount(account);
        return userProfileMapper.findByUniqueKey(userProfile);
    }

    /**
     * 查找account列表(需account)
     */
    public List<UserProfileVo> findByAccountList(List<String> accountList) {
        return userProfileMapper.findByAccountList(accountList);
    }
    //endregion

}
