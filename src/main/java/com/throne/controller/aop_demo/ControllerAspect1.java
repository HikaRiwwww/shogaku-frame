package com.throne.controller.aop_demo;

import org.shogakuframework.aop.annotation.Aspect;
import org.shogakuframework.aop.annotation.Order;
import org.shogakuframework.aop.aspect.DefaultAspect;
import org.shogakuframework.core.annotations.Controller;

import java.lang.reflect.Method;

@Aspect(value = Controller.class)
@Order(value = 0)
public class ControllerAspect1 extends DefaultAspect {
    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        System.out.println("Aspect1前置通知执行，优先级0");
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) {
        System.out.println("Aspect1后置通知执行，优先级0");
        return super.afterReturning(targetClass, method, args, returnValue);
    }

    @Override
    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable throwable) throws Throwable {
        super.afterThrowing(targetClass, method, args, throwable);
    }
}