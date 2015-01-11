package com.energicube.eno.monitor.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.energicube.eno.monitor.model.UcPassengerFlowDay;

import java.util.List;

/**
 * 客流的存储接口
 * Created by LiuGuanglu
 * 2014/5/23.
 */
public interface UcPassengerFlowDayRepository extends JpaRepository<UcPassengerFlowDay, String> {

    /**
     * 查询店铺的某天的客流情况
     *
     * @param shopCode  店铺编码
     * @param dateYear  年
     * @param dateMonth 月
     * @param dateDay   日
     * @return 店铺的客流信息
     */
    public List<UcPassengerFlowDay> findByShopCodeAndDateYearAndDateMonthAndDateDay(String shopCode, int dateYear, int dateMonth, int dateDay);

    /**
     * 查询某类店铺的某天的客流情况
     *
     * @param shopType  店铺类型编码1
     * @param shopType1 店铺类型编码2
     * @param dateYear  年
     * @param dateMonth 月
     * @param dateDay   日
     * @return 店铺的客流信息
     */
    @Query("select ufd from UcPassengerFlowDay ufd,UcPassengerFlowShopConfig upfs where ufd.shopCode=upfs.shopCode and (upfs.shopType=?1 or upfs.shopType=?2) and ufd.dateYear=?3 and ufd.dateMonth=?4  and ufd.dateDay=?5 order by (ufd.inCount - ufd.outCount) desc")
    public List<UcPassengerFlowDay> findByShopCodeAndDateYearAndDateMonthAndDateDay(String shopType, String shopType1, int dateYear, int dateMonth, int dateDay);


    /**
     * 查询某店铺的某天的客流情况
     *
     * @param shopName     店铺名称
     * @param dateYear     开始年
     * @param dateMonth    开始月
     * @param dateDay      开始日
     * @param endDateYear  结束年
     * @param endDateMonth 结束月
     * @param endDateDay   结束日
     * @return
     */
    @Query("select ufd from UcPassengerFlowDay ufd where ufd.shopName=?1 and ((ufd.dateYear>=?2 and ufd.dateMonth>=?3 and ufd.dateDay>=?4) or (ufd.dateYear<=?5 and ufd.dateMonth<=?6 and ufd.dateDay<=?7 )) order by ufd.inCount desc")
    public List<UcPassengerFlowDay> findByShopNameAndDate(String shopName, int dateYear, int dateMonth, int dateDay, int endDateYear, int endDateMonth, int endDateDay);

    /**
     * 查询某店铺的某天的客流情况
     *
     * @param shopType     店铺类型
     * @param dateYear     开始年
     * @param dateMonth    开始月
     * @param dateDay      开始日
     * @param endDateYear  结束年
     * @param endDateMonth 结束月
     * @param endDateDay   结束日
     * @return
     */
    @Query("select ufd from UcPassengerFlowDay ufd,UcPassengerFlowShopConfig upfs where ufd.shopCode=upfs.shopCode and upfs.shopType=?1  and ((ufd.dateYear>=?2 and ufd.dateMonth>=?3 and ufd.dateDay>=?4) or (ufd.dateYear<=?5 and ufd.dateMonth<=?6  and  ufd.dateDay<=?7 )) order by ufd.inCount desc")
    public List<UcPassengerFlowDay> findByShopTypeAndDate(String shopType, int dateYear, int dateMonth, int dateDay, int endDateYear, int endDateMonth, int endDateDay);

    /**
     * 查询某店铺的某天的客流情况
     *
     * @param shopType     店铺类型
     * @param shopType1    店铺类型
     * @param dateYear     开始年
     * @param dateMonth    开始月
     * @param dateDay      开始日
     * @param endDateYear  结束年
     * @param endDateMonth 结束月
     * @param endDateDay   结束日
     * @return
     */
    @Query("select ufd from UcPassengerFlowDay ufd,UcPassengerFlowShopConfig upfs where ufd.shopCode=upfs.shopCode and (upfs.shopType=?1 or upfs.shopType=?2 ) and ((ufd.dateYear>=?3 and ufd.dateMonth>=?4 and ufd.dateDay>=?5)or (ufd.dateYear<=?6 and ufd.dateMonth<=?7  and  ufd.dateDay<=?8 )) order by ufd.inCount desc")
    public List<UcPassengerFlowDay> findByShopTypeAndDate(String shopType, String shopType1, int dateYear, int dateMonth, int dateDay, int endDateYear, int endDateMonth, int endDateDay);

    /**
     * 查询当前店铺客流
     *
     * @param shopType  店铺类型
     * @param shopType1 店铺类型
     * @param shopType2 店铺类型
     * @param dateYear  年
     * @param dateMonth 月
     * @param dateDay   日
     * @param pageable
     * @return
     */
    @Query("select ufd from UcPassengerFlowDay ufd,UcPassengerFlowShopConfig upfs where ufd.shopCode=upfs.shopCode and (upfs.shopType=?1 or upfs.shopType=?2 or upfs.shopType=?3) and ufd.dateYear=?4 and ufd.dateMonth=?5 and ufd.dateDay=?6")
    public Page<UcPassengerFlowDay> findAllShopPassengerFlowByDate(String shopType, String shopType1, String shopType2, int dateYear, int dateMonth, int dateDay, Pageable pageable);

    /**
     * 查询总客流每月的数据
     * @param shopType
     * @param dateYear
     * @param dateMonth
     * @return
     */
    @Query("select ufd from UcPassengerFlowDay ufd,UcPassengerFlowShopConfig upfs where ufd.shopCode=upfs.shopCode and upfs.shopType=?1 and ufd.dateYear=?2 and ufd.dateMonth=?3")
    public List<UcPassengerFlowDay> findAllShopPassengerFlowByMonth(String shopType, int dateYear, int dateMonth);
}