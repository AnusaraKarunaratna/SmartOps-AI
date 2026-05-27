package com.smartops.inventory_service.controller;

import com.smartops.inventory_service.entity.Inventory;
import com.smartops.inventory_service.service.InventoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service;

    // Manual constructor (NO Lombok needed)
    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @PostMapping("/update")
    public Inventory updateStock(
            @RequestParam Long branchId,
            @RequestParam Long productId,
            @RequestParam int qty
    ) {
        return service.updateStock(branchId, productId, qty);
    }
}