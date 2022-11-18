package pers.sms.springboot.ali;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pers.sms.springboot.common.SmsProviderHandler;
import pers.sms.springboot.common.SmsRequestAdapter;
import pers.sms.springboot.common.SmsResponseAdapter;
import pers.sms.springboot.exception.SmsException;

import java.util.Map;
import java.util.Objects;


/**
 * 阿里短信服务类
 *
 * @author amvilcresx
 */
public class AliSmsSenderHandler implements SmsProviderHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliSmsSenderHandler.class);

    public static final String ALI_HANDLER_NAME = "aliSmsHandler";

    private AliSmsConfigProperties configProperties;

    private IAcsClient client;

    private static final Gson GSON = new Gson();

    /**
     * 初始化 profile 与 client
     */
    public void initNecessaryData() {
        DefaultProfile profile = DefaultProfile.getProfile(configProperties.getRegionId(), configProperties.getAccessKey(), configProperties.getAccessKeySecret());
        client = new DefaultAcsClient(profile);
    }

    /**
     * 注入 {@link AliSmsConfigProperties}
     * @param aliSmsConfigProperties {@link AliSmsConfigProperties}
     */
    public void setConfigProperties(AliSmsConfigProperties aliSmsConfigProperties) {
        this.configProperties = aliSmsConfigProperties;
    }

    @Override
    public AliSmsRequest generateCommonSmsRequest(Class<? extends SmsRequestAdapter> requestClass) {
        AliSmsRequest smsRequest;
        try{
            smsRequest = (AliSmsRequest) requestClass.getDeclaredConstructor().newInstance();
            smsRequest.setAreaCode(configProperties.getDefaultAreaCode());
            smsRequest.setTemplateId(configProperties.getDefaultTemplateCode());
            smsRequest.setExtendCode(configProperties.getSmsUpExtendCode());
            smsRequest.setSignName(configProperties.getDefaultSignature());
            smsRequest.setAction(AliSmsConstant.SEND_SMS);
            if (StringUtils.hasText(configProperties.getSysDomain())) {
                smsRequest.setSysDomain(configProperties.getSysDomain());
            }
        }catch (Exception e) {
            throw new SmsException(e);
        }
        return smsRequest;
    }

    @Override
    public AliSmsResponse sendSms(String phone, String templateParam) {
        return sendSms(null, phone, templateParam);
    }

    @Override
    public AliSmsResponse sendSms(String areaCode, String phone, String templateParam) {
        AliSmsRequest smsRequest = this.generateCommonSmsRequest(AliSmsRequest.class);
        if (StringUtils.hasText(areaCode)) {
            smsRequest.setAreaCode(areaCode);
        }
        smsRequest.setPhone(phone);
        smsRequest.setTemplateParam(GSON.fromJson(templateParam, Map.class));
        return doSend(smsRequest);
    }

    @Override
    public SmsResponseAdapter sendBatchSms(String[] phoneNumbers, String[] templateParamJsonArray, String templateCode, String[] signatureNames, String[] smsUpExtendCodeJson) throws SmsException{
        if (Objects.isNull(signatureNames) || signatureNames.length == 0) {
            throw new SmsException("短信签名不可为空");
        }

        if (Objects.isNull(phoneNumbers) || phoneNumbers.length == 0) {
            throw new SmsException("手机号不可为空");
        }

        if (signatureNames.length != phoneNumbers.length) {
            throw new SmsException("短信签名数量与手机号数量不匹配");
        }

        if (Objects.nonNull(templateParamJsonArray)) {
            if(templateParamJsonArray.length != phoneNumbers.length) {
                throw new SmsException("短信模板变量的数量与手机号数量不匹配");
            }
        }

        if (Objects.nonNull(smsUpExtendCodeJson)) {
            if(smsUpExtendCodeJson.length != phoneNumbers.length) {
                throw new SmsException("扩展码的数量与手机号数量不匹配");
            }
        }

        if (phoneNumbers.length > AliSmsConstant.MAX_SEND_BATCH_SMS_NUMBER) {
            throw new SmsException("一次性最多发送" + AliSmsConstant.MAX_SEND_BATCH_SMS_NUMBER + "条短信...");
        }

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(configProperties.getSysDomain());
        request.setSysRegionId(configProperties.getRegionId());
        request.setSysVersion(AliSmsConstant.SYS_VERSION);
        request.setSysAction(AliSmsConstant.SEND_BATCH_SMS);
        // 接收短信的手机号码数组
        request.putQueryParameter(AliSmsConstant.QueryParameterKey.PHONE_NUMBER_JSON, GSON.toJson(phoneNumbers));
        // 短信签名名称数组
        request.putQueryParameter(AliSmsConstant.QueryParameterKey.SIGN_NAME_JSON, GSON.toJson(signatureNames));
        // 短信模板ID
        request.putQueryParameter(AliSmsConstant.QueryParameterKey.TEMPLATE_CODE, templateCode);
        // 短信模板变量对应的实际值，JSON数组格式
        if (Objects.nonNull(templateParamJsonArray)) {
            request.putQueryParameter(AliSmsConstant.QueryParameterKey.TEMPLATE_PARAM_JSON, GSON.toJson(templateParamJsonArray));
        }
        // 上行扩展码
        if (Objects.nonNull(smsUpExtendCodeJson)) {
            request.putQueryParameter(AliSmsConstant.QueryParameterKey.SMS_UP_EXTEND_CODE_JSON, GSON.toJson(smsUpExtendCodeJson));
        }

        try {
            CommonResponse response = client.getCommonResponse(request);
            LOGGER.info("【阿里云】短信接口返回信息：{}", response);
            String data = response.getData();
            return AliSmsResponse.buildResp(data);
        } catch (Exception e) {
            throw new SmsException(e);
        }
    }

    @Override
    public String getName() {
        return ALI_HANDLER_NAME;
    }

    @Override
    public String defaultAreaCode() {
        String defaultAreaCode = configProperties.getDefaultAreaCode();
        if (StringUtils.hasText(defaultAreaCode)){
            return defaultAreaCode;
        }
        return "";
    }

    @Override
    public String defaultSignName() {
        String defaultSignature = configProperties.getDefaultSignature();
        if (StringUtils.hasText(defaultSignature)){
            return defaultSignature;
        }
        throw new SmsException("未配置默认签名");
    }

    @Override
    public String defaultTemplateCode() {
        String defaultTemplateCode = configProperties.getDefaultTemplateCode();
        if (StringUtils.hasText(defaultTemplateCode)){
            return defaultTemplateCode;
        }
        throw new SmsException("未配置默认模板");
    }

    /**
     * 发送短信核心方法
     * @param smsRequest {@link AliSmsRequest}
     * @return AliSmsResponse
     */
    private AliSmsResponse doSend(AliSmsRequest smsRequest){
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysRegionId(configProperties.getRegionId());
        request.setSysDomain(smsRequest.getSysDomain());
        request.setSysVersion(AliSmsConstant.SYS_VERSION);
        request.setSysAction(smsRequest.getAction());
        // 接收短信的手机号码
        request.putQueryParameter(AliSmsConstant.QueryParameterKey.PHONE_NUMBERS, smsRequest.getPhone());
        // 短信签名名称。请在控制台签名管理页面签名名称一列查看（必须是已添加、并通过审核的短信签名）。
        request.putQueryParameter(AliSmsConstant.QueryParameterKey.SIGN_NAME, smsRequest.getSignName());
        // 短信模板ID
        request.putQueryParameter(AliSmsConstant.QueryParameterKey.TEMPLATE_CODE, smsRequest.getTemplateId());
        // 短信模板变量对应的实际值，JSON格式。
        if (!CollectionUtils.isEmpty(smsRequest.getTemplateParam())) {
            request.putQueryParameter(AliSmsConstant.QueryParameterKey.TEMPLATE_PARAM, buildTemplateParam(smsRequest.getTemplateParam()));
        }
        // 上行短信扩展码
        if (StringUtils.hasText(smsRequest.getExtendCode())) {
            request.putQueryParameter(AliSmsConstant.QueryParameterKey.SMS_UP_EXTEND_CODE, smsRequest.getExtendCode());
        }
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            return new AliSmsResponse(data);
        } catch (Exception e) {
            LOGGER.error("【阿里云】短信调用接口异常：", e);
            throw new SmsException(e);
        }
    }

    /**
     * 构建模板变量的JSON字符换
     * @param paramMap 参数Map
     * @return JSON串
     */
    private String buildTemplateParam(Map<String, String> paramMap) {
        if (CollectionUtils.isEmpty(paramMap)) {
            throw new SmsException("模板参数为空");
        }
        return GSON.toJson(paramMap);
    }
}
