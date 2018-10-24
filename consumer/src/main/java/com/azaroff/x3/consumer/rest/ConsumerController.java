package com.azaroff.x3.consumer.rest;

import com.azaroff.x3.consumer.flow.OrderGateway;
import com.azaroff.x3.type.consumer.ConsumerRequest;
import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    private final OrderGateway orderGateway;

    @Autowired
    public ConsumerController(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }


    @PostMapping
    public String consume(@RequestBody ConsumerRequest request, @RequestHeader("correlationId") String correlationId) {
        orderGateway.sendOrder(request, correlationId);
        return "200";
    }
}
