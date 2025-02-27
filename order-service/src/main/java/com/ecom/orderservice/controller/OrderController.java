package com.ecom.orderservice.controller;

import com.ecom.basedomains.dto.Order;
import com.ecom.basedomains.dto.OrderEvent;
import com.ecom.orderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer){
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder (@RequestBody Order order){

       order.setOrderId(UUID.randomUUID().toString());

       OrderEvent orderEvent = new OrderEvent();
       orderEvent.setStatus("PENDING");
       orderEvent.setMessage("order status is in pending status");
       orderEvent.setOrder(order);

       orderProducer.sendMessage(orderEvent);

       return "Order place successfully....";
    }
}
