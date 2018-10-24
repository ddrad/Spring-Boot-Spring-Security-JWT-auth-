package com.azaroff.x3.user.service;

import com.azaroff.x3.type.consumer.ConsumerRequest;
import com.azaroff.x3.type.consumer.ConsumerTarget;
import com.azaroff.x3.type.consumer.ConsumerType;

import java.util.UUID;

public interface BaseUserService {

    default void confirm(Object message) {
        ConsumerRequest consumerRequest = new ConsumerRequest();
        consumerRequest.setType(ConsumerType.notifyByEmail);
        consumerRequest.setTarget(getConsumerTarget());
        consumerRequest.setMessage(message);
        String correlationId = UUID.randomUUID().toString();
        sendToConfirm(consumerRequest, correlationId);
    }

    void sendToConfirm(ConsumerRequest consumerRequest, String correlationId);

    ConsumerTarget getConsumerTarget();
}
