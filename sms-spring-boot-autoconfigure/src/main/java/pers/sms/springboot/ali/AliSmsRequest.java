package pers.sms.springboot.ali;

import pers.sms.springboot.common.SmsRequestAdapter;

import java.util.Map;

/**
 * 阿里短信服务请求参数封装对象
 *
 * @author amvilcresx
 */
public class AliSmsRequest extends SmsRequestAdapter {

    private String phone;



    private String sysDomain;

    private Map<String, String> templateParam;

    private String[] extendCodeJson;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSysDomain() {
        return sysDomain;
    }

    public void setSysDomain(String sysDomain) {
        this.sysDomain = sysDomain;
    }


    public Map<String, String> getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(Map<String, String> templateParam) {
        this.templateParam = templateParam;
    }

    public String[] getExtendCodeJson() {
        return extendCodeJson;
    }

    public void setExtendCodeJson(String[] extendCodeJson) {
        this.extendCodeJson = extendCodeJson;
    }
}
