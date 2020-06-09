package org.shogakuframework.aop.aspect;

import java.lang.reflect.Method;

/**
 * 每一个DefaultAspect实例即代表了一次切面织入的行为
 * 模拟事前，事后以及异常时三种织入情景
 * 定义三个钩子方法给子类按需实现
 * org.shogakuframework.aop.aspect
 * Created by throne on 2020/6/9
 */
public abstract class DefaultAspect {
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {

    }

    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) {
        return returnValue;
    }

    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable throwable) throws Throwable {

    }
}
