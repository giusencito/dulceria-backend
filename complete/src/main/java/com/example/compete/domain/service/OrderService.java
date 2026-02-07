package com.example.compete.domain.service;

import com.example.compete.application.dto.OrderDTO;
import com.example.compete.application.dto.ResultDetailDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface OrderService {
    List<OrderDTO> ordersByEmail(String email);
    List<ResultDetailDTO> detailByOrderId(Long orderId) throws JsonProcessingException;
}
