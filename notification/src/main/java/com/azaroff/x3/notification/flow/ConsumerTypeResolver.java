package com.azaroff.x3.notification.flow;

import com.azaroff.x3.notification.model.ConsumerRequest;
import com.azaroff.x3.notification.model.ConsumerType;

public class ConsumerTypeResolver {

    public static ConsumerType resolveType(ConsumerRequest b2bOrderRequest) {
        return b2bOrderRequest.getType();
    }

}
