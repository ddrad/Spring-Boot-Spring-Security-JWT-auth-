package com.azaroff.x3.integration.rest;

import com.azaroff.x3.type.consumer.ConsumerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class RestSender {
    @Value("${gateway.consumer.url}")
    private String consumerUrl;
    @Value("${conrirmation.verify.url}")
    private String confirmationVerifyUrl;
    private static final Logger logger = LoggerFactory.getLogger(RestSender.class);

    public void toConsumer(ConsumerRequest request, String correlationId) {
        logger.info("Prepare to call Consumer App, correlationID=" + correlationId);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("correlationId", correlationId);

        HttpEntity<ConsumerRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(consumerUrl, HttpMethod.POST, entity, String.class);
        logger.info("Reply was received " + responseEntity);
        logger.info("Replay was sent to Consumer");
    }

    public boolean checkConfirmationOfUser(long userId) {
        logger.info("Prepare to call Notification App, userId = " + userId);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Long> entity = new HttpEntity<>(userId);
        ResponseEntity<String> responseEntity = restTemplate.exchange(confirmationVerifyUrl + userId, HttpMethod.GET, entity, String.class);
        boolean isConfirm = Boolean.parseBoolean(responseEntity.getBody());
        logger.info("Reply was received");
        logger.info("User Account was confirmed : " + isConfirm);
        logger.info("Replay was sent to Notification");
        return isConfirm;
    }
}
