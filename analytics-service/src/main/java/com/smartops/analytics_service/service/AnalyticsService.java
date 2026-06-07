package com.smartops.analytics_service.service;

import com.smartops.analytics_service.entity.BranchRevenue;
import com.smartops.analytics_service.entity.DailySalesSummary;
import com.smartops.analytics_service.kafka.SaleEvent;
import com.smartops.analytics_service.repository.BranchRevenueRepository;
import com.smartops.analytics_service.repository.DailySalesSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final BranchRevenueRepository revenueRepository;
    private final DailySalesSummaryRepository summaryRepository;

    public void processSale(SaleEvent event) {
        updateBranchRevenue(event);
        updateDailySummary(event);
    }

    private void updateBranchRevenue(SaleEvent event) {

        BranchRevenue revenue = revenueRepository.findById(event.getBranchId())
                .orElse(
                    BranchRevenue.builder()
                        .branchId(event.getBranchId())
                        .finalRevenue(0.0)
                        .build()
                );

        revenue.setFinalRevenue(
            revenue.getFinalRevenue() + event.getTotalAmount()
        );
        revenueRepository.save(revenue);
    }

    private void updateDailySummary(SaleEvent event) {

        LocalDate today = LocalDate.now();

        DailySalesSummary summary = summaryRepository.findByBranchIdAndSalesDate(event.getBranchId(), today)
                .orElse(
                    DailySalesSummary.builder()
                        .branchId(event.getBranchId())
                        .salesDate(today)
                        .totalRevenue(0.0)
                        .totalOrders(0L)
                        .build()
                );

        summary.setTotalRevenue(
            summary.getTotalRevenue() + event.getTotalAmount()
        );

        summary.setTotalOrders(
            summary.getTotalOrders() + 1
        );

        summaryRepository.save(summary);
    }
}