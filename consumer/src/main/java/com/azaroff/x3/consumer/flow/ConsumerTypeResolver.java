package com.azaroff.x3.consumer.flow;

import com.azaroff.x3.consumer.model.ConsumerRequest;
import com.azaroff.x3.consumer.model.ConsumerType;

public class ConsumerTypeResolver {

    public static ConsumerType resolveType(ConsumerRequest b2bOrderRequest) {
        return b2bOrderRequest.getType();
    }

//    private static Boolean isTypeExpected(String type) {
//        return type.equalsIgnoreCase(ConsumerType.email.name()) || type.equalsIgnoreCase(ConsumerType.sms.name())
//                || type.equalsIgnoreCase(ConsumerType.other.name());
//    }

}
