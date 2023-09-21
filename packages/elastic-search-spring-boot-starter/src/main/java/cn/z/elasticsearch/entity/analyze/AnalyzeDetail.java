package cn.z.elasticsearch.entity.analyze;

import java.util.List;

/**
 * <h1>分析细节</h1>
 *
 * <p>
 * createDate 2023/09/12 17:47:34
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class AnalyzeDetail {

    /**
     * 分析器细节
     */
    private AnalyzerDetail analyzer;
    /**
     * 字符拦截细节
     */
    private List<CharFilterDetail> charfilters;
    /**
     * 自定义分析器
     */
    private boolean customAnalyzer;
    /**
     * Token细节拦截器
     */
    private List<TokenDetail> tokenfilters;
    /**
     * Token细节
     */
    private TokenDetail tokenizer;

    public AnalyzeDetail() {
    }

    public AnalyzeDetail(co.elastic.clients.elasticsearch.indices.analyze.AnalyzeDetail detail) {
        if (detail.analyzer() != null) {
            this.analyzer = new AnalyzerDetail(detail.analyzer());
        }
        this.charfilters = CharFilterDetail.toList(detail.charfilters());
        this.customAnalyzer = detail.customAnalyzer();
        this.tokenfilters = TokenDetail.toList(detail.tokenfilters());
        if (detail.tokenizer() != null) {
            this.tokenizer = new TokenDetail(detail.tokenizer());
        }
    }

    public AnalyzerDetail getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(AnalyzerDetail analyzer) {
        this.analyzer = analyzer;
    }

    public List<CharFilterDetail> getCharfilters() {
        return charfilters;
    }

    public void setCharfilters(List<CharFilterDetail> charfilters) {
        this.charfilters = charfilters;
    }

    public boolean isCustomAnalyzer() {
        return customAnalyzer;
    }

    public void setCustomAnalyzer(boolean customAnalyzer) {
        this.customAnalyzer = customAnalyzer;
    }

    public List<TokenDetail> getTokenfilters() {
        return tokenfilters;
    }

    public void setTokenfilters(List<TokenDetail> tokenfilters) {
        this.tokenfilters = tokenfilters;
    }

    public TokenDetail getTokenizer() {
        return tokenizer;
    }

    public void setTokenizer(TokenDetail tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public String toString() {
        return "AnalyzeDetail{" +
                "analyzer=" + analyzer +
                ", charfilters=" + charfilters +
                ", customAnalyzer=" + customAnalyzer +
                ", tokenfilters=" + tokenfilters +
                ", tokenizer=" + tokenizer +
                '}';
    }

}
