package com.example.meteorCleaning.repository;

import com.example.meteorCleaning.model.OrderPrices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceCrudRepository extends JpaRepository<OrderPrices,Boolean> {
}
