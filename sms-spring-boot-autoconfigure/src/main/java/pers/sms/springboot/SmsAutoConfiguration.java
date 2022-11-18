package pers.sms.springboot;

import com.aliyuncs.IAcsClient;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import pers.sms.springboot.ali.AliSmsSenderHandler;
import pers.sms.springboot.ali.AliSmsConfigProperties;
import pers.sms.springboot.tx.TxSmsConfigProperties;
import pers.sms.springboot.tx.TxSmsSenderHandler;

/**
 * @author amvilcresx
 */
@EnableConfigurationProperties({AliSmsConfigProperties.class, TxSmsConfigProperties.class})
public class SmsAutoConfiguration{

    @Bean(initMethod = "initNecessaryData")
    @ConditionalOnClass(IAcsClient.class)
    public AliSmsSenderHandler aliSmsSenderHandler(AliSmsConfigProperties aliSmsConfigProperties) {
        AliSmsSenderHandler smsSenderHandler = new AliSmsSenderHandler();
        smsSenderHandler.setConfigProperties(aliSmsConfigProperties);
        return smsSenderHandler;
    }

    @Bean(initMethod = "initNecessaryData")
    @ConditionalOnClass(SmsClient.class)
    public TxSmsSenderHandler txSmsSenderHandler(TxSmsConfigProperties smsConfigProperties) {
        TxSmsSenderHandler smsSenderHandler = new TxSmsSenderHandler();
        smsSenderHandler.setTxSmsConfigProperties(smsConfigProperties);
        return smsSenderHandler;
    }
}
