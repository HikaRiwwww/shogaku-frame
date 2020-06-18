package org.shogakuframework.aop;

import org.shogakuframework.aop.annotation.Aspect;
import org.shogakuframework.aop.annotation.Order;
import org.shogakuframework.aop.aspect.AspectInfo;
import org.shogakuframework.aop.aspect.DefaultAspect;
import org.shogakuframework.core.BeanContainer;
import org.shogakuframework.utils.Validator;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * org.shogakuframework.aop
 * Created by throne on 2020/6/9
 */
public class Weaver {
    private BeanContainer beanContainer;

    public Weaver() {
        this.beanContainer = BeanContainer.getInstance();
    }

    /**
     * 执行切面织入的主要方法
     */
    public void doAop() {
        HashMap<Class<? extends Annotation>, List<AspectInfo>> categorizedMap = new HashMap<>();
        // 获取所有被@Aspect标记的类
        Set<Class<?>> aspectSet = beanContainer.getClassByAnnotation(Aspect.class);
        if (Validator.isEmpty(aspectSet)) {
            return;
        }
        List<AspectInfo> aspectInfoList = packAsAspectInfoList(aspectSet);
        Set<Class<?>> classes = beanContainer.getClasses();
        for (Class<?> clazz : classes) {
            if (!clazz.isAnnotationPresent(Aspect.class)) {
                List<AspectInfo> roughMatchedList = collectRoughMatchedForSpecificClass(aspectInfoList, clazz);
                // 织入
                wrapIfNecessary(roughMatchedList, clazz);
            }
        }
    }

    private void wrapIfNecessary(List<AspectInfo> roughMatchedList, Class<?> clazz) {
        if (!Validator.isEmpty(roughMatchedList)) {
            ListableAspectExecutor listableAspectExecutor = new ListableAspectExecutor(clazz, roughMatchedList);
            Object bean = ProxyCreator.createProxy(clazz, listableAspectExecutor);
            beanContainer.addBean(clazz, bean);
        }
    }

    private List<AspectInfo> collectRoughMatchedForSpecificClass(List<AspectInfo> aspectInfoList, Class<?> clazz) {
        List<AspectInfo> roughMatchedList = new ArrayList<>();
        for (AspectInfo aspectInfo : aspectInfoList) {
            if (aspectInfo.getPointcutLocator().roughMatches(clazz)) {
                roughMatchedList.add(aspectInfo);
            }
        }
        return roughMatchedList;
    }

    private List<AspectInfo> packAsAspectInfoList(Set<Class<?>> aspectSet) {
        List<AspectInfo> aspectInfoList = new ArrayList<>();
        for (Class<?> aspect : aspectSet) {
            if (isLegalAspect(aspect)) {
                Aspect aspectTag = aspect.getAnnotation(Aspect.class);
                Order orderTag = aspect.getAnnotation(Order.class);
                DefaultAspect defaultAspect = (DefaultAspect) beanContainer.getBean(aspect);
                AspectInfo aspectInfo = new AspectInfo(defaultAspect, orderTag.value(), new PointcutLocator(aspectTag.pointcut()));
                aspectInfoList.add(aspectInfo);
            } else {
                throw new RuntimeException("当前类缺少@Order注解，或未继承自DefaultAspect类");
            }
        }
        return aspectInfoList;

    }

    private void weaveByCategory(Class<? extends Annotation> category, List<AspectInfo> aspectInfos) {
        Set<Class<?>> classSet = beanContainer.getClassByAnnotation(category);
        if (!Validator.isEmpty(classSet)) {
            for (Class<?> clazz : classSet) {
                ListableAspectExecutor aspectExecutor = new ListableAspectExecutor(clazz, aspectInfos);
                Object proxyBean = ProxyCreator.createProxy(clazz, aspectExecutor);
                beanContainer.addBean(clazz, proxyBean);
            }
        }
    }

    /**
     * 验证一个类是否符合被织入前的基本条件
     * 被@Aspect和@Order所标记
     * 同DefaultAspect有继承关系
     * Aspect标记中的value不能是Aspect类本身，否则陷入死循环
     *
     * @param aspectClazz Class类型的对象
     * @return 符合 或 不符合
     */
    private boolean isLegalAspect(Class<?> aspectClazz) {
        return aspectClazz.isAnnotationPresent(Aspect.class) &&
                aspectClazz.isAnnotationPresent(Order.class) &&
                DefaultAspect.class.isAssignableFrom(aspectClazz);
    }

}
