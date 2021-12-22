package com.demo.entity.pojo;

import com.demo.base.ToStringBase;
import lombok.Getter;
import lombok.Setter;

import java.security.Principal;

/**
 * <h1>WsUser</h1>
 *
 * <p>
 * createDate 2021/12/17 10:21:59
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
public class WsUser extends ToStringBase implements Principal {

    private String user;

    @Override
    public String getName() {
        return user;
    }

}
