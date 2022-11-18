package pers.sms.springboot.tx;

import org.springframework.util.StringUtils;
import pers.sms.springboot.common.SmsProviderHandler;
import pers.sms.springboot.common.SmsResponseAdapter;
import pers.sms.springboot.exception.SmsException;

/**
 *
 * @author amvilcresx
 *
 * @see TxSmsSenderHandler
 */
public interface TxSmsProviderHandler extends SmsProviderHandler {

    /**
     * 腾讯云发送短信方法
     *
     * @param phoneNumbers 手机号数字
     * @param templateParamSet 参数数组
     * @param sessionCtx session山下文（可选）
     * @return SmsResponseAdapter
     * @throws SmsException {@link SmsException}
     */
    default SmsResponseAdapter sendSms(String phoneNumbers, String[] templateParamSet, String sessionCtx) throws SmsException {
        return sendBatchSms(new String[]{phoneNumbers}, templateParamSet, defaultTemplateCode(), defaultSignName(), null, sessionCtx);
    }

    /**
     * 腾讯云发送短信方法
     *
     * @param areaCode  区号或国家码
     * @param phoneNumbers 手机号
     * @param templateParam 参数
     * @return SmsResponseAdapter
     * @throws SmsException {@link SmsException}
     */
    @Override
    default SmsResponseAdapter sendSms(String areaCode, String phoneNumbers, String templateParam) throws SmsException {
        if (StringUtils.hasText(areaCode)) {
            phoneNumbers = areaCode.concat(phoneNumbers);
        }
        return sendSms(phoneNumbers, new String[]{templateParam});
    }

    @Override
    default TxSmsResponse sendBatchSms(String[] phoneNumbers, String[] templateParamSet, String templateCode, String[] signatureNames, String[] smsUpExtendCodes) throws SmsException{
        return sendBatchSms(phoneNumbers, templateParamSet, templateCode, signatureNames[0], smsUpExtendCodes[0], null);
    }

    /**
     * 批量发送短信
     * @param phoneNumbers 手机号【含区号】
     * @param templateParams 模板参数对应的实际值
     * @param templateCode 模板Id
     * @param signatureName 签名
     * @param smsUpExtendCode 扩展码
     * @param sessionCtx session上下文
     * @return {@link TxSmsResponse}
     * @throws SmsException
     */
    TxSmsResponse sendBatchSms(String[] phoneNumbers, String[] templateParams, String templateCode, String signatureName, String smsUpExtendCode, String sessionCtx) throws SmsException;

    /**
     * 发送短信简单方法
     * @param phoneNumbers 手机号
     * @param templateParamSet 模板变量参数
     * @return SmsResponseAdapter
     * @throws SmsException 发送短信异常
     */
    default SmsResponseAdapter sendSms(String phoneNumbers, String[] templateParamSet) throws SmsException {
        return sendSms(phoneNumbers, templateParamSet, null);
    }

    /**
     * 短信SdkAppid
     * @return smsSdkAppId
     */
    String smsSdkAppId();
}
