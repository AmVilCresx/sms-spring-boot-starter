package pers.sms.springboot.common;

/**
 * 执行发送后返回的结果适配器
 *
 * @author amvilcresx
 */
public class SmsResponseAdapter {

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
