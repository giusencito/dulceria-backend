package com.example.premieres.api.controllers;


import com.example.premieres.application.dto.PremiereDTO;
import com.example.premieres.domain.service.PremiereService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/premieres")
@CrossOrigin
public class PremiereController {

    @Autowired
    private PremiereService premiereService;


    @ApiOperation(value = "Get Premieres",notes = "Este consulta consiste en obtener imagenes y texto")
    @GetMapping()
    public List<PremiereDTO> getPremieres() {
        return premiereService.getPremieres();
    }
}
