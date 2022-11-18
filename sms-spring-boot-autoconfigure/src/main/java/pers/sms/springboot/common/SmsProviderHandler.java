package pers.sms.springboot.common;

import org.springframework.beans.factory.InitializingBean;
import pers.sms.springboot.ali.AliSmsSenderHandler;
import pers.sms.springboot.exception.SmsException;
import pers.sms.springboot.tx.TxSmsSenderHandler;

/**
 * 短信服务商服务顶层接口
 *
 * @author amvilcresx
 *
 * @see AliSmsSenderHandler
 * @see TxSmsSenderHandler
 */
public interface SmsProviderHandler extends InitializingBean {

    /**
     * 发送短信
     * @param phone 手机号
     * @param templateParam 模板参数与待替换值JSON串
     * @return SmsResponseAdapter
     */
    SmsResponseAdapter sendSms(String phone, String templateParam);

    /**
     * 发送短信
     * @param areaCode 区号
     * @param phone 手机号
     * @param templateParam 模板参数与待替换值JSON串
     * @return SmsResponseAdapter
     */
    SmsResponseAdapter sendSms(String areaCode, String phone, String templateParam) throws SmsException;

    /**
     *  批量发送短信
     * @param signatureNames 签名数据
     * @param templateCode 模板Id
     * @param phoneNumbers 手机号数据（必要时需要含区号），
     * @param templateParamJsonArray 短信模板变量对应的实际值，JSON数组
     * @param smsUpExtendCodeJson  上行扩展码数组
     * @return SmsResponseAdapter
     * @throws SmsException 发送短信异常时抛出
     */
    SmsResponseAdapter sendBatchSms(String[] phoneNumbers, String[] templateParamJsonArray, String templateCode, String[] signatureNames, String[] smsUpExtendCodeJson) throws SmsException;

    /**
     * 构建 请求对象
     * @param requestClass 请求对象Class
     * @return SmsRequestAdapter
     *
     * @see pers.sms.springboot.ali.AliSmsRequest
     */
    SmsRequestAdapter generateCommonSmsRequest(Class<? extends SmsRequestAdapter> requestClass);

    /**
     * SmsProviderHandler 的名称
     * @return SmsProviderHandler 的名称，不可重复
     */
    String getName();

    /**
     * 区号
     * @return 区号
     */
    default String defaultAreaCode(){
        return "";
    }

    /**
     * 获取默认短信签名
     * @return 短信签名
     */
    String defaultSignName();

    /**
     * 获取默认的短信模板Id
     * @return 模板Id
     */
    String defaultTemplateCode();

    /**
     * 将Provider注册到 ProviderManager
     */
    @Override
    default void afterPropertiesSet(){
        SmsProviderManager.registryProvider(getName(), this);
    }
}
