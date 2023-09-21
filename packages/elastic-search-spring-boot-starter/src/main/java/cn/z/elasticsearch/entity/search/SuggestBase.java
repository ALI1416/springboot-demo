package cn.z.elasticsearch.entity.search;

/**
 * <h1>建议基类</h1>
 *
 * <p>
 * createDate 2023/09/12 15:54:12
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public abstract class SuggestBase {

    /**
     * 长度
     */
    private int length;
    /**
     * 偏移
     */
    private int offset;
    /**
     * 文本
     */
    private String text;

    public SuggestBase() {
    }

    public SuggestBase(co.elastic.clients.elasticsearch.core.search.SuggestBase base) {
        this.length = base.length();
        this.offset = base.offset();
        this.text = base.text();
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SuggestBase{" +
                "length=" + length +
                ", offset=" + offset +
                ", text='" + text + '\'' +
                '}';
    }

}
