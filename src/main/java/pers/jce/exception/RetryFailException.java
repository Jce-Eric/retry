package pers.jce.exception;

/**
 * @author: hongjie
 * @description:
 * @data: 2019/4/9
 */
public class RetryFailException extends Exception {
    public RetryFailException(){
        super();
    }
    public RetryFailException(String msg){
        super(msg);
    }
}
