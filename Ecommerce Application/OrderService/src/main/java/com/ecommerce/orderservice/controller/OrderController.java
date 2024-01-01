package com.ecommerce.orderservice.controller;


import com.ecommerce.orderservice.model.OrderRequest;
import com.ecommerce.orderservice.model.OrderResponse;
import com.ecommerce.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenDecoderFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public Map<String, Object> profileJwt(@AuthenticationPrincipal Jwt jwt) {

        return jwt.getClaims();
    }



    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @GetMapping("/get")
    public String get(){
        return "STRING ::: ";
    }


    @PreAuthorize("hasAuthority('Customer')")
    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
        long orderId = orderService.placeOrder(orderRequest);
        log.info("Order Id : {}", orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Admin') || hasAuthority('Customer')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId) {

        OrderResponse orderResponse = orderService.getOrderDetails(orderId);

        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable("id") long orderId) {
        orderService.deleteOrderById(orderId);
        return new ResponseEntity<>("Order Deleted Successfully with the Id :" + orderId, HttpStatus.OK);
    }

}
