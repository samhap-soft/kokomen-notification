package com.samhap.kokomen.notification.repository;

import com.samhap.kokomen.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
