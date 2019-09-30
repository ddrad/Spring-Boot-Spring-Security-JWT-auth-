package com.azaroff.x3.user.dao.repository;

import com.azaroff.x3.user.dao.entity.BusinessAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Transactional
@Repository("BusinessAccountRepository")
public interface BusinessAccountRepository extends JpaRepository<BusinessAccount, Long> {
}