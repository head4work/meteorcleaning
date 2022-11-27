package com.example.meteorCleaning.repository;

import com.example.meteorCleaning.model.OrderPrices;
import org.springframework.stereotype.Repository;
@Repository
public class DataJpaPriceRepository {
    private PriceCrudRepository repository;

    public DataJpaPriceRepository(PriceCrudRepository repository) {
        this.repository = repository;
    }

    public OrderPrices getPrices() {
        return repository.findAll().stream().findFirst().orElse(new OrderPrices(true,
                100,100,100,1,1,
                50,24,50,50,50,
                50,50,5,5,50,50));
    }

    public OrderPrices save(OrderPrices prices) {
        return repository.save(prices);
    }
}
