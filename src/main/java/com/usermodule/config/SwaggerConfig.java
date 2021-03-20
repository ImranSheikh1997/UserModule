package com.usermodule.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Docket api(){

        return new Docket(DocumentationType.SWAGGER_2)//
        .select()//
        .apis(RequestHandlerSelectors.any())//
        .paths(Predicates.not(PathSelectors.regex("/error")))//
        .build()//
        .apiInfo(metadata())//
        .useDefaultResponseMessages(false)//
        .securitySchemes(Collections.singletonList(apiKey()))
        .securityContexts(Collections.singletonList(securityContext()))//
        .tags(new Tag("User","Crud Operations on User"))//
        .genericModelSubstitutes(Optional.class);

    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization","Authorization","Header");
    }

    private springfox.documentation.spi.service.contexts.SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("Global","Access Everything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization",authorizationScopes));
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()//
        .title("User Module Using MYSQL & JSON Web Token Authentication API")//
        .description("This is simple UserModule Service Having Registration Form, Log in Form, and Crud Operations")//
        .version("1.0.0")//
        .license("GlobalVox PVT ltd").licenseUrl("https://www.globalvoxinc.com")//
        .contact(new Contact("Imran","https://www.globalvoxinc.com/contact-us","imran.sheikh@globalvoxinc.com"))//
        .build();
    }
}
