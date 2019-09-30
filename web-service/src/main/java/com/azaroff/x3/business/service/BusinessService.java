package com.azaroff.x3.business.service;

import com.azaroff.x3.business.dao.entity.Business;

import java.util.List;

public interface BusinessService {

    Business create(Business business);

    Business updateStatus(Business business);

    List<Business> getAll();
}
