package com.demo.controller;

import com.demo.entity.po.User;
import com.demo.entity.pojo.Result;
import com.demo.util.EsUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
public class IndexController {

    /**
     * 创建索引
     * http://localhost:8080/createIndex?index=a
     * true
     */
    @PostMapping("/createIndex")
    public Result<Boolean> createIndex(String index) {
        String mapping = "{\n" + //
                "  \"properties\": {\n" + //
                "    \"account\": {\n" + // 字段名
                "      \"type\": \"text\",\n" + // 类型keyword、text
                "      \"analyzer\": \"ik_max_word\",\n" + // 分词器standard、ik_smart、ik_max_word
                "      \"search_analyzer\": \"ik_max_word\"\n" + // 搜索用分词器
                "    }\n" + //
                "  }\n" + //
                "}";
        boolean ok = EsUtils.createIndex(index, mapping);
        return Result.o(ok);
    }

    /**
     * 是否存在索引
     * http://localhost:8080/existIndex?index=a
     * true
     */
    @PostMapping("/existIndex")
    public Result<Boolean> existIndex(String index) {
        boolean ok = EsUtils.existIndex(index);
        return Result.o(ok);
    }

    /**
     * 删除索引
     * http://localhost:8080/deleteIndex?index=aa
     */
    @PostMapping("/deleteIndex")
    public Result<Boolean> deleteIndex(String index) {
        boolean ok = EsUtils.deleteIndex(index);
        return Result.o(ok);
    }

    /**
     * 新增文档(随机文档id)
     * http://localhost:8080/addDocument?index=a
     * body JSON
     * {"account":"广东省深圳市宝安区新安街道岭下花园七巷4号"}
     * 返回
     * {
     *   "code": "0",
     *   "data": {
     *     "fragment": "false",
     *     "id": "i7DpAYABeAgtN07CsJyP",
     *     "index": "a",
     *     "primaryTerm": "1",
     *     "result": "CREATED",
     *     "seqNo": "0",
     *     "shardId": {
     *       "fragment": "true",
     *       "id": "-1",
     *       "index": {
     *         "fragment": "false",
     *         "name": "a",
     *         "uUID": "_na_"
     *       },
     *       "indexName": "a"
     *     },
     *     "shardInfo": {
     *       "failed": "0",
     *       "failures": [],
     *       "fragment": "false",
     *       "successful": "1",
     *       "total": "2"
     *     },
     *     "type": "_doc",
     *     "version": "1"
     *   },
     *   "msg": "成功",
     *   "ok": "true"
     * }
     */
    @PostMapping("/addDocument")
    public Result<IndexResponse> addDocument(String index, @RequestBody User user) {
        IndexResponse ok = EsUtils.addDocument(index, user);
        return Result.o(ok);
    }

    /**
     * 新增文档
     * http://localhost:8080/addDocument2?index=a&id=abcde
     * body JSON
     * {"account":"广东省深圳市南山区深圳国际创新谷8栋41楼瑞斯康"}
     * 返回
     * {
     *   "code": "0",
     *   "data": {
     *     "fragment": "false",
     *     "id": "abcde",
     *     "index": "a",
     *     "primaryTerm": "1",
     *     "result": "CREATED",
     *     "seqNo": "1",
     *     "shardId": {
     *       "fragment": "true",
     *       "id": "-1",
     *       "index": {
     *         "fragment": "false",
     *         "name": "a",
     *         "uUID": "_na_"
     *       },
     *       "indexName": "a"
     *     },
     *     "shardInfo": {
     *       "failed": "0",
     *       "failures": [],
     *       "fragment": "false",
     *       "successful": "1",
     *       "total": "2"
     *     },
     *     "type": "_doc",
     *     "version": "1"
     *   },
     *   "msg": "成功",
     *   "ok": "true"
     * }
     */
    @PostMapping("/addDocument2")
    public Result<IndexResponse> addDocument2(String index, String id, @RequestBody User user) {
        IndexResponse ok = EsUtils.addDocument(index, id, user);
        return Result.o(ok);
    }

    /**
     * 是否存在文档
     * http://localhost:8080/existDocument?index=a&id=abcde
     * true
     */
    @PostMapping("/existDocument")
    public Result<Boolean> existDocument(String index, String id) {
        boolean ok = EsUtils.existDocument(index, id);
        return Result.o(ok);
    }

