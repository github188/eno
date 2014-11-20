package com.energicube.eno.common.jdbc;

import com.energicube.eno.common.dto.SasacObject;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: EnergyUser
 * Date: 13-11-27
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 */
public class SasacRowMapper implements ParameterizedRowMapper {


    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        SasacObject sasacObject = new SasacObject();
        sasacObject.setId(rs.getString(1));
        sasacObject.setUserName(rs.getString(2));
        sasacObject.setCardId(rs.getString(3));
        Date da = rs.getTimestamp(4);
        if (da != null) {
            DateTime dt = new DateTime(da);
            sasacObject.setEventTime(dt.toString("yyyy-MM-dd HH:mm:ss"));
        }
        sasacObject.setContentMsg(rs.getString(5));
        return sasacObject;
    }
}
