package pers.sms.springboot.exception;

/**
 * 短信服务异常
 *
 * @author amvilcresx
 */
public class SmsException extends RuntimeException{

    public SmsException(String msg){
        super(msg);
    }

    public SmsException(Exception e) {
        super(e);
    }
}
