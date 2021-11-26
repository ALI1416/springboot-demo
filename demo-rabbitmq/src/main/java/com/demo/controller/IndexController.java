package com.demo.controller;

import cn.z.clock.Clock;
import com.alibaba.fastjson.JSON;
import com.demo.entity.po.Car;
import com.demo.entity.po.Person;
import com.demo.entity.pojo.Result;
import com.demo.entity.proto.PersonProto;
import com.google.protobuf.util.JsonFormat;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * <h3>Hello World模型，点对点模型</h3>
     * POST /hello<br>
     * 控制台打印 RabbitService.receiver收到消息：hello:2021-11-24 10:39:55.584
     */
    @PostMapping("hello")
    public Result hello() {
        // 队列名称，对象
        rabbitTemplate.convertAndSend("hello", "hello:" + Clock.timestamp());
        return Result.o();
    }

    /**
     * <h3>Work模型，工作模型</h3>
     * POST /work<br>
     * 控制台打印<br>
     * RabbitService2.receiver收到消息：work.0:2021-11-24 10:39:55.584<br>
     * RabbitService2.receiver2收到消息：work.1:2021-11-24 10:39:55.584<br>
     * 等。平均分配
     */
    @PostMapping("work")
    public Result work() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work", "work." + i + ":" + Clock.timestamp());
        }
        return Result.o();
    }

    /**
     * <h3>Fanout模型，广播模型</h3>
     * POST /work<br>
     * 控制台打印<br>
     * RabbitService2.receiver3收到消息：fanout:2021-11-24 10:39:55.584<br>
     * RabbitService2.receiver4收到消息：fanout:2021-11-24 10:39:55.584
     */
    @PostMapping("fanout")
    public Result fanout() {
        // 交换机名称，路由key(广播不需要)，对象
        rabbitTemplate.convertAndSend("fanout", "", "fanout:" + Clock.timestamp());
        return Result.o();
    }

    /**
     * <h3>Direct模型，路由模型</h3>
     * POST /direct?key=aa<br>
     * 控制台无打印<br>
     * POST /direct?key=error<br>
     * 控制台打印<br>
     * RabbitService2.receiver6收到消息：direct.error:2021-11-24 10:39:55.584<br>
     * RabbitService2.receiver5收到消息：direct.error:2021-11-24 10:39:55.584<br>
     * POST /direct?key=debug<br>
     * 控制台打印 RabbitService2.receiver5收到消息：direct.debug:2021-11-24 10:39:55.584
     */
    @PostMapping("direct")
    public Result direct(String key) {
        rabbitTemplate.convertAndSend("direct", key, "direct." + key + ":" + Clock.timestamp());
        return Result.o();
    }

    /**
     * <h3>Topic模型，动态路由模型</h3>
     * POST /topic?key=aa<br>
     * 控制台无打印<br>
     * POST /topic?key=user<br>
     * 控制台打印 RabbitService2.receiver7收到消息：topic.user:2021-11-24 10:39:55.584<br>
     * POST /topic?key=user.root<br>
     * 控制台无打印<br>
     * POST /topic?key=admin<br>
     * 控制台打印 RabbitService2.receiver8收到消息：topic.admin:2021-11-24 10:39:55.584<br>
     * POST /topic?key=admin.user<br>
     * 控制台打印 RabbitService2.receiver7收到消息：topic.admin.user:2021-11-24 10:39:55.584<br>
     * POST /topic?key=admin.user.root<br>
     * 控制台无打印<br>
     * POST /topic?key=root<br>
     * 控制台打印 RabbitService2.receiver7收到消息：topic.root:2021-11-24 10:39:55.584<br>
     * POST /topic?key=root.user<br>
     * 控制台打印<br>
     * RabbitService2.receiver7收到消息：topic.root.user:2021-11-24 10:39:55.584<br>
     * RabbitService2.receiver8收到消息：topic.root.user:2021-11-24 10:39:55.584<br>
     * POST /topic?key=root.user.admin<br>
     * 控制台打印 RabbitService2.receiver7收到消息：topic.root.user.admin:2021-11-24 10:39:55.584<br>
     */
    @PostMapping("topic")
    public Result topic(String key) {
        rabbitTemplate.convertAndSend("topic", key, "topic." + key + ":" + Clock.timestamp());
        return Result.o();
    }

    /**
     * <h3>protobuf</h3>
     * POST /proto
     */
    @PostMapping("proto")
    public Result proto() {
        // 创建PersonProto.Person对象
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
        return Result.o(bytes);
    }

    /**
     * <h3>protobuf2</h3>
     * POST /proto2
     */
    @PostMapping("proto2")
    public Result proto2() {
        // 创建Person对象
        Person person = new Person();
        person.setName("ali");
        person.setYear(1998);
        person.setGender(true);
        person.setDate(Clock.timestamp());
        person.setAddress("address");
        Car car = new Car();
        car.setName("Car");
        car.setColor("Red");
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        person.setCars(cars);
        Map<String, String> other = new HashMap<>();
        other.put("描述", "暂无");
        person.setOther(other);
        // 先转换成JSON字符串，再转换成PersonProto.Person.Builder对象
        PersonProto.Person.Builder builder = PersonProto.Person.newBuilder();
        try {
            JsonFormat.parser().merge(JSON.toJSONString(person), builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 编码成bytes
        byte[] bytes = builder.build().toByteArray();
        // rabbit发送
        rabbitTemplate.convertAndSend("proto2", bytes);
        return Result.o(bytes);
    }

}
