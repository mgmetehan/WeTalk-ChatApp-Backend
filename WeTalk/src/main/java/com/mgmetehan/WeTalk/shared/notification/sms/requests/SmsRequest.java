package com.mgmetehan.WeTalk.shared.notification.sms.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SmsRequest {
    private String msisdn;
    private String message;
}