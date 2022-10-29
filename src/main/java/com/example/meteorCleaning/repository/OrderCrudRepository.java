package com.example.meteorCleaning.repository;

import com.example.meteorCleaning.model.EstimateOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OrderCrudRepository extends JpaRepository<EstimateOrder,Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM EstimateOrder o WHERE o.id=:id")
    int delete(@Param("id") int id);
}
