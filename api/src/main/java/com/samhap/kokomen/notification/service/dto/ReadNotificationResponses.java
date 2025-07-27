package com.samhap.kokomen.notification.service.dto;

import com.samhap.kokomen.notification.domain.Notification;
import java.util.List;

public record ReadNotificationResponses(
        List<NotificationResponse> notifications
) {

    public static ReadNotificationResponses from(List<Notification> notifications) {
        List<NotificationResponse> notificationPayloads = notifications.stream()
                .map(NotificationResponse::new)
                .toList();

        return new ReadNotificationResponses(notificationPayloads);
    }
}
