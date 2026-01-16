package com.ruby.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SwaggerConfig {
    //http://localhost:8080/swagger-ui/index.html
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
		        .addSecurityItem(new SecurityRequirement().addList("Authentication"))
		        .components(new Components()
                        .addSecuritySchemes("Authentication",
                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                        .type(Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(info());
    }

    private Info info() {
        return new Info()
                .title("Mini Project: 전국공공체육시설 현황 API")
                .description("부산대 KDT 2-3기 Mini Project: 전국공공체육시설 현황 API 명세서입니다.")
                .version("1.0");
    }
}