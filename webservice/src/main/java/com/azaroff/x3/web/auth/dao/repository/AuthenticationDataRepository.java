package com.azaroff.x3.web.auth.dao.repository;

import com.azaroff.x3.web.auth.dao.entity.AuthenticationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("userRepository")
public interface AuthenticationDataRepository extends JpaRepository<AuthenticationData, Long> {
    AuthenticationData findByUsername(String username);
}