package com.example.meteorCleaning.web;

import com.example.meteorCleaning.service.EstimateDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private EstimateDataService service;

    public AdminController(EstimateDataService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("orders", service.getAll());
        return "admin";
    }
}
