package org.example.datajdbc.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BagDefinitionLegacyRouter {

    @Bean
    public RouterFunction<ServerResponse> bagDefinitionLegacyRoutes(BagDefinitionLegacyHandler handler) {
        return route(GET("/api/legacy/bag-definitions"), handler::getAll)
                .andRoute(GET("/api/legacy/bag-definitions/{id}"), handler::getById)
                .andRoute(POST("/api/legacy/bag-definitions"), handler::create)
                .andRoute(PUT("/api/legacy/bag-definitions/{id}"), handler::update)
                .andRoute(DELETE("/api/legacy/bag-definitions/{id}"), handler::delete)
                .andRoute(GET("/api/legacy/bag-definitions/filter")
                                .and(queryParam("originCc", param -> true))
                                .and(queryParam("originSlic", param -> true))
                                .and(queryParam("originSort", param -> true))
                                .and(queryParam("startDate", param -> true))
                                .and(queryParam("endDate", param -> true)),
                        handler::getBagDefinitionsForOriginAndDateRange);
    }
}
