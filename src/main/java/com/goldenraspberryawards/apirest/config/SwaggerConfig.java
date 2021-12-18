package com.goldenraspberryawards.apirest.config;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.goldenraspberryawards.apirest"))
                .paths(regex("/api.*"))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Golden Raspberry Awards API REST",
                "API REST de nomeações ao Prêmio Framboesa de Ouro.",
                "1.0",
                "Terms of Service",
                new Contact("Alex Sandro Petriu Malinoski", "https://www.linkedin.com/in/alex-sandro-petriu-malinoski-4750b5228/",
                        "alexpetriu2009@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licences.html", new ArrayList<VendorExtension>()
        );

        return apiInfo;
    }

}