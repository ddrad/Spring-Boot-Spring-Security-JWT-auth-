package com.azaroff.x3.user.service;

import com.azaroff.x3.exception.NotFoundException;
import com.azaroff.x3.integration.rest.RestSender;
import com.azaroff.x3.type.consumer.ConsumerTarget;
import com.azaroff.x3.user.dao.entity.User;
import com.azaroff.x3.user.dao.repository.UserRepository;
import com.azaroff.x3.type.consumer.ConsumerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestSender restSender;

    @Override
    public User createCustomer(User customer) {
         User createdUser = userRepository.save(customer);
         sendNotification(customer);
         return createdUser;
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(long userId) {
        Optional<User> users = userRepository.findById(userId);
        if (!users.isPresent()) {
            throw new NotFoundException("Cannot find customer by id: " + userId);
        }
        return users.get();
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<User> users = Optional.ofNullable(userRepository.findByEmail(email));
        return users.isPresent() ? users.get() : null;
    }

    @Override
    public List<User> getAllCustomers() {
        return userRepository.findAll();
    }

    @Override
    public void sendNotification(ConsumerRequest consumerRequest, String correlationId) {
        restSender.toConsumer(consumerRequest, correlationId);
    }

    @Override
    public ConsumerTarget getConsumerTarget() {
        return ConsumerTarget.user;
    }
}