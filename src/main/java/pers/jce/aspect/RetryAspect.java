package pers.jce.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import pers.jce.RetryTemplate;
import pers.jce.annotation.RetryDot;
import pers.jce.annotation.RetryTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: hongjie
 * @description:
 * @data: 2019/4/9
 */
//test方法的参数类型为int
@Component
@Aspect
public class RetryAspect {
    @Around(value = "@annotation(retryDot)")
    public Object around(final ProceedingJoinPoint joinPoint, RetryDot retryDot) throws Throwable {
        final Object target = joinPoint.getTarget();
        final Method testMethod = findTestMethod(target.getClass(),retryDot.testMethod());
        final Class parameterType = convert(getFirstParamType(testMethod));

        RetryTemplate myTemplate = new RetryTemplate() {
            @Override
            public Object doThing() throws Throwable {
                return joinPoint.proceed();
            }
            @Override
            public boolean doTest(Object result) {
                boolean flag = true;
                if(testMethod != null){
                    try {
                        if(parameterType == null){
                            flag = (boolean) testMethod.invoke(target);
                        }else{
                            flag = (boolean) testMethod.invoke(target,parameterType.cast(result));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                //System.out.println("test result:"+flag);
                return flag;

            }
        };
        myTemplate.setCount(retryDot.count());
        myTemplate.setSleepTime(retryDot.sleepTime());
        Object result = myTemplate.execute(retryDot.finalTest());
        return result;
    }
    //找到有@RetryTest的方法
    private Method findTestMethod(Class currentClass,String methodName){
        //System.out.println("查找"+currentClass.getName()+"类中的@RetryTest.....");
        Method[] methods = currentClass.getDeclaredMethods();
        Method testMethod = null;
        for(Method method: methods){
            if(method.isAnnotationPresent(RetryTest.class) && method.getName() != null && method.getName().equals(methodName)){
                testMethod = method;
                break;
            }
        }
        if(testMethod.getModifiers() != 1){
            return null;
        }
        return testMethod;
    }
    //找到@RetryTest的第一个参数的类型
    private Class getFirstParamType(Method method){
        if(method == null){
            return null;
        }
        Class[] parameterTypes = method.getParameterTypes();
        if(parameterTypes.length == 0){
            return null;
        }else{
            return parameterTypes[0];
        }

    }
    //将基本数据类型转化为对应的包装类型
    private Class convert(Class clas){
        Class result = clas;
        if(clas == byte.class){
            result = Byte.class;
        }else if(clas == short.class){
            result = Short.class;
        }else if(clas == int.class){
            result = Integer.class;
        }else if(clas == long.class){
            result = Long.class;
        }else if(clas == float.class){
            result = Float.class;
        }else if(clas == double.class){
            result = Double.class;
        }else if(clas == char.class){
            result = Character.class;
        }else if(clas == boolean.class){
            result = Boolean.class;
        }else{
            result = clas;
        }
        return result;
    }
}
