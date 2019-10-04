package org.camunda.bpm.getstarted.loanapproval.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import com.azaroff.x3.type.consumer.ConsumerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.jms.dsl.Jms;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import java.util.concurrent.TimeUnit;


@Configuration
public class ActiveMQConfiguration {
    private final Logger logger = LoggerFactory.getLogger(ActiveMQConfiguration.class);
    @Value("${jms.queue.notify.business.accaunt}")
    private String queueName;
    @Value("${jms.notify.polling.interval.ms}")
    private long queuePollingIntervalMs;
    @Value("${jms.notify.polling.initial.delay.ms}")
    private long queuePollingInitialDelayMs;
    @Value("${jms.notify.polling.max.messages.per.pool}")
    private long maxMessagesPerPoll;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory();
    }

    @Bean
    public Queue queueBusinessProcess() {
        return new ActiveMQQueue(queueName);
    }

    @Bean
    public IntegrationFlow startBusinessProcess() {
        return IntegrationFlows.from(Jms.inboundAdapter(connectionFactory()).destination(queueBusinessProcess()), c -> c.poller(
                Pollers.fixedDelay(queuePollingIntervalMs, TimeUnit.MILLISECONDS, queuePollingInitialDelayMs)
                        .errorHandler(e -> logger.info("Can't handle incoming message", e))
                        .maxMessagesPerPoll(maxMessagesPerPoll))).wireTap(h -> h.log().channel("nullChannel"))
                .transform(Transformers.fromJson(ConsumerRequest.class))
                .channel("businessProcessChannel").get();
    }
}