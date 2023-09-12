package com.demo.tool.entity.elasticsearch.search;

import java.util.List;

/**
 * <h1>条款建议</h1>
 *
 * <p>
 * createDate 2023/09/12 16:27:22
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class TermSuggest extends SuggestBase implements SuggestionVariant {

    /**
     * 条款建议选项
     */
    private List<TermSuggestOption> options;

    @Override
    public Suggestion.Kind suggestionKind() {
        return Suggestion.Kind.Term;
    }

    public TermSuggest() {
    }

    public TermSuggest(co.elastic.clients.elasticsearch.core.search.TermSuggest suggest) {
        super(suggest);
        this.options = TermSuggestOption.toList(suggest.options());
    }

    public List<TermSuggestOption> getOptions() {
        return options;
    }

    public void setOptions(List<TermSuggestOption> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "TermSuggest{" +
                "options=" + options +
                '}';
    }

}
