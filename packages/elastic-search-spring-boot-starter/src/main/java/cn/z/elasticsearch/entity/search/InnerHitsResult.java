package cn.z.elasticsearch.entity.search;

import co.elastic.clients.json.JsonData;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>内部命中结果</h1>
 *
 * <p>
 * createDate 2023/09/12 10:58:55
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class InnerHitsResult {

    /**
     * 命中
     */
    private HitsMetadata<JsonData> hits;

    public InnerHitsResult() {
    }

    public InnerHitsResult(co.elastic.clients.elasticsearch.core.search.InnerHitsResult result) {
        this.hits = new HitsMetadata<>(result.hits());
    }

    public static Map<String, InnerHitsResult> toMap(Map<String, co.elastic.clients.elasticsearch.core.search.InnerHitsResult> resultMap) {
        Map<String, InnerHitsResult> map = new HashMap<>();
        resultMap.forEach((key, value) -> map.put(key, new InnerHitsResult(value)));
        return map;
    }

    public HitsMetadata<JsonData> getHits() {
        return hits;
    }

    public void setHits(HitsMetadata<JsonData> hits) {
        this.hits = hits;
    }

    @Override
    public String toString() {
        return "InnerHitsResult{" +
                "hits=" + hits +
                '}';
    }

}
