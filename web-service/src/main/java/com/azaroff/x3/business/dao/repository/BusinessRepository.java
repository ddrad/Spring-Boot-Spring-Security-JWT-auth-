package com.azaroff.x3.business.dao.repository;


import com.azaroff.x3.business.dao.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("businessRepository")
public interface BusinessRepository extends JpaRepository<Business, Long> {

    Business findByBusinessName(String businessName);

}