package com.demo.entity.po;

import com.demo.base.EntityBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>角色</h1>
 *
 * <p>
 * createDate 2021/11/26 09:34:07
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@Schema(description = "角色")
public class Role extends EntityBase {

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;
    /**
     * 顺序
     */
    @Schema(description = "顺序")
    private Integer seq;

}
