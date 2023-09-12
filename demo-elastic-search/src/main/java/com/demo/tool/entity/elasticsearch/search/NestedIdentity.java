package com.demo.tool.entity.elasticsearch.search;


/**
 * <h1>嵌套标识</h1>
 *
 * <p>
 * createDate 2023/09/12 11:00:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class NestedIdentity {

    /**
     * 字段
     */
    private String field;
    /**
     * 偏移
     */
    private int offset;
    /**
     * 嵌套标识
     */
    private NestedIdentity nested;

    public NestedIdentity() {
    }

    public NestedIdentity(co.elastic.clients.elasticsearch.core.search.NestedIdentity identity) {
        this.field = identity.field();
        this.offset = identity.offset();
        if (identity.nested() != null) {
            this.nested = new NestedIdentity(identity.nested());
        }
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public NestedIdentity getNested() {
        return nested;
    }

    public void setNested(NestedIdentity nested) {
        this.nested = nested;
    }

    @Override
    public String toString() {
        return "NestedIdentity{" +
                "field='" + field + '\'' +
                ", offset=" + offset +
                ", nested=" + nested +
                '}';
    }

}
