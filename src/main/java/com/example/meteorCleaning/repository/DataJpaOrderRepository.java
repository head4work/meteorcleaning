package com.example.meteorCleaning.repository;

import com.example.meteorCleaning.model.EstimateOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaOrderRepository {

    private OrderCrudRepository repository;

    public DataJpaOrderRepository(OrderCrudRepository repository) {
        this.repository = repository;
    }

    public EstimateOrder save(EstimateOrder order) {
        return repository.save(order);
    }

    public EstimateOrder delete(EstimateOrder order) {
        return repository.save(order);
    }

    public EstimateOrder get(EstimateOrder order) {
        return repository.save(order);
    }

    public EstimateOrder update(EstimateOrder order) {
        return repository.save(order);
    }

    public List<EstimateOrder> getAll() {
        return repository.findAll();
    }

    public Page<EstimateOrder> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
