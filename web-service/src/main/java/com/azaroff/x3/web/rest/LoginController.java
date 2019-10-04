package com.azaroff.x3.web.rest;

import com.azaroff.x3.annotation.FormatAuthRequest;
import com.azaroff.x3.web.auth.dao.entity.AuthenticationData;
import com.azaroff.x3.web.auth.dao.repository.AuthenticationDataRepository;
import com.azaroff.x3.web.model.CommonResponse;
import com.azaroff.x3.web.util.CommonResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private AuthenticationDataRepository authenticationDataRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    @FormatAuthRequest
    public CommonResponse signUp(@RequestBody AuthenticationData user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(user.getEmail().toLowerCase());
        authenticationDataRepository.save(user);
        CommonResponseFactory<CommonResponse> commonResponseFactory = CommonResponse::new;
        CommonResponse commonResponse = commonResponseFactory.create(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase(), null);
        return commonResponse;
    }
}
