package pers.jce.bean;

import org.springframework.stereotype.Component;
import pers.jce.annotation.RetryDot;
import pers.jce.annotation.RetryTest;
import pers.jce.exception.RetryFailException;

/**
 * @author: hongjie
 * @description:
 * @data: 2019/4/9
 */
@Component
public class TestPerson {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @RetryDot(count = 3,sleepTime = 1000)
    public int incAge(int a) throws RetryFailException{
        a ++;
        System.out.println("incAge:"+a);
        return a;
        //return this.age+"";
    }
    //1。抛出异常
    @RetryTest
    private boolean test(int a){
        return false;
        /*
        if(this.age <= 100){
            return false;
        }else{
            return true;
        }
        */
    }
}
