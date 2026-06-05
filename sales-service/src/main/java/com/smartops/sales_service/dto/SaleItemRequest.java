package com.smartops.sales_service.dto;
import lombok.Data;

@Data
public class SaleItemRequest {
   private Long productId;
   private Integer quantity;
   private Double price; 
}
