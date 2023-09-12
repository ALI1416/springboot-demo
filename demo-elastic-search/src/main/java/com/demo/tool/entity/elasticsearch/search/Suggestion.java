package com.demo.tool.entity.elasticsearch.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>建议</h1>
 *
 * <p>
 * createDate 2023/09/12 15:31:39
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Suggestion<T> {

    /**
     * 类型
     */
    public enum Kind {

        /**
         * 完成
         */
        Completion("completion"),
        /**
         * 短语
         */
        Phrase("phrase"),
        /**
         * 条款
         */
        Term("term");

        private final String jsonValue;

        Kind(String jsonValue) {
            this.jsonValue = jsonValue;
        }

        public static Kind toEnum(co.elastic.clients.elasticsearch.core.search.Suggestion.Kind kind) {
            switch (kind.name()) {
                case "Completion":
                    return Kind.Completion;
                case "Phrase":
                    return Kind.Phrase;
                case "Term":
                    return Kind.Term;
                default:
                    return null;
            }
        }

        public String getJsonValue() {
            return jsonValue;
        }

        @Override
        public String toString() {
            return "Kind{" +
                    "jsonValue='" + jsonValue + '\'' +
                    '}';
        }

    }

    /**
     * 种类
     */
    private Suggestion.Kind kind;
    /**
     * 值
     */
    private SuggestionVariant value;

    public Suggestion() {
    }

    public Suggestion(co.elastic.clients.elasticsearch.core.search.Suggestion<T> suggestion) {
        this.kind = Kind.toEnum(suggestion._kind());
        co.elastic.clients.elasticsearch.core.search.SuggestionVariant variant = suggestion._get();
        if (variant instanceof co.elastic.clients.elasticsearch.core.search.CompletionSuggest) {
            this.value = new CompletionSuggest<>((co.elastic.clients.elasticsearch.core.search.CompletionSuggest<T>) variant);
        } else if (variant instanceof co.elastic.clients.elasticsearch.core.search.PhraseSuggest) {
            this.value = new PhraseSuggest((co.elastic.clients.elasticsearch.core.search.PhraseSuggest) variant);
        } else if (variant instanceof co.elastic.clients.elasticsearch.core.search.TermSuggest) {
            this.value = new TermSuggest((co.elastic.clients.elasticsearch.core.search.TermSuggest) variant);
        }
    }

    public static <T> Map<String, List<Suggestion<T>>> toMap(Map<String, List<co.elastic.clients.elasticsearch.core.search.Suggestion<T>>> suggestionMap) {
        Map<String, List<Suggestion<T>>> map = new HashMap<>();
        suggestionMap.forEach((key, value) -> map.put(key, Suggestion.toList(value)));
        return map;
    }

    public static <T> List<Suggestion<T>> toList(List<co.elastic.clients.elasticsearch.core.search.Suggestion<T>> suggestionList) {
        List<Suggestion<T>> list = new ArrayList<>();
        for (co.elastic.clients.elasticsearch.core.search.Suggestion<T> suggestion : suggestionList) {
            list.add(new Suggestion<>(suggestion));
        }
        return list;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public SuggestionVariant getValue() {
        return value;
    }

    public void setValue(SuggestionVariant value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "kind=" + kind +
                ", value=" + value +
                '}';
    }

}
