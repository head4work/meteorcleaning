package com.example.meteorCleaning.web;

import com.example.meteorCleaning.dto.ForgotTo;
import com.example.meteorCleaning.dto.UserTo;
import com.example.meteorCleaning.model.EstimateOrder;
import com.example.meteorCleaning.model.ForgottenPasswordToken;
import com.example.meteorCleaning.model.User;
import com.example.meteorCleaning.service.EstimateDataService;
import com.example.meteorCleaning.service.TokenService;
import com.example.meteorCleaning.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.meteorCleaning.util.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController extends AbstractUserController {
    static final String REST_URL = "/rest/profile";
    @Autowired
    TokenService tokenService;

    @Autowired
    EstimateDataService service;

    @GetMapping
    public User get() {
        return super.get(authUserId());
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgotTo to) throws MessagingException {

        Map<String, String> result = new HashMap<>();

        //if user exist
        User user = null;
        try {
            user = super.getByMail(to.getEmail());
        } catch (NotFoundException e) {
            result.put("detail", to.getEmail() + " " + " do not exist");
            return ResponseEntity.badRequest().body(result);
        }
        result.put("email", to.getEmail());

        // create token
        ForgottenPasswordToken token = tokenService.create(user);

        //send token
        String[] emailTo = new String[]{user.getEmail()};

        service.sendEmail(emailTo, "Meteorcleaning forgotten password", token.toString());

        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        super.delete(authUserId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        User created = super.create(userTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        super.updateFromProfile(user, authUserId());
    }

    @GetMapping("/text")
    public String testUTF() {
        return "Русский текст";
    }

    @GetMapping("/with-orders")
    public User getWithOrders() {
        return super.getWithOrders(authUserId());
    }

    @GetMapping("/orders")
    public List<EstimateOrder> getOrders() {
        return super.getOrders(authUserId());
    }
}