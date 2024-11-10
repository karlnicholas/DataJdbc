package org.example.datajdbc.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BagDefinitionRouter {

    @Bean
    public RouterFunction<ServerResponse> bagDefinitionRoutes(BagDefinitionHandler handler) {
        return route(GET("/api/bag-definitions"), handler::getAll)
                .andRoute(GET("/api/bag-definitions/{id}"), handler::getById)
                .andRoute(POST("/api/bag-definitions"), handler::create)
                .andRoute(PUT("/api/bag-definitions/{id}"), handler::update)
                .andRoute(DELETE("/api/bag-definitions/{id}"), handler::delete);
    }
}
