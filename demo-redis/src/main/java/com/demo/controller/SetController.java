package com.demo.controller;

import com.demo.entity.pojo.Result;
import com.demo.tool.RedisTemp;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <h1>SetController</h1>
 *
 * <p>
 * createDate 2021/11/30 11:24:38
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@RestController
@RequestMapping("set")
@AllArgsConstructor
public class SetController {

    private final RedisTemp redisTemp;

    /**
     * <h3>添加</h3>
     * POST/set/sAdd?key=a&value=a 1<br>
     * POST/set/sAdd?key=a&value=b 1<br>
     * POST/set/sAdd?key=a&value=a 0
     */
    @PostMapping("sAdd")
    public Result<Long> sAdd(String key, String value) {
        return Result.o(redisTemp.sAdd(key, value));
    }

    /**
     * <h3>添加多个</h3>
     * POST /set/sAddMulti?key=a<br>
     * body JSON ["a","b"] 2<br>
     * body JSON ["b","c"] 1
     */
    @PostMapping("sAddMulti")
    public Result<Long> sAddMulti(String key, @RequestBody List<String> value) {
        return Result.o(redisTemp.sAddMulti(key, value));
    }

    /**
     * <h3>添加多个</h3>
     * POST /set/sAddMultiArray?key=a&value=b 2<br>
     * POST /set/sAddMultiArray?key=b&value=c 1
     */
    @PostMapping("sAddMultiArray")
    public Result<Long> sAddMultiArray(String key, String[] value) {
        return Result.o(redisTemp.sAddMulti(key, value));
    }

    /**
     * <h3>删除值</h3>
     * 已存在键a值a/b/c<br>
     * POST /set/sDelete?key=a&value=a 1<br>
     * POST /set/sDelete?key=a&value=d 0<br>
     * POST /set/sDelete?key=b&value=d 0
     */
    @PostMapping("sDelete")
    public Result<Long> sDelete(String key, String value) {
        return Result.o(redisTemp.sDelete(key, value));
    }

    /**
     * <h3>删除多个值</h3>
     * POST /set/sDeleteMulti?key=a<br>
     * body JSON ["a","b"] 2<br>
     * body JSON ["b","c"] 1
     */
    @PostMapping("sDeleteMulti")
    public Result<Long> sDeleteMulti(String key, @RequestBody List<String> value) {
        return Result.o(redisTemp.sDeleteMulti(key, value));
    }

    /**
     * <h3>删除多个值</h3>
     * POST /set/sDeleteMultiArray?key=a&value=a&value=b 2<br>
     * POST /set/sDeleteMultiArray?key=b&value=c&value=d 1
     */
    @PostMapping("sDeleteMultiArray")
    public Result<Long> sDeleteMultiArray(String key, String[] value) {
        return Result.o(redisTemp.sDeleteMulti(key, value));
    }

    /**
     * <h3>返回并删除1个随机值</h3>
     * 已存在值a/b/c<br>
     * POST /set/sPop?key=a c<br>
     * POST /set/sPop?key=a a
     */
    @PostMapping("sPop")
    public Result<Object> sPop(String key) {
        return Result.o(redisTemp.sPop(key));
    }

    /**
     * <h3>返回并删除多个随机值</h3>
     * 已存在值a/b/c<br>
     * POST /set/sPopMulti?key=a&count=2<br>
     * ["b","c"]
     */
    @PostMapping("sPopMulti")
    public Result<List<Object>> sPopMulti(String key, long count) {
        return Result.o(redisTemp.sPopMulti(key, count));
    }

    /**
     * <h3>移动元素到目的键</h3>
     * POST /set/sMove?key=a&value=b&destKey=c<br>
     * true
     */
    @PostMapping("sMove")
    public Result<Boolean> sMove(String key, String value, String destKey) {
        return Result.o(redisTemp.sMove(key, value, destKey));
    }

