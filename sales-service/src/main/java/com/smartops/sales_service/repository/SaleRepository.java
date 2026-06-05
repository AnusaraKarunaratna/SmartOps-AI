package com.smartops.sales_service.repository;

import com.smartops.sales_service.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long>{
}
