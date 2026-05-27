package com.smartops.inventory_service.repository;

// Database access for stock movement history
import com.smartops.inventory_service.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
}