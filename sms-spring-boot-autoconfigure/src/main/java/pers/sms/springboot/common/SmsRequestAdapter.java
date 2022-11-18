package pers.sms.springboot.common;

/**
 * 短信服务请求参数适配器
 *
 * @author amvilcresx
 */
public class SmsRequestAdapter{

    /**
     * 短信签名
     */
    private String signName;

    /**
     * 模板Id
     */
    private String templateId;

    private String areaCode;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }


    private String action;

    /**
     * 手机号数组
     */
    private String[] phoneNumbers;

    /**
     * 模板参数数组
     */
    private String[] templateParams;

    /**
     * 扩展码
     */
    private String extendCode;

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String[] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String[] getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(String[] templateParams) {
        this.templateParams = templateParams;
    }

    public String getExtendCode() {
        return extendCode;
    }

    public void setExtendCode(String extendCode) {
        this.extendCode = extendCode;
    }
}
