package com.demo.controller;

import com.demo.constant.ResultEnum;
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
public class IndexController {

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080<br>
     * GET http://localhost:8080/<br>
     * GET http://localhost:8080/index<br>
     * 结果 {"code":0,"msg":"成功","ok":true}
     * <h3>请求地址找不到</h3>
     * POST http://localhost:8080/e<br>
     * 结果 {"code":101,"msg":"请求地址找不到","ok":false}
     * <h3>请求方法不支持</h3>
     * POST http://localhost:8080/index<br>
     * 结果 {"code":110,"msg":"请求方法不支持","ok":false}
     */
    @GetMapping(value = {"", "/", "index"})
    public Result index() {
        return Result.o();
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/intTest?n=1<br>
     * 结果 {"code":0,"data":1,"msg":"成功","ok":true}
     * <h3>请求参数错误</h3>
     * <h4>IllegalStateException(拆箱类型不能为null)</h4>
     * GET http://localhost:8080/intTest<br>
     * <h4>MethodArgumentTypeMismatchException(为空字符串、超出int取值范围、无法将String转为int)</h4>
     * GET http://localhost:8080/intTest?n=<br>
     * GET http://localhost:8080/intTest?n=123456789012<br>
     * GET http://localhost:8080/intTest?n=abc<br>
     * 结果 {"code":120,"msg":"请求参数错误","ok":false}
     */
    @GetMapping("intTest")
    public Result<Integer> intTest(int n) {
        return Result.o(n);
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/stringTest?s=abc<br>
     * 结果 {"code":0,"data":"abc","msg":"成功","ok":true}
     * <h3>正常请求(装箱类型可以为null)</h3>
     * GET http://localhost:8080/stringTest<br>
     * 结果 {"code":0,"msg":"成功","ok":true}
     * <h3>正常请求(String可以接收空字符串)</h3>
     * GET http://localhost:8080/stringTest?s=<br>
     * 结果 {"code":0,"data":"","msg":"成功","ok":true}
     * <h3>正常请求(String可以接收emoji表情)</h3>
     * GET http://localhost:8080/stringTest?s=😊<br>
     * 结果 {"code":0,"data":"😊","msg":"成功","ok":true}
     */
    @GetMapping("stringTest")
    public Result<String> stringTest(String s) {
        return Result.o(s);
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/dateTest?d=2021/01/01 01:01:01<br>
     * 结果 {"code":0,"data":"2021-01-01 01:01:01","msg":"成功","ok":true}
     * GET http://localhost:8080/dateTest?d=2021/01/01<br>
     * 结果 {"code":0,"data":"2021-01-01 00:00:00","msg":"成功","ok":true}
     * <h3>请求参数错误</h3>
     * <h4>MethodArgumentTypeMismatchException(不能为null、格式错误)</h4>
     * GET http://localhost:8080/dateTest?d=<br>
     * GET http://localhost:8080/dateTest?d=2021-01-01 01:01:01<br>
     * 结果 {"code":103,"msg":"请求参数错误","ok":false}
     */
    @GetMapping("dateTest")
    public Result<Date> dateTest(Date d) {
        return Result.o(d);
    }

    /**
     * <h3>正常请求(无参)</h3>
     * GET http://localhost:8080/userTest<br>
     * 结果 {"code":0,"data":{"year":0},"msg":"成功","ok":true}
     * <h3>正常请求(部分参数)</h3>
     * GET http://localhost:8080/userTest?account=ck<br>
     * 结果 {"code":0,"data":{"account":"ck","year":0},"msg":"成功","ok":true}
     * <h3>请求参数错误</h3>
     * <h4>BindException(对象的属性类型错误)</h4>
     * GET http://localhost:8080/userTest?account=ck&year=aaa<br>
     * 结果 {"code":110,"msg":"请求参数错误","ok":false}
     */
    @GetMapping("userTest")
    public Result<User> userTest(User u) {
        return Result.o(u);
    }

    /**
     * <h3>正常请求(空JSON)</h3>
     * POST http://localhost:8080/userTest2 JSON {}<br>
     * 结果 {"code":0,"data":{"year":0},"msg":"成功","ok":true}
     * <h3>正常请求(date类型格式自动探测)</h3>
     * POST http://localhost:8080/userTest2 JSON {"date":"2021-01-01 01:01:01"}<br>
     * POST http://localhost:8080/userTest2 JSON {"date":"2021/01/01 01:01:01"}<br>
     * 结果 {"code":0,"data":{"date":"2021-01-01 01:01:01","year":0},"msg":"成功","ok":true}
     * POST http://localhost:8080/userTest2 JSON {"date":"2021/01/01"}<br>
     * 结果 {"code":0,"data":{"date":"2021-01-01 00:00:00","year":0},"msg":"成功","ok":true}
     * <h3>媒体类型不支持</h3>
     * POST http://localhost:8080/userTest2 XML &lt;year>aaa&lt;/year><br>
     * 结果 {"code":111,"msg":"媒体类型不支持","ok":false}
     * <h3>请求参数错误</h3>
     * <h4>HttpMessageNotReadableException(无参、不是JSON格式、参数类型错误)</h4>
     * POST http://localhost:8080/userTest2 JSON<br>
     * POST http://localhost:8080/userTest2 JSON "year":"aaa"<br>
     * POST http://localhost:8080/userTest2 JSON {"year":"aaa"}<br>
     * 结果 {"code":120,"msg":"请求参数错误","ok":false}
     */
    @PostMapping("userTest2")
    public Result<User> userTest2(@RequestBody User u) {
        return Result.o(u);
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/fileTest form-data file 文件<br>
     * 结果 {"code":0,"data":"1536","msg":"成功","ok":true}
     */
    @GetMapping("fileTest")
    public Result<Long> fileTest(MultipartFile file) {
        return Result.o(file.getSize());
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/filesTest form-data files 文件1 files 文件2<br>
     * 结果 {"code":0,"data":"2","msg":"成功","ok":true}
     */
    @GetMapping("filesTest")
    public Result<Integer> filesTest(MultipartFile[] files) {
        return Result.o(files.length);
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/runtimeException?n=11<br>
     * 结果 {"code":0,"data":9,"msg":"成功","ok":true}
     * <h3>除0异常</h3>
     * GET http://localhost:8080/runtimeException?n=0<br>
     * 结果 {"code":100,"data":"RuntimeException","msg":"系统内部错误","ok":false}
     */
    @GetMapping("runtimeException")
    public Result<Integer> runtimeException(int n) {
        return Result.o(100 / n);
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/ioException?path=D:\1.txt<br>
     * 结果 {"code":0,"data":true,"msg":"成功","ok":true}
     * <h3>拒绝访问异常</h3>
     * GET http://localhost:8080/ioException?path=C:\1.txt<br>
     * 结果 {"code":100,"data":"IOException","msg":"系统内部错误","ok":false}
     */
    @GetMapping("ioException")
    public Result<Boolean> ioException(String path) throws IOException {
        return Result.o(new File(path).createNewFile());
    }

    /**
     * <h3>正常请求</h3>
     * GET http://localhost:8080/exception?enable=false<br>
     * 结果 {"code":0,"data":false,"msg":"成功","ok":true}
     * <h3>自定义异常</h3>
     * GET http://localhost:8080/exception?enable=true<br>
     * 结果 {"code":100,"data":"Exception","msg":"系统内部错误","ok":false}
     */
    @GetMapping("exception")
    public Result<Boolean> exception(boolean enable) {
        if (enable) {
            throw new GlobalException(ResultEnum.SYSTEM_INNER_ERROR);
        }
        return Result.o(false);
    }

}
