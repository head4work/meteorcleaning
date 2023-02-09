package com.example.meteorCleaning.repository.datajpa;

import com.example.meteorCleaning.model.EstimateOrder;
import com.example.meteorCleaning.repository.OrderCrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    public EstimateOrder get(int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Order with id: " + id +" not found" ));
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

    public Integer delete(int id){
      return   repository.delete(id);
    }

    public Map<Object, Long> getAllDates() {

        List<EstimateOrder> futureOrders = repository.getFutureOrders();
        Map<Object,Long> result = new ConcurrentHashMap<>();
         futureOrders.forEach(e -> result.compute(e.getDateTime().toLocalDate(), (k, v) -> {
             if (v == null){
                 if(e.getDateTime().getHour() == 11){
                     return 0L;
                 } return 1L;
             }
             return  v + 3L;
         }));

         return result;
    }
}
