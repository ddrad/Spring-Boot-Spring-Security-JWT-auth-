package com.azaroff.x3.web.rest;

import com.azaroff.x3.business.dao.entity.Business;
import com.azaroff.x3.business.service.BusinessService;
import com.azaroff.x3.web.model.CommonResponse;
import com.azaroff.x3.web.util.CommonResponseFactory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @PostMapping()
    public CommonResponse create(@RequestBody Business business) {
        Business createdBusiness = businessService.create(business);
        CommonResponse commonResponse;
        CommonResponseFactory<CommonResponse> commonResponseFactory = CommonResponse::new;
        if(createdBusiness == null) {
            commonResponse = commonResponseFactory.create(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Business was not created.");
        } else {
            commonResponse = commonResponseFactory.create(HttpStatus.ACCEPTED.value(),
                    HttpStatus.ACCEPTED.getReasonPhrase(), null);
        }
        return commonResponse;
    }

    @GetMapping("/all")
    public List<Business> getAll() {
        return businessService.getAll();
    }
}
