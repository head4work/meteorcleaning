package com.example.meteorCleaning.repository;

import com.example.meteorCleaning.model.EstimateOrder;
import org.springframework.stereotype.Repository;

@Repository
public class DataJpaOrderRepository {

    private OrderCrudRepository repository;

    public DataJpaOrderRepository(OrderCrudRepository repository) {
        this.repository = repository;
    }
    public EstimateOrder save(EstimateOrder order){
      return   repository.save(order);
    }

    public EstimateOrder delete(EstimateOrder order){
        return   repository.save(order);
    }

    public EstimateOrder get(EstimateOrder order){
        return   repository.save(order);
    }

    public EstimateOrder update(EstimateOrder order){
        return   repository.save(order);
    }

    public EstimateOrder getAll(EstimateOrder order){
        return   repository.save(order);
    }
}
