package com.example.meteorCleaning.web;

import com.example.meteorCleaning.dto.UserTo;
import com.example.meteorCleaning.model.EstimateOrder;
import com.example.meteorCleaning.model.ForgottenPasswordToken;
import com.example.meteorCleaning.model.User;
import com.example.meteorCleaning.service.EstimateDataService;
import com.example.meteorCleaning.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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

    @PostMapping("/forget")
    public ResponseEntity<String> forgetPassword(@RequestParam String email) throws MessagingException {
        //if user exist
        User user = super.getByMail(email);

        // create token
        ForgottenPasswordToken token = tokenService.create(user);

        //send token
        String[] to = new String[]{user.getEmail()};

        service.sendEmail(to, "Meteorcleaning forgotten password", token.toString());

        return new ResponseEntity<>("We send retrieving password link to email:" + user.getEmail(), HttpStatus.OK);
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