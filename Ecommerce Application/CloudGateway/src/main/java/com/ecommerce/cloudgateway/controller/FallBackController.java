package com.ecommerce.cloudgateway.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class FallBackController {

    @GetMapping("/orderServiceFallBack")
    public String orderServiceFallBack(){
        log.info("Order Service ..");
        return "Order Service is down!!";
    }


    @GetMapping("/paymentServiceFallBack")
    public String paymentServiceFallBack(){
        return "Payment Service is down!!";
    }

    @GetMapping("/productServiceFallBack")
    public String productServiceFallBack(){
        return "Product Service is down!!";
    }

    @GetMapping("/gateway")
    public String get(){
        return "From Gateway...";
    }
}
