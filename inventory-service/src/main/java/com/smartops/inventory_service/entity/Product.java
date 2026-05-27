package com.smartops.inventory_service.entity;

// Product table entity
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Product name
    private String name;

    // Stock Keeping Unit
    @Column(unique = true)
    private String sku;

    // Barcode
    @Column(unique = true)
    private String barcode;

    // Product selling price
    private Double price;
}