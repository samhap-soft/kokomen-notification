package com.samhap.kokomen.notification.service.dto;

import com.samhap.kokomen.notification.domain.Notification;
import java.util.List;

public record UnreadNotificationResponses(
        List<NotificationResponse> notifications,
        Boolean hasNext
) {

    public static UnreadNotificationResponses of(List<Notification> notifications, int size) {
        boolean hasNext = notifications.size() > size;
        List<NotificationResponse> notificationPayloads = notifications.stream()
                .limit(size)
                .map(NotificationResponse::new)
                .toList();

        return new UnreadNotificationResponses(notificationPayloads, hasNext);
    }
}