    /**
     * 查找文档
     * http://localhost:8080/getDocument?index=a&id=abcde
     * {
     *   "code": "0",
     *   "data": {
     *     "exists": "true",
     *     "fields": {},
     *     "fragment": "false",
     *     "id": "abcde",
     *     "index": "a",
     *     "primaryTerm": "1",
     *     "seqNo": "1",
     *     "source": {
     *       "account": "广东省深圳市南山区深圳国际创新谷8栋41楼瑞斯康"
     *     },
     *     "sourceAsBytes":
     *     "eyJhY2NvdW50Ijoi5bm
     *     /5Lic55yB5rex5Zyz5biC5Y2X5bGx5Yy65rex5Zyz5Zu96ZmF5Yib5paw6LC3OOagizQx5qW855Ge5pav5bq3In0=",
     *     "sourceAsBytesRef": {
     *       "fragment": "true"
     *     },
     *     "sourceAsMap": {
     *       "account": "广东省深圳市南山区深圳国际创新谷8栋41楼瑞斯康"
     *     },
     *     "sourceAsString": "{\"account\":\"广东省深圳市南山区深圳国际创新谷8栋41楼瑞斯康\"}",
     *     "sourceEmpty": "false",
     *     "sourceInternal": {
     *       "fragment": "true"
     *     },
     *     "type": "_doc",
     *     "version": "1"
     *   },
     *   "msg": "成功",
     *   "ok": "true"
     * }
     */
    @PostMapping("/getDocument")
    public Result<GetResponse> getDocument(String index, String id) {
        GetResponse ok = EsUtils.getDocument(index, id);
        return Result.o(ok);
    }

    /**
     * 修改文档
     * http://localhost:8080/updateDocument?index=a&id=abcde
     * body JSON
     * {"account":"广东省深圳市南山区深圳国际创新谷8栋41楼瑞斯康(深圳)微电子有限公司"}
     * 返回
     * {
     *   "code": "0",
     *   "data": {
     *     "fragment": "false",
     *     "id": "abcde",
     *     "index": "a",
     *     "primaryTerm": "1",
     *     "result": "UPDATED",
     *     "seqNo": "2",
     *     "shardId": {
     *       "fragment": "true",
     *       "id": "-1",
     *       "index": {
     *         "fragment": "false",
     *         "name": "a",
     *         "uUID": "_na_"
     *       },
     *       "indexName": "a"
     *     },
     *     "shardInfo": {
     *       "failed": "0",
     *       "failures": [],
     *       "fragment": "false",
     *       "successful": "1",
     *       "total": "2"
     *     },
     *     "type": "_doc",
     *     "version": "2"
     *   },
     *   "msg": "成功",
     *   "ok": "true"
     * }
     */
    @PostMapping("/updateDocument")
    public Result<UpdateResponse> updateDocument(String index, String id, @RequestBody User user) {
        UpdateResponse ok = EsUtils.updateDocument(index, id, user);
        return Result.o(ok);
    }

    /**
     * 删除文档
     * http://localhost:8080/deleteDocument?index=a&id=abcde
     * {
     *   "code": "0",
     *   "data": {
     *     "fragment": "false",
     *     "id": "abcde",
     *     "index": "a",
     *     "primaryTerm": "1",
     *     "result": "DELETED",
     *     "seqNo": "3",
     *     "shardId": {
     *       "fragment": "true",
     *       "id": "-1",
     *       "index": {
     *         "fragment": "false",
     *         "name": "a",
     *         "uUID": "_na_"
     *       },
     *       "indexName": "a"
     *     },
     *     "shardInfo": {
     *       "failed": "0",
     *       "failures": [],
     *       "fragment": "false",
     *       "successful": "1",
     *       "total": "2"
     *     },
     *     "type": "_doc",
     *     "version": "3"
     *   },
     *   "msg": "成功",
     *   "ok": "true"
     * }
     */
    @PostMapping("/deleteDocument")
    public Result<DeleteResponse> deleteDocument(String index, String id) {
        DeleteResponse ok = EsUtils.deleteDocument(index, id);
        return Result.o(ok);
    }

    /**
     * 批量新增文档(随机文档id)
     * http://localhost:8080/addDocumentBulk?index=a
     * body JSON
     * [
     *   {
     *     "account": "广东省深圳市南山区深圳国际创新谷8栋41楼瑞斯康(深圳)微电子有限公司"
     *   },
     *   {
     *     "account": "广东省深圳市南山区软件园二期11栋7楼华云中盛"
     *   },
     *   {
     *     "account": "山东省济宁市任城区曹东社区21号楼2单元"
     *   }
     * ]
     * 返回
     * true
     */
    @PostMapping("/addDocumentBulk")
    public Result<Boolean> addDocumentBulk(String index, @RequestBody List<User> objects) {
        boolean ok = EsUtils.addDocumentBulk(index, objects);
        return Result.o(ok);
    }

