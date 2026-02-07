package com.example.compete.application.mapping.configuration;
import com.example.compete.application.mapping.entity.OrderMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("enhancedModelMapperConfiguration")
public class MappingConfiguration {

    @Bean
    public EnhancedModelMapper modelMapper() {
        return new EnhancedModelMapper();
    }
    @Bean
    public OrderMapper orderMapper(){return new OrderMapper();}

}
