package com.smartops.analytics_service.service;

import com.smartops.analytics_service.kafka.SaleEvent;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AnalyticsService {
    
    private final ConcurrentHashMap<Long, Double> branchRevenue = new ConcurrentHashMap<>();

    public void processSale(SaleEvent event) {

        branchRevenue.merge(event.getBranchId(), event.getTotalAmount(), Double::sum);
    }

    public double getBranchRevenue(Long branchId) {
        return branchRevenue.getOrDefault(branchId, 0.0);
    }

    public ConcurrentHashMap<Long, Double> getAllRevenue() {
        return branchRevenue;
    }
}
