package com.mgmetehan.WeTalk.shared.notification.sms.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SmsResponse {
    private String transactionId;
    private String msisdn;
    private String message;
}
