package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.entity.vo.RoleVo;
import com.demo.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h1>RoleController</h1>
 *
 * <p>
 * createDate 2021/11/29 15:56:18
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("role")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 查询列表
     */
    @PostMapping("findAll")
    public Result findAll() {
        return Result.o(roleService.findAll());
    }

    /**
     * 查询所有通过UserId
     */
    @PostMapping("findByUserId")
    public Result findByUserId(Long id) {
        return Result.o(roleService.findByUserId(id));
    }

}
