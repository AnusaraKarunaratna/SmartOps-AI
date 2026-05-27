package com.smartops.inventory_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Kafka configuartion class
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic stockUpdatedTopic() {
        return new NewTopic("STOCK_UPDATED", 3, (short) 1);
    }

    @Bean
    public NewTopic lowStockTopic() {
        return new NewTopic("LOW_STOCK_DETECTED", 3, (short) 1);
    }
}