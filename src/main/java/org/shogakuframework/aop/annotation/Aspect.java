package org.shogakuframework.aop.annotation;

import java.lang.annotation.*;

/**
 * org.shogakuframework.aop.annotation
 * Created by throne on 2020/6/9
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();

}
