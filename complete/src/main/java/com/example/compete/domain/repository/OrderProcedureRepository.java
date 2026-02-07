package com.example.compete.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Repository
public class OrderProcedureRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createOrder(
            String email,
            String name,
            String documentType,
            String documentNumber,
            String transactionId,
            LocalDateTime operationDate,
            String itemsJson
    ) {
        entityManager
                .createNativeQuery("CALL sp_create_order(?,?,?,?,?,?,?)")
                .setParameter(1, email)
                .setParameter(2, name)
                .setParameter(3, documentType)
                .setParameter(4, documentNumber)
                .setParameter(5, transactionId)
                .setParameter(6, operationDate)
                .setParameter(7, itemsJson)
                .executeUpdate();
    }
}
