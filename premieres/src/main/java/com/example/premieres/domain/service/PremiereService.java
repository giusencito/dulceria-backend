package com.example.premieres.domain.service;

import com.example.premieres.application.dto.PremiereDTO;

import java.util.List;

public interface PremiereService {
    List<PremiereDTO> getPremieres();
    void generatePremieres();
}
