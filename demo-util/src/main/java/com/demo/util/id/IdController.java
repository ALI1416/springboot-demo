package com.demo.util.id;

import cn.z.id.Id;
import com.demo.entity.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>高性能雪花ID生成器Controller</h1>
 *
 * <p>
 * createDate 2022/03/25 09:46:21
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@Slf4j
public class IdController {

    /**
     * 获取<br>
     * http://localhost:8080/id
     */
    @GetMapping("id")
    public Result<Long> get() {
        long id = Id.next();
        log.info(Long.toString(id));
        return Result.o(id);
    }

}
