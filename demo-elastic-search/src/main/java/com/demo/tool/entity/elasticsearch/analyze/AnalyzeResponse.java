package com.demo.tool.entity.elasticsearch.analyze;


import java.util.List;

/**
 * <h1>分析返回</h1>
 *
 * <p>
 * createDate 2023/09/12 17:55:55
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class AnalyzeResponse {

    /**
     * 分析细节
     */
    private AnalyzeDetail detail;
    /**
     * 分析Token
     */
    private List<AnalyzeToken> tokens;

    public AnalyzeResponse() {
    }

    public AnalyzeResponse(co.elastic.clients.elasticsearch.indices.AnalyzeResponse response) {
        if (response.detail() != null) {
            this.detail = new AnalyzeDetail(response.detail());
        }
        this.tokens = AnalyzeToken.toList(response.tokens());
    }

    public AnalyzeDetail getDetail() {
        return detail;
    }

    public void setDetail(AnalyzeDetail detail) {
        this.detail = detail;
    }

    public List<AnalyzeToken> getTokens() {
        return tokens;
    }

    public void setTokens(List<AnalyzeToken> tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return "AnalyzeResponse{" +
                "detail=" + detail +
                ", tokens=" + tokens +
                '}';
    }

}
