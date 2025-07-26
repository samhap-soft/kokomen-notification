package com.samhap.kokomen.notification.service.dto;

import com.samhap.kokomen.notification.domain.Notification;
import com.samhap.kokomen.notification.domain.payload.NotificationPayload;
import java.util.List;

public record UnreadNotificationResponses(
        List<NotificationPayload> notifications,
        Boolean hasNext
) {

    public static UnreadNotificationResponses of(List<Notification> notifications, int size) {
        boolean hasNext = notifications.size() > size;
        List<NotificationPayload> notificationPayloads = notifications.stream()
                .limit(size)
                .map(Notification::getNotificationPayload)
                .toList();

        return new UnreadNotificationResponses(notificationPayloads, hasNext);
    }
}
