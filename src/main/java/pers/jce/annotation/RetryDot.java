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
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryDot {
    public int count() default 3;
    public long sleepTime() default 0;
    public boolean finalTest() default true;    //是否进行最终测试
    public String testMethod() default "";      //指定测试方法
}
