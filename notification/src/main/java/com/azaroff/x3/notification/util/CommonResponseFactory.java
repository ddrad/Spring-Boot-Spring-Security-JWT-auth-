package com.azaroff.x3.notification.util;


import com.azaroff.x3.notification.model.CommonResponse;

public interface CommonResponseFactory<P extends CommonResponse>{

    P create(int code, String status, String message);
}
