package com.azaroff.x3.service.business.route;

import com.azaroff.x3.service.business.handler.BusinessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class BusinesRouter {

    @Bean
    public RouterFunction<ServerResponse> route(BusinessHandler businessHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/vehicles")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)),
                        businessHandler::vehicleDetected);
    }
}
