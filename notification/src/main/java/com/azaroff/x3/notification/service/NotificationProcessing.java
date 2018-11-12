package com.azaroff.x3.notification.service;

import com.azaroff.x3.confirmation.service.ConfirmService;
import com.azaroff.x3.connector.email.EmailService;
import com.azaroff.x3.confirmation.dao.entity.Confirmation;
import com.azaroff.x3.type.consumer.ConsumerRequest;
import com.azaroff.x3.type.consumer.ConsumerTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@Component
public class NotificationProcessing {
    private final Logger logger = LoggerFactory.getLogger(NotificationProcessing.class);
    private final ConfirmService confirmService;
    private final EmailService emailService;

    @Autowired
    public NotificationProcessing(ConfirmService confirmService, EmailService emailService) {
        this.confirmService = confirmService;
        this.emailService = emailService;
    }

    public void process(ConsumerRequest consumerRequest, String correlationId) {
        LinkedHashMap message = (LinkedHashMap)consumerRequest.getMessage();
        ConsumerTarget target = consumerRequest.getTarget();
        StringBuilder sb = new StringBuilder("Dear ");
        if(target == ConsumerTarget.user) {
            long userId = (Integer) message.get("id");
            String email  = (String) message.get("email");
            Confirmation confirmation = buildVerification(userId, correlationId);
            confirmService.create(confirmation);
            sb.append(message.get("firstName")).append(" ")
                    .append(message.get("lastName")).append(".\n")
                    .append("Congratulations on your decision to join our app!").append("\n")
                    .append("Please confirm your created User Account (id=").append(userId)
                    .append(", enter the link http://localhost:8093/confirm/")
                    .append(correlationId);
            emailService.sendSimpleMessage(email,
                    "X3 - Confirm User Account",
                    sb.toString());

        }
        else if (target == ConsumerTarget.businessAccount) {
            long bankAccountId = (Integer) message.get("id");
            LinkedHashMap user = (LinkedHashMap) message.get("user");
            String email = (String) user.get("email");
            Confirmation confirmation = buildVerification(bankAccountId, correlationId);
            confirmService.create(confirmation);
            sb.append(user.get("firstName")).append(" ")
                    .append(user.get("lastName")).append(".\n")
                    .append("We are really happy you decided create yourself Business Account").append("\n")
                    .append("Please confirm your created Business Account, enter the link http://localhost:8092/confirm/")
                    .append(correlationId);
            emailService.sendSimpleMessage(email,
                    "X3 - Confirm Bank Account",
                    "Please confirm your created account, enter the link http://localhost:8092/confirm/"+correlationId);
        }
    }

    private Confirmation buildVerification(long userId, String correlationId) {
        Confirmation confirmation = new Confirmation();
        confirmation.setCorrelationId(correlationId);
        confirmation.setConfirm(false);
        confirmation.setUserId(userId);
        confirmation.setExpiredDate(LocalDateTime.now().plusHours(24));
        return confirmation;
    }
}