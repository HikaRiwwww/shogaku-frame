package org.shogakuframework.core;

import org.shogakuframework.core.annotations.Component;
import org.shogakuframework.core.annotations.Controller;
import org.shogakuframework.core.annotations.Repository;
import org.shogakuframework.core.annotations.Service;
import org.shogakuframework.utils.ClassUtil;
import org.shogakuframework.utils.Validator;

import java.lang.annotation.Annotation;
import java.util.*;
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

    public Object addBean(Class<?> clazz, Object bean) {
        return beanMap.put(clazz, bean);
    }

    public Object removeBean(Class<?> clazz) {
        return beanMap.remove(clazz);
    }

    public Object getBean(Class<?> clazz) {
        return beanMap.get(clazz);
    }

    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    public Set<Object> getBeans() {
        return new HashSet<>(beanMap.values());
    }

    /**
     * @param:
     * @return: null || 长度大于0的Set<Class<?>>
     * @description: 根据注解获取beanMap中对应的Class集合
     */
    public Set<Class<?>> getClassByAnnotation(Class<? extends Annotation> annotation) {
        if (Validator.isEmptyMap(beanMap)) {
            return null;
        }
        HashSet<Class<?>> hashSet = new HashSet<>();
        Set<Class<?>> classSet = getClasses();
        if (Validator.isEmptySet(classSet)){
            return null;
        }
        for (Class<?> clazz : classSet) {
            if (clazz.isAnnotationPresent(annotation)) {
                hashSet.add(clazz);
            }
        }
        return hashSet.size() > 0 ? hashSet : null;
    }

    /**
     * @param: boolean selfInclude 如果传入的类本身也在容器中，选择是否需要包含在返回结果中
     * @return: null || 长度大于0的Set<Class<?>>
     * @description: 根据父类或接口获取beanMap中的子类或实现类，可以选择是否包括该接口或父类本身
     */
    public Set<Class<?>> getClassBySuper(Class<?> interfaceOrClass, boolean selfInclude) {
        if (Validator.isEmptyMap(beanMap)) {
            return null;
        }
        HashSet<Class<?>> hashSet = new HashSet<>();
        Set<Class<?>> classSet = getClasses();
        if (Validator.isEmptySet(classSet)){
            return null;
        }
        for (Class<?> clazz : classSet) {
            if (interfaceOrClass.isAssignableFrom(clazz)) {
                if (clazz.equals(interfaceOrClass)) {
                    if (selfInclude) {
                        hashSet.add(clazz);
                    }
                } else {
                    hashSet.add(clazz);
                }
            }
        }

        return hashSet.size() > 0 ? hashSet : null;
    }

    /**
     * 通过枚举保证容器是单例
     */
    private enum ContainerHolder {
        HOLDER;
        private BeanContainer instance;

        ContainerHolder() {
            this.instance = new BeanContainer();
        }
    }

}
