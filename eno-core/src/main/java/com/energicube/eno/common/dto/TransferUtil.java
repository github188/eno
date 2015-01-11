package com.energicube.eno.common.dto;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.energicube.eno.monitor.model.UcPassengerFlowDetail;

/**
 * DTO的转换工具
 * @author LiuGuanglu
 * @date 2014/6/3.
 */
public class TransferUtil {

    public static PfeObjectDTO PfeHuiNaToPfeObject(PfeHuiNaDTO pfeHuiNaDTO){
        PfeObjectDTO pfeObjectDTO=new PfeObjectDTO();
        pfeObjectDTO.setCountTime(pfeHuiNaDTO.getCountDate());
        pfeObjectDTO.setId(pfeHuiNaDTO.getSiteKey());
        pfeObjectDTO.setStatus(pfeHuiNaDTO.getSiteType());
        pfeObjectDTO.setBlockName(pfeHuiNaDTO.getSiteName());
        pfeObjectDTO.setInCount(pfeHuiNaDTO.getInSum());
        pfeObjectDTO.setOutCount(pfeHuiNaDTO.getOutSum());
        return pfeObjectDTO;
    }

    public static UcPassengerFlowDetail PfeHuiNaToUcPassengerFlow(PfeHuiNaDTO pfeHuiNaDTO){
        UcPassengerFlowDetail ucPassengerFlowDetail=new UcPassengerFlowDetail();
        String countDate=pfeHuiNaDTO.getCountDate();
        int loca=countDate.lastIndexOf(".");
        if(loca>0){
            countDate=countDate.substring(0,loca);
        }
        DateTime da=DateTime.parse(countDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        ucPassengerFlowDetail.setCreateDate(da.toDate());
        ucPassengerFlowDetail.setDateDay(da.getDayOfMonth());
        ucPassengerFlowDetail.setDateHour(da.getHourOfDay());
        ucPassengerFlowDetail.setDateMinute(da.getMinuteOfHour());
        ucPassengerFlowDetail.setDateMonth(da.getMonthOfYear());
        ucPassengerFlowDetail.setDateYear(da.getYear());
        ucPassengerFlowDetail.setShopCode(pfeHuiNaDTO.getSiteKey());
        ucPassengerFlowDetail.setShopName(pfeHuiNaDTO.getSiteName());
        ucPassengerFlowDetail.setInCount(Integer.parseInt(pfeHuiNaDTO.getInSum() + ""));
        ucPassengerFlowDetail.setOutCount(Integer.parseInt(pfeHuiNaDTO.getOutSum()+""));
        return ucPassengerFlowDetail;
    }
    // OPEN_FLAG_CARD = $10;   //刷卡开门
    // OPEN_FLAG_PSW = $20;    //密码开门
    // OPEN_FLAG_INVCARD = $30; //非法卡
    // OPEN_FLAG_INVEMP = $40; //非法开门
    // OPEN_FLAG_BUTTON = $50;  //按钮开门
    // OPEN_FLAG_REMOTE = $60; //遥控开门
    // OPEN_FLAG_HOLD = $70; //胁迫开门

    /**
     * 门禁开门的信息
     *
     * @param code 开门的编码
     * @return
     */
    public static String sasacTransfer(String code) {
        if (StringUtils.isNotEmpty(code)) {
            if (code.equals("10")) {
                return "刷卡开门";
            }
            if (code.equals("20")) {
                return "密码开门";
            }
            if (code.equals("30")) {
                return "非法卡";
            }
            if (code.equals("40")) {
                return "非法开门";
            }
            if (code.equals("50")) {
                return "按钮开门";
            }
            if (code.equals("60")) {
                return "遥控开门";
            }
            if (code.equals("70")) {
                return "胁迫开门";
            }
        }
        return code;
    }
}