    /**
     * 批量新增文档
     * http://localhost:8080/addDocumentBulk2?index=a
     * body JSON
     * {
     *   "aa": {
     *     "account": "山东省济宁市任城区石桥镇栗河涯村平安街十六号胡同4号"
     *   },
     *   "bb": {
     *     "account": "广西壮族自治区玉林市博白县松旺镇周北村丁山坡"
     *   }
     * }
     * 返回
     * true
     */
    @PostMapping("/addDocumentBulk2")
    public Result<Boolean> addDocumentBulk2(String index, @RequestBody Map<String, User> objects) {
        boolean ok = EsUtils.addDocumentBulk(index, objects);
        return Result.o(ok);
    }

    /**
     * 查询文档
     * http://localhost:8080/search?index=a&value=深圳
     * 返回
     * {
     *   "code": "0",
     *   "data": [
     *     {
     *       "account": "广东省<em>深圳</em>市南山区<em>深圳</em>国际创新谷8栋41楼瑞斯康(<em>深圳</em>)微电子有限公司"
     *     },
     *     {
     *       "account": "广东省<em>深圳</em>市宝安区新安街道岭下花园七巷4号"
     *     },
     *     {
     *       "account": "广东省<em>深圳</em>市南山区软件园二期11栋7楼华云中盛"
     *     }
     *   ],
     *   "msg": "成功",
     *   "ok": "true"
     * }
     */
    @PostMapping("/search")
    public Result<List<Map<String, Object>>> search(String index, String value) {
        String field = "account";
        QueryBuilder queryBuilder = QueryBuilders.matchQuery(field, value);
        HighlightBuilder highlightBuilder = new HighlightBuilder().field(field)// 匹配字段
                // .requireFieldMatch(false)// 匹配所有字段
                // .preTags("<span style='color:red'>")// 内容前缀
                // .postTags("</span>")// 内容后缀
                ;
        SearchResponse searchResponse = EsUtils.search(index, queryBuilder, highlightBuilder, 1, 10, null);
        return Result.o(EsUtils.extractHighlightResult(searchResponse));
    }

