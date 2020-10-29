package com.ftn.sep.paypal.controller;

import com.ftn.sep.paypal.dto.BuyMagazineDTO;
import com.ftn.sep.paypal.service.PaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private static final String SUCCESS_URL = "/pay/complete";
    private static final String CANCEL_URL = "/pay/cancel";

    @PostMapping(value = "/pay")
    public String pay(@RequestBody BuyMagazineDTO request) {

        String cancelUrl = "";
        String successUrl = "";
        successUrl = "http://localhost:8090/api/payment" + SUCCESS_URL+"/"+ request.getBuyingId();
        cancelUrl = "http://localhost:8090/api/payment" + CANCEL_URL+"/" + request.getBuyingId();

        try {
            Payment payment = paymentService.createPayment(
                    request.getPrice(),
                    "EUR",
                    cancelUrl,
                    successUrl);



            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            System.out.print(e.getMessage());
        }


        return "";
    }

    @GetMapping(value = "/pay/complete/{id}")
    public RedirectView completePay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @PathVariable Long id){
        try {
            Payment payment = paymentService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> resp = restTemplate.getForEntity("http://localhost:8080/api/magazines/complete/"+id, String.class);
                return new RedirectView("http://localhost:4200");
            }
        } catch (PayPalRESTException e) {
            System.out.print(e);
        }
        return new RedirectView("http://localhost:4200");

    }

    @GetMapping(value = "/pay/cancel/{id}")
    public RedirectView cancelPay(@PathVariable String id) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.getForEntity("http://localhost:8080/api/magazines/cancel/"+id, String.class);
        return new RedirectView("http://localhost:4200");

    }


}
