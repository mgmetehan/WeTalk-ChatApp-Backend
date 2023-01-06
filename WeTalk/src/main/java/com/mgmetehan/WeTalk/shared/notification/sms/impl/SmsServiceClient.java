package com.mgmetehan.WeTalk.shared.notification.sms.impl;

import com.mgmetehan.WeTalk.shared.notification.sms.requests.SmsRequest;
import com.mgmetehan.WeTalk.shared.notification.sms.response.SmsResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
@UtilityClass
public class SmsServiceClient {
    public SmsResponse sendSms(
            SmsRequest smsRequest,
            RestTemplate restTemplate,
            String url
    ) {
        log.info("[SmsServiceClient] (sendSms) Url : {}", url);

        return restTemplate.postForObject(url, smsRequest, SmsResponse.class);
    }
}
