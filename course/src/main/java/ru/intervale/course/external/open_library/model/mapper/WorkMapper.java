package ru.intervale.course.external.open_library.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.intervale.course.external.open_library.model.Work;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Конвертация результата запроса в БД в класс работы автора
 */
public class WorkMapper implements RowMapper<Work> {
    /**
     *
     * @param rs результат выполения запроса к БД
     * @param rowNum количество строк, возвращенных после выполнения запроса
     * @return класс работы, сформированный из запроса к БД
     * @throws SQLException ошибка запроса
     */
    @Override
    public Work mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Work(rs.getString("NAME"), rs.getInt("PAGES"),
                new String[] {rs.getString("AUTHOR")},  new String[] {rs.getString("ISBN")});
    }
}
