package org.shogakuframework.inject;

import org.shogakuframework.core.BeanContainer;
import org.shogakuframework.inject.annotations.AutoWired;
import org.shogakuframework.utils.ClassUtil;
import org.shogakuframework.utils.Validator;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author throne
 * @packageName org.shogakuframework.inject.annotations
 * @className DependencyInjector
 * @date 2020/5/15 9:39
 * @description
 */
public class DependencyInjector {
    private BeanContainer beanContainer;


    public DependencyInjector() {
        beanContainer = BeanContainer.getInstance();
    }

    /**
     * @param:
     * @return:
     * @description: 遍历容器中的Class的成员变量，找出其中被AutoWired注解的进行注入
     */
    public void doIoc(boolean accessible) throws Exception {
        Set<Class<?>> classSet = beanContainer.getClasses();
        if (Validator.isEmptySet(classSet)) {
            return;
        }
        for (Class<?> clazz : classSet) {
            Field[] fields = clazz.getDeclaredFields();
            if (Validator.isEmptyList(fields)) {
                continue;
            }
            for (Field field : fields) {
                if (field.isAnnotationPresent(AutoWired.class)) {
                    Class<?> fieldClass = field.getType();
                    AutoWired autoWired = field.getAnnotation(AutoWired.class);
                    String autoWiredValue = autoWired.value();
                    Object fieldInstance = getFieldInstance(fieldClass, autoWiredValue);
                    if (fieldInstance == null) {
                        throw new Exception("无法获取" + fieldClass.getName() + "的实例, 注入失败");
                    } else {
                        Object targetBean = beanContainer.getBean(clazz);
                        ClassUtil.setField(targetBean, field, fieldInstance, accessible);
                    }
                }

            }

        }
    }

    /**
     * @param:
     * @return:
     * @description: 根据Field的Class对象，获取其实例
     */
    private Object getFieldInstance(Class<?> fieldClass, String autoWiredValue) {
        /*
         * 首先判断需要注入的Field在beanMap中有没有实例，如果有可以直接返回
         * 如果没有，那么需要判断这个被@AutoWired标记的Field是不是接口，如果是接口，则需要从容器里找到其实现类
         */
        Object fieldInstance = beanContainer.getBean(fieldClass);
        if (fieldInstance != null) {
            return fieldInstance;
        } else {
            Class<?> implementedClass = getImplementedClass(fieldClass, autoWiredValue);
            if (implementedClass != null) {
                return beanContainer.getBean(implementedClass);
            } else {
                return null;
            }
        }
    }

    /**
     * @param:
     * @return:
     * @description: 从一个接口获取其实现类
     */
    private Class<?> getImplementedClass(Class<?> fieldClass, String autoWiredValue) {
        Set<Class<?>> classSet = beanContainer.getClassBySuper(fieldClass, false);
        if (Validator.isEmptySet(classSet)) {
            return null;
        } else {
            // todo: Spring还考虑了多个实现类的情况，@Qualifier
            if ("".equals(autoWiredValue)) {
                if (classSet.size() == 1) {
                    return classSet.iterator().next();
                } else {
                    throw new RuntimeException(fieldClass.getName() + "存在多个实现类，但未指定需要注入哪一个");
                }
            } else {
                for (Class<?> clazz : classSet) {
                    if (autoWiredValue.equals(clazz.getSimpleName())) {
                        return clazz;
                    }
                }
                throw new RuntimeException("未获取到名为" + autoWiredValue + "类型为" + fieldClass.getName() + "的变量");
            }
        }
    }

}
