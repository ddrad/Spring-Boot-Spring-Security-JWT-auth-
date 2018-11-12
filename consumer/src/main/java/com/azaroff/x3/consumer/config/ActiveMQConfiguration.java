package com.azaroff.x3.consumer.config;

import com.azaroff.x3.consumer.flow.ConsumerTypeResolver;
import com.azaroff.x3.type.consumer.ConsumerRequest;
import com.azaroff.x3.type.consumer.ConsumerType;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.jms.dsl.Jms;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;


@Configuration
public class ActiveMQConfiguration {
    private final Logger logger = LoggerFactory.getLogger(ActiveMQConfiguration.class);

    @Value("${jms.queue.notification.email}")
    private String email_queue;
    @Value("${jms.queue.notification.sms}")
    private String sms_queue;
    @Value("${jms.queue.businessAccount}")
    private String businessAccountQueueName;

    @Bean
    public Queue emailQueue() {
        return new ActiveMQQueue(email_queue);
    }

    @Bean
    public Queue smsQueue() {
        return new ActiveMQQueue(sms_queue);
    }

    @Bean
    public Queue businessAccountQueue() {
        return new ActiveMQQueue(businessAccountQueueName);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory();
    }

    @Bean
    public MessageChannel orderChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow orders() {
        return IntegrationFlows.from(orderChannel()).log().<ConsumerRequest, ConsumerType>route(ConsumerTypeResolver::resolveType,
                mapping -> mapping
                        .subFlowMapping(ConsumerType.notifyByEmail,
                                sf -> sf.transform(Transformers.toJson()).handle(Jms.outboundAdapter(connectionFactory()).destination(emailQueue())))
                        .subFlowMapping(ConsumerType.notifyBySms,
                                sf -> sf.transform(Transformers.toJson()).handle(Jms.outboundAdapter(connectionFactory()).destination(smsQueue())))
                        .subFlowMapping(ConsumerType.other,
                                sf -> sf.transform(Transformers.toJson()).handle(Jms.outboundAdapter(connectionFactory()).destination(smsQueue())))
                        .subFlowMapping(ConsumerType.createBusinessAccount,
                                sf -> sf.transform(Transformers.toJson()).handle(Jms.outboundAdapter(connectionFactory()).destination(businessAccountQueue())))
                        .defaultSubFlowMapping(
                                sf -> sf.handle(m -> {
                                    logger.warn("Wrong message type " + ConsumerTypeResolver.resolveType((ConsumerRequest) m.getPayload()) +
                                            " for message with correlationId = " + m.getHeaders().get(IntegrationMessageHeaderAccessor.CORRELATION_ID));
                                }))
                        .resolutionRequired(false)).get();
    }
}
