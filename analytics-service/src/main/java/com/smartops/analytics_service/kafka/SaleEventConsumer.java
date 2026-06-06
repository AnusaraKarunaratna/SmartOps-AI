package com.smartops.analytics_service.kafka;

import org.springframework.stereotype.Service;
import com.smartops.analytics_service.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;


@Service
@RequiredArgsConstructor
public class SaleEventConsumer {
   
    private final AnalyticsService analyticsService;

    @KafkaListener(topics = "SALE_CREATED", groupId = "analytics_group")
    public void consumeSaleEvent(SaleEvent event){
        analyticsService.processSale(event);
    }
}
