package cn.z.elasticsearch.entity.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>上下文</h1>
 *
 * <p>
 * createDate 2023/09/12 15:58:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Context {

    /**
     * 类型
     */
    public enum Kind {

        /**
         * 本地
         */
        Location,
        /**
         * 类别
         */
        Category;

        public static Kind toEnum(co.elastic.clients.elasticsearch.core.search.Context.Kind kind) {
            switch (kind.name()) {
                case "Location":
                    return Kind.Location;
                case "Category":
                    return Kind.Category;
                default:
                    return null;
            }
        }

        @Override
        public String toString() {
            return "Kind{}";
        }

    }

    /**
     * 种类
     */
    private Kind kind;
    /**
     * 值
     */
    private Object value;

    public Context() {
    }

    public Context(co.elastic.clients.elasticsearch.core.search.Context context) {
        this.kind = Kind.toEnum(context._kind());
        this.value = context._get();
    }

    public static Map<String, List<Context>> toMap(Map<String, List<co.elastic.clients.elasticsearch.core.search.Context>> contextMap) {
        Map<String, List<Context>> map = new HashMap<>();
        contextMap.forEach((key, value) -> map.put(key, Context.toList(value)));
        return map;
    }

    public static List<Context> toList(List<co.elastic.clients.elasticsearch.core.search.Context> contextList) {
        List<Context> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.search.Context context : contextList) {
            list.add(new Context(context));
        }
        return list;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Context{" +
                "kind=" + kind +
                ", value=" + value +
                '}';
    }

}
