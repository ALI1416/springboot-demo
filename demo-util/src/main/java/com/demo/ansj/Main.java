package com.demo.ansj;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>Ansj</h1>
 *
 * <p>
 * createDate 2022/02/22 10:47:54
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        String s1 = "1234567890" + // 数字
                "qwertyuiopasdfghjklzxcvbnm" + // 英文字母
                "QWERTYUIOPASDFGHJKLZXCVBNM" + //
                "`-=[]\\;',./" + // 符号
                "~!@#$%^&*()_+{}|:\"<>?" + //
                "    " + // 空格
                "广西壮族自治区桂林市七星区桂林航天工业学院南校区"; // 汉字
        String s2 = "１２３４５６７８９０" + // 全角数字
                "·-=【】、；‘，。、" + // 中文符号
                "~！@#￥%……&*（）——+{}|：“《》？" + //
                "｀－＝［］＼；＇，．／" + // 全角中文符号
                "～！＠＃＄％＾＆＊（）＿＋｛｝｜：＂＜＞？" + //
                "　全　角　　　空格　" + // 全角空格
                "广西壮族自治区桂林市七星区桂林航天工业学院南校区"; // 汉字
        log.info(AnsjUtils.getAnsj(s1));
        log.info(AnsjUtils.getAnsj(s2));
    }

}
