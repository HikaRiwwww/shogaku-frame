package com.throne.controller.aop_demo;

import org.shogakuframework.aop.annotation.Aspect;
import org.shogakuframework.aop.annotation.Order;
import org.shogakuframework.aop.aspect.DefaultAspect;
import org.shogakuframework.core.annotations.Controller;

import java.lang.reflect.Method;

@Aspect(pointcut = "within(com.throne.controller.aop_demo.*)")
@Order(value = 1)
public class ControllerAspect2 extends DefaultAspect {
    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        System.out.println("Aspect2前置通知执行，优先级1");
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) {
        System.out.println("Aspect2后置通知执行，优先级1");
        return super.afterReturning(targetClass, method, args, returnValue);
    }

    @Override
    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable throwable) throws Throwable {
        super.afterThrowing(targetClass, method, args, throwable);
    }
}