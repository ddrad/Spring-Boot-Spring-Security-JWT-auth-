package com.azaroff.x3.web.util;

import com.azaroff.x3.web.model.CommonResponse;

public interface CommonResponseFactory <P extends CommonResponse>{

    P create(int code, String status, String message);
}
