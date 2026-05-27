package com.smartops.inventory_service.repository;

// Database access for Inventory table
import com.smartops.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Find inventory using branch + product
    Optional<Inventory> findByBranchIdAndProductId(
            Long branchId,
            Long productId
    );
}