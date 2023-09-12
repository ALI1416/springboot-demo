package com.demo.tool.entity.elasticsearch.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>字段值</h1>
 *
 * <p>
 * createDate 2023/09/12 11:03:27
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class FieldValue {

    /**
     * 类型
     */
    public enum Kind {

        /**
         * 双精度浮点型
         */
        Double,
        /**
         * 长整型
         */
        Long,
        /**
         * 布尔
         */
        Boolean,
        /**
         * 字符串
         */
        String,
        /**
         * 空
         */
        Null,
        /**
         * 任意
         */
        Any;

        public static Kind toEnum(co.elastic.clients.elasticsearch._types.FieldValue.Kind kind) {
            switch (kind.name()) {
                case "Double":
                    return Kind.Double;
                case "Long":
                    return Kind.Long;
                case "Boolean":
                    return Kind.Boolean;
                case "String":
                    return Kind.String;
                case "Null":
                    return Kind.Null;
                case "Any":
                    return Kind.Any;
                default:
                    return null;
            }
        }

        @Override
        public java.lang.String toString() {
            return "Kind{}";
        }

    }

    /**
     * 类型
     */
    private FieldValue.Kind kind;
    /**
     * 值
     */
    private Object value;

    public FieldValue() {
    }

    public FieldValue(co.elastic.clients.elasticsearch._types.FieldValue value) {
        this.kind = FieldValue.Kind.toEnum(value._kind());
        this.value = value._get();
    }

    public static List<FieldValue> toList(List<co.elastic.clients.elasticsearch._types.FieldValue> valueList) {
        List<FieldValue> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch._types.FieldValue value : valueList) {
            list.add(new FieldValue(value));
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
        return "FieldValue{" +
                "kind=" + kind +
                ", value=" + value +
                '}';
    }

}