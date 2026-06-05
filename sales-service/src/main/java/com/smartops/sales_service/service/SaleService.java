package com.smartops.sales_service.service;

import com.smartops.sales_service.dto.*;
import com.smartops.sales_service.entity.*;
import com.smartops.sales_service.kafka.*;
import com.smartops.sales_service.repository.*;
import lombok.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class SaleService {
    
    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final InvoiceRepository invoiceRepository;
    private final SalesEventProducer producer;

    public SaleResponse createSale(CreateSaleRequest request) {

        double total = request.getItems()
                .stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity()
                ).sum();

        Sale sale = Sale.builder()
                .branchId(request.getBranchId())
                .totalAmount(total)
                .createdAt(LocalDateTime.now())
                .build();

        Sale savedSale = saleRepository.save(sale);

        request.getItems().forEach(item -> {

            SaleItem saleItem = SaleItem.builder()
                    .saleId(savedSale.getId())
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .price(item.getPrice())
                    .build();
            saleItemRepository.save(saleItem);
        });
        
        Invoice invoice = Invoice.builder()
                .saleId(savedSale.getId())
                .total(total)
                .status("GENERATED")
                .build();
        invoiceRepository.save(invoice);

        producer.publishSaleCreated(
            new SaleEvent(
                savedSale.getId(),
                savedSale.getBranchId(),
                total
            )
        );

        return SaleResponse.builder()
                .saleId(savedSale.getId())
                .totalAmount(total)
                .invoiceStatus("GENERATED")
                .build();
    }
}