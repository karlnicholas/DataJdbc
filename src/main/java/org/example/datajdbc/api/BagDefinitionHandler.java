package org.example.datajdbc.api;

import org.example.datajdbc.domain.BagDefinitionView;
import org.example.datajdbc.service.BagDefinitionService;
import org.example.datajdbc.util.QueryExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class BagDefinitionHandler {

    private final BagDefinitionService bagDefinitionService;
    private final QueryExtractor queryExtractor;
    @Autowired
    public BagDefinitionHandler(BagDefinitionService bagDefinitionService, QueryExtractor queryExtractor) {
        this.bagDefinitionService = bagDefinitionService;
        this.queryExtractor = queryExtractor;
    }

    public Mono<ServerResponse> getAll(ServerRequest ignoredRequest) {
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
                .flatMap(id -> ServerResponse.ok().bodyValue(id));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(BagDefinitionView.class)
                .flatMap(bagDefinitionView -> Mono.fromSupplier(() -> bagDefinitionService.updateBagDefinition(bagDefinitionView)))
                .flatMap(updated -> updated.isPresent()
                        ? ServerResponse.ok().bodyValue(updated)
                        : ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getBagDefinitionsForOriginAndDateRange(ServerRequest request) {
        try {
            return Mono.fromSupplier(() -> queryExtractor.extractCcSlicSort(request, bagDefinitionService))
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
