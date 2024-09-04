package com.demo.controller;

import cn.z.influx.InfluxTemp;
import com.demo.entity.pojo.Result;
import com.influxdb.client.domain.Bucket;
import com.influxdb.client.domain.Organization;
import com.influxdb.client.domain.SecretKeysResponse;
import com.influxdb.client.domain.User;
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
 * createDate 2023/11/04 16:42:19
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@AllArgsConstructor
public class IndexController {

    private final InfluxTemp influxTemp;

    /**
     * 获取所有用户<br>
     * http://localhost:8080/userAll
     */
    @GetMapping("userAll")
    public Result<List<User>> userAll() {
        return Result.o(influxTemp.userAll());
    }

    /**
     * 获取当前用户<br>
     * http://localhost:8080/user
     */
    @GetMapping("user")
    public Result<User> user() {
        return Result.o(influxTemp.user());
    }

    /**
     * 获取用户通过ID<br>
     * http://localhost:8080/userGetById?userId=0d9ab19e3a021000
     */
    @GetMapping("userGetById")
    public Result<User> userGetById(String userId) {
        return Result.o(influxTemp.userGetById(userId));
    }

    /**
     * 获取用户通过ID<br>
     * http://localhost:8080/userCreateByName?userName=test
     */
    @GetMapping("userCreateByName")
    public Result<User> userCreateByName(String userName) {
        return Result.o(influxTemp.userCreateByName(userName));
    }

    /**
     * 更新用户<br>
     * http://localhost:8080/userUpdate?userId=0d9afca2af821000
     */
    @GetMapping("userUpdate")
    public Result<User> userUpdate(String userId) {
        User user = influxTemp.userGetById(userId);
        user.setName("aaa");
        user.setStatus(User.StatusEnum.ACTIVE);
        return Result.o(influxTemp.userUpdate(user));
    }

    /**
     * 更新当前用户密码<br>
     * http://localhost:8080/userUpdatePassword?oldPassword=influxdb&newPassword=12345678
     */
    @GetMapping("userUpdatePassword")
    public Result userUpdatePassword(String oldPassword, String newPassword) {
        influxTemp.userUpdatePassword(oldPassword, newPassword);
        return Result.o();
    }

    /**
     * 更新用户密码通过用户ID<br>
     * http://localhost:8080/userUpdatePassword?userId=0d9afca2af821000&oldPassword=inflexdb&newPassword=12345678
     */
    @GetMapping("userUpdatePasswordById")
    public Result userUpdatePasswordById(String userId, String oldPassword, String newPassword) {
        influxTemp.userUpdatePasswordById(userId, oldPassword, newPassword);
        return Result.o();
    }

    /**
     * 获取所有组织<br>
     * http://localhost:8080/orgAll
     */
    @GetMapping("orgAll")
    public Result<List<Organization>> orgAll() {
        return Result.o(influxTemp.orgAll());
    }

    /**
     * 通过组织ID获取组织<br>
     * http://localhost:8080/orgGetById?orgId=16e124d328e290a6
     */
    @GetMapping("orgGetById")
    public Result<Organization> orgGetById(String orgId) {
        return Result.o(influxTemp.orgGetById(orgId));
    }

    /**
     * 通过组织名获取组织<br>
     * http://localhost:8080/orgGetByName?orgName=influx
     */
    @GetMapping("orgGetByName")
    public Result<Organization> orgGetByName(String orgName) {
        return Result.o(influxTemp.orgGetByName(orgName));
    }

    /**
     * 通过组织名创建组织<br>
     * http://localhost:8080/orgCreateByName?orgName=test
     */
    @GetMapping("orgCreateByName")
    public Result<Organization> orgCreateByName(String orgName) {
        return Result.o(influxTemp.orgCreateByName(orgName));
    }

    /**
     * 更新组织<br>
     * http://localhost:8080/orgUpdate?orgName=test
     */
    @GetMapping("orgUpdate")
    public Result<Organization> orgUpdate(String orgName) {
        Organization organization = influxTemp.orgGetByName(orgName);
        organization.description("测试");
        return Result.o(influxTemp.orgUpdate(organization));
    }

