package org.shogakuframework.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * org.shogakuframework.aop.annotation
 * Created by throne on 2020/6/9
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {
    // 表示注解的优先级，值越小，优先级越高
    int value();
}
