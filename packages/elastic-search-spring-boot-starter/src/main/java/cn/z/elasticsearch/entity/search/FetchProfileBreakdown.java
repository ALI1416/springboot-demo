package cn.z.elasticsearch.entity.search;

/**
 * <h1>抓取配置细分</h1>
 *
 * <p>
 * createDate 2023/09/12 15:12:00
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class FetchProfileBreakdown {

    /**
     * 加载源
     */
    private Integer loadSource;
    /**
     * 加载源总数
     */
    private Integer loadSourceCount;
    /**
     * 加载存储字段
     */
    private Integer loadStoredFields;
    /**
     * 加载存储字段总数
     */
    private Integer loadStoredFieldsCount;
    /**
     * 下一个阅读器
     */
    private Integer nextReader;
    /**
     * 下一个阅读器总数
     */
    private Integer nextReaderCount;
    /**
     * 进程总数
     */
    private Integer processCount;
    /**
     * 进程
     */
    private Integer process;

    public FetchProfileBreakdown() {
    }

    public FetchProfileBreakdown(co.elastic.clients.elasticsearch.core.search.FetchProfileBreakdown breakdown) {
        this.loadSource = breakdown.loadSource();
        this.loadSourceCount = breakdown.loadSourceCount();
        this.loadStoredFields = breakdown.loadStoredFields();
        this.loadStoredFieldsCount = breakdown.loadStoredFieldsCount();
        this.nextReader = breakdown.nextReader();
        this.nextReaderCount = breakdown.nextReaderCount();
        this.processCount = breakdown.processCount();
        this.process = breakdown.process();
    }

    public Integer getLoadSource() {
        return loadSource;
    }

    public void setLoadSource(Integer loadSource) {
        this.loadSource = loadSource;
    }

    public Integer getLoadSourceCount() {
        return loadSourceCount;
    }

    public void setLoadSourceCount(Integer loadSourceCount) {
        this.loadSourceCount = loadSourceCount;
    }

    public Integer getLoadStoredFields() {
        return loadStoredFields;
    }

    public void setLoadStoredFields(Integer loadStoredFields) {
        this.loadStoredFields = loadStoredFields;
    }

    public Integer getLoadStoredFieldsCount() {
        return loadStoredFieldsCount;
    }

    public void setLoadStoredFieldsCount(Integer loadStoredFieldsCount) {
        this.loadStoredFieldsCount = loadStoredFieldsCount;
    }

    public Integer getNextReader() {
        return nextReader;
    }

    public void setNextReader(Integer nextReader) {
        this.nextReader = nextReader;
    }

    public Integer getNextReaderCount() {
        return nextReaderCount;
    }

    public void setNextReaderCount(Integer nextReaderCount) {
        this.nextReaderCount = nextReaderCount;
    }

    public Integer getProcessCount() {
        return processCount;
    }

    public void setProcessCount(Integer processCount) {
        this.processCount = processCount;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return "FetchProfileBreakdown{" +
                "loadSource=" + loadSource +
                ", loadSourceCount=" + loadSourceCount +
                ", loadStoredFields=" + loadStoredFields +
                ", loadStoredFieldsCount=" + loadStoredFieldsCount +
                ", nextReader=" + nextReader +
                ", nextReaderCount=" + nextReaderCount +
                ", processCount=" + processCount +
                ", process=" + process +
                '}';
    }

}