    /**
     * 通过组织ID删除组织<br>
     * http://localhost:8080/orgDeleteById?orgId=574b96e46cc37d04
     */
    @GetMapping("orgDeleteById")
    public Result orgDeleteById(String orgId) {
        influxTemp.orgDeleteById(orgId);
        return Result.o();
    }

    /**
     * 通过组织名删除组织<br>
     * http://localhost:8080/orgDeleteByName?orgName=test
     */
    @GetMapping("orgDeleteByName")
    public Result orgDeleteByName(String orgName) {
        influxTemp.orgDeleteByName(orgName);
        return Result.o();
    }

    /**
     * 通过组织ID克隆组织<br>
     * http://localhost:8080/orgCloneById?orgName=test&cloneOrgId=574b96e46cc37d04
     */
    @GetMapping("orgCloneById")
    public Result<Organization> orgCloneById(String orgName, String cloneOrgId) {
        return Result.o(influxTemp.orgCloneById(orgName, cloneOrgId));
    }

    /**
     * 通过组织名克隆组织<br>
     * http://localhost:8080/orgCloneByName?orgName=test&cloneOrgName=aaa
     */
    @GetMapping("orgCloneByName")
    public Result<Organization> orgCloneByName(String orgName, String cloneOrgName) {
        return Result.o(influxTemp.orgCloneByName(orgName, cloneOrgName));
    }

    /**
     * 通过组织ID获取组织密钥<br>
     * http://localhost:8080/orgSecretGetById?orgId=16e124d328e290a6
     */
    @GetMapping("orgSecretGetById")
    public Result<SecretKeysResponse> orgSecretGetById(String orgId) {
        return Result.o(influxTemp.orgSecretGetById(orgId));
    }

    /**
     * 通过组织名获取组织密钥<br>
     * http://localhost:8080/orgSecretGetByName?orgName=influx
     */
    @GetMapping("orgSecretGetByName")
    public Result<SecretKeysResponse> orgSecretGetByName(String orgName) {
        return Result.o(influxTemp.orgSecretGetByName(orgName));
    }

    /**
     * 通过组织ID添加组织密钥<br>
     * http://localhost:8080/orgSecretAddById?orgId=16e124d328e290a6&key=a&value=b
     */
    @GetMapping("orgSecretAddById")
    public Result orgSecretAddById(String orgId, String key, String value) {
        Map<String, String> secrets = new HashMap<>(1);
        secrets.put(key, value);
        influxTemp.orgSecretAddById(orgId, secrets);
        return Result.o();
    }

    /**
     * 通过组织名添加组织密钥<br>
     * http://localhost:8080/orgSecretAddByName?orgName=test&key=a&value=b
     */
    @GetMapping("orgSecretAddByName")
    public Result orgSecretAddByName(String orgName, String key, String value) {
        Map<String, String> secrets = new HashMap<>(1);
        secrets.put(key, value);
        influxTemp.orgSecretAddByName(orgName, secrets);
        return Result.o();
    }

    /**
     * 通过组织ID删除组织密钥<br>
     * http://localhost:8080/orgSecretDeleteById?orgId=16e124d328e290a6&key=a
     */
    @GetMapping("orgSecretDeleteById")
    public Result orgSecretDeleteById(String orgId, String key) {
        List<String> secrets = new ArrayList<>(1);
        secrets.add(key);
        influxTemp.orgSecretDeleteById(orgId, secrets);
        return Result.o();
    }

    /**
     * 通过组织名删除组织密钥<br>
     * http://localhost:8080/orgSecretDeleteByName?orgName=test&key=a
     */
    @GetMapping("orgSecretDeleteByName")
    public Result orgSecretDeleteByName(String orgName, String key) {
        List<String> secrets = new ArrayList<>(1);
        secrets.add(key);
        influxTemp.orgSecretDeleteByName(orgName, secrets);
        return Result.o();
    }


    /**
     * 获取所有储存桶<br>
     * http://localhost:8080/bucketAll
     */
    @GetMapping("bucketAll")
    public Result<List<Bucket>> bucketAll() {
        return Result.o(influxTemp.bucketAll());
    }

    /**
     * 创建储存桶<br>
     * http://localhost:8080/bucketCreate?org=a&bucket=test
     */
    @GetMapping("bucketCreate")
    public Result<Bucket> bucketCreate(String org, String bucket) {
        return Result.o(influxTemp.bucketCreate(org, bucket));
    }

}
