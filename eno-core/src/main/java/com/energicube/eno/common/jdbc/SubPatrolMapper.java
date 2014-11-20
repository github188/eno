package com.energicube.eno.common.jdbc;

import com.energicube.eno.common.PatternConst;
import com.energicube.eno.monitor.model.SubPatrol;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubPatrolMapper implements ParameterizedRowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        SubPatrol subPatrol = new SubPatrol();
        subPatrol.setId(rs.getString(1));
        subPatrol.setLineNum(rs.getString(2));
        subPatrol.setLineName(rs.getString(3));
        subPatrol.setUserId(rs.getString(4));
        subPatrol.setUserName(rs.getString(5));
        subPatrol.setPlaceName(rs.getString(6));
        subPatrol.setStartTime(rs.getDate(7));
        subPatrol.setEndTime(rs.getDate(8));
        subPatrol.setCheckTime(rs.getDate(9));
        String re = PatternConst.getEpResult(rs.getString(10));
        subPatrol.setCheckResult(re);
        re = PatternConst.getShiftType(rs.getString(11));
        subPatrol.setShifts(re);
        subPatrol.setMissedNum(rs.getInt(12));
        subPatrol.setOnTimeNum(rs.getInt(13));
        subPatrol.setEarlyNum(rs.getInt(14));
        subPatrol.setLateNum(rs.getInt(15));
        return subPatrol;
    }

}
