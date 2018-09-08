package com.azaroff.x3.user.service;

import com.azaroff.x3.user.dao.entity.BusinessAccount;

import java.util.List;

public interface BusinessAccountService extends BaseUserService {

    List<BusinessAccount> getAll();

    BusinessAccount mergeBusinessUser(long customerId, BusinessAccount businessAccount);

}
