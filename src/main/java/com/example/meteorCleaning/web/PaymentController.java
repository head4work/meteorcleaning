package com.example.meteorCleaning.web;

import com.example.meteorCleaning.dto.CreatePayment;
import com.example.meteorCleaning.dto.CreatePaymentResponse;
import com.example.meteorCleaning.model.EstimateOrder;
import com.example.meteorCleaning.service.EstimateDataService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class PaymentController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    EstimateDataService orderService;

    @Value("${stripe.webhook.secret}")
    private String secret;

    @Value("${stripe.api.key}")
    private String apiKey;

    static Long calculateOrderAmount(int amount) {
        // Replace this constant with a calculation of the order's amount
        // Calculate the order total on the server to prevent
        // people from directly manipulating the amount on the client
        return amount * 100L;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(@RequestHeader(value = "Stripe-Signature") String sigHeader,
                                          @RequestBody String payload) throws StripeException {
        Event event;
        if (secret == null) {
            return ResponseEntity.badRequest().body("Secret not provided");
        }
        // Only verify the event if you have an endpoint secret defined.
        // Otherwise use the basic event deserialized with GSON.
        try {
            event = Webhook.constructEvent(
                    payload, sigHeader, secret
            );
        } catch (SignatureVerificationException e) {
            // Invalid signature
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        if (event.getType().equals("charge.succeeded")) {
            String paymentIntentId = getPaymentIntentId(payload);
            if (paymentIntentId != null) {
                EstimateOrder order = orderService.getByPaymentIntentId(paymentIntentId);
                order.setPaid(true);
                orderService.update(order);
            }
        }
        log.info(event.getType());
        return ResponseEntity.ok("");
    }

    private String getPaymentIntentId(String payload) {
        String payment_intent = Arrays.stream(payload.split("\n"))
                .filter(s -> s.contains("payment_intent"))
                .collect(Collectors.joining());
        String s = payment_intent.replaceAll("[',\" ]", "");
        String[] split = s.split(":");
        return split[1];
    }

    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse initPayment(@RequestBody CreatePayment payment) throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(calculateOrderAmount(payment.getAmount()))
                        .setCurrency("usd")
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        // Create a PaymentIntent with the order amount and currency
        PaymentIntent paymentIntent = PaymentIntent.create(params);

        return new CreatePaymentResponse(paymentIntent.getClientSecret());
    }
}
