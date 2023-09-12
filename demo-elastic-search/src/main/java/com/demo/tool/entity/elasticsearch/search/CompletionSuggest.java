package com.demo.tool.entity.elasticsearch.search;

import java.util.List;

/**
 * <h1>完成建议</h1>
 *
 * <p>
 * createDate 2023/09/12 15:53:29
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class CompletionSuggest<T> extends SuggestBase implements SuggestionVariant {

    /**
     * 完成建议选项
     */
    private List<CompletionSuggestOption<T>> options;

    @Override
    public Suggestion.Kind suggestionKind() {
        return Suggestion.Kind.Completion;
    }

    public CompletionSuggest() {
    }

    public CompletionSuggest(co.elastic.clients.elasticsearch.core.search.CompletionSuggest<T> suggest) {
        super(suggest);
        this.options = CompletionSuggestOption.toList(suggest.options());
    }

    public List<CompletionSuggestOption<T>> getOptions() {
        return options;
    }

    public void setOptions(List<CompletionSuggestOption<T>> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "CompletionSuggest{" +
                "options=" + options +
                '}';
    }

}
