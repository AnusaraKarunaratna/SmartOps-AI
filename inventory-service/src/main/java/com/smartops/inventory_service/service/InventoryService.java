package com.smartops.inventory_service.service;

import com.smartops.inventory_service.entity.Inventory;
import com.smartops.inventory_service.entity.StockMovement;
import com.smartops.inventory_service.kafka.InventoryEventProducer;
import com.smartops.inventory_service.kafka.StockEvent;
import com.smartops.inventory_service.repository.InventoryRepository;
import com.smartops.inventory_service.repository.StockMovementRepository;

import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDateTime;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepo;
    private final StockMovementRepository movementRepo;
    private final InventoryEventProducer producer;

    // Manual Constrcutor instead of lombok
    public InventoryService(
            InventoryRepository inventoryRepo,
            StockMovementRepository movementRepo,
            InventoryEventProducer producer
    ) {
        this.inventoryRepo = inventoryRepo;
        this.movementRepo = movementRepo;
        this.producer = producer;
    }

    public Inventory createInventory(Inventory inventory){
        return inventoryRepo.save(inventory);
    }

    public List<Inventory> getAllInventory(){
        return inventoryRepo.findAll();
    }

    public Inventory getInventoryById(Long id){
        return inventoryRepo.findById(id).orElseThrow(()-> new RuntimeException("Inventory not found"));
    }

    public Inventory updateStock(Long branchId, Long productId, int qty) {

        Inventory inventory = inventoryRepo
                .findByBranchIdAndProductId(branchId, productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        int before = inventory.getQuantity();
        int after = before + qty;

        inventory.setQuantity(after);

        Inventory saved = inventoryRepo.save(inventory);

        StockMovement movement = new StockMovement(
                null,
                productId,
                branchId,
                "UPDATE",
                before,
                after,
                LocalDateTime.now()
        );

        movementRepo.save(movement);

        StockEvent event = new StockEvent(
                productId,
                branchId,
                after,
                "STOCK_UPDATED"
        );

        producer.sendStockUpdate(event);

        if (after < inventory.getMinimumStock()) {

            StockEvent lowStockEvent = new StockEvent(
                    productId,
                    branchId,
                    after,
                    "LOW_STOCK"
            );

            producer.sendLowStockAlert(lowStockEvent);
        }

        return saved;
    }

    public void deleteInventory(Long id){
        inventoryRepo.deleteById(id);
    }
}
