package com.smartops.sales_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleResponse {
    private Long saleId;
    private double totalAmount;
    private String invoiceStatus;
}
