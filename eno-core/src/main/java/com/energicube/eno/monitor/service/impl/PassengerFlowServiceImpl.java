package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.dto.PfeObjectDTO;
import com.energicube.eno.common.jdbc.PfeTotalRowMapper;
import com.energicube.eno.monitor.model.*;
import com.energicube.eno.monitor.repository.*;
import com.energicube.eno.monitor.service.PassengerFlowService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 
 * All rights Reserved, Designed By ZCLF
 * Copyright:  Copyright(C) 2013-2014
 * Company  ZCLF Energy Technologies Inc.
 *
 * @author 尚培宝
 * @version 1.0
 * @date 2014年11月26日
 * @Description  客流查询实现类
 */
@Service
public class PassengerFlowServiceImpl implements PassengerFlowService {

    private final Log logger = LogFactory.getLog(PassengerFlowServiceImpl.class);
    
    @Autowired
    private UcPassengerFlowDetailRepository ucPassengerFlowDetailRepository;

    @Autowired
    private UcPassengerFlowDayRepository ucPassengerFlowDayRepository;

    @Autowired
    private UcPassengerFlowMonthRepository ucPassengerFlowMonthRepository;

    @Autowired
    private UcPassengerFlowYearRepository ucPassengerFlowYearRepository;

    @Autowired
    private UcPassengerFlowShopConfigRepository ucPassengerFlowShopConfigRepository;

    @Autowired
    private JdbcTemplate dataSourceTemplate;

    @Override
    public List<UcPassengerFlowShopConfig> findAllShopConfig(String action) {
        List<UcPassengerFlowShopConfig> list = ucPassengerFlowShopConfigRepository.findByAction(action);
        return list;
    }

    @Override
    public UcPassengerFlowDetail saveUcPassengerFlow(UcPassengerFlowDetail ucPassengerFlow) {
        return ucPassengerFlowDetailRepository.saveAndFlush(ucPassengerFlow);
    }