    /**
     * 分析文本
     * http://localhost:8080/analyze?analyzer=ik_smart&text=山东省济宁市任城区石桥镇栗河涯村平安街十六号胡同4号
     * 返回
     * {
     *   "code": "0",
     *   "data": {
     *     "tokens": [
     *       {
     *         "attributes": {},
     *         "endOffset": "3",
     *         "position": "0",
     *         "positionLength": "1",
     *         "startOffset": "0",
     *         "term": "山东省",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "6",
     *         "position": "1",
     *         "positionLength": "1",
     *         "startOffset": "3",
     *         "term": "济宁市",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "9",
     *         "position": "2",
     *         "positionLength": "1",
     *         "startOffset": "6",
     *         "term": "任城区",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "11",
     *         "position": "3",
     *         "positionLength": "1",
     *         "startOffset": "9",
     *         "term": "石桥",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "12",
     *         "position": "4",
     *         "positionLength": "1",
     *         "startOffset": "11",
     *         "term": "镇",
     *         "type": "CN_CHAR"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "13",
     *         "position": "5",
     *         "positionLength": "1",
     *         "startOffset": "12",
     *         "term": "栗",
     *         "type": "CN_CHAR"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "14",
     *         "position": "6",
     *         "positionLength": "1",
     *         "startOffset": "13",
     *         "term": "河",
     *         "type": "CN_CHAR"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "15",
     *         "position": "7",
     *         "positionLength": "1",
     *         "startOffset": "14",
     *         "term": "涯",
     *         "type": "CN_CHAR"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "16",
     *         "position": "8",
     *         "positionLength": "1",
     *         "startOffset": "15",
     *         "term": "村",
     *         "type": "CN_CHAR"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "18",
     *         "position": "9",
     *         "positionLength": "1",
     *         "startOffset": "16",
     *         "term": "平安",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "19",
     *         "position": "10",
     *         "positionLength": "1",
     *         "startOffset": "18",
     *         "term": "街",
     *         "type": "CN_CHAR"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "22",
     *         "position": "11",
     *         "positionLength": "1",
     *         "startOffset": "19",
     *         "term": "十六号",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "24",
     *         "position": "12",
     *         "positionLength": "1",
     *         "startOffset": "22",
     *         "term": "胡同",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "26",
     *         "position": "13",
     *         "positionLength": "1",
     *         "startOffset": "24",
     *         "term": "4号",
     *         "type": "TYPE_CQUAN"
     *       }
     *     ]
     *   },
     *   "msg": "成功",
     *   "ok": "true"
     * }
     * http://localhost:8080/analyze?analyzer=ik_max_word&text=山东省济宁市任城区石桥镇栗河涯村平安街十六号胡同4号
     * 返回
     * {
     *   "code": "0",
     *   "data": {
     *     "tokens": [
     *       {
     *         "attributes": {},
     *         "endOffset": "3",
     *         "position": "0",
     *         "positionLength": "1",
     *         "startOffset": "0",
     *         "term": "山东省",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "2",
     *         "position": "1",
     *         "positionLength": "1",
     *         "startOffset": "0",
     *         "term": "山东",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "3",
     *         "position": "2",
     *         "positionLength": "1",
     *         "startOffset": "2",
     *         "term": "省",
     *         "type": "CN_CHAR"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "6",
     *         "position": "3",
     *         "positionLength": "1",
     *         "startOffset": "3",
     *         "term": "济宁市",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "5",
     *         "position": "4",
     *         "positionLength": "1",
     *         "startOffset": "3",
     *         "term": "济宁",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "6",
     *         "position": "5",
     *         "positionLength": "1",
     *         "startOffset": "5",
     *         "term": "市",
     *         "type": "CN_CHAR"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "9",
     *         "position": "6",
     *         "positionLength": "1",
     *         "startOffset": "6",
     *         "term": "任城区",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "9",
     *         "position": "7",
     *         "positionLength": "1",
     *         "startOffset": "7",
     *         "term": "城区",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "11",
     *         "position": "8",
     *         "positionLength": "1",
     *         "startOffset": "9",
     *         "term": "石桥",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "12",
     *         "position": "9",
     *         "positionLength": "1",
     *         "startOffset": "11",
     *         "term": "镇",
     *         "type": "CN_CHAR"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "13",
     *         "position": "10",
     *         "positionLength": "1",
     *         "startOffset": "12",
     *         "term": "栗",
     *         "type": "CN_CHAR"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "14",
     *         "position": "11",
     *         "positionLength": "1",
     *         "startOffset": "13",
     *         "term": "河",
     *         "type": "CN_CHAR"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "15",
     *         "position": "12",
     *         "positionLength": "1",
     *         "startOffset": "14",
     *         "term": "涯",
     *         "type": "CN_CHAR"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "16",
     *         "position": "13",
     *         "positionLength": "1",
     *         "startOffset": "15",
     *         "term": "村",
     *         "type": "CN_CHAR"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "18",
     *         "position": "14",
     *         "positionLength": "1",
     *         "startOffset": "16",
     *         "term": "平安",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "19",
     *         "position": "15",
     *         "positionLength": "1",
     *         "startOffset": "18",
     *         "term": "街",
     *         "type": "CN_CHAR"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "22",
     *         "position": "16",
     *         "positionLength": "1",
     *         "startOffset": "19",
     *         "term": "十六号",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "21",
     *         "position": "17",
     *         "positionLength": "1",
     *         "startOffset": "19",
     *         "term": "十六",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "22",
     *         "position": "18",
     *         "positionLength": "1",
     *         "startOffset": "20",
     *         "term": "六号",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "24",
     *         "position": "19",
     *         "positionLength": "1",
     *         "startOffset": "22",
     *         "term": "胡同",
     *         "type": "CN_WORD"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "25",
     *         "position": "20",
     *         "positionLength": "1",
     *         "startOffset": "24",
     *         "term": "4",
     *         "type": "ARABIC"
     *       },
     *       {
     *         "attributes": {},
     *         "endOffset": "26",
     *         "position": "21",
     *         "positionLength": "1",
     *         "startOffset": "25",
     *         "term": "号",
     *         "type": "COUNT"
     *       }
     *     ]
     *   },
     *   "msg": "成功",
     *   "ok": "true"
     * }
     */
    @PostMapping("/analyze")
    public Result<AnalyzeResponse> analyze(String analyzer, String text) {
        AnalyzeResponse ok = EsUtils.analyze(analyzer, text);
        return Result.o(ok);
    }

}
