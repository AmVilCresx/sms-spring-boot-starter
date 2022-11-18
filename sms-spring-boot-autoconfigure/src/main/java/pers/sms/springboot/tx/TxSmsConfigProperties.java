package pers.sms.springboot.tx;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 腾讯短信服务属性配置类
 * @author amvilcresx
 */
@ConfigurationProperties(prefix = TxSmsConfigProperties.PREFIX)
public class TxSmsConfigProperties {

    public static final String PREFIX = "sms.tx";

    /**
     * 密钥Id
     */
    private String secretId;

    /**
     * 密钥key
     */
    private String secretKey;

    /**
     * 短信SdkAppid在 短信控制台 添加应用后生成的实际SdkAppid，示例如1400006666。
     */
    private String smsSdkAppId;

    /**
     * 短信签名内容，使用 UTF-8 编码，必须填写已审核通过的签名，签名信息可登录 短信控制台 查看。注：国内短信为必填参数
     */
    private String sign;

    /**
     * 模板 ID，必须填写已审核通过的模板 ID
     */
    private String templateId;

    /**
     * 短信码号扩展号，默认未开通
     */
    private String extendCode;

    /**
     * 国内短信无senderid，无需填写该项
     */
    private String senderId;

    /**
     * 区域Id
     */
    private String regionId = TxSmsConstant.REGION_ID;

    /**
     * httpProfile配置
     */
    private HttpProfileConfig httpProfileConfig = new HttpProfileConfig();


    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSmsSdkAppId() {
        return smsSdkAppId;
    }

    public void setSmsSdkAppId(String smsSdkAppId) {
        this.smsSdkAppId = smsSdkAppId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getExtendCode() {
        return extendCode;
    }

    public void setExtendCode(String extendCode) {
        this.extendCode = extendCode;
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

    public HttpProfileConfig getHttpProfileConfig() {
        return httpProfileConfig;
    }

    public void setHttpProfileConfig(HttpProfileConfig httpProfileConfig) {
        this.httpProfileConfig = httpProfileConfig;
    }

    public static class HttpProfileConfig {

        /**
         * 代理服务器地址
         */
        private String proxyHost;

        /**
         * 代理服务器端口
         */
        private Integer proxyPort;

        /**
         * SDK有默认的超时时间，非必要请不要进行调整
         */
        private Integer connectionTimeOut;

        /* SDK会自动指定域名。通常是不需要特地指定域名的，但是如果你访问的是金融区的服务
         * 则必须手动指定域名，例如sms的上海金融区域名： sms.ap-shanghai-fsi.tencentcloudapi.com */
        private String endPoint;

        public String getProxyHost() {
            return proxyHost;
        }

        public void setProxyHost(String proxyHost) {
            this.proxyHost = proxyHost;
        }

        public Integer getProxyPort() {
            return proxyPort;
        }

        public void setProxyPort(Integer proxyPort) {
            this.proxyPort = proxyPort;
        }

        public Integer getConnectionTimeOut() {
            return connectionTimeOut;
        }

        public void setConnectionTimeOut(Integer connectionTimeOut) {
            this.connectionTimeOut = connectionTimeOut;
        }

        public String getEndPoint() {
            return endPoint;
        }

        public void setEndPoint(String endPoint) {
            this.endPoint = endPoint;
        }
    }
}
