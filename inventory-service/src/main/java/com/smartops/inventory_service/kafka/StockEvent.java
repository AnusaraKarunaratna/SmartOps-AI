package com.smartops.inventory_service.kafka;

public class StockEvent {

    private Long productId;
    private Long branchId;
    private Integer quantity;
    private String eventType;

    public StockEvent() {}

    public StockEvent(Long productId,
                      Long branchId,
                      Integer quantity,
                      String eventType) {
        this.productId = productId;
        this.branchId = branchId;
        this.quantity = quantity;
        this.eventType = eventType;
    }

    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
}