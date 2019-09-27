package pers.jce;

import pers.jce.exception.RetryFailException;

/**
 * @author: hongjie
 * @description:
 * @data: 2019/4/9
 */
//?重试失败后要抛出实际异常
public abstract class RetryTemplate {
    private int count = 3;
    private long sleepTime = 0;
    public abstract Object doThing() throws Throwable;
    public abstract boolean doTest(Object result);
    public Object execute(boolean finalTest) throws Throwable {
        Object result = null;
        Exception finalException = null;
        boolean testFlag = false;
        for(int i = 0; i < getCount(); i++){
            System.out.println("Jce-Retry: execute times:"+i);
            finalException = null;
            try{
                result = doThing();
                testFlag = doTest(result);
                if(testFlag){
                    return result;
                }
            }catch (Exception e) {
                finalException = e;
            }
            Thread.sleep(getSleepTime());
        }
        if(finalException != null){
            throw finalException;
        }
        if(!finalTest){
            return result;
        }else{
            if(testFlag){
                return result;
            }else{
                throw new RetryFailException("Jce-Retry: 重试失败，未通过测试");
            }
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }
}