    /**
     * <h3>元素个数</h3>
     * POST /set/sSize?key=a<br>
     * 3
     */
    @PostMapping("sSize")
    public Result<Long> sSize(String key) {
        return Result.o(redisTemp.sSize(key));
    }

    /**
     * <h3>是否存在元素</h3>
     * 存在键a值a/b/c<br>
     * POST /set/sIsMember?key=a&value=a true<br>
     * POST /set/sIsMember?key=a&value=e false<br>
     * POST /set/sIsMember?key=b&value=a false
     */
    @PostMapping("sIsMember")
    public Result<Boolean> sIsMember(String key, String value) {
        return Result.o(redisTemp.sIsMember(key, value));
    }

    /**
     * <h3>是否存在多个元素</h3>
     * 存在元素a/b/c
     * POST /set/sIsMultiMember?key=a<br>
     * body JSON ["a","b","d"] {"a":true,"b":true,"d":false}<br>
     */
    @PostMapping("sIsMultiMember")
    public Result<Map<Object, Boolean>> sIsMultiMember(String key, @RequestBody List<String> value) {
        return Result.o(redisTemp.sIsMemberMulti(key, value));
    }

    /**
     * <h3>是否存在多个元素</h3>
     * 存在元素a/b/c
     * POST /set/sIsMultiMemberArray?key=a&value=a&value=b&value=d<br>
     * {"a":true,"b":true,"d":false}
     */
    @PostMapping("sIsMultiMemberArray")
    public Result<Map<Object, Boolean>> sIsMultiMemberArray(String key, String[] value) {
        return Result.o(redisTemp.sIsMemberMulti(key, value));
    }

    /**
     * <h3>所有元素</h3>
     * 存在键a值a/b/c<br>
     * POST /set/sMembers?key=a ["a","b","c"]<br>
     * POST /set/sMembers?key=b []
     */
    @PostMapping("sMembers")
    public Result<Set<Object>> sMembers(String key) {
        return Result.o(redisTemp.sMembers(key));
    }

    /**
     * <h3>随机获取1个元素</h3>
     * 存在键a值a/b/c<br>
     * POST /set/sRandomMember?key=a a<br>
     * POST /set/sRandomMember?key=a c<br>
     * POST /set/sRandomMember?key=b null
     */
    @PostMapping("sRandomMember")
    public Result<Object> sRandomMember(String key) {
        return Result.o(redisTemp.sRandomMember(key));
    }

    /**
     * <h3>随机获取多个元素</h3>
     * 存在键a值a/b/c<br>
     * POST /set/sRandomMember?key=a&count=2 ["a","b"]<br>
     * POST /set/sRandomMember?key=a&count=2 ["a","a"]<br>
     * POST /set/sRandomMember?key=b&count=2 []
     */
    @PostMapping("sRandomMultiMember")
    public Result<List<Object>> sRandomMultiMember(String key, long count) {
        return Result.o(redisTemp.sRandomMemberMulti(key, count));
    }

    /**
     * <h3>随机获取多个元素(不重复)</h3>
     * 存在键a值a/b/c<br>
     * POST /set/sRandomMultiDistinctMember?key=a&count=2 ["a","b"]<br>
     * POST /set/sRandomMultiDistinctMember?key=a&count=2 ["a","c"]<br>
     * POST /set/sRandomMultiDistinctMember?key=b&count=2 []
     */
    @PostMapping("sRandomMultiDistinctMember")
    public Result<Set<Object>> sRandomMultiDistinctMember(String key, long count) {
        return Result.o(redisTemp.sRandomMemberMultiDistinct(key, count));
    }

    /**
     * <h3>两键交集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * POST /set/sIntersect?key=a&otherKey=b<br>
     * ["b","c"]
     */
    @PostMapping("sIntersect")
    public Result<Set<Object>> sIntersect(String key, String otherKey) {
        return Result.o(redisTemp.sIntersect(key, otherKey));
    }

