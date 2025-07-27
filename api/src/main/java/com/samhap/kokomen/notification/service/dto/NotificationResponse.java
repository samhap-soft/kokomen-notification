package com.samhap.kokomen.notification.service.dto;

import com.samhap.kokomen.notification.domain.Notification;
import com.samhap.kokomen.notification.domain.payload.NotificationPayload;
import java.time.LocalDateTime;

public record NotificationResponse(
        NotificationPayload notificationPayload,
        LocalDateTime createdAt
) {

    public NotificationResponse(Notification notification) {
        this(notification.getNotificationPayload(), notification.getCreatedAt());
    }
}
