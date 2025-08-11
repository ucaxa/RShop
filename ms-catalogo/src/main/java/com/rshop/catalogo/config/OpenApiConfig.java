package com.rshop.catalogo.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RSHop Catálogo API - Microserviço de Produtos")
                        .description("API para gerenciamento do catálogo de produtos")
                        .version("v1.0"));
    }
}

