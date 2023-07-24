package com.example.meteorCleaning.web;

import com.example.meteorCleaning.model.AjaxResponseBody;
import com.example.meteorCleaning.model.EstimateOrder;
import com.example.meteorCleaning.repository.UserRepository;
import com.example.meteorCleaning.service.EstimateDataService;
import com.example.meteorCleaning.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
public class EstimateController {

    @Value("${HOME_EMAIL:head4work@gmail.com}")
    private String homeEmail;

    @Autowired
    EstimateDataService service;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/estimate")
    public ResponseEntity<?> sendEstimateViaAjax(@Valid @RequestBody EstimateOrder data) throws MessagingException {

        AjaxResponseBody result = new AjaxResponseBody();

        //If error, just return a 400 bad request, along with the error message
//        if (errors.hasErrors()) {
//            result.setMsg(errors.getAllErrors()
//                    .stream().map(x -> x.getDefaultMessage())
//                    .collect(Collectors.joining(",")));
//            return ResponseEntity.badRequest().body(result);
//        }

        String[] to = new String[]{homeEmail, data.getEmail()};

        int userId = SecurityUtil.getUserId();
        if (userId != 0) {
            data.setUser(userRepository.get(userId));
        }
        EstimateOrder order = service.save(data);
        service.sendEmail(to, "CLEANING ORDER", data.toString());
        result.setMsg("success");
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/estimate/cancel")
    private void delete(@RequestParam("id") String id) {
        service.cancelOrder(id);
    }
}
