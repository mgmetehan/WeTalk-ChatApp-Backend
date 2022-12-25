package com.mgmetehan.WeTalk.shared.notification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("mail")
public class MailConfigurationProperties {
    private String from;
}
