package com.mgmetehan.WeTalk.shared.notification.mail;

import com.mgmetehan.WeTalk.shared.notification.mail.model.BaseNotificationModel;

public interface INotificationSender {
    void sendNotification(BaseNotificationModel model);
}
