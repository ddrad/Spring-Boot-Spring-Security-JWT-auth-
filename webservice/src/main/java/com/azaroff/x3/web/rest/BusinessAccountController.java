package com.azaroff.x3.web.rest;

import com.azaroff.x3.user.dao.entity.BusinessAccount;
import com.azaroff.x3.user.service.BusinessAccountService;
import com.azaroff.x3.web.model.CommonResponse;
import com.azaroff.x3.web.util.CommonResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business-account")
public class BusinessAccountController {

    @Autowired
    private BusinessAccountService businessAccountService;

    @PostMapping()
    public CommonResponse create(@RequestBody BusinessAccount businessAccount) {
        BusinessAccount createdUser = businessAccountService.mergeBusinessUser(businessAccount.getId(), businessAccount);
        CommonResponseFactory<CommonResponse> commonResponseFactory = CommonResponse::new;
        CommonResponse commonResponse;
        if (createdUser == null) {
            commonResponse = commonResponseFactory.create(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Business User was not created.");
        } else {
            commonResponse = commonResponseFactory.create(HttpStatus.ACCEPTED.value(),
                    HttpStatus.ACCEPTED.getReasonPhrase(), null);
        }
        return commonResponse;
    }
}