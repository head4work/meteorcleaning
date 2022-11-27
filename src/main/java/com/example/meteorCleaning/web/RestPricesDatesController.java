package com.example.meteorCleaning.web;

import com.example.meteorCleaning.model.OrderPrices;
import com.example.meteorCleaning.service.EstimateDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestPricesDatesController {
    private final EstimateDataService service;

    public RestPricesDatesController(EstimateDataService service) {
        this.service = service;
    }


    @GetMapping("/prices")
    OrderPrices getPrices(){
        return null;
    }
}
