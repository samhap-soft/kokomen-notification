package com.samhap.kokomen.notification.controller;

import com.samhap.kokomen.notification.service.NotificationInternalService;
import com.samhap.kokomen.notification.service.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/internal/v1/notifications")
@RestController
public class NotificationInternalController {

    private final NotificationInternalService notificationInternalService;

    @PostMapping
    public ResponseEntity<Void> saveNotification(NotificationRequest notificationRequest) {
        notificationInternalService.saveNotification(notificationRequest);
        return ResponseEntity.noContent().build();
    }
}
