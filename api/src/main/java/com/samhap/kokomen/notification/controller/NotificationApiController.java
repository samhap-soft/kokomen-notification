package com.samhap.kokomen.notification.controller;

import com.samhap.kokomen.global.annotation.Authentication;
import com.samhap.kokomen.global.dto.MemberAuth;
import com.samhap.kokomen.notification.service.NotificationApiService;
import com.samhap.kokomen.notification.service.dto.ReadNotificationResponses;
import com.samhap.kokomen.notification.service.dto.UnreadNotificationResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@RestController
public class NotificationApiController {

    private final NotificationApiService notificationApiService;

    @GetMapping("/me/unread")
    public ResponseEntity<UnreadNotificationResponses> findMyUnreadNotifications(
            @RequestParam(required = false, defaultValue = "10") int size,
            @SortDefault(sort = "id", direction = Sort.Direction.DESC) Sort sort,
            @Authentication MemberAuth memberAuth
    ) {
        return ResponseEntity.ok(notificationApiService.findMyUnreadNotifications(memberAuth, size, sort));
    }

    @GetMapping("/me/read")
    public ResponseEntity<ReadNotificationResponses> findMyReadNotifications(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @Authentication MemberAuth memberAuth
    ) {
        return ResponseEntity.ok(notificationApiService.findMyReadNotifications(memberAuth, pageable));
    }
}
