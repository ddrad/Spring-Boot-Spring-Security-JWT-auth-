package com.azaroff.x3.notification.flow;

import com.azaroff.x3.type.consumer.ConsumerRequest;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;


@MessagingGateway
public interface NotifyProcessingGateway {
    @Gateway(requestChannel = "orderNotifyChannel")
    void processOrderNotify(ConsumerRequest request, @Header(IntegrationMessageHeaderAccessor.CORRELATION_ID) String correlationId);
}
