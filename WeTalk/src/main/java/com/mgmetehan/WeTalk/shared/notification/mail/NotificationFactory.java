package com.mgmetehan.WeTalk.shared.notification.mail;

import com.mgmetehan.WeTalk.shared.notification.mail.model.BaseNotificationModel;
import com.mgmetehan.WeTalk.shared.notification.mail.model.MailNotificationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationFactory {
    private final INotificationSender mailSenderImpl;

    @Async
    public void sendNotification(NotificationType type, BaseNotificationModel model) {
        if (type == NotificationType.EMAIL) {
            if (!(model instanceof MailNotificationModel)) {
                throw new IllegalArgumentException("This model not supported for this notification type.");
            }
            mailSenderImpl.sendNotification(model);
        } else {
            throw new UnsupportedOperationException("Unsupported notification type.");
        }
    }
}
