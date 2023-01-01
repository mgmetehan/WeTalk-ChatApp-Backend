package com.mgmetehan.WeTalk.shared.notification.sms.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsConfigurationProperties {
    private String url;
    private boolean mock;
    private String otpMessageFormat;
}
