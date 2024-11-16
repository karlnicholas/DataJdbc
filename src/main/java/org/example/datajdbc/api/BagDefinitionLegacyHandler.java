package org.example.datajdbc.api;

import org.example.datajdbc.domain.BagDefinitionLegacy;
import org.example.datajdbc.service.BagDefinitionLegacyService;
import org.example.datajdbc.util.QueryExtractor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class BagDefinitionLegacyHandler {

    private final BagDefinitionLegacyService bagDefinitionLegacyService;
    private final QueryExtractor queryExtractor;

    public BagDefinitionLegacyHandler(BagDefinitionLegacyService bagDefinitionLegacyService, QueryExtractor queryExtractor) {
        this.bagDefinitionLegacyService = bagDefinitionLegacyService;
        this.queryExtractor = queryExtractor;
    }

    public Mono<ServerResponse> getAll(ServerRequest ignoredRequest) {
        return Mono.fromSupplier(bagDefinitionLegacyService::findAll)
                .flatMap(bagDefinitions -> ServerResponse.ok().bodyValue(bagDefinitions));
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return Mono.fromSupplier(() -> bagDefinitionLegacyService.findById(id))
                .flatMap(bagDefinition -> bagDefinition.isPresent()
                        ? ServerResponse.ok().bodyValue(bagDefinition)
                        : ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(BagDefinitionLegacy.class)
                .flatMap(bagDefinitionLegacy -> Mono.fromSupplier(() -> bagDefinitionLegacyService.createBagDefinition(bagDefinitionLegacy)))
                .flatMap(created -> ServerResponse.ok().bodyValue(created));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(BagDefinitionLegacy.class)
                .flatMap(bagDefinitionLegacy -> Mono.fromSupplier(() -> bagDefinitionLegacyService.updateBagDefinition(bagDefinitionLegacy)))
                .flatMap(updated -> updated.isPresent()
                        ? ServerResponse.ok().bodyValue(updated)
                        : ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return Mono.fromRunnable(() -> bagDefinitionLegacyService.delete(id))
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getBagDefinitionsForOriginAndDateRange(ServerRequest request) {
        try {
            return Mono.fromSupplier(() -> queryExtractor.extractCcSlicSort(request, bagDefinitionLegacyService))
                    .flatMap(bagDefinitions -> ServerResponse.ok().bodyValue(bagDefinitions))
                    .switchIfEmpty(ServerResponse.noContent().build());

        } catch (Exception e) {
            return ServerResponse.badRequest().bodyValue("Invalid parameters: " + e.getMessage());
        }
    }
}
