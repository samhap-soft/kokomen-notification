package com.samhap.kokomen.notification.service;

import com.samhap.kokomen.global.dto.MemberAuth;
import com.samhap.kokomen.notification.domain.Notification;
import com.samhap.kokomen.notification.domain.NotificationState;
import com.samhap.kokomen.notification.repository.NotificationRepository;
import com.samhap.kokomen.notification.service.dto.ReadNotificationResponses;
import com.samhap.kokomen.notification.service.dto.UnreadNotificationResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NotificationApiService {

    private final NotificationRepository notificationRepository;

    @Transactional(readOnly = true)
    public UnreadNotificationResponses findMyUnreadNotifications(MemberAuth memberAuth, int size, Sort sort) {
        List<Notification> unreadNotifications = findMyNotifications(memberAuth, NotificationState.UNREAD, PageRequest.of(0, size + 1, sort));
        unreadNotifications.stream()
                .limit(size)
                .forEach(Notification::markAsRead);

        return UnreadNotificationResponses.of(unreadNotifications, size);
    }

    @Transactional(readOnly = true)
    public ReadNotificationResponses findMyReadNotifications(MemberAuth memberAuth, Pageable pageable) {
        List<Notification> readNotifications = findMyNotifications(memberAuth, NotificationState.READ, pageable);

        return ReadNotificationResponses.from(readNotifications);
    }

    private List<Notification> findMyNotifications(MemberAuth memberAuth, NotificationState notificationState, Pageable pageable) {
        return notificationRepository.findByReceiverMemberIdAndNotificationState(memberAuth.memberId(), notificationState, pageable);
    }
}
