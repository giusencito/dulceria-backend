package com.example.compete.application.mapping.entity;

import com.example.compete.application.dto.OrderDTO;
import com.example.compete.application.mapping.configuration.EnhancedModelMapper;
import com.example.compete.domain.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderMapper {
    @Autowired
    private EnhancedModelMapper mapper;

    public OrderDTO toResource(Order model) {
        return mapper.map(model, OrderDTO.class);
    }
}
