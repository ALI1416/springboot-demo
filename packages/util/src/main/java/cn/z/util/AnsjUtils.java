package cn.z.util;

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

    private AnsjUtils() {
    }

    /**
     * 解析
     *
     * @param string 字符串
     */
    public static String parse(String string) {
        return ToAnalysis.parse(string) // 标准分词
                .toStringWithOutNature(" ") // 没有词性
                .replaceAll("[\\p{P}`=~$^+|<>￥｀＝～＄＾＋｜＜＞]", "") // 去掉中英文标点符号
                .replace("\u3000", " ") // 全角空格转为英文空格
                .replaceAll(" +", " ") // 多个空格合成1个
                .trim(); // 去除首尾空格
    }

}
