package com.userCrud.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
    	
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.userCrud.controller"))
                .paths(regex("/user.*"))
                .build()
                .apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
    	return new ApiInfoBuilder()
                .title("User API Documentation")
                .description("Spring Boot REST Explorer - Swagger")
                .contact(new Contact("Marco Barcellos", "https://github.com/marcogbarcellos", "marcogbarcellos@gmail.com"))
                .version("0.1")
                .build();
    }
}
