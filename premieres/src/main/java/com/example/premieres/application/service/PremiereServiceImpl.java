package com.example.premieres.application.service;

import com.example.premieres.application.dto.PremiereDTO;
import com.example.premieres.application.mapping.entity.PremiereMapper;
import com.example.premieres.domain.entity.Premiere;
import com.example.premieres.domain.repository.PremiereRepository;
import com.example.premieres.domain.service.PremiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PremiereServiceImpl implements PremiereService {

    @Autowired
    private PremiereRepository premiereRepository;


    @Autowired
    private PremiereMapper mapper;

    @Override
    public List<PremiereDTO> getPremieres() {
        var premieres = premiereRepository.getPremieres();
        return premieres
                .stream()
                .map(mapper::toResource)
                .collect(Collectors.toList());
    }

    @Override
    public void generatePremieres() {
        List<Premiere> premieres = List.of(
                create("https://images.unsplash.com/photo-1651248340393-9ebeaee08e29?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1yZWxhdGVkfDJ8fHxlbnwwfHx8fHw%3D\n",
                        "Descripción 1"),
                create("https://plus.unsplash.com/premium_photo-1661255468024-de3a871dfc16?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1yZWxhdGVkfDR8fHxlbnwwfHx8fHw%3D",
                        "Descripción 2"),
                create("https://plus.unsplash.com/premium_photo-1684952848980-ae30a28543d6?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1yZWxhdGVkfDEyfHx8ZW58MHx8fHx8",
                        "Descripción 3")
        );
        premieres.stream()
                .filter(p -> !premiereRepository.existsByImageUrl(p.getImageUrl()))
                .forEach(premiereRepository::save);
    }
    private Premiere create(String image,  String description) {
        Premiere p = new Premiere();
        p.setImageUrl(image);
        p.setDescription(description);
        p.setCreatedAt(new Date());
        p.setUpdatedAt(new Date());
        return p;
    }
}
