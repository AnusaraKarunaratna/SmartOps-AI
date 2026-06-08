package com.smartops.notification_service.kafka;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockEvent {
    private Long productId;
    private Long branchId;
    private Integer quantity;
    private String eventType;
}
