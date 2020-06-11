package org.shogakuframework.aop.annotation;

import java.lang.annotation.*;

/**
 * org.shogakuframework.aop.annotation
 * Created by throne on 2020/6/9
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    // 针对特定注解进行织入，
    // 例如，当value为@Controller时，切面逻辑将织入被@Controller标记的类
    Class<? extends Annotation> value();

}
