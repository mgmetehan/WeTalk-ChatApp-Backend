package com.mgmetehan.WeTalk.shared.notification.mail.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class MailAttachment {
    private String attachmentFilename;
    private File file;
}
