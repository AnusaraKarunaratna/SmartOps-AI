package com.smartops.inventory_service.controller;

import com.smartops.inventory_service.entity.Inventory;
import com.smartops.inventory_service.service.InventoryService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service;

    // Manual constructor
    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return service.createInventory(inventory);
    }

    @GetMapping
    public List<Inventory> getAllInventory(){
        return service.getAllInventory();
    }

    @GetMapping("/{id}")
    public Inventory getInventoryById(@PathVariable Long id){
        return service.getInventoryById(id);
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