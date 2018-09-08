package com.azaroff.x3.business.service;

import com.azaroff.x3.business.dao.entity.Business;
import com.azaroff.x3.business.dao.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    @Qualifier("businessRepository")
    private BusinessRepository businessRepository;

    @Override
    public Business create(Business business) {
        return businessRepository.save(business);
    }

    @Override
    public Business updateStatus(Business business) {
        return businessRepository.save(business);
    }


}