package pers.sms.springboot.tx;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import pers.sms.springboot.ali.AliSmsConstant;
import pers.sms.springboot.common.SmsRequestAdapter;
import pers.sms.springboot.common.SmsResponseAdapter;
import pers.sms.springboot.exception.SmsException;

import java.util.Objects;


/**
 * 腾讯短信服务类
 *
 * @author amvilcresx
 */
public class TxSmsSenderHandler implements TxSmsProviderHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TxSmsSenderHandler.class);

    public static final String TX_SENDER_HANDLER = "txSenderHandler";

    private TxSmsConfigProperties txSmsConfigProperties;

    private SmsClient client;

    /**
     * 初始化 {@link SmsClient} 实例
     */
    public void initNecessaryData() {
        Credential cred = new Credential(txSmsConfigProperties.getSecretId(), txSmsConfigProperties.getSecretKey());
        HttpProfile httpProfile = new HttpProfile();
        TxSmsConfigProperties.HttpProfileConfig profileConfig = txSmsConfigProperties.getHttpProfileConfig();
        // 代理
        if (StringUtils.hasText(profileConfig.getProxyHost())){
            httpProfile.setProxyHost(profileConfig.getProxyHost());
            if (Objects.nonNull(profileConfig.getProxyPort())){
                httpProfile.setProxyPort(profileConfig.getProxyPort());
            }
        }
        // 域名
        if (StringUtils.hasText(profileConfig.getEndPoint())) {
            httpProfile.setEndpoint(profileConfig.getEndPoint());
        }

        // 超时时间
        if (Objects.nonNull(profileConfig.getConnectionTimeOut())) {
            httpProfile.setConnTimeout(profileConfig.getConnectionTimeOut());
        }

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
         /* 非必要请不要修改这个字段 */
        /// clientProfile.setSignMethod("HmacSHA256");
        client = new SmsClient(cred, txSmsConfigProperties.getRegionId(),clientProfile);
    }

    @Override
    public TxSmsResponse sendBatchSms(String[] phoneNumbers, String[] templateParams, String templateCode, String signatureName, String extendCode, String sessionCtx) throws SmsException {
        SendSmsRequest req = new SendSmsRequest();
        TxSmsRequest smsRequest = generateCommonSmsRequest(TxSmsRequest.class);
        /* 短信应用ID: 短信SdkAppid在 [短信控制台] 添加应用后生成的实际SdkAppid，示例如1400006666 */
        req.setSmsSdkAppid(smsSdkAppId());
        /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，签名信息可登录 [短信控制台] 查看 */
        req.setSign(signatureName);
        /* 国际/港澳台短信 senderid: 国内短信填空，默认未开通，如需开通请联系 [sms helper] */
        req.setSenderId(smsRequest.getSenderId());
        /* 用户的 session 内容: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
        req.setSessionContext(sessionCtx);
        /* 短信码号扩展号: 默认未开通，如需开通请联系 [sms helper] */
        req.setExtendCode(extendCode);
        /* 模板 ID: 必须填写已审核通过的模板 ID。模板ID可登录 [短信控制台] 查看 */
        req.setTemplateID(templateCode);
        /* 下发手机号码，采用 e.164 标准，+[国家或地区码][手机号]
         * 示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号*/
        req.setPhoneNumberSet(phoneNumbers);
        /* 模板参数: 若无模板参数，则设置为空*/
        req.setTemplateParamSet(templateParams);

        /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
         * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
        try {
            SendSmsResponse res = client.SendSms(req);

            TxSmsResponse response = TxSmsResponse.convert2This(res);
            // 输出json格式的字符串回包
            LOGGER.info("【腾讯云】短信接口返回：{}", SendSmsResponse.toJsonString(res));
            // 也可以取出单个值，你可以通过官网接口文档或跳转到response对象的定义处查看返回字段的定义
            // System.out.println(res.getRequestId());
            return response;
        } catch (Exception e) {
            LOGGER.error("【腾讯云】短信接口调用异常:", e);
            throw new SmsException(e);
        }
    }
  
    @Override
    public String smsSdkAppId() {
        if (StringUtils.hasText(txSmsConfigProperties.getSmsSdkAppId())) {
            return txSmsConfigProperties.getSmsSdkAppId();
        }
        throw new SmsException("未配置SmsSdkAppId");
    }

    /**
     * 根据腾讯云短信规则
     * @param phone 手机号
     * @param templateParam 模板参数与待替换值JSON串
     * @return
     */
    @Override
    public SmsResponseAdapter sendSms(String phone, String templateParam) {
        return sendSms(null, phone, templateParam);
    }

    @Override
    public TxSmsRequest generateCommonSmsRequest(Class<? extends SmsRequestAdapter> requestClass) {
        TxSmsRequest smsRequest;
        try{
            smsRequest = (TxSmsRequest) requestClass.getDeclaredConstructor().newInstance();
            smsRequest.setTemplateId(txSmsConfigProperties.getTemplateId());
            smsRequest.setExtendCode(txSmsConfigProperties.getExtendCode());
            smsRequest.setSignName(txSmsConfigProperties.getSign());
            smsRequest.setAction(TxSmsConstant.SEND_SMS);
            smsRequest.setAreaCode(TxSmsConstant.AREA_CODE);
            if (StringUtils.hasText(txSmsConfigProperties.getRegionId())) {
                smsRequest.setRegionId(txSmsConfigProperties.getRegionId());
            }
        }catch (Exception e) {
            throw new SmsException(e);
        }
        return smsRequest;
    }

    @Override
    public String getName() {
        return TX_SENDER_HANDLER;
    }

    @Override
    public String defaultSignName() {
        return txSmsConfigProperties.getSign();
    }

    @Override
    public String defaultTemplateCode() {
        if (StringUtils.hasText(txSmsConfigProperties.getTemplateId())) {
            return txSmsConfigProperties.getTemplateId();
        }
        throw new SmsException("未配置templateId");
    }

    public void setTxSmsConfigProperties(TxSmsConfigProperties txSmsConfigProperties) {
        this.txSmsConfigProperties = txSmsConfigProperties;
    }
}
