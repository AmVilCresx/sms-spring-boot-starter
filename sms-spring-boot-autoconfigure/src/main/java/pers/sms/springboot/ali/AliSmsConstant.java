package pers.sms.springboot.ali;

/**
 * @author amvilcresx
 */
public class AliSmsConstant {

    public static final String REGION_ID = "cn-hangzhou";

    public static final String SEND_SMS = "SendSms";

    public static final String SEND_BATCH_SMS = "SendBatchSms";

    public static final String SYS_DOMAIN = "dysmsapi.aliyuncs.com";

    public static final String SYS_VERSION = "2017-05-25";

    public static final int MAX_SEND_BATCH_SMS_NUMBER = 100;

    public static class QueryParameterKey {

        public static final String PHONE_NUMBERS = "PhoneNumbers";

        public static final String PHONE_NUMBER_JSON = "PhoneNumberJson";

        public static final String SIGN_NAME = "SignName";

        public static final String SIGN_NAME_JSON = "SignNameJson";

        public static final String TEMPLATE_CODE = "TemplateCode";

        public static final String TEMPLATE_PARAM = "TemplateParam";

        public static final String TEMPLATE_PARAM_JSON = "TemplateParamJson";

        public static final String SMS_UP_EXTEND_CODE = "SmsUpExtendCode";

        public static final String SMS_UP_EXTEND_CODE_JSON = "SmsUpExtendCodeJson";
    }
}
