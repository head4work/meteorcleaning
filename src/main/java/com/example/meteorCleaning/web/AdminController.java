package com.example.meteorCleaning.web;

import com.example.meteorCleaning.model.EstimateOrder;
import com.example.meteorCleaning.service.EstimateDataService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private EstimateDataService service;

    public AdminController(EstimateDataService service) {
        this.service = service;
    }
    @GetMapping()
    public String getFirstPage(Model model){
        return getOnePage(model, 1,"dateTime","desc");
    }

    @GetMapping("/orders")
    public String getAll(Model model) {
        List<EstimateOrder> orders =  service.getAll() ;
        model.addAttribute("orders", orders);
        return "admin";
    }

    @GetMapping("/page/{pageNumber}")
    public String getOnePage(Model model,
                             @PathVariable("pageNumber") int pageNumber,
                             @RequestParam("sortField") String field,
                             @RequestParam("sortDir") String direction
                             ){
        int pageSize = 5;
        Page<EstimateOrder> page = service.findPage(pageNumber,pageSize,field,direction);
        List<EstimateOrder> orders = page.getContent();
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", field);
        model.addAttribute("sortDir", direction);
        model.addAttribute("reverseSortDir", direction.equals("asc") ? "desc" : "asc");


        model.addAttribute("orders", orders);

        return "admin";
    }


}
