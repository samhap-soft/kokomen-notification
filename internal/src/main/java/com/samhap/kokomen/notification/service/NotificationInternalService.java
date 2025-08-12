package com.samhap.kokomen.notification.service;

import com.samhap.kokomen.notification.repository.NotificationRepository;
import com.samhap.kokomen.notification.service.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationInternalService {

    private final NotificationRepository notificationRepository;

    public void saveNotification(NotificationRequest notificationRequest) {
        notificationRepository.save(notificationRequest.toNotification());
    }
}
