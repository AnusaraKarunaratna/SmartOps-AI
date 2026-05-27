package com.smartops.inventory_service.repository;

// Database access for Product table
import com.smartops.inventory_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}