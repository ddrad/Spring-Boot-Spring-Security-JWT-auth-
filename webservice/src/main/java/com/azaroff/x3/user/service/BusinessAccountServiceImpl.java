package com.azaroff.x3.user.service;

import com.azaroff.x3.integration.rest.RestSender;
import com.azaroff.x3.type.consumer.ConsumerRequest;
import com.azaroff.x3.type.consumer.ConsumerTarget;
import com.azaroff.x3.user.dao.entity.BusinessAccount;
import com.azaroff.x3.user.dao.entity.User;
import com.azaroff.x3.user.dao.repository.BusinessAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessAccountServiceImpl implements BusinessAccountService {
    @Autowired
    private BusinessAccountRepository businessAccountRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RestSender restSender;

    @Override
    public List<BusinessAccount> getAll() {
        return businessAccountRepository.findAll();
    }

    @Override
    public BusinessAccount mergeBusinessUser(long customerId, BusinessAccount businessAccount) {
        User customer = userService.getUserById(customerId);
        isConfirm(customerId);
        businessAccount.setUser(customer);
        confirm(businessAccount);
        return businessAccountRepository.save(businessAccount);
    }

    @Override
    public void sendToConfirm(ConsumerRequest consumerRequest, String correlationId) {
        restSender.toConsumer(consumerRequest, correlationId);
    }

    @Override
    public ConsumerTarget getConsumerTarget() {
        return ConsumerTarget.businessAccount;
    }

    private boolean isConfirm(long userId) {
        return restSender.checkConfirmationOfUser(userId);
    }

}