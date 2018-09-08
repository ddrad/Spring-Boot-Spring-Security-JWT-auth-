package com.azaroff.x3.user.service;

import com.azaroff.x3.integration.rest.model.ConsumerRequest;
import com.azaroff.x3.integration.rest.model.ConsumerTarget;
import com.azaroff.x3.integration.rest.model.ConsumerType;
import com.azaroff.x3.user.dao.entity.BusinessAccount;
import com.azaroff.x3.user.dao.entity.User;

import java.util.UUID;

public interface BaseUserService {

    default void confirm(Object message) {
        ConsumerRequest consumerRequest = new ConsumerRequest();
        consumerRequest.setType(ConsumerType.email);
        consumerRequest.setTarget(getConsumerTarget());
        consumerRequest.setMessage(message);
        String correlationId = UUID.randomUUID().toString();
        sendToConfirm(consumerRequest, correlationId);
    }

    void sendToConfirm(ConsumerRequest consumerRequest, String correlationId);

    ConsumerTarget getConsumerTarget();
}
