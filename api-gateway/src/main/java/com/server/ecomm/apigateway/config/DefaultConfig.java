package com.server.ecomm.apigateway.config;

import com.server.ecomm.apigateway.filters.AuthFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class DefaultConfig {

    @Value("${spring.gateway.excludedURLsNew}")
    private String excludedUrlString;

    @Bean
    @Qualifier("excludedUrls")
    public List<String> excludedUrls(){
        return Arrays.stream(excludedUrlString.split(",")).collect(Collectors.toList());
    }

    @Bean
    public RouteLocator customRouteConfig(RouteLocatorBuilder builder, AuthFilter authFilter){
        return builder.routes()
                .route("authentication-server",r -> r.path("/authentication-service/**")
                        .filters(f ->
                                f.rewritePath("/authentication-service(?<segment>/?.*)","$\\{segment}")
                                        .filter(authFilter.apply(new AuthFilter.Config())))
                        .uri("lb://authentication-service"))
                .route("user-service", r -> r.path("/user-service/**")
                        .filters(f ->
                                f.rewritePath("/user-service(?<segment>/?.*)", "$\\{segment}")
                                        .filter(authFilter.apply(new AuthFilter.Config())))
                        .uri("lb://user-service"))
                .route("product-service", r -> r.path("/product-service/**")
                        .filters(f ->
                                f.rewritePath("/product-service(?<segment>/?.*)", "$\\{segment}")
                                        .filter(authFilter.apply(new AuthFilter.Config())))
                        .uri("lb://product-service"))
                .route("order-service", r -> r.path("/order-service/**")
                        .filters(f ->
                                f.rewritePath("/order-service(?<segment>/?.*)", "$\\{segment}")
                                        .filter(authFilter.apply(new AuthFilter.Config())))
                        .uri("lb://order-service"))
                .route("review-service", r -> r.path("/review-service/**")
                        .filters(f ->
                                f.rewritePath("/review-service(?<segment>/?.*)", "$\\{segment}")
                                        .filter(authFilter.apply(new AuthFilter.Config())))
                        .uri("lb://review-service"))
                .build();
    }
}
