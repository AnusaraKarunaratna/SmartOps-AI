package com.smartops.notification_service.kafka;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AiAlertEvent {
    
    private String alertType;
    private String message;
}
