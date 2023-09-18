package com.demo.controller;

import cn.z.clock.Clock;
import cn.z.id.Id;
import com.alibaba.fastjson2.JSON;
import com.demo.entity.po.Car;
import com.demo.entity.po.Person;
import com.demo.entity.pojo.Result;
import com.demo.entity.proto.PersonProto;
import com.demo.tool.RabbitTemp;
import com.google.protobuf.util.JsonFormat;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final RabbitTemp rabbitTemp;

    /**
     * <h3>点对点模型</h3>
     * http://localhost:8080/p2p <br>
     * 点对点模型 89724233216688128
     */
    @GetMapping("p2p")
    public Result p2p() {
        // 队列名称，对象
        rabbitTemp.send("p2p", Id.next());
        return Result.o();
    }

    /**
     * <h3>工作模型</h3>
     * http://localhost:8080/work <br>
     * 工作模型消费者2 序号1:89724248195596290<br>
     * 工作模型消费者1 序号0:89724248195596289<br>
     * 工作模型消费者2 序号3:89724248195596292<br>
     * 工作模型消费者2 序号5:89724248195596294<br>
     * 工作模型消费者2 序号7:89724248195596296<br>
     * 工作模型消费者2 序号9:89724248195596298<br>
     * 工作模型消费者1 序号2:89724248195596291<br>
     * 工作模型消费者1 序号4:89724248195596293<br>
     * 工作模型消费者1 序号6:89724248195596295<br>
     * 工作模型消费者1 序号8:89724248195596297
     */
    @GetMapping("work")
    public Result work() {
        for (int i = 0; i < 10; i++) {
            rabbitTemp.send("work", "序号" + i + ":" + Id.next());
        }
        return Result.o();
    }

    /**
     * <h3>广播模型</h3>
     * http://localhost:8080/broadcast <br>
     * 广播模型消费者1 89724299505565697<br>
     * 广播模型消费者2 89724299505565697
     */
    @GetMapping("broadcast")
    public Result broadcast() {
        // 交换机名称，路由key(广播不需要)，对象
        rabbitTemp.send("broadcast", "", Id.next());
        return Result.o();
    }

    /**
     * <h3>路由模型</h3>
     * http://localhost:8080/route?key=aa <br>
     * 无<br>
     * http://localhost:8080/route?key=error <br>
     * 路由模型消费者2 error:89724352960921601<br>
     * 路由模型消费者1 error:89724352960921601<br>
     * http://localhost:8080/route?key=debug <br>
     * 路由模型消费者1 debug:89724360485502977
     */
    @GetMapping("route")
    public Result route(String key) {
        rabbitTemp.send("route", key, key + ":" + Id.next());
        return Result.o();
    }

    /**
     * <h3>动态路由模型</h3>
     * http://localhost:8080/dynamicRoute?key=aa <br>
     * 无<br>
     * http://localhost:8080/dynamicRoute?key=user <br>
     * 动态路由模型消费者1 user:89724645977096192<br>
     * http://localhost:8080/dynamicRoute?key=user.root <br>
     * 无<br>
     * http://localhost:8080/dynamicRoute?key=admin <br>
     * 动态路由模型消费者2 admin:89724664629166080<br>
     * http://localhost:8080/dynamicRoute?key=admin.user <br>
     * 动态路由模型消费者1 admin.user:89724676895408129<br>
     * http://localhost:8080/dynamicRoute?key=admin.user.root <br>
     * 无<br>
     * http://localhost:8080/dynamicRoute?key=root <br>
     * 动态路由模型消费者1 root:89724693238513664<br>
     * http://localhost:8080/dynamicRoute?key=root.user <br>
     * 动态路由模型消费者1 root.user:89724701986783233<br>
     * 动态路由模型消费者2 root.user:89724701986783233<br>
     * http://localhost:8080/dynamicRoute?key=root.user.admin <br>
     * 动态路由模型消费者1 root.user.admin:89724710057672704
     */
    @GetMapping("dynamicRoute")
    public Result dynamicRoute(String key) {
        rabbitTemp.send("dynamicRoute", key, key + ":" + Id.next());
        return Result.o();
    }

    /**
     * <h3>ProtocolBuffers1</h3>
     * http://localhost:8080/protocolBuffers1 <br>
     * ProtocolBuffers1 {
     * "name": "ali",
     * "year": 1998,
     * "gender": true,
     * "date": "1695027410957",
     * "address": "address",
     * "cars": [{
     * "name": "Car",
     * "color": "Red"
     * }],
     * "other": {
     * "描述": "暂无"
     * }
     * }
     */
    @GetMapping("protocolBuffers1")
    public Result protocolBuffers1() {
        // 创建PersonProto.Person对象
        PersonProto.Person person = PersonProto.Person.newBuilder() //
                .setName("ali") //
                .setYear(1998) //
                .setGender(true) //
                .setDate(Clock.now()) //
                .setAddress("address") //
                .addCars(0, PersonProto.Car.newBuilder() //
                        .setName("Car") //
                        .setColor("Red") //
                        .build() //
                ) //
                .putOther("描述", "暂无") //
                .build();
        // 编码成bytes
        byte[] bytes = person.toByteArray();
        // rabbit发送
        rabbitTemp.send("protocolBuffers1", bytes);
        return Result.o();
    }

    /**
     * <h3>ProtocolBuffers2</h3>
     * http://localhost:8080/protocolBuffers2 <br>
     * ProtocolBuffers2 {"address":"address","cars":[{"color":"Red","name":"Car"}],"date":"1695027857124","gender":true,"name":"ali","other":{"描述":"暂无"},"year":1998}
     */
    @GetMapping("protocolBuffers2")
    public Result protocolBuffers2() throws Exception {
        // 创建Person对象
        Person person = new Person();
        person.setName("ali");
        person.setYear(1998);
        person.setGender(true);
        person.setDate(Clock.now());
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
        JsonFormat.parser().merge(JSON.toJSONString(person), builder);
        // 编码成bytes
        byte[] bytes = builder.build().toByteArray();
        // rabbit发送
        rabbitTemp.send("protocolBuffers2", bytes);
        return Result.o();
    }

    /**
     * <h3>死信测试1</h3>
     * http://localhost:8080/deadLetterTest1?msg=a <br>
     * http://localhost:8080/deadLetterTest1?msg=ab <br>
     */
    @GetMapping("deadLetterTest1")
    public Result deadLetterTest1(String msg) {
        rabbitTemp.send("deadLetterTest", "deadLetterTest1", msg);
        return Result.o();
    }

}
