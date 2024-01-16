package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.external.client.PaymentService;
import com.ecommerce.orderservice.external.client.ProductService;
import com.ecommerce.orderservice.external.exception.CustomException;
import com.ecommerce.orderservice.external.request.PaymentRequest;
import com.ecommerce.orderservice.external.response.PaymentResponse;
import com.ecommerce.orderservice.model.OrderRequest;
import com.ecommerce.orderservice.model.OrderResponse;
import com.ecommerce.orderservice.model.ProductResponse;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Log4j2
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public void deleteOrderById(long orderId) {
        if(orderRepository.existsById(orderId)){
            orderRepository.deleteById(orderId);
        }else{
            throw new CustomException("Order not found with OrderId :"+orderId,"ORDER_NOT_FOUND", 200);
        }
    }

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Placing Order Request : {}", orderRequest);

//        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
        order = orderRepository.save(order);

        log.info("Calling payment service to complete the payment");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .amount(orderRequest.getTotalAmount())
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .build();

        String orderStatus = null;

        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully. Changing order status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("Error occurred in payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order Placed successfully with order ID: {} ", order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get order details for order Id: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new CustomException("Order not found with order Id:" + orderId, "NOT_FOUND", 404));


        log.info("Invoking Product service to fetch the product id: {}", order.getProductId());


        ProductResponse productResponse = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/product/" + order.getProductId(), ProductResponse.class);

        PaymentResponse paymentResponse = restTemplate.getForObject(
                "http://PAYMENT-SERVICE/payment/order/"+order.getId(), PaymentResponse.class
        );


        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentStatus(paymentResponse.getStatus())
                .paymentMode(paymentResponse.getPaymentMode())
                .amount(paymentResponse.getAmount())
                .build();



        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .price(productResponse.getPrice())
                .quantity(productResponse.getQuantity())
                .build();

        OrderResponse orderResponse = OrderResponse.builder()
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .orderId(order.getId())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();

        return orderResponse;
    }


}
