package com.example.candystore.domain.service;

import com.example.candystore.application.dto.DetailProductDTO;
import com.example.candystore.application.dto.ProducTODetailDTO;
import com.example.candystore.application.dto.ProductDTO;
import com.example.candystore.application.interfaces.ProducTODetailProjection;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getProducts();
    void generateProducts();
    List<ProducTODetailProjection> getProductsByIds(DetailProductDTO request) throws JsonProcessingException;
}
