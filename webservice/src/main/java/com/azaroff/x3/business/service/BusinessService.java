package com.azaroff.x3.business.service;

import com.azaroff.x3.business.dao.entity.Business;

public interface BusinessService {

    Business create(Business business);

    Business updateStatus(Business business);
}
