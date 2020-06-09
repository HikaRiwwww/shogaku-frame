package org.shogakuframework.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.shogakuframework.aop.aspect.AspectInfo;
import org.shogakuframework.utils.Validator;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 按优先级顺序执行织入方法的类
 * 继承了MethodInterceptor接口，从而实现了cglib动态代理的关键方法
 * org.shogakuframework.aop.aspect
 * Created by throne on 2020/6/9
 */
public class ListableAspectExecutor implements MethodInterceptor {
    private final Class<?> targetClass;
    private final List<AspectInfo> sortedAspectInfoList;

    public ListableAspectExecutor(Class<?> targetClass, List<AspectInfo> aspectInfoList) {
        this.targetClass = targetClass;
        this.sortedAspectInfoList = sorted(aspectInfoList);
    }

    /**
     * 在构造实例时，对aspectInfoList进行优先级排序，
     * AspectInfo对象的orderIndex值越小，优先级越高
     *
     * @param aspectInfoList 原始的未经排序的AspectInfo集合
     * @return 正序排列后的AspectInfo集合
     */
    private List<AspectInfo> sorted(List<AspectInfo> aspectInfoList) {
        Collections.sort(aspectInfoList, new Comparator<AspectInfo>() {
            @Override
            public int compare(AspectInfo o1, AspectInfo o2) {
                return o1.getOrderIndex() - o2.getOrderIndex();
            }
        });
        return aspectInfoList;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        // 按照order的顺序执行所有before()
        // 正常返回则依次执行afterReturning()
        // 抛出异常则依次执行afterThrowing()
        Object returnValue = null;
        if (Validator.isEmptyList(sortedAspectInfoList)) {
            return null;
        }
        invokeBeforeAdvices(method, args);
        try {
            returnValue = methodProxy.invokeSuper(proxy, args);
            returnValue = invokeAfterReturningAdvices(method, args, returnValue);
            return returnValue;
        } catch (Exception e) {
            invokeAfterThrowingAdvices(method, args, e);
        }
        return returnValue;
    }

    private void invokeAfterThrowingAdvices(Method method, Object[] args, Exception e) throws Throwable {
        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
            sortedAspectInfoList.get(i).getDefaultAspect().
                    afterThrowing(targetClass, method, args, e);
        }
    }

    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) {
        // todo: 为什么要倒序执行？
        // todo: 可不可以不另外声明retValToUse而是直接使用returnValue?
        Object retValToUse = null;
        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
            retValToUse = sortedAspectInfoList.get(i).getDefaultAspect().
                    afterReturning(targetClass, method, args, returnValue);
        }
        return retValToUse;
    }

    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable {
        for (AspectInfo aspectInfo : sortedAspectInfoList) {
            aspectInfo.getDefaultAspect().before(targetClass, method, args);
        }
    }
}
