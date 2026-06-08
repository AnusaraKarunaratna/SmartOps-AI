package com.smartops.notification_service.controller;

import com.smartops.notification_service.entity.Notification;
import com.smartops.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    
    private final NotificationService service;

    @GetMapping
    public List<Notification> getAllNotifications(){
        return service.getAllNotifications();
    }
}
