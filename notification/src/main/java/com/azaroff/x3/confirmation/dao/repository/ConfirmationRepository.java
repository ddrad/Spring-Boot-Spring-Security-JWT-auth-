package com.azaroff.x3.confirmation.dao.repository;


import com.azaroff.x3.confirmation.dao.entity.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository("confirmationRepository")
public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {

    Confirmation findByUserId(long userId);
    Confirmation findByCorrelationId(String correlationId);
}