package com.samhap.kokomen.notification.repository;

import com.samhap.kokomen.notification.domain.Notification;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByReceiverMemberId(Long receiverMemberId, Pageable pageable);
}
