package pers.sms.springboot.common;

import org.springframework.util.StringUtils;
import pers.sms.springboot.exception.SmsProviderException;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link SmsProviderHandler} 管理类
 *
 * @author amvilcresx
 */
public class SmsProviderManager {

    private static final Map<String, SmsProviderHandler> PROVIDER_HANDLERS= new ConcurrentHashMap<>();

    public static SmsProviderHandler getProvider(String name) {
        return PROVIDER_HANDLERS.get(name);
    }

    public static void registryProvider(String name, SmsProviderHandler providerHandler) {
        if (!StringUtils.hasText(name)) {
            throw new SmsProviderException("provider name 不可为空....");
        }

        if (Objects.isNull(providerHandler)) {
            throw new SmsProviderException("providerHandler对象不可为空....");
        }

        PROVIDER_HANDLERS.put(name, providerHandler);
    }
}
