package com.samhap.kokomen.notification.domain.payload;

import com.samhap.kokomen.notification.domain.NotificationType;

public record InterviewViewCountPayload(
        NotificationType notificationType,
        Long interviewId,
        Long viewCount
) implements NotificationPayload {
}
