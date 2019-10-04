package com.azaroff.x3.business.service;

import com.azaroff.x3.business.dao.entity.Business;
import com.azaroff.x3.business.dao.repository.BusinessRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    @Qualifier("businessRepository")
    private BusinessRepository businessRepository;

    @Override
    public Business create(Business business) {
        business.setCreateDate(LocalDateTime.now());
        return businessRepository.save(business);
    }

    @Override
    public Business updateStatus(Business business) {
        business.setLastUpdateDate(LocalDateTime.now());
        return businessRepository.save(business);
    }

    @Override
    public List<Business> getAll() {
        return businessRepository.findAll();
    }


}