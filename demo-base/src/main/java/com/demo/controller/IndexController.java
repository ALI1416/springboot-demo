package com.demo.controller;

import com.demo.base.ControllerBase;
import com.demo.constant.ResultCode;
import com.demo.entity.po.User;
import com.demo.entity.pojo.GlobalException;
import com.demo.entity.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
public class IndexController extends ControllerBase {

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080<br>
     * GET http://localhost:8080/<br>
     * 结果 {"code":0,"message":"成功","ok":true}
     * <h3>页面未找到</h3>
     * GET http://localhost:8080/abc<br>
     * POST http://localhost:8080/abc<br>
     * 结果 {"code":110,"message":"页面未找到","ok":false}
     * <h3>请求方法不支持</h3>
     * POST http://localhost:8080/<br>
     * 结果 {"code":120,"message":"请求方法不支持","ok":false}
     */
    @GetMapping
    // @GetMapping("")
    // @GetMapping("/")
    // @GetMapping(value = {"", "/"})
    public Result index() {
        return Result.o();
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/intTest?n=1<br>
     * 结果 {"code":0,"data":1,"message":"成功","ok":true}
     * <h3>请求参数错误</h3>
     * <h4>IllegalStateException(拆箱类型不能为null)</h4>
     * GET http://localhost:8080/intTest<br>
     * GET http://localhost:8080/intTest?<br>
     * <h4>MethodArgumentTypeMismatchException(为空字符串、超出int取值范围、无法将String转为int)</h4>
     * GET http://localhost:8080/intTest?n<br>
     * GET http://localhost:8080/intTest?n=<br>
     * GET http://localhost:8080/intTest?n=123456789012<br>
     * GET http://localhost:8080/intTest?n=abc<br>
     * 结果 {"code":130,"message":"请求参数错误","ok":false}
     */
    @GetMapping("intTest")
    public Result<Integer> intTest(int n) {
        return Result.o(n);
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/stringTest?s=abc<br>
     * 结果 {"code":0,"data":"abc","message":"成功","ok":true}
     * <h3>正常请求(装箱类型可以为null)</h3>
     * GET http://localhost:8080/stringTest<br>
     * GET http://localhost:8080/stringTest?<br>
     * 结果 {"code":0,"message":"成功","ok":true}
     * <h3>正常请求(String可以接收空字符串)</h3>
     * GET http://localhost:8080/stringTest?s<br>
     * GET http://localhost:8080/stringTest?s=<br>
     * 结果 {"code":0,"data":"","message":"成功","ok":true}
     * <h3>正常请求(String可以接收emoji表情)</h3>
     * GET http://localhost:8080/stringTest?s=😊<br>
     * 结果 {"code":0,"data":"😊","message":"成功","ok":true}
     */
    @GetMapping("stringTest")
    public Result<String> stringTest(String s) {
        return Result.o(s);
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/dateTest?d=2021/01/01 01:01:01<br>
     * 结果 {"code":0,"data":"2021-01-01 01:01:01","message":"成功","ok":true}
     * GET http://localhost:8080/dateTest?d=2021/01/01<br>
     * 结果 {"code":0,"data":"2021-01-01 00:00:00","message":"成功","ok":true}
     * <h3>正常请求(无参)</h3>
     * GET http://localhost:8080/dateTest<br>
     * GET http://localhost:8080/dateTest?<br>
     * 结果 {"code":0,"message":"成功","ok":true}
     * <h3>请求参数错误</h3>
     * <h4>MethodArgumentTypeMismatchException(不能为空字符串、格式错误)</h4>
     * GET http://localhost:8080/dateTest?d<br>
     * GET http://localhost:8080/dateTest?d=<br>
     * GET http://localhost:8080/dateTest?d=2021-01-01 01:01:01<br>
     * 结果 {"code":130,"message":"请求参数错误","ok":false}
     */
    @GetMapping("dateTest")
    public Result<Date> dateTest(Date d) {
        return Result.o(d);
    }

    /**
     * <h3>正常请求(无参)</h3>
     * GET http://localhost:8080/userTest<br>
     * GET http://localhost:8080/userTest?<br>
     * GET http://localhost:8080/userTest?u<br>
     * GET http://localhost:8080/userTest?u=<br>
     * GET http://localhost:8080/userTest?u=a<br>
     * 结果 {"code":0,"data":{"year":0},"message":"成功","ok":true}
     * <h3>正常请求(部分参数)</h3>
     * GET http://localhost:8080/userTest?account=ck<br>
     * 结果 {"code":0,"data":{"account":"ck","year":0},"message":"成功","ok":true}
     * <h3>请求参数错误</h3>
     * <h4>BindException(对象的属性类型错误)</h4>
     * GET http://localhost:8080/userTest?account=ck&year=aaa<br>
     * 结果 {"code":130,"message":"请求参数错误","ok":false}
     */
    @GetMapping("userTest")
    public Result<User> userTest(User u) {
        return Result.o(u);
    }

    /**
     * <h3>正常请求(空JSON)</h3>
     * POST http://localhost:8080/userTest2 JSON {}<br>
     * 结果 {"code":0,"data":{"year":0},"message":"成功","ok":true}
     * <h3>正常请求(date类型格式自动探测)</h3>
     * POST http://localhost:8080/userTest2 JSON {"date":"2021-01-01 01:01:01"}<br>
     * POST http://localhost:8080/userTest2 JSON {"date":"2021/01/01 01:01:01"}<br>
     * 结果 {"code":0,"data":{"date":"2021-01-01 01:01:01","year":0},"message":"成功","ok":true}
     * POST http://localhost:8080/userTest2 JSON {"date":"2021-01-01"}<br>
     * POST http://localhost:8080/userTest2 JSON {"date":"2021/01/01"}<br>
     * 结果 {"code":0,"data":{"date":"2021-01-01 00:00:00","year":0},"message":"成功","ok":true}
     * <h3>媒体类型不支持</h3>
     * POST http://localhost:8080/userTest2 form-data account:abc<br>
     * 结果 {"code":121,"message":"媒体类型不支持","ok":false}
     * <h3>请求参数错误</h3>
     * <h4>HttpMessageNotReadableException(无参、不是JSON格式、参数类型错误)</h4>
     * POST http://localhost:8080/userTest2<br>
     * POST http://localhost:8080/userTest2 JSON "year":"aaa"<br>
     * POST http://localhost:8080/userTest2 JSON {"year":"aaa"}<br>
     * 结果 {"code":130,"message":"请求参数错误","ok":false}
     */
    @PostMapping("userTest2")
    public Result<User> userTest2(@RequestBody User u) {
        return Result.o(u);
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/fileTest form-data file 文件<br>
     * 结果 {"code":0,"data":"1536","message":"成功","ok":true}
     */
    @GetMapping("fileTest")
    public Result<Long> fileTest(MultipartFile file) {
        return Result.o(file.getSize());
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/filesTest form-data files 文件1 files 文件2<br>
     * 结果 {"code":0,"data":"2","message":"成功","ok":true}
     */
    @GetMapping("filesTest")
    public Result<Integer> filesTest(MultipartFile[] files) {
        return Result.o(files.length);
    }

    /**
     * <h3>全局异常</h3>
     * GET http://localhost:8080/globalException<br>
     * 结果 {"code":-1,"message":"未知错误","ok":false}
     */
    @GetMapping("globalException")
    public Result globalException() {
        throw new GlobalException();
    }

    /**
     * <h3>全局异常2</h3>
     * GET http://localhost:8080/globalException2<br>
     * 结果 {"code":-1,"data":"全局异常2","message":"未知错误","ok":false}
     */
    @GetMapping("globalException2")
    public Result globalException2() {
        throw new GlobalException("全局异常2");
    }

    /**
     * <h3>全局异常3</h3>
     * GET http://localhost:8080/globalException3<br>
     * 结果 {"code":1000,"data":"账号不存在或密码错误","message":"账号不存在或密码错误","ok":false}
     */
    @GetMapping("globalException3")
    public Result globalException3() {
        throw new GlobalException(ResultCode.LOGIN_ERROR);
    }

    /**
     * <h3>全局异常4</h3>
     * GET http://localhost:8080/globalException4<br>
     * 结果 {"code":1000,"data":"全局异常4","message":"账号不存在或密码错误","ok":false}
     */
    @GetMapping("globalException4")
    public Result globalException4() {
        throw new GlobalException(ResultCode.LOGIN_ERROR, "全局异常4");
    }

    /**
     * <h3>运行时异常</h3>
     * GET http://localhost:8080/runtimeException<br>
     * 结果 {"code":100,"message":"系统内部错误","ok":false}
     */
    @GetMapping("runtimeException")
    public Result runtimeException() {
        int a = 100 / 0;
        return Result.o();
    }

    /**
     * <h3>IO异常</h3>
     * GET http://localhost:8080/ioException<br>
     * 结果 {"code":100,"message":"系统内部错误","ok":false}
     */
    @GetMapping("ioException")
    public Result ioException() throws IOException {
        new File("A:/1.txt").createNewFile();
        return Result.o();
    }

    /**
     * <h3>异常</h3>
     * GET http://localhost:8080/exception<br>
     * 结果 {"code":100,"message":"系统内部错误","ok":false}
     */
    @GetMapping("exception")
    public Result exception() throws Exception {
        throw new Exception("异常");
    }

}
