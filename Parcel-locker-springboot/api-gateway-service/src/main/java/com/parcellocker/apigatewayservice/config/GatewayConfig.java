package com.parcellocker.apigatewayservice.config;

import com.parcellocker.apigatewayservice.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    //Routing
    //A bejövő kérés útvonala alapján elirányítom a kérést az adott service-hez
    //Például, ha bejön az, hogy /auth/**(bármi), akkor azt az authentication-service-ben keresse
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes().route("authentication-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://authentication-service"))

                .route("notification-service", r -> r.path("/notification/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://notification-service"))

                .route("statistics-service", r -> r.path("/statistics/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://statistics-service"))
                .build();


    }
}
