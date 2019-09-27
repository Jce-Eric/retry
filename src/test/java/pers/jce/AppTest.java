package pers.jce;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.jce.bean.TestPerson;
import pers.jce.exception.RetryFailException;

/**
 * Unit test for simple
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {

    }
    public static void main( String[] args )
    {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/applicationContext.xml");
        TestPerson person = (TestPerson) applicationContext.getBean("testPerson");
        try {
            person.incAge(1);
        } catch (RetryFailException e) {
            System.out.println("重试失败");
        }


        //System.out.println("p:"+i);
    }

}
