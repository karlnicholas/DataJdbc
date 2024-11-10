package org.example.datajdbc.api;

import org.example.datajdbc.domain.BagDefinitionView;
import org.example.datajdbc.service.BagDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
public class BagDefinitionHandler {

    private final BagDefinitionService bagDefinitionService;

    @Autowired
    public BagDefinitionHandler(BagDefinitionService bagDefinitionService) {
        this.bagDefinitionService = bagDefinitionService;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return Mono.fromSupplier(bagDefinitionService::findAll)
                .flatMap(bagDefinitions -> ServerResponse.ok().bodyValue(bagDefinitions));
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return Mono.fromSupplier(() -> bagDefinitionService.findById(id))
                .flatMap(bagDefinitionView -> bagDefinitionView
                        .map(view -> ServerResponse.ok().bodyValue(view))
                        .orElseGet(() -> ServerResponse.notFound().build())
                );
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(BagDefinitionView.class)
                .flatMap(bagDefinitionView -> Mono.fromSupplier(() -> bagDefinitionService.createBagDefinition(bagDefinitionView)))
                .flatMap(savedBagDefinitionView -> ServerResponse.ok().bodyValue(savedBagDefinitionView));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(BagDefinitionView.class)
                .flatMap(bagDefinitionView -> Mono.fromSupplier(() -> bagDefinitionService.updateBagDefinition(bagDefinitionView)))
                .flatMap(updatedBagDefinitionView -> updatedBagDefinitionView
                        .map(view -> ServerResponse.ok().bodyValue(view))
                        .orElseGet(() -> ServerResponse.notFound().build())
                );
    }

    public Mono<ServerResponse> getBagDefinitionsForOriginAndDateRange(ServerRequest request) {
        try {
            String originCc = request.queryParam("originCc").orElseThrow(() -> new IllegalArgumentException("OriginCc is required"));
            String originSlic = request.queryParam("originSlic").orElseThrow(() -> new IllegalArgumentException("OriginSlic is required"));
            String originSort = request.queryParam("originSort").orElseThrow(() -> new IllegalArgumentException("OriginSort is required"));
            LocalDate startDate = LocalDate.parse(request.queryParam("startDate").orElseThrow(() -> new IllegalArgumentException("Start date is required")));
            LocalDate endDate = LocalDate.parse(request.queryParam("endDate").orElseThrow(() -> new IllegalArgumentException("End date is required")));

            return Mono.fromSupplier(() -> bagDefinitionService.getBagDefinitionsForOriginAndDateRange(originCc, originSlic, originSort, startDate, endDate))
                    .flatMap(bagDefinitions -> ServerResponse.ok().bodyValue(bagDefinitions))
                    .switchIfEmpty(ServerResponse.noContent().build());

        } catch (Exception e) {
            return ServerResponse.badRequest().bodyValue("Invalid parameters: " + e.getMessage());
        }
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return Mono.fromRunnable(() -> bagDefinitionService.delete(id))
                .then(ServerResponse.noContent().build());
    }
}
