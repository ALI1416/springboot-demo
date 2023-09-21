package cn.z.elasticsearch.entity.search;

/**
 * <h1>查询细分</h1>
 *
 * <p>
 * createDate 2023/09/12 14:49:10
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class QueryBreakdown {

    /**
     * 推进
     */
    private long advance;
    /**
     * 推进总数
     */
    private long advanceCount;
    /**
     * 构建计分器
     */
    private long buildScorer;
    /**
     * 构建计分器总数
     */
    private long buildScorerCount;
    /**
     * 创建权重
     */
    private long createWeight;
    /**
     * 创建权重总数
     */
    private long createWeightCount;
    /**
     * 匹配
     */
    private long match;
    /**
     * 匹配总数
     */
    private long matchCount;
    /**
     * 浅层推进
     */
    private long shallowAdvance;
    /**
     * 浅层推进总数
     */
    private long shallowAdvanceCount;
    /**
     * 下一个文档
     */
    private long nextDoc;
    /**
     * 下一个文档总数
     */
    private long nextDocCount;
    /**
     * 分数
     */
    private long score;
    /**
     * 分数总数
     */
    private long scoreCount;
    /**
     * 计算最大分数
     */
    private long computeMaxScore;
    /**
     * 计算最大分数总数
     */
    private long computeMaxScoreCount;
    /**
     * 设置最低竞争分数
     */
    private long setMinCompetitiveScore;
    /**
     * 设置最低竞争分数总数
     */
    private long setMinCompetitiveScoreCount;

    public QueryBreakdown() {
    }

    public QueryBreakdown(co.elastic.clients.elasticsearch.core.search.QueryBreakdown breakdown) {
        this.advance = breakdown.advance();
        this.advanceCount = breakdown.advanceCount();
        this.buildScorer = breakdown.buildScorer();
        this.buildScorerCount = breakdown.buildScorerCount();
        this.createWeight = breakdown.createWeight();
        this.createWeightCount = breakdown.createWeightCount();
        this.match = breakdown.match();
        this.matchCount = breakdown.matchCount();
        this.shallowAdvance = breakdown.shallowAdvance();
        this.shallowAdvanceCount = breakdown.shallowAdvanceCount();
        this.nextDoc = breakdown.nextDoc();
        this.nextDocCount = breakdown.nextDocCount();
        this.score = breakdown.score();
        this.scoreCount = breakdown.scoreCount();
        this.computeMaxScore = breakdown.computeMaxScore();
        this.computeMaxScoreCount = breakdown.computeMaxScoreCount();
        this.setMinCompetitiveScore = breakdown.setMinCompetitiveScore();
        this.setMinCompetitiveScoreCount = breakdown.setMinCompetitiveScoreCount();
    }

    public long getAdvance() {
        return advance;
    }

    public void setAdvance(long advance) {
        this.advance = advance;
    }

    public long getAdvanceCount() {
        return advanceCount;
    }

    public void setAdvanceCount(long advanceCount) {
        this.advanceCount = advanceCount;
    }

    public long getBuildScorer() {
        return buildScorer;
    }

    public void setBuildScorer(long buildScorer) {
        this.buildScorer = buildScorer;
    }

    public long getBuildScorerCount() {
        return buildScorerCount;
    }

    public void setBuildScorerCount(long buildScorerCount) {
        this.buildScorerCount = buildScorerCount;
    }

    public long getCreateWeight() {
        return createWeight;
    }

    public void setCreateWeight(long createWeight) {
        this.createWeight = createWeight;
    }

    public long getCreateWeightCount() {
        return createWeightCount;
    }

    public void setCreateWeightCount(long createWeightCount) {
        this.createWeightCount = createWeightCount;
    }

    public long getMatch() {
        return match;
    }

    public void setMatch(long match) {
        this.match = match;
    }

    public long getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(long matchCount) {
        this.matchCount = matchCount;
    }

    public long getShallowAdvance() {
        return shallowAdvance;
    }

    public void setShallowAdvance(long shallowAdvance) {
        this.shallowAdvance = shallowAdvance;
    }

    public long getShallowAdvanceCount() {
        return shallowAdvanceCount;
    }

    public void setShallowAdvanceCount(long shallowAdvanceCount) {
        this.shallowAdvanceCount = shallowAdvanceCount;
    }

    public long getNextDoc() {
        return nextDoc;
    }

    public void setNextDoc(long nextDoc) {
        this.nextDoc = nextDoc;
    }

    public long getNextDocCount() {
        return nextDocCount;
    }

    public void setNextDocCount(long nextDocCount) {
        this.nextDocCount = nextDocCount;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(long scoreCount) {
        this.scoreCount = scoreCount;
    }

    public long getComputeMaxScore() {
        return computeMaxScore;
    }

    public void setComputeMaxScore(long computeMaxScore) {
        this.computeMaxScore = computeMaxScore;
    }

    public long getComputeMaxScoreCount() {
        return computeMaxScoreCount;
    }

    public void setComputeMaxScoreCount(long computeMaxScoreCount) {
        this.computeMaxScoreCount = computeMaxScoreCount;
    }

    public long getSetMinCompetitiveScore() {
        return setMinCompetitiveScore;
    }

    public void setSetMinCompetitiveScore(long setMinCompetitiveScore) {
        this.setMinCompetitiveScore = setMinCompetitiveScore;
    }

    public long getSetMinCompetitiveScoreCount() {
        return setMinCompetitiveScoreCount;
    }

    public void setSetMinCompetitiveScoreCount(long setMinCompetitiveScoreCount) {
        this.setMinCompetitiveScoreCount = setMinCompetitiveScoreCount;
    }

    @Override
    public String toString() {
        return "QueryBreakdown{" +
                "advance=" + advance +
                ", advanceCount=" + advanceCount +
                ", buildScorer=" + buildScorer +
                ", buildScorerCount=" + buildScorerCount +
                ", createWeight=" + createWeight +
                ", createWeightCount=" + createWeightCount +
                ", match=" + match +
                ", matchCount=" + matchCount +
                ", shallowAdvance=" + shallowAdvance +
                ", shallowAdvanceCount=" + shallowAdvanceCount +
                ", nextDoc=" + nextDoc +
                ", nextDocCount=" + nextDocCount +
                ", score=" + score +
                ", scoreCount=" + scoreCount +
                ", computeMaxScore=" + computeMaxScore +
                ", computeMaxScoreCount=" + computeMaxScoreCount +
                ", setMinCompetitiveScore=" + setMinCompetitiveScore +
                ", setMinCompetitiveScoreCount=" + setMinCompetitiveScoreCount +
                '}';
    }

}
