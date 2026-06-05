package com.smartops.sales_service.controller;

import com.smartops.sales_service.dto.CreateSaleRequest;
import com.smartops.sales_service.dto.SaleResponse;
import com.smartops.sales_service.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {
    
    private final SaleService saleService;

    @PostMapping
    public SaleResponse createSale(
        @RequestBody CreateSaleRequest request
    ){
        return saleService.createSale(request);
    }
}
