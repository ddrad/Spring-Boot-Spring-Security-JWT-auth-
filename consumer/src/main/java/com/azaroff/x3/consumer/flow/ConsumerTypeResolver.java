package com.azaroff.x3.consumer.flow;

import com.azaroff.x3.type.consumer.ConsumerRequest;
import com.azaroff.x3.type.consumer.ConsumerType;

public class ConsumerTypeResolver {

    public static ConsumerType resolveType(ConsumerRequest b2bOrderRequest) {
        return b2bOrderRequest.getType();
    }

//    private static Boolean isTypeExpected(String type) {
//        return type.equalsIgnoreCase(ConsumerType.notifyByEmail.name()) || type.equalsIgnoreCase(ConsumerType.notifyBySms.name())
//                || type.equalsIgnoreCase(ConsumerType.other.name());
//    }

}
