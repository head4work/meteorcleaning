package com.example.meteorCleaning.web;

import com.example.meteorCleaning.dto.UserTo;
import com.example.meteorCleaning.model.EstimateOrder;
import com.example.meteorCleaning.model.OrderPrices;
import com.example.meteorCleaning.model.User;
import com.example.meteorCleaning.service.EstimateDataService;
import com.example.meteorCleaning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractUserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EstimateDataService service;


    @GetMapping()
    public String getFirstPage(Model model){
        return getOnePage(model, 1,"dateTime","desc","10");
    }

    @GetMapping("/users")
    public String getFirstPageUsers(Model model){
        return getOnePageUsers(model, 1,"registered","desc","10");
    }

    @GetMapping("/users/page/{pageNumber}")
    public String getOnePageUsers(Model model,
                             @PathVariable("pageNumber") int pageNumber,
                             @RequestParam("sortField") String field,
                             @RequestParam("sortDir") String direction,
                             @RequestParam(name="size",required = false) String size
    ){
        int pageSize = size == null ? 1 :  Integer.parseInt(size);
        Page<User> page = super.findPage(pageNumber,pageSize,field,direction);
        setModel(model, pageNumber, field, direction, size, page,"users");
        return "users";
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

    @PostMapping("/users/save")
    public String updateUser(@ModelAttribute("user") UserTo user) {
        super.update(user, user.id());
        return "redirect:/admin/users";
    }

    @GetMapping("/orders/page/{pageNumber}")
    public String getOnePage(Model model,
                             @PathVariable("pageNumber") int pageNumber,
                             @RequestParam("sortField") String field,
                             @RequestParam("sortDir") String direction,
                             @RequestParam(name="size",required = false) String size
                             )
    {
            int pageSize = size == null ? 1 :  Integer.parseInt(size);
        Page<EstimateOrder> page = service.findPage(pageNumber,pageSize,field,direction);
        setModel(model, pageNumber, field, direction, size, page,"orders");
        return "admin";
    }

    private <T> void setModel(Model model, int pageNumber, String field, String direction, String size, Page<T> page,String target) {
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", field);
        model.addAttribute("sortDir", direction);
        model.addAttribute("size", size);
        model.addAttribute("reverseSortDir", direction.equals("asc") ? "desc" : "asc");
        List<T> content = page.getContent();
        model.addAttribute(target, content);
    }

    @GetMapping("/orders/edit/{id}")
    public String orderUpdate(@PathVariable int id, Model model) {
        EstimateOrder order = service.get(id);
        model.addAttribute("order", order );
        return "edit";
    }

    @GetMapping("/users/edit/{id}")
    public String userUpdate(@PathVariable int id, Model model) {
        User user = super.get(id);
        model.addAttribute("user", user );
        return "user";
    }

    @GetMapping("/orders/delete/{id}")
    public String orderDelete(@PathVariable int id) {
         service.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/users/delete/{id}")
    public String userDelete(@PathVariable int id) {
        super.delete(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/users/orders/{id}")
    public String getUsersOrders(@PathVariable int id,Model model) {
        User userWithOrders = userService.getWithOrders(id);
        List<EstimateOrder> orders =  userWithOrders.getOrders();
        model.addAttribute("orders", orders);
        return "admin";
    }

    @PostMapping("/orders/save")
    public String submitForm(@ModelAttribute("order") EstimateOrder order) {
        service.save(order);
        return "redirect:/admin";
    }
}
