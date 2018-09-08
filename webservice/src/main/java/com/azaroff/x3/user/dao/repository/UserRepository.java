package com.azaroff.x3.user.dao.repository;


import com.azaroff.x3.user.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository("UserRepository")
public interface UserRepository  extends JpaRepository<User, Long> {

    User findByEmail(String email);
}