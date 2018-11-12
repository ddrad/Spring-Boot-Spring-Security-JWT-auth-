package com.azaroff.x3.user.service;

import com.azaroff.x3.type.consumer.ConsumerRequest;
import com.azaroff.x3.type.consumer.ConsumerTarget;
import com.azaroff.x3.type.consumer.ConsumerType;

import java.util.UUID;

public interface BaseUserService {

    default void sendNotification(Object message) {
        ConsumerRequest consumerRequest = new ConsumerRequest();
        consumerRequest.setType(ConsumerType.notifyByEmail);
        consumerRequest.setTarget(getConsumerTarget());
        consumerRequest.setMessage(message);
        String correlationId = UUID.randomUUID().toString();
        sendNotification(consumerRequest, correlationId);
    }

    void sendNotification(ConsumerRequest consumerRequest, String correlationId);

    ConsumerTarget getConsumerTarget();
}
