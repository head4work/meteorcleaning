package com.example.meteorCleaning.web;

import com.example.meteorCleaning.dto.CreatePayment;
import com.example.meteorCleaning.dto.CreatePaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class PaymentController {

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
