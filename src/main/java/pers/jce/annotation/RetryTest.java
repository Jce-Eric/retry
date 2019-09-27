package pers.jce.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: hongjie
 * @description:
 * @data: 2019/4/9
 */
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RetryTest {
}
