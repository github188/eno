package com.energicube.eno.common.jdbc;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: EnergyUser
 * Date: 13-11-26
 * Time: 下午11:42
 * 班次和路线的映射工具
 */
public class EpShiftRowMapper implements ParameterizedRowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        String[] strs = new String[2];
        strs[0] = rs.getString(1);
        strs[1] = rs.getString(2);
        return strs;
    }
}
