package cn.z.elasticsearch.entity.search;

import java.util.List;

/**
 * <h1>短语建议</h1>
 *
 * <p>
 * createDate 2023/09/12 16:22:55
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class PhraseSuggest extends SuggestBase implements SuggestionVariant {

    /**
     * 短语建议选项
     */
    private List<PhraseSuggestOption> options;

    /**
     * 建议类型
     */
    @Override
    public Suggestion.Kind suggestionKind() {
        return Suggestion.Kind.Phrase;
    }

    public PhraseSuggest() {
    }

    public PhraseSuggest(co.elastic.clients.elasticsearch.core.search.PhraseSuggest suggest) {
        super(suggest);
        this.options = PhraseSuggestOption.toList(suggest.options());
    }

    public List<PhraseSuggestOption> getOptions() {
        return options;
    }

    public void setOptions(List<PhraseSuggestOption> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "PhraseSuggest{" +
                "options=" + options +
                '}';
    }

}
