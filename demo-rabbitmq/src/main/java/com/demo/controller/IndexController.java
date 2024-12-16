package com.demo.controller;

import cn.z.clock.Clock;
import cn.z.id.Id;
import cn.z.rabbit.RabbitTemp;
import com.alibaba.fastjson2.JSON;
import com.demo.constant.RabbitExchange;
import com.demo.constant.RabbitQueue;
import com.demo.entity.po.Car;
import com.demo.entity.po.Person;
import com.demo.entity.pojo.Result;
import com.demo.entity.proto.PersonProto;
import com.google.protobuf.util.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class IndexController {

    private final RabbitTemp rabbitTemp;

    /**
     * <h3>点对点模型</h3>
     * http://localhost:8080/p2p<br>
     * 点对点模型 89724233216688128
     */
    @GetMapping("p2p")
    public Result p2p() {
        rabbitTemp.send(RabbitQueue.P2P, Id.next());
        return Result.o();
    }

    /**
     * <h3>工作模型</h3>
     * http://localhost:8080/work<br>
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
            rabbitTemp.send(RabbitQueue.WORK, "序号" + i + ":" + Id.next());
        }
        return Result.o();
    }

    /**
     * <h3>广播模型</h3>
     * http://localhost:8080/broadcast<br>
     * 广播模型消费者1 89724299505565697<br>
     * 广播模型消费者2 89724299505565697
     */
    @GetMapping("broadcast")
    public Result broadcast() {
        rabbitTemp.broadcast(RabbitExchange.BROADCAST, Id.next());
        return Result.o();
    }

    /**
     * <h3>路由模型</h3>
     * http://localhost:8080/route?key=aa<br>
     * 无<br>
     * http://localhost:8080/route?key=error<br>
     * 路由模型消费者2 error:89724352960921601<br>
     * 路由模型消费者1 error:89724352960921601<br>
     * http://localhost:8080/route?key=debug<br>
     * 路由模型消费者1 debug:89724360485502977
     */
    @GetMapping("route")
    public Result route(String key) {
        rabbitTemp.send(RabbitExchange.ROUTE, key, key + ":" + Id.next());
        return Result.o();
    }

    /**
     * <h3>动态路由模型</h3>
     * http://localhost:8080/dynamicRoute?key=aa<br>
     * 无<br>
     * http://localhost:8080/dynamicRoute?key=user<br>
     * 动态路由模型消费者1 user:89724645977096192<br>
     * http://localhost:8080/dynamicRoute?key=user.root<br>
     * 无<br>
     * http://localhost:8080/dynamicRoute?key=admin<br>
     * 动态路由模型消费者2 admin:89724664629166080<br>
     * http://localhost:8080/dynamicRoute?key=admin.user<br>
     * 动态路由模型消费者1 admin.user:89724676895408129<br>
     * http://localhost:8080/dynamicRoute?key=admin.user.root<br>
     * 无<br>
     * http://localhost:8080/dynamicRoute?key=root<br>
     * 动态路由模型消费者1 root:89724693238513664<br>
     * http://localhost:8080/dynamicRoute?key=root.user<br>
     * 动态路由模型消费者1 root.user:89724701986783233<br>
     * 动态路由模型消费者2 root.user:89724701986783233<br>
     * http://localhost:8080/dynamicRoute?key=root.user.admin<br>
     * 动态路由模型消费者1 root.user.admin:89724710057672704
     */
    @GetMapping("dynamicRoute")
    public Result dynamicRoute(String key) {
        rabbitTemp.send(RabbitExchange.DYNAMIC_ROUTE, key, key + ":" + Id.next());
        return Result.o();
    }

    /**
     * <h3>ProtocolBuffers1</h3>
     * http://localhost:8080/protocolBuffers1<br>
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
        PersonProto.Person person = PersonProto.Person.newBuilder()
                .setName("ali")
                .setYear(1998)
                .setGender(true)
                .setDate(Clock.now())
                .setAddress("address")
                .addCars(0, PersonProto.Car.newBuilder()
                        .setName("Car")
                        .setColor("Red")
                        .build()
                )
                .putOther("描述", "暂无")
                .build();
        // 编码成bytes
        byte[] bytes = person.toByteArray();
        // rabbit发送
        rabbitTemp.send(RabbitQueue.PROTOCOL_BUFFERS1, bytes);
        return Result.o();
    }

    /**
     * <h3>ProtocolBuffers2</h3>
     * http://localhost:8080/protocolBuffers2<br>
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
        rabbitTemp.send(RabbitQueue.PROTOCOL_BUFFERS2, bytes);
        return Result.o();
    }

    /**
     * <h3>死信测试</h3>
     * http://localhost:8080/deadLetterTest?queue=deadLetterTest1&msg=-1<br>
     * 死信测试1 -1<br>
     * 消息被丢弃(报错，没有指定死信队列)<br>
     * http://localhost:8080/deadLetterTest?queue=deadLetterTest1&msg=100<br>
     * 死信测试1 100<br>
     * http://localhost:8080/deadLetterTest?queue=deadLetterTest2&msg=-1<br>
     * 死信测试2 -1<br>
     * 进入死信队列(报错)<br>
     * 死信消息 -1 属性 MessageProperties [headers={x-first-death-exchange=, x-death=[{reason=rejected, count=1, exchange=, time=Tue Sep 19 17:32:35 CST 2023, routing-keys=[deadLetterTest2], queue=deadLetterTest2}], x-first-death-reason=rejected, x-first-death-queue=deadLetterTest2}, contentType=text/plain, contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, redelivered=false, receivedExchange=, receivedRoutingKey=deadLetter, deliveryTag=1, consumerTag=amq.ctag-fdjeQ8myfJHt79xGM3jI_A, consumerQueue=deadLetter]<br>
     * http://localhost:8080/deadLetterTest?queue=deadLetterTest3&msg=test&expire=5<br>
     * 队列 deadLetterTest3 消息 test 消息过期时间 5 秒<br>
     * 5秒后进入死信队列(超出消息过期时间)<br>
     * 死信消息 test 属性 MessageProperties [headers={x-first-death-exchange=, x-death=[{reason=expired, original-expiration=5000, count=1, exchange=, time=Tue Sep 19 18:26:46 CST 2023, routing-keys=[deadLetterTest3], queue=deadLetterTest3}], x-first-death-reason=expired, x-first-death-queue=deadLetterTest3}, contentType=text/plain, contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, redelivered=false, receivedExchange=, receivedRoutingKey=deadLetter, deliveryTag=2, consumerTag=amq.ctag-_Z7gd1UKV4jO7XAsnMGahQ, consumerQueue=deadLetter]<br>
     * 队列 deadLetterTest3 消息 test 消息过期时间 15 秒<br>
     * 10秒后进入死信队列(超出队列消息过期时间)<br>
     * 死信消息 test 属性 MessageProperties [headers={x-first-death-exchange=, x-death=[{reason=expired, original-expiration=15000, count=1, exchange=, time=Tue Sep 19 18:27:32 CST 2023, routing-keys=[deadLetterTest3], queue=deadLetterTest3}], x-first-death-reason=expired, x-first-death-queue=deadLetterTest3}, contentType=text/plain, contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, redelivered=false, receivedExchange=, receivedRoutingKey=deadLetter, deliveryTag=3, consumerTag=amq.ctag-_Z7gd1UKV4jO7XAsnMGahQ, consumerQueue=deadLetter]<br>
     * http://localhost:8080/deadLetterTest?queue=deadLetterTest3&msg=a<br>
     * x6<br>
     * 第6条进入死信队列(超出最大队列长度)<br>
     * 死信消息 a 属性 MessageProperties [headers={x-first-death-exchange=, x-death=[{reason=maxlen, count=1, exchange=, time=Wed Sep 20 10:41:12 CST 2023, routing-keys=[deadLetterTest3], queue=deadLetterTest3}], x-first-death-reason=maxlen, x-first-death-queue=deadLetterTest3}, contentType=text/plain, contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, redelivered=false, receivedExchange=, receivedRoutingKey=deadLetter, deliveryTag=8, consumerTag=amq.ctag-ZVBkGWyAnlFuH-3nNpyixg, consumerQueue=deadLetter]<br>
     * http://localhost:8080/deadLetterTest?queue=deadLetterTest3&msg=test<br>
     * x3<br>
     * 第3条进入死信队列(超出最大总数据长度)<br>
     * 死信消息 test 属性 MessageProperties [headers={x-first-death-exchange=, x-death=[{reason=maxlen, count=1, exchange=, time=Wed Sep 20 11:17:54 CST 2023, routing-keys=[deadLetterTest3], queue=deadLetterTest3}], x-first-death-reason=maxlen, x-first-death-queue=deadLetterTest3}, contentType=text/plain, contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, redelivered=false, receivedExchange=, receivedRoutingKey=deadLetter, deliveryTag=5, consumerTag=amq.ctag-AZpDylQKucTRtyvvpuGydA, consumerQueue=deadLetter]<br>
     * http://localhost:8080/deadLetterTest?queue=deadLetterTest4&msg=-1<br>
     * 死信测试4(线程池模拟死信) -1<br>
     * 死信消息 -1 属性 MessageProperties [headers={}, contentType=application/x-java-serialized-object, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, redelivered=false, receivedExchange=, receivedRoutingKey=deadLetter, deliveryTag=1, consumerTag=amq.ctag-0ViY3hb_0-FisGaFsgSHTg, consumerQueue=deadLetter]<br>
     */
    @GetMapping("deadLetterTest")
    public Result deadLetterTest(String queue, String msg, Long expire) {
        if (expire == null) {
            log.info("队列 {} 消息 {}", queue, msg);
            rabbitTemp.send(queue, msg);
        } else {
            log.info("队列 {} 消息 {} 消息过期时间 {} 秒", queue, msg, expire);
            rabbitTemp.send(queue, msg, (long) expire);
        }
        return Result.o();
    }

    /**
     * <h3>测试</h3>
     * http://localhost:8080/test<br>
     * 测试 ID 90261031736049664
     */
    @GetMapping("test")
    public Result test() {
        rabbitTemp.send(RabbitQueue.TEST, "ID " + Id.next());
        return Result.o();
    }

}
