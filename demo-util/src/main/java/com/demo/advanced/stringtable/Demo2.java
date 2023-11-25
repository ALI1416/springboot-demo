package com.demo.advanced.stringtable;

/**
 * <h1>Demo2</h1>
 *
 * <p>
 * createDate 2022/06/14 10:52:28
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Demo2 {

    public static void main(String[] args) {
        /*1*/
        // StringTable中只有a和b，没有ab
        String s1 = new String("a") + new String("b");
        // 调用intern方法时，查询ab是否在StringTable中，
        // 如果没有，将对象s1插入，并返回s1，
        // 如果有，直接返回已存在的对象
        String s2 = s1.intern();
        // 返回true，因为StringTable中ab的对象是s1，此时"ab"就是s1
        System.out.println(s1 == "ab");
        // 返回true，因为s2就是s1
        System.out.println(s2 == "ab");

        /*2*/
        String s3 = new String("c") + new String("d");
        String s4 = "cd";
        String s5 = s3.intern();
        // 返回false，由于"cd"已存在于StringTable中，对象是s4
        System.out.println(s3 == "cd");
        // 返回true，因为"ab"就是s4
        System.out.println(s4 == "cd");
        // 返回true，因为s5就是s4
        System.out.println(s5 == "cd");
    }

}
