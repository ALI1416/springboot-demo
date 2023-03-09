package com.demo.util.hutool.util;


/**
 * <h1>测试枚举</h1>
 *
 * <p>
 * createDate 2022/03/04 11:24:26
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public enum TestEnum {
    /**
     * TEST1
     */
    TEST1("type1"),
    /**
     * TEST2
     */
    TEST2("type2"),
    /**
     * TEST3
     */
    TEST3("type3");

    TestEnum(String type) {
        this.type = type;
    }

    private final String type;

    public String getType() {
        return this.type;
    }

}
