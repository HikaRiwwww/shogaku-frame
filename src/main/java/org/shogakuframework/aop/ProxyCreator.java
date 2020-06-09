package org.shogakuframework.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * org.shogakuframework.aop.aspect
 * Created by throne on 2020/6/9
 */
public class ProxyCreator {
    public static Object createProxy(Class<?> targetClass, MethodInterceptor methodInterceptor){
        return Enhancer.create(targetClass, methodInterceptor);
    }
}
