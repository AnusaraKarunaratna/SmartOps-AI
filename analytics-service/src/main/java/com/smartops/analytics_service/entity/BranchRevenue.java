package com.smartops.analytics_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "branch_revenue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchRevenue {
    
    @Id
    private long branchId;

    private Double finalRevenue;
}