    /**
     * <h3>键与其他键的交集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sIntersectMulti?key=a<br>
     * body JSON ["b","c"]<br>
     * ["c"]
     */
    @PostMapping("sIntersectMulti")
    public Result<Set<Object>> sIntersectMulti(String key, @RequestBody List<String> otherKey) {
        return Result.o(redisTemp.sIntersectMulti(key, otherKey));
    }

    /**
     * <h3>所有键的交集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sIntersectAll<br>
     * body JSON ["a","b","c"]<br>
     * ["c"]
     */
    @PostMapping("sIntersectAll")
    public Result<Set<Object>> sIntersect(@RequestBody List<String> keys) {
        return Result.o(redisTemp.sIntersectAll(keys));
    }

    /**
     * <h3>两键交集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * POST /set/sIntersectAndStore?key=a&otherKey=b&destKey=z<br>
     * 2
     */
    @PostMapping("sIntersectAndStore")
    public Result<Long> sIntersectAndStore(String key, String otherKey, String destKey) {
        return Result.o(redisTemp.sIntersectAndStore(key, otherKey, destKey));
    }

    /**
     * <h3>键与其他键的交集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sIntersectMultiAndStore?key=a&destKey=z<br>
     * body JSON ["b","c"]<br>
     * 1
     */
    @PostMapping("sIntersectMultiAndStore")
    public Result<Long> sIntersectMultiAndStore(String key, @RequestBody List<String> otherKey, String destKey) {
        return Result.o(redisTemp.sIntersectAndStoreMulti(key, otherKey, destKey));
    }

    /**
     * <h3>所有键的交集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sIntersectAllAndStore?destKey=z<br>
     * body JSON ["a","b","c"]<br>
     * 1
     */
    @PostMapping("sIntersectAllAndStore")
    public Result<Long> sIntersectAndStore(@RequestBody List<String> keys, String destKey) {
        return Result.o(redisTemp.sIntersectAndStoreAll(keys, destKey));
    }

    /**
     * <h3>两键并集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * POST /set/sUnion?key=a&otherKey=b<br>
     * ["c","a","b","d"]
     */
    @PostMapping("sUnion")
    public Result<Set<Object>> sUnion(String key, String otherKey) {
        return Result.o(redisTemp.sUnion(key, otherKey));
    }

    /**
     * <h3>键与其他键的并集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sUnionMulti?key=a<br>
     * body JSON ["b","c"]<br>
     * ["c","a","b","d","e"]
     */
    @PostMapping("sUnionMulti")
    public Result<Set<Object>> sUnionMulti(String key, @RequestBody List<String> otherKey) {
        return Result.o(redisTemp.sUnionMulti(key, otherKey));
    }

    /**
     * <h3>所有键的并集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sUnionAll?key=a<br>
     * body JSON ["a","b","c"]<br>
     * ["c","a","b","d","e"]
     */
    @PostMapping("sUnionAll")
    public Result<Set<Object>> sUnion(@RequestBody List<String> keys) {
        return Result.o(redisTemp.sUnionAll(keys));
    }

    /**
     * <h3>两键并集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * POST /set/sUnionAndStore?key=a&otherKey=b&destKey=z<br>
     * 4
     */
    @PostMapping("sUnionAndStore")
    public Result<Long> sUnionAndStore(String key, String otherKey, String destKey) {
        return Result.o(redisTemp.sUnionAndStore(key, otherKey, destKey));
    }

    /**
     * <h3>键与其他键的并集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sUnionMultiAndStore?key=a&destKey=z<br>
     * body JSON ["b","c"]<br>
     * 5
     */
    @PostMapping("sUnionMultiAndStore")
    public Result<Long> sUnionMultiAndStore(String key, @RequestBody List<String> otherKey, String destKey) {
        return Result.o(redisTemp.sUnionAndStoreMulti(key, otherKey, destKey));
    }

