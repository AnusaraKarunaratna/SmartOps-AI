package com.smartops.sales_service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
public class SalesEventProducer {

    private final Optional<KafkaTemplate<String, Object>> kafkaTemplate;

    public SalesEventProducer(Optional<KafkaTemplate<String, Object>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishSaleCreated(SaleEvent event) {
        if (kafkaTemplate.isPresent()) {
            kafkaTemplate.get().send("SALE_CREATED", event);
            log.info("Published sale event to Kafka: {}", event);
        } else {
            log.warn("Kafka not available, skipping event publishing for sale: {}", event.getSaleId());
        }
    }

}
