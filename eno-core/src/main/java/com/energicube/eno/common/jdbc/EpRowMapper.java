package com.energicube.eno.common.jdbc;

import com.energicube.eno.common.PatternConst;
import com.energicube.eno.common.dto.EpObject;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: EnergyUser
 * Date: 13-11-26
 * Time: 下午12:11
 * 巡更的信息的映射工具
 */
public class EpRowMapper implements ParameterizedRowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        EpObject epObject = new EpObject();
        epObject.setId(rs.getString(1));
        epObject.setRoad(rs.getString(3));
        epObject.setShiftWork(rs.getString(4));
        Date sd = rs.getTimestamp(5);
        if (sd != null) {
            DateTime dt = new DateTime(sd);
            epObject.setStartDate(dt.toString("yyyy-MM-dd HH:mm:ss"));
        }
        sd = rs.getTimestamp(6);
        if (sd != null) {
            DateTime dt = new DateTime(sd);
            epObject.setEndDate(dt.toString("yyyy-MM-dd HH:mm:ss"));
        }
        String re = PatternConst.getEpResult(rs.getString(7));
        epObject.setResult(re);
        epObject.setPerson(rs.getString(8));
        sd = rs.getTimestamp(9);
        if (sd != null) {
            DateTime dt = new DateTime(sd);
            epObject.setFirstTime(dt.toString("yyyy-MM-dd HH:mm:ss"));
        }
        sd = rs.getTimestamp(10);
        if (sd != null) {
            DateTime dt = new DateTime(sd);
            epObject.setEndTime(dt.toString("yyyy-MM-dd HH:mm:ss"));
        }
        re = PatternConst.getRoadType(rs.getString(11));
        epObject.setRoadType(re);
        epObject.setRemark(rs.getString(12));
        re = PatternConst.getShiftType(rs.getString(13));
        epObject.setShiftType(re);
        epObject.setMissedNum(rs.getString(14));
        epObject.setOnTimeNum(rs.getString(15));
        epObject.setEarlyNum(rs.getString(16));
        epObject.setLateNum(rs.getString(17));
        return epObject;
    }
}
