package ru.intervale.course.integration.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.intervale.course.integration.model.Work;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkMapper implements RowMapper<Work> {
    @Override
    public Work mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Work(rs.getString("NAME"), rs.getInt("PAGES"),
                new String[] {rs.getString("AUTHOR")},  new String[] {rs.getString("ISBN")});
    }
}
