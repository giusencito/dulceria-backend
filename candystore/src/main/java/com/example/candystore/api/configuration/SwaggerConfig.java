package com.example.candystore.api.configuration;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer{

    public static final String PATH = "/myapi";

    @Bean
    public Docket swaggerApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();

    }

    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }
    private ApiKey apiKey(){
        return new ApiKey("JWT", "Authorization", "header");
    }
    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "CandyStore API",
                "Api for cineplanet developers",
                "1.0",
                "http://codmind.com/terms",
                new Contact("Developer", "https://www.cineplanet.com.pe", "cineplanet@gmail.com"),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        );
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PATH + "/**").addResourceLocations("classpath:/META-INF/resources/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        final var apiDocs = "/v2/api-docs";
        final var configUi = "/swagger-resources/configuration/ui";
        final var configSecurity = "/swagger-resources/configuration/security";
        final var resources = "/swagger-resources";
        registry.addRedirectViewController(PATH + apiDocs, apiDocs).setKeepQueryParams(true);
        registry.addRedirectViewController(PATH + resources, resources);
        registry.addRedirectViewController(PATH + configUi, configUi);
        registry.addRedirectViewController(PATH + configSecurity, configSecurity);
        registry.addRedirectViewController(PATH, "/");
    }






















}
