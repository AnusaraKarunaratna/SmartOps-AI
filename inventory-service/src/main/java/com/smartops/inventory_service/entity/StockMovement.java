package com.smartops.inventory_service.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long branchId;
    private String type;
    private Integer quantityBefore;
    private Integer quantityAfter;
    private LocalDateTime timestamp;

    // REQUIRED: no-args constructor
    public StockMovement() {}

    // REQUIRED: all-args constructor (THIS FIXES YOUR ERROR)
    public StockMovement(Long id,
                         Long productId,
                         Long branchId,
                         String type,
                         Integer quantityBefore,
                         Integer quantityAfter,
                         LocalDateTime timestamp) {
        this.id = id;
        this.productId = productId;
        this.branchId = branchId;
        this.type = type;
        this.quantityBefore = quantityBefore;
        this.quantityAfter = quantityAfter;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getQuantityBefore() { return quantityBefore; }
    public void setQuantityBefore(Integer quantityBefore) { this.quantityBefore = quantityBefore; }

    public Integer getQuantityAfter() { return quantityAfter; }
    public void setQuantityAfter(Integer quantityAfter) { this.quantityAfter = quantityAfter; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}