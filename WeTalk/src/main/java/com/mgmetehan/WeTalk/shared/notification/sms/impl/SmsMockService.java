package com.mgmetehan.WeTalk.shared.notification.sms.impl;

import com.mgmetehan.WeTalk.shared.notification.sms.SmsService;
import com.mgmetehan.WeTalk.shared.notification.sms.requests.SmsRequest;
import com.mgmetehan.WeTalk.shared.notification.sms.response.SmsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "sms", name = "mock", havingValue = "true")
public class SmsMockService implements SmsService {

    @Override
    public SmsResponse sendSms(SmsRequest smsRequest) {
        return new SmsResponse();
    }
}
