package com.example.candystore.application.mapping.entity;

import com.example.candystore.application.dto.ProductDTO;
import com.example.candystore.application.mapping.configuration.EnhancedModelMapper;
import com.example.candystore.domain.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductMapper {
    @Autowired
    private EnhancedModelMapper mapper;

    public ProductDTO toResource(Product model) {
        return mapper.map(model, ProductDTO.class);
    }
}
