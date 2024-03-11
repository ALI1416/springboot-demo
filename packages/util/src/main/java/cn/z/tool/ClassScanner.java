package cn.z.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <h1>类扫描器</h1>
 *
 * <p>
 * 根据<a href="https://github.com/dromara/hutool/blob/5.8.22/hutool-core/src/main/java/cn/hutool/core/lang/ClassScanner.java">dromara/hutool</a>重构
 * </p>
 *
 * <p>
 * createDate 2023/12/13 15:30:59
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class ClassScanner {

    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(ClassScanner.class);

    private ClassScanner() {
    }

    /**
     * 获取类
     *
     * @param packageName 包名
     * @return 类集合(失败的不放入)
     */
    public static Set<Class<?>> getClass(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        for (String className : getClassName(packageName)) {
            try {
                classSet.add(Class.forName(className));
            } catch (Throwable e) {
                log.error("获取类[{}]失败", className, e);
            }
        }
        return classSet;
    }

    /**
     * 获取类名
     *
     * @param packageName 包名
     * @return 类名集合
     */
    public static Set<String> getClassName(String packageName) {
        Set<String> classNameSet = new HashSet<>();
        String packagePath = packageName.replace('.', '/');
        URL url = Thread.currentThread().getContextClassLoader().getResource(packagePath);
        if (url != null) {
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                scanFile(packageName, new File(url.getFile()), classNameSet);
            } else if ("jar".equals(protocol)) {
                try {
                    scanJar(((JarURLConnection) url.openConnection()).getJarFile(), packagePath, classNameSet);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return classNameSet;
    }

    /**
     * 扫描文件
     *
     * @param packageName  包名
     * @param directory    目录
     * @param classNameSet 类名集合
     */
    private static void scanFile(String packageName, File directory, Set<String> classNameSet) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                if (file.isDirectory()) {
                    scanFile(packageName + "." + fileName, file, classNameSet);
                } else if (file.getName().endsWith(".class")) {
                    classNameSet.add(packageName + "." + fileName.substring(0, fileName.length() - 6));
                }
            }
        }
    }

    /**
     * 扫描jar文件
     *
     * @param jarFile      jar文件
     * @param packagePath  包路径
     * @param classNameSet 类名集合
     */
    private static void scanJar(JarFile jarFile, String packagePath, Set<String> classNameSet) {
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String entryName = entry.getName();
            if (!entry.isDirectory() && entryName.startsWith(packagePath) && entryName.endsWith(".class")) {
                classNameSet.add(entryName.substring(0, entryName.length() - 6).replace('/', '.'));
            }
        }
    }

}
