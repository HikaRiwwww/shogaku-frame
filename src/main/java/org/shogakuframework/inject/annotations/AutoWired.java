package org.shogakuframework.inject.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author throne
 * @packageName org.shogakuframework.inject.annotations
 * @className AutoWired
 * @date 2020/5/15 9:37
 * @description
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoWired {
    String value() default "";
}
