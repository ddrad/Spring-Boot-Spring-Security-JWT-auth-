package com.azaroff.x3.connector.rest;

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
    @Value("${gateway.user.url}")
    private String userServiceUrl;

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

//    public String findUserType(long id) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<String> entity = restTemplate.getForEntity(userServiceUrl + "/" + id, String.class);
//        return entity.getBody();
//    }
}
