package com.azaroff.x3.web.auth.service;

import com.azaroff.x3.web.auth.dao.entity.AuthenticationData;
import com.azaroff.x3.web.auth.dao.repository.AuthenticationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AuthenticationDataRepository authenticationDataRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthenticationData applicationUser = authenticationDataRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }
}