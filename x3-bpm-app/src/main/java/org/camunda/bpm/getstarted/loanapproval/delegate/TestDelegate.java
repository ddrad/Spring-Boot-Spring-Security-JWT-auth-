package org.camunda.bpm.getstarted.loanapproval.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestDelegate.class);

    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("TestDelegate's running: execution = [" + execution + "]");
    }
}
