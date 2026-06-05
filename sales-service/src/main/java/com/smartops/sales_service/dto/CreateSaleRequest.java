package com.smartops.sales_service.dto;

import java.util.*;
import lombok.*;

@Data
public class CreateSaleRequest {
    
    private Long branchId;
    private List<SaleItemRequest> items;
}
