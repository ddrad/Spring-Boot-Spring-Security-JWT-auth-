package com.azaroff.x3.confirmation.service;

import com.azaroff.x3.confirmation.dao.entity.Confirmation;
import com.azaroff.x3.confirmation.dao.repository.ConfirmationRepository;
import com.azaroff.x3.connector.rest.RestSender;
import com.azaroff.x3.type.consumer.ConsumerRequest;
import com.azaroff.x3.type.consumer.ConsumerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ConfirmServiceImpl implements ConfirmService {

    private final ConfirmationRepository confirmationRepository;
    private final RestSender restSender;

    @Autowired
    public ConfirmServiceImpl(@Qualifier("confirmationRepository") ConfirmationRepository confirmationRepository,
                              RestSender restSender) {
        this.confirmationRepository = confirmationRepository;
        this.restSender = restSender;
    }

    public void create(Confirmation confirmation) {
        confirmationRepository.save(confirmation);
    }

    public Confirmation findByCorrelationId(String correlationId) {
        return confirmationRepository.findByCorrelationId(correlationId);
    }

    public Confirmation findByUserId(long userId) {
        return confirmationRepository.findByUserId(userId);
    }

    public Confirmation confirm(String correlationId) {
        Confirmation confirmation = findByCorrelationId(correlationId);
        confirmation.setConfirm(true);
        return confirmationRepository.save(confirmation);
    }

    public boolean verify(long userId) {
        Confirmation confirmation = confirmationRepository.findByUserId(userId);
        return confirmation != null && confirmation.isConfirm();
    }

    public void sendToBusinessAccountProcess(String correlationId, Confirmation data) {
        ConsumerRequest request = new ConsumerRequest();
        request.setMessage(data);
        request.setType(ConsumerType.createBusinessAccount);
        restSender.toConsumer(request, correlationId);
    }
}
