package com.mgmetehan.WeTalk.shared.notification.mail.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class BaseNotificationModel {
    private String content;
}
