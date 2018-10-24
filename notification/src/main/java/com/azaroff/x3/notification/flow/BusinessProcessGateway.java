package com.azaroff.x3.notification.flow;

import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

//
//@MessagingGateway
//public interface BusinessProcessGateway {
//
//    @Gateway(requestChannel = "processChannel")
//    void sendOrder(ConsumerRequest data, @Header(IntegrationMessageHeaderAccessor.CORRELATION_ID) String correlationId);
//}
