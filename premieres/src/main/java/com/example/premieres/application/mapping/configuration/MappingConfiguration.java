package com.example.premieres.application.mapping.configuration;
import com.example.premieres.application.mapping.entity.PremiereMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("enhancedModelMapperConfiguration")
public class MappingConfiguration {

    @Bean
    public EnhancedModelMapper modelMapper() {
        return new EnhancedModelMapper();
    }


    @Bean
    public PremiereMapper premiereMapper(){return new PremiereMapper();}
}
