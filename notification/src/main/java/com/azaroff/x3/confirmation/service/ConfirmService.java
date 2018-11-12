package com.azaroff.x3.confirmation.service;

import com.azaroff.x3.confirmation.dao.entity.Confirmation;

public interface ConfirmService {

    void create(Confirmation confirmation);

    Confirmation findByCorrelationId(String correlationId);

    Confirmation findByUserId(long userId);

    Confirmation confirm(String correlationId);

    boolean verify(long userId);

    void sendToBusinessAccountProcess(String correlationId, Confirmation data);
}
