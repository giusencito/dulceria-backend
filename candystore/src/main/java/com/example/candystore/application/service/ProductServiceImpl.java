package com.example.candystore.application.service;

import com.example.candystore.application.dto.DetailProductDTO;
import com.example.candystore.application.dto.ProducTODetailDTO;
import com.example.candystore.application.dto.ProductDTO;
import com.example.candystore.application.interfaces.ProducTODetailProjection;
import com.example.candystore.application.mapping.entity.ProductMapper;
import com.example.candystore.domain.entity.Product;
import com.example.candystore.domain.repository.ProductRepository;
import com.example.candystore.domain.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    private final Random random = new Random();


    private static final List<String> CANDY_PRODUCTS = List.of(
            "Chocolate Sublime",
            "Chocolate Princesa",
            "Chocolate Vizzio",
            "Chocolate Ferrero",
            "Caramelo de Menta",
            "Caramelo de Fresa",
            "Chupetín",
            "Gomitas de Osito",
            "Gomitas Ácidas",
            "Malvaviscos",
            "Toffee",
            "Turrón",
            "Chicle"
    );


    @Override
    public List<ProductDTO> getProducts() {
        var premieres = productRepository.getProducts();
        return premieres
                .stream()
                .map(mapper::toResource)
                .collect(Collectors.toList());
    }

    @Override
    public void generateProducts() {
        List<Product>products = new ArrayList<>();
        for (String item:CANDY_PRODUCTS) {
          products.add( create(item,"Ejemplo de Descripción",random.nextFloat() * 5));
        }
        products.stream()
                .filter(p -> !productRepository.existsByName(p.getName()))
                .forEach(productRepository::save);

    }

    @Override
    public List<ProducTODetailProjection> getProductsByIds(DetailProductDTO request) throws JsonProcessingException {
        String idsJson = new ObjectMapper().writeValueAsString(request.getIds());
        List<ProducTODetailProjection> products = productRepository.getProductsByIds(idsJson);
        return products;
    }

    private Product create(String name, String description,Float price) {
        Product p = new Product();
        p.setName(name);
        p.setDescription(description);
        p.setPrice(price);
        p.setCreatedAt(new Date());
        p.setUpdatedAt(new Date());
        return p;
    }
}
