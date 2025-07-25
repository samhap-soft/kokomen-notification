package com.samhap.kokomen.notification.domain.payload;

import com.samhap.kokomen.notification.domain.NotificationType;

public record InterviewLikePayload(
        NotificationType notificationType,
        Long interviewId,
        Long likerMemberId,
        Long likeCount
) implements NotificationPayload {
}
