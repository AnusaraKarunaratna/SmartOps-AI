package com.smartops.analytics_service.controller;

import com.smartops.analytics_service.dto.DashboardResponse;
import com.smartops.analytics_service.entity.BranchRevenue;
import com.smartops.analytics_service.entity.DailySalesSummary;
import com.smartops.analytics_service.repository.BranchRevenueRepository;
import com.smartops.analytics_service.repository.DailySalesSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final BranchRevenueRepository revenueRepository;
    private final DailySalesSummaryRepository summaryRepository;

    @GetMapping("/dashboard/{branchId}")
    public DashboardResponse dashboard(@PathVariable Long branchId) {

        BranchRevenue revenue = revenueRepository.findById(branchId)
                .orElse(new BranchRevenue());

        DailySalesSummary summary = summaryRepository.findByBranchIdAndSalesDate(branchId, LocalDate.now())
                .orElse(new DailySalesSummary());

        return DashboardResponse.builder()
                .branchId(branchId)
                .totalRevenue(revenue.getFinalRevenue() == null ? 0.0 : revenue.getFinalRevenue())
                .totalOrders(summary.getTotalOrders() == null ? 0L : summary.getTotalOrders())
                .build();
    }
}