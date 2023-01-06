package com.mgmetehan.WeTalk.shared.notification.sms;

import com.mgmetehan.WeTalk.shared.notification.sms.requests.SmsRequest;
import com.mgmetehan.WeTalk.shared.notification.sms.response.SmsResponse;

public interface SmsService {

    SmsResponse sendSms(SmsRequest smsRequest);
}
