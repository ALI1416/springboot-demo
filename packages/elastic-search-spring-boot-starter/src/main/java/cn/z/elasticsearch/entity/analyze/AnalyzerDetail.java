package cn.z.elasticsearch.entity.analyze;


import java.util.List;

/**
 * <h1>分析器细节</h1>
 *
 * <p>
 * createDate 2023/09/12 17:38:42
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class AnalyzerDetail {

    /**
     * 名称
     */
    private String name;
    /**
     * 解释分析Token
     */
    private List<ExplainAnalyzeToken> tokens;

    public AnalyzerDetail() {
    }

    public AnalyzerDetail(co.elastic.clients.elasticsearch.indices.analyze.AnalyzerDetail detail) {
        this.name = detail.name();
        this.tokens = ExplainAnalyzeToken.toList(detail.tokens());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExplainAnalyzeToken> getTokens() {
        return tokens;
    }

    public void setTokens(List<ExplainAnalyzeToken> tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return "AnalyzerDetail{" +
                "name='" + name + '\'' +
                ", tokens=" + tokens +
                '}';
    }

}
