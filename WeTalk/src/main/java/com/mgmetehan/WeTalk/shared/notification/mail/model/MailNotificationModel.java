package com.mgmetehan.WeTalk.shared.notification.mail.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode
public class MailNotificationModel extends BaseNotificationModel {
    private boolean htmlContent;
    private Set<String> to = new HashSet<>();
    private String subject;
    private List<MailAttachment> attachments = new ArrayList<>();
}