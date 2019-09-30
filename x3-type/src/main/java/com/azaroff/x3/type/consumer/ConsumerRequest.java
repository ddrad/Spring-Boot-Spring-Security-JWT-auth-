package com.azaroff.x3.type.consumer;

public class ConsumerRequest {

    private ConsumerType type;
    private ConsumerTarget target;
    private Object message;

    public ConsumerType getType() {
        return type;
    }

    public void setType(ConsumerType type) {
        this.type = type;
    }

    public ConsumerTarget getTarget() {
        return target;
    }

    public void setTarget(ConsumerTarget target) {
        this.target = target;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
