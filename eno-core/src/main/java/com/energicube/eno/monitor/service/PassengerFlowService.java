package com.energicube.eno.monitor.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.energicube.eno.monitor.model.UcPassengerFlowDay;
import com.energicube.eno.monitor.model.UcPassengerFlowDetail;
import com.energicube.eno.monitor.model.UcPassengerFlowMonth;
import com.energicube.eno.monitor.model.UcPassengerFlowShopConfig;

/**
 * 客流数据服务接口
 * 用于客流数据的获取、向外提供数据。
 *
 * @author LiuGuanglu
 * @date 2014/5/25.
 */
public interface PassengerFlowService {


    /**
     * 根据是否激活查询店铺
     *
     * @param action 是否激活
     * @return 店铺的集合
     */
    public List<UcPassengerFlowShopConfig> findAllShopConfig(String action);

    /**
     * 保存客流信息
     *
     * @param ucPassengerFlow 客流对象
     * @return 客流对象
     */
    public UcPassengerFlowDetail saveUcPassengerFlow(UcPassengerFlowDetail ucPassengerFlow);

    /**
     * 查询一段时间所有店铺的客流信息
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 所有店铺的客流信息
     */
    public List<UcPassengerFlowDetail> findAllShopPassenger(DateTime startDate, DateTime endDate);

    /**
     * 保存店铺客流信息，同进统计客流情况
     *
     * @param ucPassengerFlow 店铺客流信息
     * @return 客流信息
     */
    public UcPassengerFlowDetail saveUcPassengerFlowAndStatistic(UcPassengerFlowDetail ucPassengerFlow);

    /**
     * 查询店铺某天的实时客流
     *
     * @param type  店铺类型1
     * @param type1 店铺类型2
     * @param year  年
     * @param month 月
     * @param day   日
     * @return
     */
    public List<UcPassengerFlowDay> findAllShopPassenger(String type, String type1, int year, int month, int day);

    /**
     * 查询店铺某天的某时刻的客流
     *
     * @param type  店铺类型
     * @param year  年
     * @param month 月
     * @param day   日
     * @return
     */
    public List<UcPassengerFlowDetail> findAllShopPassengerMinute(String type, String type1, int year, int month, int day, int hour, int minute);

    /**
     * 查询店铺某天的详细的客流
     *
     * @param shopName 店铺类型
     * @param year     年
     * @param month    月
     * @param day      日
     * @return
     */
    public List<UcPassengerFlowDetail> findAllShopPassengerDay(String shopName, int year, int month, int day);

    /**
     * 查询店铺某天的详细的客流
     *
     * @param shopName  店铺名称
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public List<UcPassengerFlowDetail> findFlowByDay(String shopName, DateTime startDate, DateTime endDate);

    /**
     * 按照inCount倒序排序查询某店铺某天的客流
     *
     * @param shopType1 店铺类型
     * @param shopType2 店铺类型
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public List<UcPassengerFlowDetail> findAllShopPassengerDetail(String shopType1, String shopType2, DateTime
            startDate, DateTime endDate);

    /**
     * 查询店铺某天的详细的客流
     *
     * @param shopName 店铺名称
     * @param year     年
     * @return
     */
    public List<UcPassengerFlowMonth> findShopPassengerShopName(String shopName, int year);

    /**
     * 查询一段时间所有店铺的客流信息
     *
     * @param shopName  店铺名称
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 所有店铺的客流信息
     */
    public List<UcPassengerFlowDay> findShopPassengerByDate(String shopName, String startDate, String endDate);

    /**
     * 查询一段时间某店铺类型的客流信息
     *
     * @param shopType  店铺类型
     * @param shopType1 店铺类型
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 所有店铺的客流信息
     */
    public Collection<UcPassengerFlowDetail> findShopTypePassengerByDate(String shopType, String shopType1, String startDate, String endDate);

    /**
     * @param shopType1 店铺类型
     * @param shopType2 店铺类型
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public List<UcPassengerFlowDetail> findTotalPassengerFlow(String shopType1, String shopType2, DateTime startDate, DateTime endDate);

    /**
     * 查询当前店铺客流
     *
     * @param shopType1 店铺类型
     * @param shopType2 店铺类型
     * @param shopType3 店铺类型
     * @param date      查询时间
     * @param pageable
     * @return
     */
    public Page<UcPassengerFlowDay> findAllShopPassengerFlow(String shopType1, String shopType2, String shopType3, DateTime date, Pageable pageable);

    /**
     * 查询总客流每月的数据
     *
     * @param shopType
     * @param year
     * @param month
     * @return
     */
    public List<UcPassengerFlowDay> findAllShopPassengerFlowByMonth(String shopType, int year, int month);

    /**
     * 查询一段时间的客流数据
     *
     * @param shopType1
     * @param shopType2
     * @param yestodayStart
     * @param yestodayEnd
     * @param todayStart
     * @param todayEnd
     * @param startHour
     * @param twentyFourHour
     * @param zeroHour
     * @return
     */
    public Map<String, UcPassengerFlowDetail> findTotalPassengerFlowBetweenTime(String shopType1, String shopType2, DateTime yestodayStart, DateTime yestodayEnd, DateTime todayStart, DateTime todayEnd, int startHour, int twentyFourHour, int zeroHour);

    /**
     * 查询当前总客流信息
     *
     * @param shopName 总客流名称
     * @param pageable
     * @return
     */
    public Page<UcPassengerFlowDetail> findTotalPassengerFlowDetail(String shopName, Pageable pageable);
}