package com.energicube.eno.common.jdbc;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by EnergyUser on 14-1-20.
 */
public class PfeTotalRowMapper implements ParameterizedRowMapper {


    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        return rs.getString(1);
    }
}
