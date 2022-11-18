package pers.sms.springboot.ali;

import com.google.gson.Gson;
import org.springframework.util.StringUtils;
import pers.sms.springboot.common.SmsResponseAdapter;
import pers.sms.springboot.exception.SmsException;

import java.util.Map;

/**
 * 阿里云短信服务返回结果
 *
 * @author amvilcresx
 */
public class AliSmsResponse extends SmsResponseAdapter {

    private final static Gson G = new Gson();

    /**
     * 正确的结果
     * {
     *     "Message":"OK",
     *     "RequestId":"2184201F-BFB3-446B-B1F2-C746B7BF0657",
     *     "BizId":"197703245997295588^0",
     *     "Code":"OK"
     * }
     *
     * @param msg 返回的信息
     */
    public AliSmsResponse(String msg) {
        if (!StringUtils.hasText(msg)) {
            throw new SmsException("返回的信息为空:" + msg);
        }

        Map<String, String> resMap = G.fromJson(msg, Map.class);
        this.bizId = resMap.get("BizId");
        this.requestId = resMap.get("RequestId");
        super.setCode(resMap.get("Code"));
        super.setMessage(resMap.get("Message"));
    }

    public static AliSmsResponse buildResp(String msg) {
        AliSmsResponse response = new AliSmsResponse();
        Map<String, String> resMap = G.fromJson(msg, Map.class);
        response.setBizId(resMap.get("BizId"));
        response.setRequestId(resMap.get("RequestId"));
        response.setCode(resMap.get("Code"));
        response.setMessage(resMap.get("Message"));
        return response;
    }

    public AliSmsResponse() {}

    private String bizId;

    private String requestId;

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
