package com.demo.stringtable;

/**
 * <h1>Demo1</h1>
 *
 * <p>
 * createDate 2022/06/12 20:31:52
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class Demo1 {
    // 字节码中的`常量`位于`常量池(Constant pool)`中，
    // 只有执行到该行代码时，才将`常量`放入到`运行时常量池(StringTable)`中(懒加载)
    // StringTable的数据结构类似于哈希表

    // 使用javap -v Demo1.class进行反编译
    // 找到主方法，查看字节码
    public static void main(String[] args) {
        /*1*/
        // 到`常量池(Constant pool)`#7加载信息，得到符号a，
        // 从StringTable查找字符串a，找不到，
        // 创建一个对象new String("a")放入StringTable到并返回
        // 0: ldc           #7                  // String a
        // 保存到`方法局部变量(LocalVariableTable)`1(Slot为1)中
        // 2: astore_1
        String s1 = "a";

        // 3: ldc           #9                  // String b
        // 5: astore_2
        String s2 = "b";

        // 6: ldc           #11                 // String ab
        // 8: astore_3
        String s3 = "ab";

        // 创建一个对象 new StringBuilder
        // 9: new           #13                 // class java/lang/StringBuilder
        // 12: dup
        // 调用一个特殊方法：无参构造方法("<init>":()) new StringBuilder()
        // 13: invokespecial #15                 // Method java/lang/StringBuilder."<init>":()V
        // 从`方法局部变量`1中加载变量
        // 16: aload_1
        // 调用方法：append:(Ljava/lang/String;)并把参数传入 new StringBuilder().append("a")
        // 17: invokevirtual #16                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)
        // Ljava/lang/StringBuilder;
        // 从`方法局部变量`2中加载变量
        // 20: aload_2
        // 调用方法：append:(Ljava/lang/String;)并把参数传入 new StringBuilder().append("a").append("b")
        // 21: invokevirtual #16                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)
        // Ljava/lang/StringBuilder;
        // 调用方法：toString:() new StringBuilder().append("a").append("b").toString()
        // 24: invokevirtual #20                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        // 保存到`方法局部变量`4中
        // 27: astore        4
        // 参考StringBuilder.toString()可知，创建了一个新的String对象，
        // 即相当于new String("ab")
        String s4 = s1 + s2;

        // 由于StringTable中已经有字符串"ab"了，所以直接返回该对象
        // 29: ldc           #11                 // String ab
        // 31: astore        5
        String s5 = "ab";

        // 编译器自动优化代码
        // "a" + "b"会变成"ab"
        // 由于StringTable中已经有字符串"ab"了，所以直接返回该对象
        // 33: ldc           #11                 // String ab
        // 35: astore        6
        String s6 = "a" + "b";

        // s3是StringTable中的对象，s4是new String("ab")，虽然值一样，但是不是同一个对象，
        // 所以为false
        System.out.println(s3 == s4);
        // s3,s5,s6都是同一个对象，
        // 所有都为true
        System.out.println(s3 == s5);
        System.out.println(s3 == s6);
        System.out.println(s5 == s6);

        /*2*/
        // 由于s4是2个变量，会进行StringBuilder拼接，导致新建一个对象，
        // 如果换成常量拼接，对进行代码优化，与s6一样
        // 106: ldc           #36                 // String c
        // 108: astore        7
        final String s7 = "c";

        // 110: ldc           #38                 // String d
        // 112: astore        8
        final String s8 = "d";

        // 114: ldc           #40                 // String cd
        // 116: astore        9
        String s9 = "cd";

        // 编译器自动优化代码
        // 118: ldc           #40                 // String cd
        // 120: astore        10
        String s10 = s7 + s8;

        // s9,s10都是同一个对象，
        // 所以为true
        System.out.println(s9 == s10);

        /*3*/
        // 140: ldc           #42                 // String e
        // 142: astore        11
        String s11 = "e";

        // 144: getstatic     #24                 // Field java/lang/System.out:Ljava/io/PrintStream;
        // 147: aload         11
        // 找到StringTable中的字符串对象并返回
        // 149: ldc           #42                 // String e
        // 进行判断
        // 151: if_acmpne     158
        // 常量true
        // 154: iconst_1
        // 跳转到159行
        // 155: goto          159
        // 常量false
        // 158: iconst_0
        // 159: invokevirtual #30                 // Method java/io/PrintStream.println:(Z)V
        // 打印true
        System.out.println(s11 == "e");

        /*4*/
        // 162: getstatic     #24                 // Field java/lang/System.out:Ljava/io/PrintStream;
        // 165: iconst_1
        // 166: invokevirtual #30                 // Method java/io/PrintStream.println:(Z)V
        // 编译器自动优化代码，
        // 直接打印true
        System.out.println("f" == "f");
    }

}
