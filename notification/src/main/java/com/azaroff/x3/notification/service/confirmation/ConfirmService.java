package com.azaroff.x3.notification.service.confirmation;

import com.azaroff.x3.notification.dao.entity.Confirmation;
import com.azaroff.x3.notification.service.connector.rest.RestSender;
import com.azaroff.x3.type.consumer.ConsumerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmService {

    @Autowired
    private ConfirmationProcessing confirmationProcessing;

    @Autowired
    private RestSender restSender;

    public Confirmation confirm(String correlationId) {
        return confirmationProcessing.confirm(correlationId);
    }

    public boolean verify(long userId) {
        return confirmationProcessing.verify(userId);
    }

    public void sendToConsumer(ConsumerRequest request, String correlationId) {
        restSender.toConsumer(request, correlationId);
    }
}
