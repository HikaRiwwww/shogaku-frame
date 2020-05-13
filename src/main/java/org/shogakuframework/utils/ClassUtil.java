package org.shogakuframework.utils;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author throne
 * @packageName org.shogakuframework.utils
 * @className ClassUtil
 * @date 2020/5/13 14:34
 * @description 提供类相关的工具方法
 */
public class ClassUtil {
    public static final String FILE_PROTOCOL = "file";

    /**
    * @param:
    * @return:
    * @description: 通过递归遍历文件路径，筛选出.class文件并放入classSet中
    */ 
    private static void recursionForClassFile(Set<Class<?>> classSet, File file, String packageName) {
        if (file.isDirectory()) {
            File[] files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (file.isDirectory()) {
                        return true;
                    } else {
                        String fileAbsolutePath = file.getAbsolutePath();
                        if (fileAbsolutePath.endsWith(".class")) {
                            addClassToSet(fileAbsolutePath);
                        }
                    }
                    return false;
                }

                private void addClassToSet(String fileAbsolutePath) {
                    fileAbsolutePath = fileAbsolutePath.replace(File.separator, ".");
                    int startIndex = fileAbsolutePath.indexOf(packageName);
                    int endIndex = fileAbsolutePath.lastIndexOf(".");
                    String classFullName = fileAbsolutePath.substring(startIndex, endIndex);
                    System.out.println(classFullName);
                    Class<?> targetClass = loadClassByName(classFullName);
                    classSet.add(targetClass);
                }


            });
            if (files != null) {
                for (File subFile : files) {
                    recursionForClassFile(classSet, subFile, packageName);
                }
            }

        }

    }

    private static Class<?> loadClassByName(String classFullName) {
        try {
            return Class.forName(classFullName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param:
     * @return: 类加载器
     * @description: 获取当前线程中的类加载器，从而可以通过类加载器获取资源路径
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public Set<Class<?>> extractPackageClass(String packageName) throws Exception {
        ClassLoader classLoader = getClassLoader();
        URL url = classLoader.getResource(packageName.replace(".", File.separator));
        if (url == null) {
            throw new Exception("给定的包路径不存在:" + packageName);
        }
        if (!url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
            throw new Exception("给定的包路径协议不是file");
        }
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        File packageDirectory = new File(url.getPath());
        recursionForClassFile(classSet, packageDirectory, packageName);


        return classSet;
    }

}
