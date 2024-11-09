package org.example.datajdbc.service;

import lombok.extern.slf4j.Slf4j;
import org.example.datajdbc.domain.BagDefinition;
import org.example.datajdbc.domain.BagDefinitionView;
import org.example.datajdbc.domain.FlowNode;
import org.example.datajdbc.repository.BagDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ApiService {

    private final BagDefinitionRepository bagDefinitionRepository;
    private final FlowNodeService flowNodeService;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ApiService(BagDefinitionRepository bagDefinitionRepository, FlowNodeService flowNodeService, JdbcTemplate jdbcTemplate) {
        this.bagDefinitionRepository = bagDefinitionRepository;
        this.flowNodeService = flowNodeService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public BagDefinition createBagDefinition(
            String originCc, String originSlic, String originSort,
            String destinationCc, String destinationSlic, String destinationSort,
            LocalDate startDate, LocalDate endDate) {

        // Get or create origin and destination FlowNodes
        FlowNode origin = flowNodeService.getOrCreateFlowNode(originCc, originSlic, originSort);
        FlowNode destination = flowNodeService.getOrCreateFlowNode(destinationCc, destinationSlic, destinationSort);

        // Create a new BagDefinition instance with IDs instead of objects
        BagDefinition bagDefinition = BagDefinition.builder()
                .originId(origin.getId())
                .destinationId(destination.getId())
                .startDate(startDate)
                .endDate(endDate)
                .build();

        return bagDefinitionRepository.save(bagDefinition);
    }

    /**
     * Retrieves BagDefinitionView entries filtered by originCc, originSlic, originSort, and a date range.
     *
     * @param originCc   The origin country code.
     * @param originSlic The origin SLIC code.
     * @param originSort The origin sort code.
     * @param startDate  The start date for filtering.
     * @param endDate    The end date for filtering.
     * @return A list of BagDefinitionView entries matching the criteria.
     */
    public List<BagDefinitionView> getBagDefinitionsForOriginAndDateRange(
            String originCc, String originSlic, String originSort,
            LocalDate startDate, LocalDate endDate) {

        String sql = "SELECT * FROM vw_BagDefinitionDetails " +
                "WHERE originCc = ? AND originSlic = ? AND originSort = ? " +
                "AND startDate >= ? AND endDate <= ?";

        return jdbcTemplate.query(
                sql,
                new Object[]{originCc, originSlic, originSort, startDate, endDate},
                this::mapRowToBagDefinitionView
        );
    }

    private BagDefinitionView mapRowToBagDefinitionView(ResultSet rs, int rowNum) throws SQLException {
        return new BagDefinitionView(
                rs.getLong("bagDefinitionId"),
                rs.getDate("startDate").toLocalDate(),
                rs.getDate("endDate").toLocalDate(),
                rs.getString("originCc"),
                rs.getString("originSlic"),
                rs.getString("originSort"),
                rs.getString("destinationCc"),
                rs.getString("destinationSlic"),
                rs.getString("destinationSort")
        );
    }
}
