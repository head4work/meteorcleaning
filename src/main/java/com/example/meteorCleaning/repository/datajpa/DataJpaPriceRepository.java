package com.example.meteorCleaning.repository.datajpa;

import com.example.meteorCleaning.model.OrderPrices;
import com.example.meteorCleaning.repository.PriceCrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public class DataJpaPriceRepository {
    private PriceCrudRepository repository;

    public DataJpaPriceRepository(PriceCrudRepository repository) {
        this.repository = repository;
    }

    public OrderPrices getPrices() {
        return repository.findAll().stream().findFirst().orElse(new OrderPrices(true,
                100, 130, 180, 0.1, 0.2,
                20, 24, 40, 1.3, 20,
                30, 30, 8, 10, 10, 50));
    }

    public OrderPrices save(OrderPrices prices) {
        return repository.save(prices);
    }
}
