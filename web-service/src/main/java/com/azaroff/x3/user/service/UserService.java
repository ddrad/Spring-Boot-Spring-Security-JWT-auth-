package com.azaroff.x3.user.service;

import com.azaroff.x3.user.dao.entity.BusinessAccount;
import com.azaroff.x3.user.dao.entity.User;

import java.util.List;

public interface UserService extends BaseUserService {

    User createCustomer(User user);

    User update(User user);

    User getUserById(long userId);

    User findUserByEmail(String email);

    List<User> getAllCustomers();
}
