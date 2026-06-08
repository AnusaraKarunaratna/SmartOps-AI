package com.smartops.notification_service.kafka;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleEvent {
    
    private Long saleId;
    private Long branchId;
    private Double totalAmount;

}
