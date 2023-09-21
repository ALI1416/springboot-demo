package cn.z.elasticsearch.entity.search;

/**
 * <h1>建议接口</h1>
 *
 * <p>
 * createDate 2023/09/12 15:48:58
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public interface SuggestionVariant {

    /**
     * 建议类型
     */
    Suggestion.Kind suggestionKind();

}
