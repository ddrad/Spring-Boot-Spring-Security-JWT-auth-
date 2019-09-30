package com.azaroff.x3.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unused")
public class CommonResponse {

    private String status;
    private int code;
    private String message;

    public CommonResponse(int code, String status) {
        this.status = status;
        this.code = code;
    }

    public CommonResponse(int code, String status, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
