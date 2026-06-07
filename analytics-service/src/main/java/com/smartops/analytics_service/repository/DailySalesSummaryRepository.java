package com.smartops.analytics_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartops.analytics_service.entity.DailySalesSummary;

import java.time.LocalDate;
import java.util.Optional;

public interface DailySalesSummaryRepository extends JpaRepository<DailySalesSummary, Long> {
    Optional<DailySalesSummary> findByBranchIdAndSalesDate(Long branchId, LocalDate salesDate);
}