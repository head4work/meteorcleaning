package com.example.meteorCleaning.web;

import com.example.meteorCleaning.model.EstimateOrder;
import com.example.meteorCleaning.model.OrderPrices;
import com.example.meteorCleaning.service.EstimateDataService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return getOnePage(model, 1,"dateTime","desc","1");
    }

    @GetMapping("/orders")
    public String getAll(Model model) {
        List<EstimateOrder> orders =  service.getAll() ;
        model.addAttribute("orders", orders);
        return "admin";
    }

    @GetMapping("/prices")
    public String getPrices(Model model) {
        OrderPrices prices =  service.getPrices() ;
        model.addAttribute("prices", prices);
        return "prices";
    }

    @PostMapping("/prices/save")
    public String savePrices(@ModelAttribute("prices") OrderPrices prices) {
        service.savePrices(prices);
        return "redirect:/admin";
    }

    @GetMapping("/page/{pageNumber}")
    public String getOnePage(Model model,
                             @PathVariable("pageNumber") int pageNumber,
                             @RequestParam("sortField") String field,
                             @RequestParam("sortDir") String direction,
                             @RequestParam(name="size",required = false) String size
                             ){
            int pageSize = size == null ? 1 :  Integer.parseInt(size);
        Page<EstimateOrder> page = service.findPage(pageNumber,pageSize,field,direction);
        List<EstimateOrder> orders = page.getContent();
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", field);
        model.addAttribute("sortDir", direction);
        model.addAttribute("size", size);
        model.addAttribute("reverseSortDir", direction.equals("asc") ? "desc" : "asc");


        model.addAttribute("orders", orders);

        return "admin";
    }
    @GetMapping("/orders/edit/{id}")
    public String orderUpdate(@PathVariable int id, Model model) {
        EstimateOrder order = service.get(id);
        model.addAttribute("order", order );
        return "edit";
    }

    @GetMapping("/orders/delete/{id}")
    public String orderDelete(@PathVariable int id) {
         service.delete(id);
        return "redirect:/admin";
    }

    @PostMapping("/orders/save")
    public String submitForm(@ModelAttribute("order") EstimateOrder order) {
        service.save(order);
        return "redirect:/admin";
    }
}
