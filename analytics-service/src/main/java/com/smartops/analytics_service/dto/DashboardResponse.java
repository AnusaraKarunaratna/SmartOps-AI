package com.smartops.analytics_service.dto;

import lombok.*;

@Data
@Builder
public class DashboardResponse {
    
    private Long branchId;
    private Double totalRevenue;
    private Long totalOrders;
}
