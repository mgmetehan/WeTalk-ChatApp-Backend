package com.mgmetehan.WeTalk.shared.notification.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class BaseNotificationModel {
    private String content;
}
