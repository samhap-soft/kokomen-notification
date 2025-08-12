package com.samhap.kokomen.notification.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samhap.kokomen.notification.domain.payload.AnswerLikePayload;
import com.samhap.kokomen.notification.domain.payload.InterviewLikePayload;
import com.samhap.kokomen.notification.domain.payload.InterviewViewCountPayload;
import com.samhap.kokomen.notification.domain.payload.NotificationPayload;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NotificationType {
    INTERVIEW_LIKE(InterviewLikePayload.class),
    ANSWER_LIKE(AnswerLikePayload.class),
    INTERVIEW_VIEW_COUNT(InterviewViewCountPayload.class),
    ;

    private final Class<? extends NotificationPayload> clazz;

    public NotificationPayload toNotificationPayload(ObjectMapper objectMapper, String notificationPayload) {
        try {
            return objectMapper.readValue(notificationPayload, clazz);
        } catch (Exception e) {
            throw new IllegalArgumentException("알림 메시지(notification_payload)가 올바르지 않습니다.", e);
        }
    }
}
