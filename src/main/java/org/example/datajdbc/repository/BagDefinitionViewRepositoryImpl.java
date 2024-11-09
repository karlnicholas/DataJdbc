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

@Repository
public class BagDefinitionViewRepositoryImpl implements BagDefinitionViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BagDefinitionViewRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<BagDefinitionView> findByOriginAndDateRange(String originCc, String originSlic, String originSort, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM vw_BagDefinitionDetails " +
                "WHERE originCc = ? AND originSlic = ? AND originSort = ? " +
                "AND startDate >= ? AND endDate <= ?";

        return jdbcTemplate.query(sql, new Object[]{originCc, originSlic, originSort, startDate, endDate}, new BagDefinitionViewRowMapper());
    }

    private static class BagDefinitionViewRowMapper implements RowMapper<BagDefinitionView> {
        @Override
        public BagDefinitionView mapRow(ResultSet rs, int rowNum) throws SQLException {
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
}
