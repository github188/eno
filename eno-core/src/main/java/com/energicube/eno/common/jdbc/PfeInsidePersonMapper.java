package com.energicube.eno.common.jdbc;

import com.energicube.eno.common.dto.PfeObjectDTO;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by EnergyUser on 14-1-20.
 */
public class PfeInsidePersonMapper implements ParameterizedRowMapper {


    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        PfeObjectDTO pfeObjectDTO = new PfeObjectDTO();
        pfeObjectDTO.setId(rs.getString(1));
        pfeObjectDTO.setBlockName(rs.getString(2));
        pfeObjectDTO.setCountTime(rs.getString(3));
//        Date da=rs.getTimestamp(4);
//        if(da!=null){
//            DateTime dt = new DateTime(da);
//            sasacObject.setEventTime(dt.toString("yyyy-MM-dd HH:mm:ss"));
//        }
        String inco = rs.getString(4);
        if (inco != null && !"".equals(inco)) {
            pfeObjectDTO.setInCount(Long.parseLong(inco));
        } else {
            pfeObjectDTO.setInCount(0);
        }

        String out = rs.getString(5);
        if (out != null && !"".equals(out)) {
            pfeObjectDTO.setOutCount(Long.parseLong(out));
        } else {
            pfeObjectDTO.setOutCount(0);
        }

        String inside = rs.getString(6);
        if (inside != null && !"".equals(inside)) {
            pfeObjectDTO.setInsidePerson(Long.parseLong(inside) <= 0 ? 0 : Long.parseLong(inside));
        } else {
            pfeObjectDTO.setInsidePerson(0);
        }
        return pfeObjectDTO;
    }
}
