package pers.sms.springboot.ali;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里短信服务属性配置类
 *
 * @author amvilcresx
 */
@ConfigurationProperties(prefix = AliSmsConfigProperties.PREFIX)
public class AliSmsConfigProperties {

    public static final String PREFIX = "sms.ali";

    /**
     * 密钥
     */
    private String accessKey;

    /**
     * 凭证
     */
    private String accessKeySecret;

    /**
     * API 地区
     */
    private String regionId = AliSmsConstant.REGION_ID;

    /**
     * 默认签名
     */
    private String defaultSignature;

    /**
     * 默认模板Id
     */
    private String defaultTemplateCode;

    /**
     * 默认的区号
     */
    private String defaultAreaCode;

    /**
     * 上行短信扩展码上行短信扩展码
     */
    private String smsUpExtendCode;

    private String sysDomain = AliSmsConstant.SYS_DOMAIN;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getSysDomain() {
        return sysDomain;
    }

    public void setSysDomain(String sysDomain) {
        this.sysDomain = sysDomain;
    }

    public String getDefaultSignature() {
        return defaultSignature;
    }

    public void setDefaultSignature(String defaultSignature) {
        this.defaultSignature = defaultSignature;
    }

    public String getDefaultTemplateCode() {
        return defaultTemplateCode;
    }

    public void setDefaultTemplateCode(String defaultTemplateCode) {
        this.defaultTemplateCode = defaultTemplateCode;
    }

    public String getDefaultAreaCode() {
        return defaultAreaCode;
    }

    public void setDefaultAreaCode(String defaultAreaCode) {
        this.defaultAreaCode = defaultAreaCode;
    }

    public String getSmsUpExtendCode() {
        return smsUpExtendCode;
    }

    public void setSmsUpExtendCode(String smsUpExtendCode) {
        this.smsUpExtendCode = smsUpExtendCode;
    }
}
