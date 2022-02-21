package com.demo.controller;

import com.demo.entity.po.User;
import com.demo.entity.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class IndexController {

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080<br>
     * GET http://localhost:8080/<br>
     * GET http://localhost:8080/index<br>
     * 结果 {"code":"0","msg":"成功","ok":"true"}
     * <h3>请求方法不支持</h3>
     * POST http://localhost:8080/index<br>
     * 结果 {"code":"101","msg":"请求方法不支持","ok":"false"}
     * <h3>请求地址找不到</h3>
     * POST http://localhost:8080/e<br>
     * 结果 {"code":"102","msg":"请求地址找不到","ok":"false"}
     */
    @GetMapping(value = {"", "/", "index"})
    public Result index() {
        return Result.o();
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/intTest?n=1<br>
     * 结果 {"code":"0","data":"1","msg":"成功","ok":"true"}
     * <h3>IllegalStateException(拆箱类型不能为null)</h3>
     * GET http://localhost:8080/intTest<br>
     * 结果 {"code":"103","msg":"请求参数错误","ok":"false"}
     * <h3>MethodArgumentTypeMismatchException(无法将String转为int)</h3>
     * GET http://localhost:8080/intTest?n=123456789012<br>
     * GET http://localhost:8080/intTest?n=abc<br>
     * 结果 {"code":"103","msg":"请求参数错误","ok":"false"}
     */
    @GetMapping("intTest")
    public Result intTest(int n) {
        return Result.o(n);
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/stringTest?s=abc<br>
     * 结果 {"code":"0","msg":"abc","ok":"true"}
     * <h3>正常请求(装箱类型可以为null)</h3>
     * GET http://localhost:8080/stringTest?s=<br>
     * 结果 {"code":"0","msg":"","ok":"true"}
     * <h3>正常请求(装箱类型可以为null)</h3>
     * GET http://localhost:8080/stringTest<br>
     * 结果 {"code":"0","ok":"true"}
     * <h3>正常请求(String可以接收emoji)</h3>
     * GET http://localhost:8080/stringTest?s=😊<br>
     * 结果 {"code":"0","msg":"😊","ok":"true"}
     */
    @GetMapping("stringTest")
    public Result stringTest(String s) {
        return Result.o(s);
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/dateTest?d=2021/01/01 01:01:01<br>
     * 结果 {"code":"0","data":"2021-01-01 01:01:01","msg":"成功","ok":"true"}
     * GET http://localhost:8080/dateTest?d=2021/01/01<br>
     * 结果 {"code":"0","data":"2021-01-01 00:00:00","msg":"成功","ok":"true"}
     * <h3>MethodArgumentTypeMismatchException(无法将String转为Date,转换器配置无效)</h3>
     * GET http://localhost:8080/dateTest?d=<br>
     * GET http://localhost:8080/dateTest?d=2021-01-01 01:01:01<br>
     * 结果 {"code":"103","msg":"请求参数错误","ok":"false"}
     */
    @GetMapping("dateTest")
    public Result dateTest(Date d) {
        return Result.o(d);
    }

    /**
     * <h3>正常请求(无参)</h3>
     * GET http://localhost:8080/userTest<br>
     * 结果 {"code":"0","data":{"year":"0"},"msg":"成功","ok":"true"}
     * <h3>正常请求(部分参数)</h3>
     * GET http://localhost:8080/userTest?account=ck<br>
     * 结果 {"code":"0","data":{"account":"ck","year":"0"},"msg":"成功","ok":"true"}
     * <h3>BindException(参数类型错误)</h3>
     * GET http://localhost:8080/userTest?account=ck&year=aaa<br>
     * 结果 {"code":"103","msg":"请求参数错误","ok":"false"}
     */
    @GetMapping("userTest")
    public Result userTest(User u) {
        return Result.o(u);
    }

    /**
     * <h3>正常请求(空JSON)</h3>
     * POST http://localhost:8080/userTest2 {}<br>
     * <h3>正常请求(date类型无限制)</h3>
     * POST http://localhost:8080/userTest2 {"date":"2021-01-01 01:01:01"}<br>
     * POST http://localhost:8080/userTest2 {"date":"2021/01/01 01:01:01"}<br>
     * 结果 {"code":"0","data":{"date":"2021-01-01 01:01:01","year":"0"},"msg":"成功","ok":"true"}
     * POST http://localhost:8080/userTest2 {"date":"2021/01/01"}<br>
     * 结果 {"code":"0","data":{"date":"2021-01-01 00:00:00","year":"0"},"msg":"成功","ok":"true"}
     * <h3>HttpMessageNotReadableException(无参、不是JSON格式、参数类型错误)</h3>
     * POST http://localhost:8080/userTest2<br>
     * POST http://localhost:8080/userTest "year":"aaa"<br>
     * POST http://localhost:8080/userTest {"year":"aaa"}<br>
     * 结果 {"code":"103","msg":"请求参数错误","ok":"false"}
     */
    @PostMapping("userTest2")
    public Result userTest2(@RequestBody User u) {
        return Result.o(u);
    }

}
