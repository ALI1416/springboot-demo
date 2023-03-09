package com.demo.util.id;

import cn.z.id.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <h1>Id</h1>
 *
 * <p>
 * createDate 2022/03/25 09:46:21
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Controller
@Slf4j
public class IdController {

    @GetMapping("/id")
    public Long id() {
        long id = Id.next();
        log.info(Long.toString(id));
        return id;
    }

}
