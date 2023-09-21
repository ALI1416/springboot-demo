package cn.z.elasticsearch.entity.search;

import java.util.List;

/**
 * <h1>抓起配置调试</h1>
 *
 * <p>
 * createDate 2023/09/12 15:16:08
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class FetchProfileDebug {

    /**
     * 存储字段
     */
    private List<String> storedFields;
    /**
     * 快速路径
     */
    private Integer fastPath;

    public FetchProfileDebug() {
    }

    public FetchProfileDebug(co.elastic.clients.elasticsearch.core.search.FetchProfileDebug debug) {
        this.storedFields = debug.storedFields();
        this.fastPath = debug.fastPath();
    }

    public List<String> getStoredFields() {
        return storedFields;
    }

    public void setStoredFields(List<String> storedFields) {
        this.storedFields = storedFields;
    }

    public Integer getFastPath() {
        return fastPath;
    }

    public void setFastPath(Integer fastPath) {
        this.fastPath = fastPath;
    }

    @Override
    public String toString() {
        return "FetchProfileDebug{" +
                "storedFields=" + storedFields +
                ", fastPath=" + fastPath +
                '}';
    }

}
