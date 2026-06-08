package com.smartops.sales_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalesEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishSaleCreated(SaleEvent event) {
        try {
            kafkaTemplate.send("SALE_CREATED", event);
            log.info("Published sale event to Kafka: saleId={}, branchId={}, total={}",
                event.getSaleId(), event.getBranchId(), event.getTotalAmount());
        } catch (Exception e) {
            log.error("Error publishing sale event: saleId={}", event.getSaleId(), e);
            throw new RuntimeException("Failed to publish sale event", e);
        }
    }
}