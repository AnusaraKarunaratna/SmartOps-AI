package com.smartops.inventory_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // Manual constrcutor instead of lombok 
    public InventoryEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Send stock updated event
    public void sendStockUpdate(StockEvent event) {
        kafkaTemplate.send("STOCK_UPDATED", event);
    }

    // Send low stock alert event
    public void sendLowStockAlert(StockEvent event) {
        kafkaTemplate.send("LOW_STOCK_DETECTED", event);
    }
}