package com.azaroff.x3.notification.config;

import com.azaroff.x3.notification.model.ConsumerRequest;
import com.azaroff.x3.notification.processing.confirmation.ConfirmationProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.util.Assert;

/**
 * Copyright DonRiver Inc. All Rights Reserved.
 * <p>
 * Author: Yevgeny Barkovsky
 * Created: 10.07.2018
 */
@Configuration
public class ConfirmationProcessingConfiguration {
    private ConfirmationProcessing confirmationProcessing;

    @Autowired
    public ConfirmationProcessingConfiguration(ConfirmationProcessing confirmationProcessing) {
        this.confirmationProcessing = confirmationProcessing;
    }

    @Bean
    public IntegrationFlow processNotify() {
        return IntegrationFlows.from("orderNotifyChannel").handle(m -> {
            Assert.isInstanceOf(ConsumerRequest.class, m.getPayload());
            confirmationProcessing.process((ConsumerRequest) m.getPayload(), (String) m.getHeaders().get(IntegrationMessageHeaderAccessor.CORRELATION_ID));
        }).get();
    }
}
