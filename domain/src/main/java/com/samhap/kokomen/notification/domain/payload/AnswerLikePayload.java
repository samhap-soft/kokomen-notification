package com.samhap.kokomen.notification.domain.payload;

import com.samhap.kokomen.notification.domain.NotificationType;

public record AnswerLikePayload(
        NotificationType notificationType,
        Long answerId,
        Long interviewId,
        Long likerMemberId,
        Long likeCount
) implements NotificationPayload {
}
