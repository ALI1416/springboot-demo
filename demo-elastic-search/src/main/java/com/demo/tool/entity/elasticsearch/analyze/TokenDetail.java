package com.demo.tool.entity.elasticsearch.analyze;


import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Token细节</h1>
 *
 * <p>
 * createDate 2023/09/12 17:43:57
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class TokenDetail {

    /**
     * 名称
     */
    private String name;
    /**
     * 解释分析Token
     */
    private List<ExplainAnalyzeToken> tokens;

    public TokenDetail() {
    }

    public TokenDetail(co.elastic.clients.elasticsearch.indices.analyze.TokenDetail detail) {
        this.name = detail.name();
        this.tokens = ExplainAnalyzeToken.toList(detail.tokens());
    }

    public static List<TokenDetail> toList(List<co.elastic.clients.elasticsearch.indices.analyze.TokenDetail> detailList) {
        List<TokenDetail> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.indices.analyze.TokenDetail detail : detailList) {
            list.add(new TokenDetail(detail));
        }
        return list;
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
        return "TokenDetail{" +
                "name='" + name + '\'' +
                ", tokens=" + tokens +
                '}';
    }

}
