package com.samhap.kokomen.notification.domain;

import com.samhap.kokomen.global.domain.BaseEntity;
import com.samhap.kokomen.notification.domain.payload.NotificationPayload;
import com.samhap.kokomen.notification.infrastructure.NotificationPayloadConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification extends BaseEntity {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "receiver_member_id", nullable = false)
    private Long receiverMemberId;

    @Convert(converter = NotificationPayloadConverter.class)
    @Column(columnDefinition = "json", name = "notification_payload", nullable = false, length = 1000)
    private NotificationPayload notificationPayload;

    @Column(name = "notification_state", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private NotificationState notificationState;

    public Notification(Long receiverMemberId, NotificationPayload notificationPayload) {
        this.receiverMemberId = receiverMemberId;
        this.notificationPayload = notificationPayload;
        this.notificationState = NotificationState.UNREAD;
    }

    public void markAsRead() {
        this.notificationState = NotificationState.READ;
    }
}
