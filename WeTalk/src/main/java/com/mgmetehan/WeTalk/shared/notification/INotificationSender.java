package com.mgmetehan.WeTalk.shared.notification;

import com.mgmetehan.WeTalk.shared.notification.model.BaseNotificationModel;

public interface INotificationSender {
    void sendNotification(BaseNotificationModel model);
}
