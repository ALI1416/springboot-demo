package com.demo.controller;

import cn.z.influx.InfluxTemp;
import com.demo.entity.influx.TestClass;
import com.demo.entity.pojo.Result;
import com.influxdb.client.domain.*;
import com.influxdb.client.write.Point;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
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

    /* ==================== 用户操作 ==================== */
    // region 用户操作

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
     * 获取用户通过用户名<br>
     * http://localhost:8080/userGetByName?userName=root
     */
    @GetMapping("userGetByName")
    public Result<User> userGetByName(String userName) {
        return Result.o(influxTemp.userGetByName(userName));
    }

    /**
     * 创建用户通过用户名<br>
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
     * 重置用户密码通过用户ID<br>
     * http://localhost:8080/userResetPasswordById?userId=0d9afca2af821000&newPassword=12345678
     */
    @GetMapping("userResetPasswordById")
    public Result userResetPasswordById(String userId, String newPassword) {
        influxTemp.userResetPasswordById(userId, newPassword);
        return Result.o();
    }

    /**
     * 重置用户密码通过用户名<br>
     * http://localhost:8080/userResetPasswordByName?userName=test&newPassword=12345678
     */
    @GetMapping("userResetPasswordByName")
    public Result userResetPasswordByName(String userName, String newPassword) {
        influxTemp.userResetPasswordByName(userName, newPassword);
        return Result.o();
    }

    /**
     * 删除用户通过用户ID<br>
     * http://localhost:8080/userDeleteById?userId=0d9ab19e3a021000
     */
    @GetMapping("userDeleteById")
    public Result userDeleteById(String userId) {
        influxTemp.userDeleteById(userId);
        return Result.o();
    }

    /**
     * 删除用户通过用户名<br>
     * http://localhost:8080/userDeleteByName?userName=test
     */
    @GetMapping("userDeleteByName")
    public Result userDeleteByName(String userName) {
        influxTemp.userDeleteByName(userName);
        return Result.o();
    }

    /**
     * 克隆用户通过用户ID<br>
     * http://localhost:8080/userCloneById?userName=test&cloneUserId=0d9ab19e3a021000
     */
    @GetMapping("userCloneById")
    public Result<User> userCloneById(String userName, String cloneUserId) {
        return Result.o(influxTemp.userCloneById(userName, cloneUserId));
    }

    /**
     * 克隆用户通过用户名<br>
     * http://localhost:8080/userCloneByName?userName=test&cloneUserName=root
     */
    @GetMapping("userCloneByName")
    public Result<User> userCloneByName(String userName, String cloneUserName) {
        return Result.o(influxTemp.userCloneByName(userName, cloneUserName));
    }

    // endregion

    /* ==================== 组织基本操作 ==================== */
    // region 组织基本操作

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

    // endregion

    /* ==================== 组织密钥操作 ==================== */
    // region 组织密钥操作

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

    // endregion

    /* ==================== 组织成员操作 ==================== */
    // region 组织成员操作

    /**
     * 通过组织ID获取成员<br>
     * http://localhost:8080/orgMemberGetById?orgId=16e124d328e290a6
     */
    @GetMapping("orgMemberGetById")
    public Result<List<ResourceMember>> orgMemberGetById(String orgId) {
        return Result.o(influxTemp.orgMemberGetById(orgId));
    }

    /**
     * 通过组织名获取成员<br>
     * http://localhost:8080/orgMemberGetByName?orgName=influx
     */
    @GetMapping("orgMemberGetByName")
    public Result<List<ResourceMember>> orgMemberGetByName(String orgName) {
        return Result.o(influxTemp.orgMemberGetByName(orgName));
    }

    /**
     * 通过组织ID添加成员ID<br>
     * http://localhost:8080/orgMemberAddById?orgId=16e124d328e290a6&memberId=0d9ab19e3a021000
     */
    @GetMapping("orgMemberAddById")
    public Result<ResourceMember> orgMemberAddById(String orgId, String memberId) {
        return Result.o(influxTemp.orgMemberAddById(orgId, memberId));
    }

    /**
     * 通过组织名添加成员名<br>
     * http://localhost:8080/orgMemberAddByName?orgName=influx&memberName=test
     */
    @GetMapping("orgMemberAddByName")
    public Result<ResourceMember> orgMemberAddByName(String orgName, String memberName) {
        return Result.o(influxTemp.orgMemberAddByName(orgName, memberName));
    }

    /**
     * 通过组织ID删除成员ID<br>
     * http://localhost:8080/orgMemberDeleteById?orgId=16e124d328e290a6&memberId=0d9ab19e3a021000
     */
    @GetMapping("orgMemberDeleteById")
    public Result orgMemberDeleteById(String orgId, String memberId) {
        influxTemp.orgMemberDeleteById(orgId, memberId);
        return Result.o();
    }

    /**
     * 通过组织名删除成员名<br>
     * http://localhost:8080/orgMemberDeleteByName?orgName=influx&memberName=test
     */
    @GetMapping("orgMemberDeleteByName")
    public Result orgMemberDeleteByName(String orgName, String memberName) {
        influxTemp.orgMemberDeleteByName(orgName, memberName);
        return Result.o();
    }

    // endregion

    /* ==================== 组织所有者操作 ==================== */
    // region 组织所有者操作

    /**
     * 通过组织ID获取所有者<br>
     * http://localhost:8080/orgOwnerGetById?orgId=16e124d328e290a6
     */
    @GetMapping("orgOwnerGetById")
    public Result<List<ResourceOwner>> orgOwnerGetById(String orgId) {
        return Result.o(influxTemp.orgOwnerGetById(orgId));
    }

    /**
     * 通过组织名获取所有者<br>
     * http://localhost:8080/orgOwnerGetByName?orgName=influx
     */
    @GetMapping("orgOwnerGetByName")
    public Result<List<ResourceOwner>> orgOwnerGetByName(String orgName) {
        return Result.o(influxTemp.orgOwnerGetByName(orgName));
    }

    /**
     * 通过组织ID添加所有者ID<br>
     * http://localhost:8080/orgOwnerAddById?orgId=16e124d328e290a6&ownerId=0d9ab19e3a021000
     */
    @GetMapping("orgOwnerAddById")
    public Result<ResourceOwner> orgOwnerAddById(String orgId, String ownerId) {
        return Result.o(influxTemp.orgOwnerAddById(orgId, ownerId));
    }

    /**
     * 通过组织名添加所有者名<br>
     * http://localhost:8080/orgOwnerAddByName?orgName=influx&ownerName=test
     */
    @GetMapping("orgOwnerAddByName")
    public Result<ResourceOwner> orgOwnerAddByName(String orgName, String ownerName) {
        return Result.o(influxTemp.orgOwnerAddByName(orgName, ownerName));
    }

    /**
     * 通过组织ID删除所有者ID<br>
     * http://localhost:8080/orgOwnerDeleteById?orgId=16e124d328e290a6&ownerId=0d9ab19e3a021000
     */
    @GetMapping("orgOwnerDeleteById")
    public Result orgOwnerDeleteById(String orgId, String ownerId) {
        influxTemp.orgOwnerDeleteById(orgId, ownerId);
        return Result.o();
    }

    /**
     * 通过组织名删除所有者名<br>
     * http://localhost:8080/orgOwnerDeleteByName?orgName=influx&ownerName=test
     */
    @GetMapping("orgOwnerDeleteByName")
    public Result orgOwnerDeleteByName(String orgName, String ownerName) {
        influxTemp.orgOwnerDeleteByName(orgName, ownerName);
        return Result.o();
    }

    // endregion

    /* ==================== 储存桶基本操作 ==================== */
    // region 储存桶基本操作

    /**
     * 获取所有储存桶<br>
     * http://localhost:8080/bucketAll
     */
    @GetMapping("bucketAll")
    public Result<List<Bucket>> bucketAll() {
        return Result.o(influxTemp.bucketAll());
    }

    /**
     * 获取储存桶通过组织ID<br>
     * http://localhost:8080/bucketGetByOrgId?orgId=16e124d328e290a6
     */
    @GetMapping("bucketGetByOrgId")
    public Result<List<Bucket>> bucketGetByOrgId(String orgId) {
        return Result.o(influxTemp.bucketGetByOrgId(orgId));
    }

    /**
     * 获取储存桶通过组织名<br>
     * http://localhost:8080/bucketGetByOrgName?orgName=a
     */
    @GetMapping("bucketGetByOrgName")
    public Result<List<Bucket>> bucketGetByOrgName(String orgName) {
        return Result.o(influxTemp.bucketGetByOrgName(orgName));
    }

    /**
     * 获取储存桶通过储存桶ID<br>
     * http://localhost:8080/bucketGetById?bucketId=16e124d328e290a6
     */
    @GetMapping("bucketGetById")
    public Result<Bucket> bucketGetById(String bucketId) {
        return Result.o(influxTemp.bucketGetById(bucketId));
    }

    /**
     * 获取储存桶通过储存桶名<br>
     * http://localhost:8080/bucketGetByName?orgName=a&bucketName=test
     */
    @GetMapping("bucketGetByName")
    public Result<Bucket> bucketGetByName(String orgName, String bucketName) {
        return Result.o(influxTemp.bucketGetByName(orgName, bucketName));
    }

    /**
     * 创建储存桶通过组织ID<br>
     * http://localhost:8080/bucketCreateByOrgId?orgId=16e124d328e290a6&bucketName=test
     */
    @GetMapping("bucketCreateByOrgId")
    public Result<Bucket> bucketCreateByOrgId(String orgId, String bucketName) {
        return Result.o(influxTemp.bucketCreateByOrgId(orgId, bucketName));
    }

    /**
     * 创建储存桶通过组织名<br>
     * http://localhost:8080/bucketCreateByOrgName?orgName=a&bucketName=test
     */
    @GetMapping("bucketCreateByOrgName")
    public Result<Bucket> bucketCreateByOrgName(String orgName, String bucketName) {
        return Result.o(influxTemp.bucketCreateByOrgName(orgName, bucketName));
    }

    /**
     * 更新储存桶<br>
     * http://localhost:8080/bucketUpdate?orgName=a&bucketName=test
     */
    @GetMapping("bucketUpdate")
    public Result<Bucket> bucketUpdate(String orgName, String bucketName) {
        Bucket bucket = influxTemp.bucketGetByName(orgName, bucketName);
        bucket.description("测试123");
        return Result.o(influxTemp.bucketUpdate(bucket));
    }

    /**
     * 删除储存桶通过储存桶ID<br>
     * http://localhost:8080/bucketDeleteById?bucketId=16e124d328e290a6
     */
    @GetMapping("bucketDeleteById")
    public Result bucketDeleteById(String bucketId) {
        influxTemp.bucketDeleteById(bucketId);
        return Result.o();
    }

    /**
     * 删除储存桶通过储存桶名<br>
     * http://localhost:8080/bucketDeleteByName?orgName=a&bucketName=test
     */
    @GetMapping("bucketDeleteByName")
    public Result bucketDeleteByName(String orgName, String bucketName) {
        influxTemp.bucketDeleteByName(orgName, bucketName);
        return Result.o();
    }

    /**
     * 克隆储存桶通过储存桶ID<br>
     * http://localhost:8080/bucketCloneById?bucketName=test&cloneBucketId=16e124d328e290a6
     */
    @GetMapping("bucketCloneById")
    public Result<Bucket> bucketCloneById(String bucketName, String cloneBucketId) {
        return Result.o(influxTemp.bucketCloneById(bucketName, cloneBucketId));
    }

    /**
     * 克隆储存桶通过储存桶名<br>
     * http://localhost:8080/bucketCloneByName?orgName=a&bucketName=test&cloneBucketName=a
     */
    @GetMapping("bucketCloneByName")
    public Result<Bucket> bucketCloneByName(String orgName, String bucketName, String cloneBucketName) {
        return Result.o(influxTemp.bucketCloneByName(orgName, bucketName, cloneBucketName));
    }

    // endregion

    /* ==================== 同步写入操作 ==================== */
    // region 同步写入操作

    /**
     * 同步写入数据点<br>
     * http://localhost:8080/writeSyncPoint?measurementName=a&tagKey=b&tagValue=c&field=d&value=1
     */
    @GetMapping("writeSyncPoint")
    public Result writeSyncPoint(String measurementName, String tagKey, String tagValue, String field, long value) {
        Point point = Point.measurement(measurementName)
                .addTag(tagKey, tagValue)
                .addField(field, value)
                .time(Instant.now(), WritePrecision.MS);
        influxTemp.writeSyncPoint(point);
        return Result.o();
    }

    /**
     * 同步写入多个数据点<br>
     * http://localhost:8080/writeSyncPoints?measurementName=a&tagKey=b&tagValue=c&field=d&value=1
     */
    @GetMapping("writeSyncPoints")
    public Result writeSyncPoints(String measurementName, String tagKey, String tagValue, String field, long value) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Point point = Point.measurement(measurementName)
                    .addTag(tagKey, tagValue)
                    .addField(field, value + i)
                    .time(Instant.now().plusSeconds(i), WritePrecision.MS);
            points.add(point);
        }
        influxTemp.writeSyncPoints(points);
        return Result.o();
    }

    /**
     * 同步写入数据点通过组织名和储存桶名<br>
     * http://localhost:8080/writeSyncPointByName?orgName=influx&bucketName=db&measurementName=a&tagKey=b&tagValue=c&field=d&value=1
     */
    @GetMapping("writeSyncPointByName")
    public Result writeSyncPointByName(String orgName, String bucketName, String measurementName, String tagKey, String tagValue, String field, long value) {
        Point point = Point.measurement(measurementName)
                .addTag(tagKey, tagValue)
                .addField(field, value)
                .time(Instant.now(), WritePrecision.MS);
        influxTemp.writeSyncPointByName(orgName, bucketName, point);
        return Result.o();
    }

    /**
     * 同步写入多个数据点通过组织名和储存桶名<br>
     * http://localhost:8080/writeSyncPointsByName?orgName=influx&bucketName=db&measurementName=a&tagKey=b&tagValue=c&field=d&value=1
     */
    @GetMapping("writeSyncPointsByName")
    public Result writeSyncPointsByName(String orgName, String bucketName, String measurementName, String tagKey, String tagValue, String field, long value) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Point point = Point.measurement(measurementName)
                    .addTag(tagKey, tagValue)
                    .addField(field, value + i)
                    .time(Instant.now().plusSeconds(i), WritePrecision.MS);
            points.add(point);
        }
        influxTemp.writeSyncPointsByName(orgName, bucketName, points);
        return Result.o();
    }

    /**
     * 同步写入记录<br>
     * http://localhost:8080/writeSyncRecord?record=aa,b=c,d=e%20f=3,g=4%201725693927000
     */
    @GetMapping("writeSyncRecord")
    public Result writeSyncRecord(String record) {
        influxTemp.writeSyncRecord(WritePrecision.MS, record);
        return Result.o();
    }

    /**
     * 同步写入多条记录<br>
     * http://localhost:8080/writeSyncRecords?records=a,b=c%20e=1&records=a,b=c%20f=1
     */
    @GetMapping("writeSyncRecords")
    public Result writeSyncRecords(String[] records) {
        influxTemp.writeSyncRecords(WritePrecision.MS, List.of(records));
        return Result.o();
    }

    /**
     * 同步写入记录通过组织名和储存桶名<br>
     * http://localhost:8080/writeSyncRecordByName?orgName=influx&bucketName=db&record=a,b=c%20e=1
     */
    @GetMapping("writeSyncRecordByName")
    public Result writeSyncRecordByName(String orgName, String bucketName, String record) {
        influxTemp.writeSyncRecordByName(orgName, bucketName, WritePrecision.MS, record);
        return Result.o();
    }

    /**
     * 同步写入多条记录通过组织名和储存桶名<br>
     * http://localhost:8080/writeSyncRecordsByName?orgName=influx&bucketName=db&records=a,b=c%20e=1&records=a,b=c%20f=1
     */
    @GetMapping("writeSyncRecordsByName")
    public Result writeSyncRecordsByName(String orgName, String bucketName, String[] records) {
        influxTemp.writeSyncRecordsByName(orgName, bucketName, WritePrecision.MS, List.of(records));
        return Result.o();
    }

    /**
     * 同步写入对象<br>
     * http://localhost:8080/writeSyncObj?tag1=t1&tag2=t2&value1=1&value2=2
     */
    @GetMapping("writeSyncObj")
    public Result writeSyncObj(String tag1, String tag2, long value1, long value2) {
        TestClass testClass = new TestClass();
        testClass.setTag1(tag1);
        testClass.setTag2(tag2);
        testClass.setValue1(value1);
        testClass.setValue2(value2);
        testClass.setTime(Instant.now());
        influxTemp.writeSyncObj(WritePrecision.MS, testClass);
        return Result.o();
    }

    /**
     * 同步写入多个对象<br>
     * http://localhost:8080/writeSyncObjs?tag1=t1&value1=1
     */
    @GetMapping("writeSyncObjs")
    public Result writeSyncObjs(String tag1, long value1) {
        List<TestClass> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestClass testClass = new TestClass();
            testClass.setTag1(tag1);
            testClass.setValue1(value1 + i);
            testClass.setTime(Instant.now().plusSeconds(i));
            list.add(testClass);
        }
        influxTemp.writeSyncObjs(WritePrecision.MS, list);
        return Result.o();
    }

    /**
     * 同步写入对象通过组织名和储存桶名<br>
     * http://localhost:8080/writeSyncObjByName?orgName=influx&bucketName=db&tag1=t1&tag2=t2&value1=1&value2=2
     */
    @GetMapping("writeSyncObjByName")
    public Result writeSyncObjByName(String orgName, String bucketName, String tag1, String tag2, long value1, long value2) {
        TestClass testClass = new TestClass();
        testClass.setTag1(tag1);
        testClass.setTag2(tag2);
        testClass.setValue1(value1);
        testClass.setValue2(value2);
        testClass.setTime(Instant.now());
        influxTemp.writeSyncObjByName(orgName, bucketName, WritePrecision.MS, testClass);
        return Result.o();
    }

    /**
     * 同步写入多个对象<br>
     * http://localhost:8080/writeSyncObjsByName?orgName=influx&bucketName=db&tag1=t1&value1=1
     */
    @GetMapping("writeSyncObjsByName")
    public Result writeSyncObjsByName(String orgName, String bucketName, String tag1, long value1) {
        List<TestClass> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestClass testClass = new TestClass();
            testClass.setTag1(tag1);
            testClass.setValue1(value1 + i);
            testClass.setTime(Instant.now().plusSeconds(i));
            list.add(testClass);
        }
        influxTemp.writeSyncObjsByName(orgName, bucketName, WritePrecision.MS, list);
        return Result.o();
    }

    // endregion

    /* ==================== 写入操作 ==================== */
    // region 写入操作

    /**
     * 写入数据点<br>
     * http://localhost:8080/writePoint?measurementName=a&tagKey=b&tagValue=c&field=d&value=1
     */
    @GetMapping("writePoint")
    public Result writePoint(String measurementName, String tagKey, String tagValue, String field, long value) {
        Point point = Point.measurement(measurementName)
                .addTag(tagKey, tagValue)
                .addField(field, value)
                .time(Instant.now(), WritePrecision.MS);
        influxTemp.writePoint(point);
        return Result.o();
    }

    /**
     * 写入多个数据点<br>
     * http://localhost:8080/writePoints?measurementName=a&tagKey=b&tagValue=c&field=d&value=1
     */
    @GetMapping("writePoints")
    public Result writePoints(String measurementName, String tagKey, String tagValue, String field, long value) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Point point = Point.measurement(measurementName)
                    .addTag(tagKey, tagValue)
                    .addField(field, value + i)
                    .time(Instant.now().plusSeconds(i), WritePrecision.MS);
            points.add(point);
        }
        influxTemp.writePoints(points);
        return Result.o();
    }

    /**
     * 写入数据点通过组织名和储存桶名<br>
     * http://localhost:8080/writePointByName?orgName=influx&bucketName=db&measurementName=a&tagKey=b&tagValue=c&field=d&value=1
     */
    @GetMapping("writePointByName")
    public Result writePointByName(String orgName, String bucketName, String measurementName, String tagKey, String tagValue, String field, long value) {
        Point point = Point.measurement(measurementName)
                .addTag(tagKey, tagValue)
                .addField(field, value)
                .time(Instant.now(), WritePrecision.MS);
        influxTemp.writePointByName(orgName, bucketName, point);
        return Result.o();
    }

    /**
     * 写入多个数据点通过组织名和储存桶名<br>
     * http://localhost:8080/writePointsByName?orgName=influx&bucketName=db&measurementName=a&tagKey=b&tagValue=c&field=d&value=1
     */
    @GetMapping("writePointsByName")
    public Result writePointsByName(String orgName, String bucketName, String measurementName, String tagKey, String tagValue, String field, long value) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Point point = Point.measurement(measurementName)
                    .addTag(tagKey, tagValue)
                    .addField(field, value + i)
                    .time(Instant.now().plusSeconds(i), WritePrecision.MS);
            points.add(point);
        }
        influxTemp.writePointsByName(orgName, bucketName, points);
        return Result.o();
    }

    /**
     * 写入记录<br>
     * http://localhost:8080/writeRecord?record=aa,b=c,d=e%20f=3,g=4%201725693927000
     */
    @GetMapping("writeRecord")
    public Result writeRecord(String record) {
        influxTemp.writeRecord(WritePrecision.MS, record);
        return Result.o();
    }

    /**
     * 写入多条记录<br>
     * http://localhost:8080/writeRecords?records=a,b=c%20e=1&records=a,b=c%20f=1
     */
    @GetMapping("writeRecords")
    public Result writeRecords(String[] records) {
        influxTemp.writeRecords(WritePrecision.MS, List.of(records));
        return Result.o();
    }

    /**
     * 写入记录通过组织名和储存桶名<br>
     * http://localhost:8080/writeRecordByName?orgName=influx&bucketName=db&record=a,b=c%20e=1
     */
    @GetMapping("writeRecordByName")
    public Result writeRecordByName(String orgName, String bucketName, String record) {
        influxTemp.writeRecordByName(orgName, bucketName, WritePrecision.MS, record);
        return Result.o();
    }

    /**
     * 写入多条记录通过组织名和储存桶名<br>
     * http://localhost:8080/writeRecordsByName?orgName=influx&bucketName=db&records=a,b=c%20e=1&records=a,b=c%20f=1
     */
    @GetMapping("writeRecordsByName")
    public Result writeRecordsByName(String orgName, String bucketName, String[] records) {
        influxTemp.writeRecordsByName(orgName, bucketName, WritePrecision.MS, List.of(records));
        return Result.o();
    }

    /**
     * 写入对象<br>
     * http://localhost:8080/writeObj?tag1=t1&tag2=t2&value1=1&value2=2
     */
    @GetMapping("writeObj")
    public Result writeObj(String tag1, String tag2, long value1, long value2) {
        TestClass testClass = new TestClass();
        testClass.setTag1(tag1);
        testClass.setTag2(tag2);
        testClass.setValue1(value1);
        testClass.setValue2(value2);
        testClass.setTime(Instant.now());
        influxTemp.writeObj(WritePrecision.MS, testClass);
        return Result.o();
    }

    /**
     * 写入多个对象<br>
     * http://localhost:8080/writeObjs?tag1=t1&value1=1
     */
    @GetMapping("writeObjs")
    public Result writeObjs(String tag1, long value1) {
        List<TestClass> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestClass testClass = new TestClass();
            testClass.setTag1(tag1);
            testClass.setValue1(value1 + i);
            testClass.setTime(Instant.now().plusSeconds(i));
            list.add(testClass);
        }
        influxTemp.writeObjs(WritePrecision.MS, list);
        return Result.o();
    }

    /**
     * 写入对象通过组织名和储存桶名<br>
     * http://localhost:8080/writeObjByName?orgName=influx&bucketName=db&tag1=t1&tag2=t2&value1=1&value2=2
     */
    @GetMapping("writeObjByName")
    public Result writeObjByName(String orgName, String bucketName, String tag1, String tag2, long value1, long value2) {
        TestClass testClass = new TestClass();
        testClass.setTag1(tag1);
        testClass.setTag2(tag2);
        testClass.setValue1(value1);
        testClass.setValue2(value2);
        testClass.setTime(Instant.now());
        influxTemp.writeObjByName(orgName, bucketName, WritePrecision.MS, testClass);
        return Result.o();
    }

    /**
     * 写入多个对象<br>
     * http://localhost:8080/writeObjsByName?orgName=influx&bucketName=db&tag1=t1&value1=1
     */
    @GetMapping("writeObjsByName")
    public Result writeObjsByName(String orgName, String bucketName, String tag1, long value1) {
        List<TestClass> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestClass testClass = new TestClass();
            testClass.setTag1(tag1);
            testClass.setValue1(value1 + i);
            testClass.setTime(Instant.now().plusSeconds(i));
            list.add(testClass);
        }
        influxTemp.writeObjsByName(orgName, bucketName, WritePrecision.MS, list);
        return Result.o();
    }

    /**
     * 写入缓冲区清空<br>
     * http://localhost:8080/writeFlush
     */
    @GetMapping("writeFlush")
    public Result writeFlush() {
        influxTemp.writeFlush();
        return Result.o();
    }

    // endregion

}
