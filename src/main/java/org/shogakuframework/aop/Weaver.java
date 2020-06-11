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
        for (Class<?> aspectClazz : aspectSet) {
            // 检验这个被标记的clazz是否合法
            if (isLegalAspect(aspectClazz)) {
                categorizeAspect(categorizedMap, aspectClazz);
            } else {
                throw new RuntimeException("当前切面类不满足以下规则:" +
                        "继承DefaultAspect；被@Aspect和@Order标记；作用对象为非@Aspect注解所标记的类");
            }
        }
        if (!Validator.isEmpty(categorizedMap)) {
            for (Class<? extends Annotation> category : categorizedMap.keySet()) {
                weaveByCategory(category, categorizedMap.get(category));
            }
        }
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

    private void categorizeAspect(HashMap<Class<? extends Annotation>, List<AspectInfo>> categorizedMap, Class<?> aspectClazz) {
        Order orderTag = aspectClazz.getAnnotation(Order.class);
        Aspect aspectTag = aspectClazz.getAnnotation(Aspect.class);
        DefaultAspect aspect = (DefaultAspect) beanContainer.getBean(aspectClazz);
        AspectInfo aspectInfo = new AspectInfo(aspect, orderTag.value());
        Class<? extends Annotation> weaveTarget = aspectTag.value();
        if (!categorizedMap.containsKey(weaveTarget)) {
            List<AspectInfo> aspectInfoList = new ArrayList<>();
            aspectInfoList.add(aspectInfo);
            categorizedMap.put(weaveTarget, aspectInfoList);
        } else {
            categorizedMap.get(weaveTarget).add(aspectInfo);
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
                DefaultAspect.class.isAssignableFrom(aspectClazz) &&
                aspectClazz.getAnnotation(Aspect.class).value() != Aspect.class;
    }

}
