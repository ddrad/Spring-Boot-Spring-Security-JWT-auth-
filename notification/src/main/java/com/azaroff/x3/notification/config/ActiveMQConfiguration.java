package com.azaroff.x3.notification.config;

import com.azaroff.x3.notification.flow.ConsumerTypeResolver;
import com.azaroff.x3.notification.model.ConsumerRequest;
import com.azaroff.x3.notification.model.ConsumerType;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.jms.dsl.Jms;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import java.util.concurrent.TimeUnit;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ActiveMQConfiguration {
    private final Logger logger = LoggerFactory.getLogger(ActiveMQConfiguration.class);
    @Value("${notify.queue.name}")
    private String queueName;
    @Value("${notify.polling.interval.ms}")
    private long queuePollingIntervalMs;
    @Value("${notify.polling.initial.delay.ms}")
    private long queuePollingInitialDelayMs;
    @Value("${notify.polling.max.messages.per.pool}")
    private long maxMessagesPerPoll;
    @Value("${business.queue.name}")
    private String businessAccountQueueName;

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setTrustAllPackages(true);
        return factory;
    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue(queueName);
    }

    @Bean
    public Queue businessAccountQueue() {
        return new ActiveMQQueue(businessAccountQueueName);
    }

    @Bean
    public MessageChannel processChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow flowVerify() {
        return IntegrationFlows.from(Jms.inboundAdapter(connectionFactory()).destination(queue()), c -> c.poller(
                Pollers.fixedDelay(queuePollingIntervalMs, TimeUnit.MILLISECONDS, queuePollingInitialDelayMs)
                        .errorHandler(e -> logger.info("Can't handle incoming message", e))
                        .maxMessagesPerPoll(maxMessagesPerPoll))).wireTap(h -> h.log().channel("nullChannel"))
                .transform(Transformers.fromJson(ConsumerRequest.class))
                .channel("orderNotifyChannel").get();
    }

    @Bean
    public IntegrationFlow startBusinessProcesses() {
        return IntegrationFlows.from(processChannel()).log().<ConsumerRequest, ConsumerType>route(ConsumerTypeResolver::resolveType,
                mapping -> mapping
                        .subFlowMapping(ConsumerType.businessAccount,
                                sf -> sf.transform(Transformers.toJson()).handle(Jms.outboundAdapter(connectionFactory()).destination(businessAccountQueue())))
                        .defaultSubFlowMapping(
                                sf -> sf.handle(m -> {
                                    logger.warn("Wrong message type " + ConsumerTypeResolver.resolveType((ConsumerRequest) m.getPayload()) +
                                            " for message with correlationId = " + m.getHeaders().get(IntegrationMessageHeaderAccessor.CORRELATION_ID));
                                }))
                        .resolutionRequired(false)).get();
    }
}