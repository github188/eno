package com.energicube.eno.common.jdbc;

import com.energicube.eno.common.dto.PfeObjectDTO;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by EnergyUser on 14-1-20.
 */
public class PfeTotalRowMapper implements ParameterizedRowMapper {


    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        PfeObjectDTO pfeObjectDTO=new PfeObjectDTO();
        pfeObjectDTO.setInCount(Integer.parseInt(rs.getString(1)));
        pfeObjectDTO.setOutCount(Integer.parseInt(rs.getString(2)));
        return pfeObjectDTO;
    }
}
