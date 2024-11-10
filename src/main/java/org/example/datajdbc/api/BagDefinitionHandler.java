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
                .flatMap(bagDefinitionView -> Mono.fromSupplier(() -> bagDefinitionService.createBagDefinition(
                        bagDefinitionView.getOriginCc(),
                        bagDefinitionView.getOriginSlic(),
                        bagDefinitionView.getOriginSort(),
                        bagDefinitionView.getDestinationCc(),
                        bagDefinitionView.getDestinationSlic(),
                        bagDefinitionView.getDestinationSort(),
                        bagDefinitionView.getStartDate(),
                        bagDefinitionView.getEndDate()
                )))
                .flatMap(savedBagDefinitionView -> ServerResponse.ok().bodyValue(savedBagDefinitionView));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return request.bodyToMono(BagDefinitionView.class)
                .flatMap(bagDefinitionView -> Mono.fromSupplier(() -> bagDefinitionService.updateBagDefinition(
                        id,
                        bagDefinitionView.getOriginCc(),
                        bagDefinitionView.getOriginSlic(),
                        bagDefinitionView.getOriginSort(),
                        bagDefinitionView.getDestinationCc(),
                        bagDefinitionView.getDestinationSlic(),
                        bagDefinitionView.getDestinationSort(),
                        bagDefinitionView.getStartDate(),
                        bagDefinitionView.getEndDate()
                )))
                .flatMap(updatedBagDefinitionView -> updatedBagDefinitionView
                        .map(view -> ServerResponse.ok().bodyValue(view))
                        .orElseGet(() -> ServerResponse.notFound().build())
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return Mono.fromRunnable(() -> bagDefinitionService.delete(id))
                .then(ServerResponse.noContent().build());
    }
}
