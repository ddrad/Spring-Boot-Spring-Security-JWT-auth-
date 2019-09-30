package com.azaroff.x3.connector.email.dao.repository;

import com.azaroff.x3.connector.email.dao.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository("emailRepository")
public interface EmailRepository extends JpaRepository<Email, Long> {

}