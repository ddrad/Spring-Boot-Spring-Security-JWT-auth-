package com.azaroff.x3.config;

import com.azaroff.x3.type.consumer.ConsumerRequest;
import com.azaroff.x3.notification.service.NotificationProcessing;
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
 * Author: Dmitry Azarov
 * Created: 10.07.2018
 */
@Configuration
public class ConfirmationProcessingConfiguration {
    private NotificationProcessing notificationProcessing;

    @Autowired
    public ConfirmationProcessingConfiguration(NotificationProcessing notificationProcessing) {
        this.notificationProcessing = notificationProcessing;
    }

    @Bean
    public IntegrationFlow processNotify() {
        return IntegrationFlows.from("notifyChannel").handle(m -> {
            Assert.isInstanceOf(ConsumerRequest.class, m.getPayload());
            notificationProcessing.process((ConsumerRequest) m.getPayload(), (String) m.getHeaders().get(IntegrationMessageHeaderAccessor.CORRELATION_ID));
        }).get();
    }
}
