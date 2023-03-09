package com.demo.util.ansj;

import org.ansj.splitWord.analysis.ToAnalysis;

/**
 * <h1>Ansj工具</h1>
 *
 * <p>
 * createDate 2022/02/22 10:48:34
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class AnsjUtils {

    /**
     * 获取ansj分词
     *
     * @param s 要分词的字符串
     * @see org.ansj.splitWord.analysis.ToAnalysis#parse(String)
     */
    public static String getAnsj(String s) {
        return ToAnalysis.parse(s) // 标准分词
                .toStringWithOutNature(" ") // 没有词性
                .replaceAll("[\\p{P}`=~$^+|<>￥｀＝～＄＾＋｜＜＞]", "") // 去掉中英文标点符号
                .replace("\u3000", " ") // 全角空格转为英文空格
                .replaceAll(" +", " ") // 多个空格合成1个
                .trim(); // 去除首尾空格
    }

}
