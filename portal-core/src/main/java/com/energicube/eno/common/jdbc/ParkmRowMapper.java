package com.energicube.eno.common.jdbc;

import com.energicube.eno.common.dto.ParkmObject;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: EnergyUser
 * Date: 13-11-26
 * Time: 下午6:51
 * 停车场的信息映射工具
 */
public class ParkmRowMapper implements ParameterizedRowMapper {


    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        ParkmObject parkmObject = new ParkmObject();
        parkmObject.setCarNum(rs.getString(1));
        Date sd = rs.getTimestamp(2);
        if (sd != null) {
            DateTime dt = new DateTime(sd);
            parkmObject.setInDate(dt.toString("yyyy-MM-dd HH:mm:ss"));
        }
        sd = rs.getTimestamp(3);
        if (sd != null) {
            DateTime dt = new DateTime(sd);
            parkmObject.setOutDate(dt.toString("yyyy-MM-dd HH:mm:ss"));
        }
        parkmObject.setPayMoney(rs.getString(4));
        parkmObject.setCardType(rs.getString(5));
        parkmObject.setComeName(rs.getString(6));
        parkmObject.setGoName(rs.getString(7));
        parkmObject.setCardNo(rs.getString(8));
        parkmObject.setCardName(rs.getString(9));
        return parkmObject;
    }
}
