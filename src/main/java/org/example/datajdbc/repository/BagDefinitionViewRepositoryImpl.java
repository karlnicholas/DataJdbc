package org.example.datajdbc.repository;

import org.example.datajdbc.domain.BagDefinitionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class BagDefinitionViewRepositoryImpl implements BagDefinitionViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BagDefinitionViewRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<BagDefinitionView> findAll() {
        String sql = "SELECT * FROM BagDefinitionView";
        return jdbcTemplate.query(sql, new BagDefinitionViewRowMapper());
    }

    @Override
    public Optional<BagDefinitionView> findById(Long id) {
        String sql = "SELECT * FROM BagDefinitionView WHERE bagDefinitionId = ?";
        List<BagDefinitionView> results = jdbcTemplate.query(sql, new BagDefinitionViewRowMapper(), id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<BagDefinitionView> findByOriginAndDateRange(String originCc, String originSlic, String originSort, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM BagDefinitionView " +
                "WHERE originCc = :originCc " +
                "AND originSlic = :originSlic " +
                "AND originSort = :originSort " +
                "AND (startDate <= :endDate AND endDate >= :startDate)";
        return jdbcTemplate.query(sql, new BagDefinitionViewRowMapper(), originCc, originSlic, originSort, startDate, endDate);
    }

    private static class BagDefinitionViewRowMapper implements RowMapper<BagDefinitionView> {
        @Override
        public BagDefinitionView mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new BagDefinitionView(
                    rs.getLong("bagDefinitionId"),
                    rs.getString("originCc"),
                    rs.getString("originSlic"),
                    rs.getString("originSort"),
                    rs.getString("destinationCc"),
                    rs.getString("destinationSlic"),
                    rs.getString("destinationSort"),
                    rs.getDate("startDate").toLocalDate(),
                    rs.getDate("endDate").toLocalDate()
            );
        }
    }
}
