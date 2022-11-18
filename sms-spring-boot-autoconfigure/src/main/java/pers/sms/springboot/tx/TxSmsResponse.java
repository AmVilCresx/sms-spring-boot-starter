package pers.sms.springboot.tx;

import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.springframework.util.CollectionUtils;
import pers.sms.springboot.common.SmsResponseAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 腾讯短信服务返回结果封装
 *
 * @author amvilcresx
 */
public class TxSmsResponse extends SmsResponseAdapter {

    private List<SendStatus> sendStatusObjects;

    private String requestId;

    public List<SendStatus> getSendStatusObjects() {
        return sendStatusObjects;
    }

    public void setSendStatusObjects(List<SendStatus> sendStatusObjects) {
        this.sendStatusObjects = sendStatusObjects;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public static class SendStatus{

        private String serialNo;

        private String phoneNumber;

        private Long fee;

        private String sessionCtx;

        private String code;

        private String message;

        private String isoCode;

        public String getSerialNo() {
            return serialNo;
        }

        public void setSerialNo(String serialNo) {
            this.serialNo = serialNo;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public Long getFee() {
            return fee;
        }

        public void setFee(Long fee) {
            this.fee = fee;
        }

        public String getSessionCtx() {
            return sessionCtx;
        }

        public void setSessionCtx(String sessionCtx) {
            this.sessionCtx = sessionCtx;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getIsoCode() {
            return isoCode;
        }

        public void setIsoCode(String isoCode) {
            this.isoCode = isoCode;
        }
    }

    /**
     * 将结果做转换
     * @param sendSmsResponse {@link SendSmsResponse}
     * @return this
     */
    public static TxSmsResponse convert2This(SendSmsResponse sendSmsResponse) {
        TxSmsResponse smsResponse = new TxSmsResponse();
        if (Objects.isNull(sendSmsResponse)) {
            return smsResponse;
        }
        smsResponse.setRequestId(sendSmsResponse.getRequestId());
        if (CollectionUtils.isEmpty(Arrays.asList(sendSmsResponse.getSendStatusSet()))) {
            return smsResponse;
        }
        List<TxSmsResponse.SendStatus> statusList = Arrays.stream(sendSmsResponse.getSendStatusSet()).map(record -> {
            TxSmsResponse.SendStatus sendStatus = new TxSmsResponse.SendStatus();
            sendStatus.setSerialNo(record.getSerialNo());
            sendStatus.setCode(record.getCode());
            sendStatus.setFee(record.getFee());
            sendStatus.setIsoCode(record.getIsoCode());
            sendStatus.setMessage(record.getMessage());
            sendStatus.setPhoneNumber(record.getPhoneNumber());
            sendStatus.setSessionCtx(record.getSessionContext());
            return sendStatus;
        }).collect(Collectors.toList());
        smsResponse.setSendStatusObjects(statusList);
        return smsResponse;
    }
}
