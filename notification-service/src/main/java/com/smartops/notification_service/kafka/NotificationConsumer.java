package com.smartops.notification_service.kafka;

import com.smartops.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer { 

    private final NotificationService service;
    
    @KafkaListener(
        topics = "SALE_CREATED",
        groupId = "notification-group",
        containerFactory = "saleEventKafkaListenerContainerFactory"
    )
    public void consumeSale(SaleEvent saleEvent) {
        try {
            log.info("Consuming sale event: saleId={}, branchId={}, amount={}", 
                saleEvent.getSaleId(), saleEvent.getBranchId(), saleEvent.getTotalAmount());
            service.createNotification(
                "SALE", 
                "Sale created. Revenue: " + saleEvent.getTotalAmount());
            log.info("Successfully created notification for sale: {}", saleEvent.getSaleId());
        } catch (Exception e) {
            log.error("Error processing sale event: {}", saleEvent, e);
        }
    }

    @KafkaListener(
        topics = "LOW_STOCK_DETECTED",
        groupId = "notification-group",
        containerFactory = "stockEventKafkaListenerContainerFactory"
    )
    public void consumeStock(StockEvent event) {
        try {
            log.info("Consuming stock event: productId={}", event.getProductId());
            service.createNotification(
                "LOW_STOCK", 
                "Low stock detected for Product " + event.getProductId());
            log.info("Successfully created notification for low stock: {}", event.getProductId());
        } catch (Exception e) {
            log.error("Error processing stock event: {}", event, e);
        }
    }

    @KafkaListener(
        topics = "AI_ALERT_CREATED",
        groupId = "notification-group",
        containerFactory = "aiAlertEventKafkaListenerContainerFactory"
    )
    public void consumeAiAlert(AiAlertEvent event) {
        try {
            log.info("Consuming AI alert event: {}", event.getMessage());
            service.createNotification(
                "AI_ALERT",
                event.getMessage());
            log.info("Successfully created notification for AI alert");
        } catch (Exception e) {
            log.error("Error processing AI alert event: {}", event, e);
        }
    }
}
