package com.example.candystore.application.mapping.configuration;
import com.example.candystore.application.mapping.entity.ProductMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("enhancedModelMapperConfiguration")
public class MappingConfiguration {

    @Bean
    public EnhancedModelMapper modelMapper() {
        return new EnhancedModelMapper();
    }

    @Bean
    public ProductMapper productMapper(){return new ProductMapper();}
}
