package com.samhap.kokomen.notification.service.dto;

import com.samhap.kokomen.notification.domain.Notification;

public record NotificationRequest(
        Long receiverMemberId,
        String content
) {

    public Notification toNotification() {
        return new Notification(receiverMemberId, content);
    }
}
