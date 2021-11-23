package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.entity.proto.PersonProto;
import com.google.protobuf.util.JsonFormat;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <h1>首页</h1>
 *
 * <p>
 * createDate 2021/09/09 10:35:04
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@AllArgsConstructor
public class IndexController {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Hello World模型，点对点模型<br>
     * POST /hello<br>
     * 控制台打印 RabbitService.receiver收到消息：hello:Tue Nov 23 14:34:45 CST 2021
     */
    @PostMapping("hello")
    public Result hello() {
        // 队列名称，对象
        rabbitTemplate.convertAndSend("hello", "hello:" + new Date());
        return Result.o();
    }

    /**
     * Work模型，工作模型<br>
     * POST /work<br>
     * 控制台打印<br>
     * RabbitService2.receiver收到消息：work.0:Tue Nov 23 14:34:45 CST 2021<br>
     * RabbitService2.receiver2收到消息：work.1:Tue Nov 23 14:34:45 CST 2021<br>
     * 等。平均分配
     */
    @PostMapping("work")
    public Result work() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work", "work." + i + ":" + new Date());
        }
        return Result.o();
    }

    /**
     * Fanout模型，广播模型<br>
     * POST /work<br>
     * 控制台打印<br>
     * RabbitService2.receiver3收到消息：fanout:Tue Nov 23 14:34:45 CST 2021<br>
     * RabbitService2.receiver4收到消息：fanout:Tue Nov 23 14:34:45 CST 2021
     */
    @PostMapping("fanout")
    public Result fanout() {
        // 交换机名称，路由key(广播不需要)，对象
        rabbitTemplate.convertAndSend("fanout", "", "fanout:" + new Date());
        return Result.o();
    }

    /**
     * Direct模型，路由模型
     * POST /direct?key=aa<br>
     * 控制台无打印<br>
     * POST /direct?key=error<br>
     * 控制台打印<br>
     * RabbitService2.receiver6收到消息：direct.error:Tue Nov 23 14:34:45 CST 2021<br>
     * RabbitService2.receiver5收到消息：direct.error:Tue Nov 23 14:34:45 CST 2021<br>
     * POST /direct?key=debug<br>
     * 控制台打印 RabbitService2.receiver5收到消息：direct.debug:Tue Nov 23 14:34:45 CST 2021
     */
    @PostMapping("direct")
    public Result direct(String key) {
        rabbitTemplate.convertAndSend("direct", key, "direct." + key + ":" + new Date());
        return Result.o();
    }

    /**
     * Topic模型，动态路由模型
     * POST /topic?key=aa<br>
     * 控制台无打印<br>
     * POST /topic?key=user<br>
     * 控制台打印 RabbitService2.receiver7收到消息：topic.user:Tue Nov 23 14:34:45 CST 2021<br>
     * POST /topic?key=user.root<br>
     * 控制台无打印<br>
     * POST /topic?key=admin<br>
     * 控制台打印 RabbitService2.receiver8收到消息：topic.admin:Tue Nov 23 14:34:45 CST 2021<br>
     * POST /topic?key=admin.user<br>
     * 控制台打印 RabbitService2.receiver7收到消息：topic.admin.user:Tue Nov 23 14:34:45 CST 2021<br>
     * POST /topic?key=admin.user.root<br>
     * 控制台无打印<br>
     * POST /topic?key=root<br>
     * 控制台打印 RabbitService2.receiver7收到消息：topic.root:Tue Nov 23 14:34:45 CST 2021<br>
     * POST /topic?key=root.user<br>
     * 控制台打印<br>
     * RabbitService2.receiver7收到消息：topic.root.user:Tue Nov 23 14:34:45 CST 2021<br>
     * RabbitService2.receiver8收到消息：topic.root.user:Tue Nov 23 14:34:45 CST 2021<br>
     * POST /topic?key=root.user.admin<br>
     * 控制台打印 RabbitService2.receiver7收到消息：topic.root.user.admin:Tue Nov 23 14:34:45 CST 2021<br>
     */
    @PostMapping("topic")
    public Result topic(String key) {
        rabbitTemplate.convertAndSend("topic", key, "topic." + key + ":" + new Date());
        return Result.o();
    }

    /**
     * Hello World模型，点对点模型<br>
     * POST /hello<br>
     * 控制台打印 RabbitService.receiver收到消息：hello:Tue Nov 23 14:34:45 CST 2021
     */
    @PostMapping("proto")
    public Result proto() {
        //创建对象
        PersonProto.Person person = PersonProto.Person.newBuilder()//
                .setName("ali")//
                .setYear(1998)//
                .setGender(true)//
                .setDate(System.currentTimeMillis())//
                .setAddress("address")//
                .addCars(0, PersonProto.Car.newBuilder()//
                        .setName("Car")//
                        .setColor("Red")//
                        .build())//
                .putOther("描述", "暂无")//
                .build();
        // 编码成bytes
        byte[] bytes = person.toByteArray();
        // rabbit发送
        rabbitTemplate.convertAndSend("proto", bytes);
        // 解码成JSON
        JsonFormat.Printer printer = JsonFormat.printer();
        String json = "";
        try {
            json = printer.print(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.o(json);
    }

}
