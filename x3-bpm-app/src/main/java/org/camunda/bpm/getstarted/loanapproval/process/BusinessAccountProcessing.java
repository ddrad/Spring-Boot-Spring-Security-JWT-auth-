package org.camunda.bpm.getstarted.loanapproval.process;

import org.camunda.bpm.engine.RuntimeService;
import com.azaroff.x3.type.consumer.ConsumerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessAccountProcessing {
    private final Logger logger = LoggerFactory.getLogger(BusinessAccountProcessing.class);
    @Autowired
    private RuntimeService runtimeService;

    public void process(ConsumerRequest consumerRequest, String correlationId) {
        logger.info("BusinessAccountProcessing process was started.");
        runtimeService.startProcessInstanceByKey("loanApproval");
        logger.info("My \"loanapproval\" process was start");
    }
}