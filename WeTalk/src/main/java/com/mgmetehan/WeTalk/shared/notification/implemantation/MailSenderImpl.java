package com.mgmetehan.WeTalk.shared.notification.implemantation;

import com.mgmetehan.WeTalk.shared.notification.INotificationSender;
import com.mgmetehan.WeTalk.shared.notification.config.MailConfigurationProperties;
import com.mgmetehan.WeTalk.shared.notification.constant.MailConstants;
import com.mgmetehan.WeTalk.shared.notification.model.BaseNotificationModel;
import com.mgmetehan.WeTalk.shared.notification.model.MailAttachment;
import com.mgmetehan.WeTalk.shared.notification.model.MailNotificationModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;


@Service
@RequiredArgsConstructor
@Slf4j
public class MailSenderImpl implements INotificationSender {
    private final JavaMailSender javaMailSender;
    private final MailConfigurationProperties mailConfigurationProperties;

    @Override
    public void sendNotification(BaseNotificationModel model) {

        var mailNotificationModel = (MailNotificationModel) model;
        boolean hasAttachment = !CollectionUtils.isEmpty(mailNotificationModel.getAttachments());

        var message = javaMailSender.createMimeMessage();

        try {
            message.setContent(mailNotificationModel.getContent(), MailConstants.TYPE);
            message.setSubject(mailNotificationModel.getSubject(), MailConstants.CHARSET);
            var helper = new MimeMessageHelper(message, hasAttachment);
            helper.setTo(mailNotificationModel.getTo().toArray(new String[0]));
            helper.setFrom(mailConfigurationProperties.getFrom());

            if (!CollectionUtils.isEmpty(mailNotificationModel.getAttachments())) {
                for (MailAttachment attachment : mailNotificationModel.getAttachments()) {
                    helper.addAttachment(attachment.getAttachmentFilename(), attachment.getFile());
                }
            }

            javaMailSender.send(message);
            log.info("Successfully mail. To: {}, Subject: {}, Content: {}", mailNotificationModel.getTo(), mailNotificationModel.getSubject(), mailNotificationModel.getContent());
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }
}
