package com.energicube.eno.common.jdbc;

import com.energicube.eno.common.ChartModel;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChartModelMapper implements ParameterizedRowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        ChartModel chartModel = new ChartModel();
        chartModel.setName(rs.getString("name"));
        chartModel.setTime(rs.getString("time"));
        chartModel.setValue(rs.getFloat("value") + "");
        return chartModel;
    }

}
