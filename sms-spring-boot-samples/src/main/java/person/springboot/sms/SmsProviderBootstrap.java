package person.springboot.sms;

import com.google.gson.Gson;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pers.sms.springboot.common.SmsProviderHandler;
import pers.sms.springboot.common.SmsProviderManager;
import pers.sms.springboot.tx.TxSmsSenderHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link SmsProviderHandler} 测试类
 *
 * @author amvilcresx
 */

//@EnableSms
@SpringBootApplication
public class SmsProviderBootstrap {

    public static void main(String[] args){
        SpringApplicationBuilder builder = new SpringApplicationBuilder(SmsProviderBootstrap.class);
        ConfigurableApplicationContext context = builder.web(WebApplicationType.NONE).run(args);

        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("code", "156845");
        //SmsProviderHandler smsSenderHandler = SmsProviderManager.getProvider(TxSmsSenderHandler.TX_SENDER_HANDLER); // 腾讯云
        SmsProviderHandler smsSenderHandler = SmsProviderManager.getProvider(TxSmsSenderHandler.TX_SENDER_HANDLER); // 阿里云
        System.out.println(gson.toJson(smsSenderHandler.sendSms("188sssxxx2", gson.toJson(map))));
        String ret = gson.toJson(smsSenderHandler.sendBatchSms(new String[]{"188sssxxx2","188sssxxx3"},
                new String[]{"{\"code:\":\"125646\"}","{\"code:\":\"238723\"}"}, "SMS1259***", new String[]{"测试","test"}, null));
        System.out.println(ret);
        context.close();
    }
}
