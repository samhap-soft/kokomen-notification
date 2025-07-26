package com.samhap.kokomen.notification.service.dto;

import com.samhap.kokomen.notification.domain.Notification;
import com.samhap.kokomen.notification.domain.payload.NotificationPayload;
import java.util.List;

public record ReadNotificationResponses(
        List<NotificationPayload> notifications
) {

    public static ReadNotificationResponses from(List<Notification> notifications) {
        List<NotificationPayload> notificationPayloads = notifications.stream()
                .map(Notification::getNotificationPayload)
                .toList();

        return new ReadNotificationResponses(notificationPayloads);
    }
}
