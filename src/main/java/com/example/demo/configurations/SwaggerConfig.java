//package com.example.demo.configurations;
//
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .components(new Components()
//                        .addSecuritySchemes("apiKey", new SecurityScheme()
//                                .type(SecurityScheme.Type.APIKEY)
//                                .in(SecurityScheme.In.HEADER)
//                                .name("Authorization")
//                                .description("JWT Authorization header using the Bearer scheme")
//                        )
//                )
//                .addSecurityItem(new SecurityRequirement().addList("apiKey"))
//                .info(new Info().title("My API").version("1.0"));
//    }
//
//
//
//}
