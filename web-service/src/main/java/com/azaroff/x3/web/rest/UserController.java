package com.azaroff.x3.web.rest;

import com.azaroff.x3.user.dao.entity.User;
import com.azaroff.x3.user.service.UserService;
import com.azaroff.x3.web.model.CommonResponse;
import com.azaroff.x3.web.util.CommonResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public CommonResponse create(@RequestBody User customer) {
        User createdUser = userService.createCustomer(customer);
        CommonResponseFactory<CommonResponse> commonResponseFactory = CommonResponse::new;
        CommonResponse commonResponse;
        if (createdUser == null) {
            commonResponse = commonResponseFactory.create(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "User was not created.");
        } else {
            commonResponse = commonResponseFactory.create(HttpStatus.ACCEPTED.value(),
                    HttpStatus.ACCEPTED.getReasonPhrase(), null);
        }
        return commonResponse;
    }

    @GetMapping("/all")
    public List<User> getAllCustomers() {
        return userService.getAllCustomers();
    }

    @GetMapping()
    public User getCustomer(Authentication authentication) {
        String email = (String)authentication.getPrincipal();
        return userService.findUserByEmail(email);
    }
}