    /**
     * <h3>所有键的并集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sUnionAllAndStore?destKey=z<br>
     * body JSON ["a","b","c"]<br>
     * 5
     */
    @PostMapping("sUnionAllAndStore")
    public Result<Long> sUnionAndStore(@RequestBody List<String> keys, String destKey) {
        return Result.o(redisTemp.sUnionAndStoreAll(keys, destKey));
    }

    /**
     * <h3>两键差集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * POST /set/sDifference?key=a&otherKey=b<br>
     * ["a"]
     */
    @PostMapping("sDifference")
    public Result<Set<Object>> sDifference(String key, String otherKey) {
        return Result.o(redisTemp.sDifference(key, otherKey));
    }

    /**
     * <h3>键与其他键的差集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sDifferenceMulti?key=a<br>
     * body JSON ["b","c"]<br>
     * []
     */
    @PostMapping("sDifferenceMulti")
    public Result<Set<Object>> sDifferenceMulti(String key, @RequestBody List<String> otherKey) {
        return Result.o(redisTemp.sDifferenceMulti(key, otherKey));
    }

    /**
     * <h3>所有键的差集</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sDifferenceAll?key=a<br>
     * body JSON ["a","b","c"]<br>
     * []
     */
    @PostMapping("sDifferenceAll")
    public Result<Set<Object>> sDifference(@RequestBody List<String> keys) {
        return Result.o(redisTemp.sDifferenceAll(keys));
    }

    /**
     * <h3>两键差集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * POST /set/sDifferenceAndStore?key=a&otherKey=b&destKey=z<br>
     * 1
     */
    @PostMapping("sDifferenceAndStore")
    public Result<Long> sDifferenceAndStore(String key, String otherKey, String destKey) {
        return Result.o(redisTemp.sDifferenceAndStore(key, otherKey, destKey));
    }

    /**
     * <h3>键与其他键的差集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sDifferenceMultiAndStore?key=a&destKey=z<br>
     * body JSON ["b","c"]<br>
     * 0
     */
    @PostMapping("sDifferenceMultiAndStore")
    public Result<Long> sDifferenceMultiAndStore(String key, @RequestBody List<String> otherKey, String destKey) {
        return Result.o(redisTemp.sDifferenceAndStoreMulti(key, otherKey, destKey));
    }

    /**
     * <h3>所有键的差集并储存</h3>
     * 存在键a值a/b/c<br>
     * 存在键b值b/c/d<br>
     * 存在键c值c/d/e<br>
     * POST /set/sDifferenceAllAndStore?destKey=z<br>
     * body JSON ["a","b","c"]<br>
     * 0
     */
    @PostMapping("sDifferenceAllAndStore")
    public Result<Long> sDifferenceAndStore(@RequestBody List<String> keys, String destKey) {
        return Result.o(redisTemp.sDifferenceAndStoreAll(keys, destKey));
    }

    /**
     * <h3>模糊查询</h3>
     * 实际存在键a值有a/aabbcc/abc/abd/b/bc/bd/c/[]<br>
     * POST /sScan?key=a&match=a 不匹配字符[a]<br>
     * POST /sScan?key=a&match=b? 右侧匹配1个字符[bc,bd]<br>
     * POST /sScan?key=a&match=*c 左侧匹配0+个字符[aabbcc,abc,bc,c]<br>
     * POST /sScan?key=a&match=*b* 两侧匹配0+个字符[aabbcc,abc,abd,b,bc,bd]<br>
     * POST /sScan?key=a&match=[abd] 匹配1个指定字符[a,b]<br>
     * POST /sScan?key=a&match=[^abd] 不匹配1个指定字符[c]<br>
     * POST /sScan?key=a&match=[A-z] 匹配1个指定字符[a,b,c]<br>
     * POST /sScan?key=a&match=\[* 转义匹配匹配1个指定字符[[]]
     */
    @PostMapping("sScan")
    public Result<List<Object>> sScan(String key, String match) {
        return Result.o(redisTemp.sScan(key, match));
    }

}
