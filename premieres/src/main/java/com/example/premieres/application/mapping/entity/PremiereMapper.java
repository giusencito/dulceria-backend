package com.example.premieres.application.mapping.entity;

import com.example.premieres.application.dto.PremiereDTO;
import com.example.premieres.application.mapping.configuration.EnhancedModelMapper;
import com.example.premieres.domain.entity.Premiere;
import org.springframework.beans.factory.annotation.Autowired;

public class PremiereMapper {
    @Autowired
    private EnhancedModelMapper mapper;

    public PremiereDTO toResource(Premiere model) {
        return mapper.map(model, PremiereDTO.class);
    }
}
