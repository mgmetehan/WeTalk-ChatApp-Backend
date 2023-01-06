package com.mgmetehan.WeTalk.shared.notification.sms.impl;

import com.mgmetehan.WeTalk.shared.notification.sms.SmsService;
import com.mgmetehan.WeTalk.shared.notification.sms.config.SmsConfigurationProperties;
import com.mgmetehan.WeTalk.shared.notification.sms.requests.SmsRequest;
import com.mgmetehan.WeTalk.shared.notification.sms.response.SmsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(prefix = "sms", name = "mock", havingValue = "false")
public class SmsClientService implements SmsService {
    private final RestTemplate restTemplate;
    private final SmsConfigurationProperties smsConfigurationProperties;

    @Override
    public SmsResponse sendSms(SmsRequest smsRequest) {
        String url = smsConfigurationProperties.getUrl();
        try {
            var result = SmsServiceClient.sendSms(
                    smsRequest,
                    restTemplate,
                    url
            );

            log.info("[SmsClientService] (sendSms) req successfully completed.");
            return result;
        } catch (Exception e) {
            log.error("[SmsClientService] (sendSms) error : {}", e.getMessage());
            throw e;
        }
    }
}
