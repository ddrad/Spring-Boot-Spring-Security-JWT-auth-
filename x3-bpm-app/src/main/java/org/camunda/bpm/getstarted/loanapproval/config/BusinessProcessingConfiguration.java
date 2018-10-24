package org.camunda.bpm.getstarted.loanapproval.config;

import com.azaroff.x3.type.consumer.ConsumerRequest;
import org.camunda.bpm.getstarted.loanapproval.process.BusinessAccountProcessing;
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
 * Author:  Dmitry Azarov
 * Created: 10.07.2018
 */
@Configuration
public class BusinessProcessingConfiguration {
    private BusinessAccountProcessing businessAccountProcessing;

    @Autowired
    public BusinessProcessingConfiguration(BusinessAccountProcessing businessAccountProcessing) {
        this.businessAccountProcessing = businessAccountProcessing;
    }

    @Bean
    public IntegrationFlow processNotify() {
        return IntegrationFlows.from("businessProcessChannel").handle(m -> {
            Assert.isInstanceOf(ConsumerRequest.class, m.getPayload());
            businessAccountProcessing.process((ConsumerRequest) m.getPayload(), (String) m.getHeaders().get(IntegrationMessageHeaderAccessor.CORRELATION_ID));
        }).get();
    }
}
