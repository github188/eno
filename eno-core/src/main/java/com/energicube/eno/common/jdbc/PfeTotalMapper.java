package com.energicube.eno.common.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.energicube.eno.common.dto.PfeObjectDTO;

/**
 * Created by EnergyUser on 14-1-20.
 */
public class PfeTotalMapper implements ParameterizedRowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        PfeObjectDTO pfeObjectDTO=new PfeObjectDTO();
        pfeObjectDTO.setId(rs.getString("id"));
        return pfeObjectDTO;
    }
}
