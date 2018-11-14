package com.azaroff.x3.service.business.repository;

import com.azaroff.x3.service.business.model.Vehicle;
import reactor.core.publisher.Flux;

public interface BusinessRepository {

    public Flux<Vehicle> flowTraffic();

}
