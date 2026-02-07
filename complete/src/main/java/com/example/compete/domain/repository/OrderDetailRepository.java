package com.example.compete.domain.repository;


import com.example.compete.domain.entity.OrderDetail;
import com.example.compete.domain.valueObject.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findByIdOrderId(Long orderId);
}
