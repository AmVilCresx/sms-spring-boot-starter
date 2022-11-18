##### 0. 说明

* Spring boot版本：`2.5.2`
* JDK版本：1.8
* 运行的WEB环境：NONE、SERVLET

##### 1. 使用说明

* 在spring boot配置文件中添加必要的配置

  ```yaml
  sms:
    # 阿里短信服务配置	
    ali:
      accessKey: your AccessKey
      accessKeySecret: your accessKeySecret
      defaultSignature: your sign
      defaultTemplateCode: your templateCode
    # 腾讯短信服务配置  
    tx:
      secret-id: your secretId
      secret-key: your secretKey
      template-id: yourtemplateId
      sign: your signName
      sms-sdk-app-id: your appId
  ```

* 示例代码

  ```java
  // Provider 在 SmsProviderManager中维护，通过Key值获取
  SmsProviderHandler smsSenderHandler = SmsProviderManager.getProvider(TxSmsSenderHandler.TX_SENDER_HANDLER);
  System.out.println(gson.toJson(smsSenderHandler.sendSms("188xxxxxx", map)));
  // 模拟批量发送
  String ret = gson.toJson(smsSenderHandler.sendBatchSms(new String[]{"签名"}, "SMS_19XXXXX", new String[]{"188XXXXXX"}, new String[]{gson.toJson(map)}, null));
  System.out.println(ret);
  ```

  
