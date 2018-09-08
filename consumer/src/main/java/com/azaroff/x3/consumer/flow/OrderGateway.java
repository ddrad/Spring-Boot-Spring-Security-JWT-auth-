package com.azaroff.x3.consumer.flow;

import com.azaroff.x3.consumer.model.ConsumerRequest;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;


@MessagingGateway
public interface OrderGateway {

    @Gateway(requestChannel = "orderChannel")
    void sendOrder(ConsumerRequest data, @Header(IntegrationMessageHeaderAccessor.CORRELATION_ID) String correlationId);
}
