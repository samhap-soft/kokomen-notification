package com.samhap.kokomen.global.fixture;

import com.samhap.kokomen.notification.domain.Notification;
import com.samhap.kokomen.notification.domain.NotificationState;
import com.samhap.kokomen.notification.domain.NotificationType;
import com.samhap.kokomen.notification.domain.payload.InterviewLikePayload;
import com.samhap.kokomen.notification.domain.payload.NotificationPayload;

public class NotificationFixtureBuilder {

    private Long id;
    private Long receiverMemberId;
    private NotificationPayload notificationPayload;
    private NotificationState notificationState;

    public static NotificationFixtureBuilder builder() {
        return new NotificationFixtureBuilder();
    }

    public NotificationFixtureBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public NotificationFixtureBuilder receiverMemberId(Long receiverMemberId) {
        this.receiverMemberId = receiverMemberId;
        return this;
    }

    public NotificationFixtureBuilder notificationPayload(NotificationPayload notificationPayload) {
        this.notificationPayload = notificationPayload;
        return this;
    }

    public NotificationFixtureBuilder notificationState(NotificationState notificationState) {
        this.notificationState = notificationState;
        return this;
    }

    public Notification build() {
        return new Notification(
                id != null ? id : null,
                receiverMemberId != null ? receiverMemberId : 1L,
                notificationPayload != null ? notificationPayload : new InterviewLikePayload(NotificationType.INTERVIEW_LIKE, 1L, 1L, 10L),
                notificationState != null ? notificationState : NotificationState.UNREAD
        );
    }
}
