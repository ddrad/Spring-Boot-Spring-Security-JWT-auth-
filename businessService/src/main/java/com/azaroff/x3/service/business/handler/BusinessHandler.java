package com.azaroff.x3.service.business.handler;

import com.azaroff.x3.service.business.model.Vehicle;
import com.azaroff.x3.service.business.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class BusinessHandler {

    @Autowired
    private BusinessRepository businessRepository;

    public Mono<ServerResponse> vehicleDetected(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(businessRepository.flowTraffic(),Vehicle.class);

    }
}
