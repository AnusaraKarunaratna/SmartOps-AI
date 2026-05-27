package com.smartops.inventory_service.entity;

import jakarta.persistence.*;

//Inventory table config
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long branchId;
    private Long productId;
    private Integer quantity;
    private Integer minimumStock;

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getBranchId() { return branchId; }

    public void setBranchId(Long branchId) { this.branchId = branchId; }

    public Long getProductId() { return productId; }

    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getMinimumStock() { return minimumStock; }

    public void setMinimumStock(Integer minimumStock) { this.minimumStock = minimumStock; }
}