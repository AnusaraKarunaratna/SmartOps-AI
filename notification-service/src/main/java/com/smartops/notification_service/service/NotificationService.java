package com.smartops.notification_service.service;

import com.smartops.notification_service.entity.Notification;
import com.smartops.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void createNotification(String type, String message) {
        try {
            Notification notification = Notification.builder()
                    .type(type)
                    .message(message)
                    .status("UNREAD")
                    .createdAt(LocalDateTime.now())
                    .build();
            
            Notification saved = notificationRepository.save(notification);
            log.info("Notification created successfully - id: {}, type: {}, message: {}", 
                saved.getId(), type, message);
        } catch (Exception e) {
            log.error("Error creating notification - type: {}, message: {}", type, message, e);
            throw e;
        }
    }

    public List<Notification> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        log.info("Retrieved {} notifications", notifications.size());
        return notifications;
    }

}

