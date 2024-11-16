package org.example.datajdbc.util;

import org.example.datajdbc.domain.BagDefinitionView;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDate;
import java.util.List;

@Component
public class QueryExtractor {
    public List<BagDefinitionView> extractCcSlicSort(ServerRequest request, IBagDefinitionsForOriginAndDateRange iBagDefinitionsForOriginAndDateRange) {
        String originCc = request.queryParam("originCc").orElseThrow(() -> new IllegalArgumentException("OriginCc is required"));
        String originSlic = request.queryParam("originSlic").orElseThrow(() -> new IllegalArgumentException("OriginSlic is required"));
        String originSort = request.queryParam("originSort").orElseThrow(() -> new IllegalArgumentException("OriginSort is required"));
        LocalDate startDate = LocalDate.parse(request.queryParam("startDate").orElseThrow(() -> new IllegalArgumentException("Start date is required")));
        LocalDate endDate = LocalDate.parse(request.queryParam("endDate").orElseThrow(() -> new IllegalArgumentException("End date is required")));
        return iBagDefinitionsForOriginAndDateRange.getBagDefinitionsForOriginAndDateRange(originCc, originSlic, originSort, startDate, endDate);
    }

}
