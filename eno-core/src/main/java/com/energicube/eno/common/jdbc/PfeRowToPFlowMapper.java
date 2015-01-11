package com.energicube.eno.common.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.energicube.eno.monitor.model.UcPassengerFlowDetail;

/**
 * 客流转为PassengerFlow实体
 */
public class PfeRowToPFlowMapper implements ParameterizedRowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        UcPassengerFlowDetail ucPassengerFlowDetail=new UcPassengerFlowDetail();
        ucPassengerFlowDetail.setShopCode(rs.getString(1));
        ucPassengerFlowDetail.setShopName(rs.getString(2));
        Date da=rs.getDate(3);
        if(da!=null){
            ucPassengerFlowDetail.setCreateDate(da);
        }
//        ucPassengerFlowDetail.setCreateDate(rs.getString(3));
        ucPassengerFlowDetail.setDateYear(rs.getInt(4));
        ucPassengerFlowDetail.setDateMonth(rs.getInt(5));
        ucPassengerFlowDetail.setDateDay(rs.getInt(6));
        ucPassengerFlowDetail.setDateHour(rs.getInt(7));
        ucPassengerFlowDetail.setDateMinute(rs.getInt(8));
        ucPassengerFlowDetail.setDateSecond(0);
        ucPassengerFlowDetail.setInCount(rs.getInt(9));
        ucPassengerFlowDetail.setOutCount(rs.getInt(10));

        return ucPassengerFlowDetail;
    }
}