    @Override
    public List<UcPassengerFlowDetail> findAllShopPassenger(DateTime startDate, DateTime endDate) {
        return ucPassengerFlowDetailRepository.findByDate(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), startDate.getHourOfDay(), startDate.getMinuteOfHour(), endDate.getYear(), endDate.getMonthOfYear(), endDate.getDayOfMonth(), endDate.getHourOfDay(), endDate.getMinuteOfHour());
    }

    @Override
    public UcPassengerFlowDetail saveUcPassengerFlowAndStatistic(UcPassengerFlowDetail ucPassengerFlow) {
        UcPassengerFlowDetail ucPassengerFlowDetail = ucPassengerFlowDetailRepository.saveAndFlush(ucPassengerFlow);
        int day = ucPassengerFlowDetail.getDateDay();
        int month = ucPassengerFlowDetail.getDateMonth();
        int year = ucPassengerFlowDetail.getDateYear();
        String shopId = ucPassengerFlowDetail.getShopCode();
        //更新天的客流统计信息
        List<UcPassengerFlowDay> listDay = ucPassengerFlowDayRepository.findByShopCodeAndDateYearAndDateMonthAndDateDay(shopId, year, month, day);
        if (listDay != null && listDay.size() > 0) {//此店铺已经有今天的客流信息
            UcPassengerFlowDay old = listDay.get(0);
            updateUcPassengerFlowDay(old, ucPassengerFlowDetail);
        } else {//此店铺还无今天的客流信息
            updateUcPassengerFlowDay(null, ucPassengerFlowDetail);
        }
        //更新月的客流统计信息
        List<UcPassengerFlowMonth> listMonth = ucPassengerFlowMonthRepository.findByShopCodeAndDateYearAndDateMonth(shopId, year, month);
        if (listMonth != null && listMonth.size() > 0) {//此店铺已经有今天的客流信息
            UcPassengerFlowMonth old = listMonth.get(0);
            updateUcPassengerFlowMonth(old, ucPassengerFlowDetail);
        } else {//此店铺还无今天的客流信息
            updateUcPassengerFlowMonth(null, ucPassengerFlowDetail);
        }
        //更新年的客流统计信息
        List<UcPassengerFlowYear> listYear = ucPassengerFlowYearRepository.findByShopCodeAndDateYear(shopId, year);
        if (listYear != null && listYear.size() > 0) {//此店铺已经有今天的客流信息
            UcPassengerFlowYear old = listYear.get(0);
            updateUcPassengerFlowYear(old, ucPassengerFlowDetail);
        } else {//此店铺还无今天的客流信息
            updateUcPassengerFlowYear(null, ucPassengerFlowDetail);
        }
        return ucPassengerFlowDetail;
    }

    /**
     * 更新每天的客流统计信息
     *
     * @param oldPassengerFlowDay 原有的客流信息
     * @param newFlow             最新的客流信息
     * @return 客流信息
     */
    private UcPassengerFlowDay updateUcPassengerFlowDay(UcPassengerFlowDay oldPassengerFlowDay, UcPassengerFlowDetail newFlow) {
        UcPassengerFlowDay ucPassengerFlowDay = null;
        if (oldPassengerFlowDay == null) {//不存在已有记录
            ucPassengerFlowDay = new UcPassengerFlowDay();
            ucPassengerFlowDay.setInCount(newFlow.getInCount());
            ucPassengerFlowDay.setOutCount(newFlow.getOutCount());
            ucPassengerFlowDay.setMinValue(newFlow.getInCount());
            ucPassengerFlowDay.setMaxValue(newFlow.getInCount());
            ucPassengerFlowDay.setAvgValue(newFlow.getInCount());

            ucPassengerFlowDay.setShopCode(newFlow.getShopCode());
            ucPassengerFlowDay.setShopName(newFlow.getShopName());
            ucPassengerFlowDay.setDateYear(newFlow.getDateYear());
            ucPassengerFlowDay.setDateMonth(newFlow.getDateMonth());
            ucPassengerFlowDay.setDateDay(newFlow.getDateDay());
        } else {
            ucPassengerFlowDay = oldPassengerFlowDay;
            int oldIncout = oldPassengerFlowDay.getInCount();
            int oldOutcount = oldPassengerFlowDay.getOutCount();
            int newIncount = newFlow.getInCount();
            int newOutcount = newFlow.getOutCount();
            ucPassengerFlowDay.setInCount(oldIncout + newIncount);
            ucPassengerFlowDay.setOutCount(oldOutcount + newOutcount);
            int oldMax = oldPassengerFlowDay.getMaxValue();
            int oldMin = oldPassengerFlowDay.getMinValue();
            int avg = (oldIncout + newIncount) / (newFlow.getDateHour() + 1);
            if (oldMax < newIncount) {
                ucPassengerFlowDay.setMaxValue(newIncount);
            }
            if (oldMin > newIncount) {
                ucPassengerFlowDay.setMinValue(newIncount);
            }
            ucPassengerFlowDay.setAvgValue(avg);
        }
        return ucPassengerFlowDayRepository.saveAndFlush(ucPassengerFlowDay);
    }

    /**
     * 更新每月的客流统计信息
     *
     * @param oldPassengerFlowMonth 原有的客流信息
     * @param newFlow               最新的客流信息
     * @return 客流信息
     */
    private UcPassengerFlowMonth updateUcPassengerFlowMonth(UcPassengerFlowMonth oldPassengerFlowMonth, UcPassengerFlowDetail newFlow) {
        UcPassengerFlowMonth ucPassengerFlowMonth = null;
        if (oldPassengerFlowMonth == null) {//不存在已有记录
            ucPassengerFlowMonth = new UcPassengerFlowMonth();
            ucPassengerFlowMonth.setInCount(newFlow.getInCount());
            ucPassengerFlowMonth.setOutCount(newFlow.getOutCount());
            ucPassengerFlowMonth.setMinValue(newFlow.getInCount());
            ucPassengerFlowMonth.setMaxValue(newFlow.getInCount());
            ucPassengerFlowMonth.setAvgValue(newFlow.getInCount());
            ucPassengerFlowMonth.setShopCode(newFlow.getShopCode());
            ucPassengerFlowMonth.setShopName(newFlow.getShopName());
            ucPassengerFlowMonth.setDateYear(newFlow.getDateYear());
            ucPassengerFlowMonth.setDateMonth(newFlow.getDateMonth());
        } else {
            ucPassengerFlowMonth = oldPassengerFlowMonth;
            int oldIncout = oldPassengerFlowMonth.getInCount();
            int oldOutcount = oldPassengerFlowMonth.getOutCount();
            int newIncount = newFlow.getInCount();
            int newOutcount = newFlow.getOutCount();
            ucPassengerFlowMonth.setInCount(oldIncout + newIncount);
            ucPassengerFlowMonth.setOutCount(oldOutcount + newOutcount);
            int oldMax = oldPassengerFlowMonth.getMaxValue();
            int oldMin = oldPassengerFlowMonth.getMinValue();
            int avg = (oldIncout + newIncount) / newFlow.getDateDay();
            if (oldMax < newIncount) {
                ucPassengerFlowMonth.setMaxValue(newIncount);
            }
            if (oldMin > newIncount) {
                ucPassengerFlowMonth.setMinValue(newIncount);
            }
            ucPassengerFlowMonth.setAvgValue(avg);
        }
        return ucPassengerFlowMonthRepository.saveAndFlush(ucPassengerFlowMonth);
    }

    /**
     * 更新每年的客流统计信息
     *
     * @param oldPassengerFlowYear 原有的客流信息
     * @param newFlow              最新的客流信息
     * @return 客流信息
     */
    private UcPassengerFlowYear updateUcPassengerFlowYear(UcPassengerFlowYear oldPassengerFlowYear, UcPassengerFlowDetail newFlow) {
        UcPassengerFlowYear ucPassengerFlowYear = null;
        if (oldPassengerFlowYear == null) {//不存在已有记录
            ucPassengerFlowYear = new UcPassengerFlowYear();
            ucPassengerFlowYear.setInCount(newFlow.getInCount());
            ucPassengerFlowYear.setOutCount(newFlow.getOutCount());
            ucPassengerFlowYear.setMinValue(newFlow.getInCount());
            ucPassengerFlowYear.setMaxValue(newFlow.getInCount());
            ucPassengerFlowYear.setAvgValue(newFlow.getInCount());
            ucPassengerFlowYear.setShopCode(newFlow.getShopCode());
            ucPassengerFlowYear.setShopName(newFlow.getShopName());
            ucPassengerFlowYear.setDateYear(newFlow.getDateYear());
        } else {
            ucPassengerFlowYear = oldPassengerFlowYear;
            int oldIncout = oldPassengerFlowYear.getInCount();
            int oldOutcount = oldPassengerFlowYear.getOutCount();
            int newIncount = newFlow.getInCount();
            int newOutcount = newFlow.getOutCount();
            ucPassengerFlowYear.setInCount(oldIncout + newIncount);
            ucPassengerFlowYear.setOutCount(oldOutcount + newOutcount);
            int oldMax = oldPassengerFlowYear.getMaxValue();
            int oldMin = oldPassengerFlowYear.getMinValue();
            int avg = (oldIncout + newIncount) / newFlow.getDateMonth();
            if (oldMax < newIncount) {
                ucPassengerFlowYear.setMaxValue(newIncount);
            }
            if (oldMin > newIncount) {
                ucPassengerFlowYear.setMinValue(newIncount);
            }
            ucPassengerFlowYear.setAvgValue(avg);
        }
        return ucPassengerFlowYearRepository.saveAndFlush(ucPassengerFlowYear);
    }

    @Override
    public List<UcPassengerFlowDay> findAllShopPassenger(String type, String type1, int year, int month, int day) {
        return ucPassengerFlowDayRepository.findByShopCodeAndDateYearAndDateMonthAndDateDay(type, type1, year, month, day);
    }

    @Override
    public List<UcPassengerFlowDetail> findAllShopPassengerMinute(String type, String type1, int year, int month, int day, int hour, int minute) {
        return ucPassengerFlowDetailRepository.findByShopCodeAndDateYearAndDateMonthAndDateDay(type, type1, year, month, day, hour, minute);
    }

    @Override
    public List<UcPassengerFlowDetail> findAllShopPassengerDay(String shopName, int year, int month, int day) {
        return ucPassengerFlowDetailRepository.findByShopCodeAndDateYearAndDateMonthAndDateDay(shopName, year, month, day);
    }

    @Override
    public List<UcPassengerFlowDetail> findFlowByDay(String shopName, DateTime startDate, DateTime endDate) {
        List<UcPassengerFlowDetail> passengerFlowDetails = null;
        List<Object[]> list = ucPassengerFlowDetailRepository.findFlowByDay(shopName, startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), startDate.getHourOfDay(), endDate.getYear(), endDate.getMonthOfYear(), endDate.getDayOfMonth(), endDate.getHourOfDay());
        if (list != null) {
            passengerFlowDetails = new ArrayList(list.size());
            for (Object[] objects : list) {
                UcPassengerFlowDetail ucPassengerFlowDetail = new UcPassengerFlowDetail();
                ucPassengerFlowDetail.setShopName(objects[0] + "");
                Object countObj = objects[1];
                //sum函数查询出的类型为Long,须进行转换
                if (countObj instanceof Long) {
                    ucPassengerFlowDetail.setInCount(((Long) countObj).intValue());
                } else {
                    ucPassengerFlowDetail.setInCount((Integer) countObj);
                }
                countObj = objects[2];
                if (countObj instanceof Long) {
                    ucPassengerFlowDetail.setOutCount(((Long) countObj).intValue());
                } else {
                    ucPassengerFlowDetail.setOutCount((Integer) countObj);
                }
                ucPassengerFlowDetail.setDateHour((Integer) objects[3]);
                ucPassengerFlowDetail.setShopCode(objects[4] + "");
                ucPassengerFlowDetail.setDateYear((Integer) objects[5]);
                ucPassengerFlowDetail.setDateMonth((Integer) objects[6]);
                ucPassengerFlowDetail.setDateDay((Integer) objects[7]);
                ucPassengerFlowDetail.setCreateDate((Date) objects[8]);
                passengerFlowDetails.add(ucPassengerFlowDetail);
            }
            return passengerFlowDetails;
        }
        return null;
    }

    @Override
    public List<UcPassengerFlowDetail> findAllShopPassengerDetail(String shopType1, String shopType2, DateTime
            startDate, DateTime endDate) {
        List<UcPassengerFlowDetail> ucPassengerFlowDetailList = null;
        List<Object[]> list = ucPassengerFlowDetailRepository.findTotalPassengerFlowOrderByInCount(shopType1, shopType2, startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), startDate.getHourOfDay(), endDate.getYear(), endDate.getMonthOfYear(), endDate.getDayOfMonth(), endDate.getHourOfDay());
        if (list != null) {
            ucPassengerFlowDetailList = new ArrayList(list.size());
            for (Object[] objects : list) {
                UcPassengerFlowDetail ucPassengerFlowDetail = new UcPassengerFlowDetail();
                ucPassengerFlowDetail.setShopName(objects[0] + "");
                Object countObj = objects[1];
                //sum函数查询出的类型为Long,须进行转换
                if (countObj instanceof Long) {
                    ucPassengerFlowDetail.setInCount(((Long) countObj).intValue());
                } else {
                    ucPassengerFlowDetail.setInCount((Integer) countObj);
                }
                countObj = objects[2];
                if (countObj instanceof Long) {
                    ucPassengerFlowDetail.setOutCount(((Long) countObj).intValue());
                } else {
                    ucPassengerFlowDetail.setOutCount((Integer) countObj);
                }
                ucPassengerFlowDetail.setShopCode(objects[3] + "");
                ucPassengerFlowDetail.setCreateDate((Date) objects[4]);
                ucPassengerFlowDetailList.add(ucPassengerFlowDetail);
            }
        }
        return ucPassengerFlowDetailList;
    }

    @Override
    public List<UcPassengerFlowMonth> findShopPassengerShopName(String shopName, int year) {
        return ucPassengerFlowMonthRepository.findByShopNameAndDateYear(shopName, year);
    }

    @Override
    public List<UcPassengerFlowDay> findShopPassengerByDate(String shopName, String startDate, String endDate) {
        DateTime sd = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        DateTime ed = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        List<UcPassengerFlowDay> ucPassengerFlowDayList = ucPassengerFlowDayRepository.findByShopNameAndDate(shopName, sd.getYear(), sd.getMonthOfYear(), sd.getDayOfMonth(), ed.getYear(), ed.getMonthOfYear(), ed.getDayOfMonth());
        return ucPassengerFlowDayList;
    }

    @Override
    public Collection<UcPassengerFlowDetail> findShopTypePassengerByDate(String shopType, String shopType1, String
            startDate, String endDate) {
        DateTime sd = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        DateTime ed = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
//        List<UcPassengerFlowDetail> ucPassengerFlowDayList= ucPassengerFlowDetailRepository.findByShopTypeAndMintue(shopType, shopType1, sd.toDate(),ed.toDate());
        List<UcPassengerFlowDetail> ucPassengerFlowDayList = ucPassengerFlowDetailRepository.findByShopTypeAndMintue(shopType, shopType1, sd.getYear(), sd.getMonthOfYear(), sd.getDayOfMonth(), sd.getHourOfDay(), sd.getMinuteOfHour(), ed.getYear(), ed.getMonthOfYear(), ed.getDayOfMonth(), ed.getHourOfDay(), ed.getMinuteOfHour());
        //多条数据进行汇总
        Map<String, UcPassengerFlowDetail> hashMap = new HashMap<String, UcPassengerFlowDetail>();
        for (UcPassengerFlowDetail ucPassengerFlowDetail : ucPassengerFlowDayList) {
            if (hashMap.containsKey(ucPassengerFlowDetail.getShopCode())) {
                UcPassengerFlowDetail old = hashMap.get(ucPassengerFlowDetail.getShopCode());
                old.setInCount(old.getInCount() + ucPassengerFlowDetail.getInCount());
                old.setOutCount(old.getOutCount() + ucPassengerFlowDetail.getOutCount());
            } else {
                hashMap.put(ucPassengerFlowDetail.getShopCode(), ucPassengerFlowDetail);
            }
        }

        return hashMap.values();
    }

    @Override
    public List<UcPassengerFlowDetail> findTotalPassengerFlow(String shopType1, String shopType2, DateTime
            startDate, DateTime endDate) {
        List passengerFlowDetails = null;
        List<Object[]> list = ucPassengerFlowDetailRepository.findTotalPassengerFlow(shopType1, shopType2, startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), startDate.getHourOfDay(), endDate.getYear(), endDate.getMonthOfYear(), endDate.getDayOfMonth(), endDate.getHourOfDay());
        if (list != null) {
            passengerFlowDetails = new ArrayList(list.size());
            for (Object[] objects : list) {
                UcPassengerFlowDetail ucPassengerFlowDetail = new UcPassengerFlowDetail();
                ucPassengerFlowDetail.setShopName(objects[0] + "");
                Object countObj = objects[1];
                //sum函数查询出的类型为Long,须进行转换
                if (countObj instanceof Long) {
                    ucPassengerFlowDetail.setInCount(((Long) countObj).intValue());
                } else {
                    ucPassengerFlowDetail.setInCount((Integer) countObj);
                }
                countObj = objects[2];
                if (countObj instanceof Long) {
                    ucPassengerFlowDetail.setOutCount(((Long) countObj).intValue());
                } else {
                    ucPassengerFlowDetail.setOutCount((Integer) countObj);
                }
                ucPassengerFlowDetail.setDateHour((Integer) objects[3]);
                ucPassengerFlowDetail.setShopCode(objects[4] + "");
                ucPassengerFlowDetail.setDateYear((Integer) objects[5]);
                ucPassengerFlowDetail.setDateMonth((Integer) objects[6]);
                ucPassengerFlowDetail.setDateDay((Integer) objects[7]);
                passengerFlowDetails.add(ucPassengerFlowDetail);
            }
        }
        return passengerFlowDetails;
    }

    @Override
    public Page<UcPassengerFlowDay> findAllShopPassengerFlow(String shopType1, String shopType2, String shopType3, DateTime date, Pageable pageable) {
        return ucPassengerFlowDayRepository.findAllShopPassengerFlowByDate(shopType1, shopType2, shopType3, date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), pageable);
    }

    @Override
    public List<UcPassengerFlowDay> findAllShopPassengerFlowByMonth(String shopType, int year, int month) {
        List<UcPassengerFlowDay> ucPassengerFlowDayList = ucPassengerFlowDayRepository.findAllShopPassengerFlowByMonth(shopType, year, month);
        return ucPassengerFlowDayList;
    }

    @Override
    public Map<String, UcPassengerFlowDetail> findTotalPassengerFlowBetweenTime(String shopType1, String shopType2, DateTime yestodayStart, DateTime yestodayEnd, DateTime todayStart, DateTime todayEnd, int startHour, int twentyFourHour, int zeroHour) {
        Map<String, UcPassengerFlowDetail> map = new HashMap<String, UcPassengerFlowDetail>();

        // 获取今天当前时刻的总客流
        int t_start = todayStart.getDayOfMonth();
        int t_end = todayEnd.getDayOfMonth();
        if (t_start != t_end) { // 不是同一天
            UcPassengerFlowDetail ucPassengerFlowDetail = findTotalPassengerFlowBetweenTimes(shopType1, shopType2, todayStart.getYear(), todayStart.getMonthOfYear(), todayStart.getDayOfMonth(), startHour, twentyFourHour, shopType1, shopType2, todayEnd.getYear(), todayEnd.getMonthOfYear(), todayEnd.getDayOfMonth(), zeroHour, todayEnd.getHourOfDay());
            map.put("today", ucPassengerFlowDetail);
        } else { // 同一天
            List<Object[]> list = ucPassengerFlowDetailRepository.findTotalPassengerFlowSameDayBetweenHours(shopType1, shopType2, todayStart.getYear(), todayStart.getMonthOfYear(), t_start, todayStart.getHourOfDay(), todayEnd.getHourOfDay());
            map.put("today", getUcPassengerFlowDetail(list));
        }

        // 获取昨天当前时刻的总客流
        t_start = yestodayStart.getDayOfMonth();
        t_end = yestodayEnd.getDayOfMonth();
        if (t_start != t_end) { // 不是同一天
            UcPassengerFlowDetail ucPassengerFlowDetail = findTotalPassengerFlowBetweenTimes(shopType1, shopType2, yestodayStart.getYear(), yestodayStart.getMonthOfYear(), yestodayStart.getDayOfMonth(), startHour, twentyFourHour, shopType1, shopType2, yestodayEnd.getYear(), yestodayEnd.getMonthOfYear(), yestodayEnd.getDayOfMonth(), zeroHour, yestodayEnd.getHourOfDay());
            map.put("yesterday", ucPassengerFlowDetail);
        } else { // 同一天
            List<Object[]> list = ucPassengerFlowDetailRepository.findTotalPassengerFlowSameDayBetweenHours(shopType1, shopType2, yestodayStart.getYear(), yestodayStart.getMonthOfYear(), t_start, yestodayStart.getHourOfDay(), yestodayEnd.getHourOfDay());
            map.put("yesterday", getUcPassengerFlowDetail(list));
        }

        return map;
    }

    /**
     * 返回对应的客流数据
     *
     * @param list
     * @return
     */
    private UcPassengerFlowDetail getUcPassengerFlowDetail(List<Object[]> list) {
        UcPassengerFlowDetail ucPassengerFlowDetail = new UcPassengerFlowDetail();
        if (list != null) {
            for (Object[] objects : list) {
                Object countObj = objects[0];
                //sum函数查询出的类型为Long,须进行转换
                if (countObj instanceof Long) {
                    ucPassengerFlowDetail.setInCount(((Long) countObj).intValue());
                } else {
                    ucPassengerFlowDetail.setInCount((Integer) countObj);
                }
                countObj = objects[1];
                if (countObj instanceof Long) {
                    ucPassengerFlowDetail.setOutCount(((Long) countObj).intValue());
                } else {
                    ucPassengerFlowDetail.setOutCount((Integer) countObj);
                }
            }
        }
        return ucPassengerFlowDetail;
    }

    /**
     * 查询某个店铺不同天某个时间段内的总客流信息
     *
     * @param shopType1
     * @param shopType2
     * @param startDateYear
     * @param startDateMonth
     * @param startDateDay
     * @param startDateHour
     * @param endHour
     * @param shopType3
     * @param shopType4
     * @param endDateYear
     * @param endDateMonth
     * @param endDateDay
     * @param endDateHour1
     * @param endDateHour2
     * @return
     */
    private UcPassengerFlowDetail findTotalPassengerFlowBetweenTimes(String shopType1, String shopType2, int startDateYear, int startDateMonth, int startDateDay, int startDateHour, int endHour, String shopType3, String shopType4, int endDateYear, int endDateMonth, int endDateDay, int endDateHour1, int endDateHour2) {
        UcPassengerFlowDetail ucPassengerFlowDetail = new UcPassengerFlowDetail();
        PfeTotalRowMapper pfeTotalRowMapper = new PfeTotalRowMapper();
        String sql = "select sum(passenger.incount) as incount,sum(passenger.outcount) as outcount from ((select sum(incount) as incount, sum(outcount) as outcount from uc_passengerflowdetail ufd,uc_passengerflowshopconfig upfs where ufd.shopcode=upfs.shopcode and (upfs.shoptype="
                + shopType1
                + " or upfs.shoptype="
                + shopType2
                + ") and dateyear="
                + startDateYear
                + " and datemonth="
                + startDateMonth
                + " and dateday="
                + startDateDay
                + " and datehour>="
                + startDateHour
                + " and datehour<="
                + endHour
                + ") union all (select sum(incount)  as incount, sum(outcount) as outcount  from uc_passengerflowdetail ufd,uc_passengerflowshopconfig upfs where ufd.shopcode=upfs.shopcode and (upfs.shoptype="
                + shopType1
                + " or upfs.shoptype="
                + shopType2
                + ") and dateyear = "
                + endDateYear
                + " and datemonth="
                + endDateMonth
                + " and dateday="
                + endDateDay
                + " and datehour>="
                + endDateHour1
                + " and datehour<="
                + endDateHour2 + ")) passenger";
        logger.debug("findTotalPassengerFlowBetweenTimes----" + sql);
        List list = dataSourceTemplate.query(sql, pfeTotalRowMapper);
        if (!list.isEmpty()) {
            PfeObjectDTO pfe = (PfeObjectDTO) list.get(0);
            ucPassengerFlowDetail.setInCount(Integer.parseInt(pfe.getInCount() + ""));
            ucPassengerFlowDetail.setOutCount(Integer.parseInt(pfe.getOutCount() + ""));
        }

        return ucPassengerFlowDetail;
    }

    @Override
    public Page<UcPassengerFlowDetail> findTotalPassengerFlowDetail(String shopName, Pageable pageable) {
        return ucPassengerFlowDetailRepository.findTotalPassengerFlowDetail(shopName, pageable);
    }
}