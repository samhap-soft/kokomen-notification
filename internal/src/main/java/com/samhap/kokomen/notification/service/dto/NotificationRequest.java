package com.samhap.kokomen.notification.service.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.samhap.kokomen.notification.domain.Notification;
import com.samhap.kokomen.notification.domain.payload.NotificationPayload;
import com.samhap.kokomen.notification.infrastructure.NotificationPayloadDeserializer;

public record NotificationRequest(
        Long receiverMemberId,
        @JsonDeserialize(using = NotificationPayloadDeserializer.class)
        NotificationPayload notificationPayload
) {

    public Notification toNotification(ObjectMapper objectMapper) {
        try {
            return new Notification(receiverMemberId, objectMapper.writeValueAsString(notificationPayload));
        } catch (Exception e) {
            throw new IllegalStateException("NotificationPayload 파싱 실패", e);
        }
    }
}
