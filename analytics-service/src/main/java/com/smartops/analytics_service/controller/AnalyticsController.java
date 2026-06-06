package com.smartops.analytics_service.controller;

import com.smartops.analytics_service.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    
    private final AnalyticsService analyticsService;

    @GetMapping("/branch/{branchId}")
    public Double getBranchRevenue(@PathVariable Long branchId){
        return analyticsService.getBranchRevenue(branchId);
    }

    @GetMapping("/all")
    public Map<Long, Double> getAllRevenue(){
        return analyticsService.getAllRevenue();
    }
}
