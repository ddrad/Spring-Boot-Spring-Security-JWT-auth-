package org.camunda.bpm.getstarted.loanapproval.flow;

import org.camunda.bpm.getstarted.loanapproval.model.ConsumerRequest;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway
public interface BusinessProcessingGateway {
    @Gateway(requestChannel = "businessProcessChannel")
    void processOrderNotify(ConsumerRequest request, @Header(IntegrationMessageHeaderAccessor.CORRELATION_ID) String correlationId);
}