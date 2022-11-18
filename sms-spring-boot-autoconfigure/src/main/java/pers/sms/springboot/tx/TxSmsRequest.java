package pers.sms.springboot.tx;

import pers.sms.springboot.common.SmsRequestAdapter;

/**
 * 腾讯云短信服务请求参数封装
 *
 * @author amvilcresx
 */
public class TxSmsRequest extends SmsRequestAdapter {

    /**
     *  用户的 session 内容: 可以携带用户侧 ID 等上下文信息，server 会原样返回
     */
    private String session;

    /**
     * 国际/港澳台短信 senderId: 国内短信填空，默认未开通
     */
    private String senderId;

    /**
     * 区域
     */
    private String regionId;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
}
