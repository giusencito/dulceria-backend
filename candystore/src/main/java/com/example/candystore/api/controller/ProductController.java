package com.example.candystore.api.controller;


import com.example.candystore.application.dto.DetailProductDTO;
import com.example.candystore.application.dto.ProducTODetailDTO;
import com.example.candystore.application.dto.ProductDTO;
import com.example.candystore.application.interfaces.ProducTODetailProjection;
import com.example.candystore.domain.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin
public class ProductController {


    @Autowired
    private ProductService productService;


    @ApiOperation(value = "Get Products",notes = "Este consulta consiste en obtener los productos")
    @GetMapping()
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }

    @ApiOperation(value = "Get Products BY IDS",notes = "Este consulta consiste en obtener los productos según los ids")
    @PostMapping("ids")
    public List<ProducTODetailProjection> getProductsByIds(@RequestBody DetailProductDTO request) throws JsonProcessingException {
        return productService.getProductsByIds(request);
    }

}
