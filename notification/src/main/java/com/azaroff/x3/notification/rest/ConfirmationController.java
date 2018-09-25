package com.azaroff.x3.notification.rest;

import com.azaroff.x3.notification.dao.entity.Confirmation;
import com.azaroff.x3.notification.flow.BusinessProcessGateway;
import com.azaroff.x3.notification.model.ConsumerRequest;
import com.azaroff.x3.notification.model.ConsumerType;
import com.azaroff.x3.notification.processing.confirmation.ConfirmationProcessing;
import com.azaroff.x3.notification.model.CommonResponse;
import com.azaroff.x3.notification.util.CommonResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ConfirmationController {

    private final BusinessProcessGateway businessProcessGateway;

    @Autowired
    private ConfirmationProcessing confirmationProcessing;

    @Autowired
    public ConfirmationController(BusinessProcessGateway businessProcessGateway) {
        this.businessProcessGateway = businessProcessGateway;
    }

    @GetMapping("/confirm/{correlationId}")
    public CommonResponse confirm(@PathVariable String correlationId) {
        if(StringUtils.isEmpty(correlationId)) {
            CommonResponseFactory<CommonResponse> commonResponseFactory = CommonResponse::new;
            return commonResponseFactory.create(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), "Correlation ID must not be null");
        }
        Confirmation confirmation = confirmationProcessing.confirm(correlationId);
        sendToBusinessAccountProcess(correlationId, confirmation);
        CommonResponseFactory<CommonResponse> commonResponseFactory = CommonResponse::new;
        return commonResponseFactory.create(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase(), null);
    }

    @GetMapping("/verify/{userId}")
    public boolean verify(@PathVariable long userId) {
        return confirmationProcessing.verify(userId);
    }

    private void sendToBusinessAccountProcess(String correlationId, Confirmation data) {
        ConsumerRequest request = new ConsumerRequest();
        request.setMessage(data);
        request.setType(ConsumerType.businessAccount);
        businessProcessGateway.sendOrder(request, correlationId);
    }
}
