package com.samhap.kokomen.notification.domain;

import com.samhap.kokomen.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification extends BaseEntity {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "receiver_member_id", nullable = false)
    private Long receiverMemberId;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "notification_state", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private NotificationState notificationState;

    public Notification(Long receiverMemberId, String content) {
        this.receiverMemberId = receiverMemberId;
        this.content = content;
    }
}
