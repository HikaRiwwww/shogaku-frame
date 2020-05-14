package org.shogakuframework.core;

import org.shogakuframework.core.annotations.Component;
import org.shogakuframework.core.annotations.Controller;
import org.shogakuframework.core.annotations.Repository;
import org.shogakuframework.core.annotations.Service;
import org.shogakuframework.utils.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author throne
 * @packageName org.shogakuframework.core.annotations
 * @className BeanContainer
 * @date 2020/5/14 15:04
 * @description IOC容器 利用枚举类型实现容器本身的单例
 */
public class BeanContainer {
    private static final List<Class<? extends Annotation>> ANNOTATIONS =
            Arrays.asList(Repository.class, Component.class, Service.class, Controller.class);
    private final ConcurrentHashMap<Class<?>, Object> beanMap = new ConcurrentHashMap<Class<?>, Object>();
    private boolean loaded;

    private BeanContainer() {
    }

    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    public boolean isLoaded() {
        return loaded;
    }

    /**
     * @param: packageName 包名  accessible 是否实例化private类型
     * @return:
     * @description: 扫描指定包名下的所有文件获取装有Class类型的集合，将其中被指定注解修饰的Class和其实例作为键值对存入beanMap
     */
    public synchronized void loadBeans(String packageName, boolean accessible) throws Exception {
        if (isLoaded()) {
            return;
        }
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> annotation : ANNOTATIONS) {
                if (clazz.isAnnotationPresent(annotation)) {
                    beanMap.put(clazz, ClassUtil.loadInstance(clazz, accessible));
                }
            }
        }
        loaded = true;
    }

    public Integer getBeanMapSize() {
        return beanMap.size();
    }

    private enum ContainerHolder {
        HOLDER;
        private BeanContainer instance;

        ContainerHolder() {
            this.instance = new BeanContainer();
        }
    }
}
