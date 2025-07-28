package com.samhap.kokomen.notification.repository;

import com.samhap.kokomen.notification.domain.Notification;
import com.samhap.kokomen.notification.domain.NotificationState;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByReceiverMemberIdAndNotificationState(Long receiverMemberId, NotificationState notificationState, Pageable pageable);
}
