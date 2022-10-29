package com.example.meteorCleaning.web;

import com.example.meteorCleaning.model.AjaxResponseBody;
import com.example.meteorCleaning.model.EstimateOrder;
import com.example.meteorCleaning.repository.DataJpaOrderRepository;
import com.example.meteorCleaning.service.EstimateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
public class EstimateController {
    @Value("${HOME_EMAIL:head4work@gmail.com}")
    private String homeEmail;

    @Autowired
    DataJpaOrderRepository repository;

    @Autowired
    EstimateDataService service;

    @PostMapping("/estimate")
    public ResponseEntity<?> sendEstimateViaAjax(
            @Valid @RequestBody EstimateOrder data, Errors errors) throws MessagingException {

        AjaxResponseBody result = new AjaxResponseBody();

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);
        }

        String[] to = new String[]{homeEmail, data.getEmail()};
        repository.save(data);
        service.sendEmail(to, "CLEANING ORDER", data.toString());
        result.setMsg("success");
        return ResponseEntity.ok(result);
    }

}
