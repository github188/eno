package com.energicube.eno.monitor.repository;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.energicube.eno.monitor.model.UcPassengerFlowDetail;

import java.util.Date;
import java.util.List;

/**
 * 客流的存储接口
 * Created by LiuGuanglu
 * 2014/5/23.
 */
public interface UcPassengerFlowDetailRepository extends JpaRepository<UcPassengerFlowDetail, String> {


    /**
     * 查询时间段内所有店铺的客流
     *
     * @param startDateYear  开始年份
     * @param startDateMonth 开始日期月份
     * @param startDateDay   开始日期的日
     * @param startDateHour  开始日期的小时
     * @param startDateMinute 开始日期的分钟
     * @param endDateYear    结束年份
     * @param endDateMonth   结束日期月份
     * @param endDateDay     结束日期的日
     * @param endDateHour    结束日期的小时
     * @param endDateMinute 结束日期的分钟
     * @return 店铺客流集合
     */
    @Query("select ufd from UcPassengerFlowDetail ufd where ufd.dateYear>=?1 and ufd.dateYear<=?6 and ufd.dateMonth>=?2 and ufd.dateMonth<=?7 and ufd.dateDay>=?3 and ufd.dateDay<=?8 and ufd.dateHour>=?4 and ufd.dateHour<=?9 and ufd.dateMinute>=?5 and ufd.dateMinute<?10")
    public List<UcPassengerFlowDetail> findByDate(int startDateYear,int startDateMonth,int startDateDay,int startDateHour,int startDateMinute,int endDateYear,int endDateMonth,int endDateDay,int endDateHour,int endDateMinute);


    /**
     * 查询某类店铺的某时刻的客流情况
     *
     * @param shopType  店铺编码
     * @param shopType2 店铺编码
     * @param dateYear  年
     * @param dateMonth 月
     * @param dateDay   日
     * @return 店铺的客流信息
     */
    @Query("select ufd from UcPassengerFlowDetail ufd,UcPassengerFlowShopConfig upfs where ufd.shopCode=upfs.shopCode and (upfs.shopType=?1 or upfs.shopType=?2) and ufd.dateYear=?3 and ufd.dateMonth=?4 and ufd.dateDay=?5 and ufd.dateHour=?6 and ufd.dateMinute=?7 order by ufd.inCount desc")
    public List<UcPassengerFlowDetail> findByShopCodeAndDateYearAndDateMonthAndDateDay(String shopType, String shopType2, int dateYear, int dateMonth, int dateDay, int dateHour, int dateMinute);

    /**
     * 查询某类店铺的某时刻的客流情况
     *
     * @param shopName  店铺名称
     * @param dateYear  年
     * @param dateMonth 月
     * @param dateDay   日
     * @return 店铺的客流信息
     */
    @Query("select ufd.shopName,sum (ufd.inCount) as inCount,sum(ufd.outCount) as outCount, ufd.dateHour,ufd.shopCode,ufd.dateYear,ufd.dateMonth,ufd.dateDay from UcPassengerFlowDetail ufd where ufd.shopName=?1  and ufd.dateYear=?2 and ufd.dateMonth=?3 and ufd.dateDay=?4  order by ufd.inCount desc")
    public List<UcPassengerFlowDetail> findByShopCodeAndDateYearAndDateMonthAndDateDay(String shopName, int dateYear, int dateMonth, int dateDay);

    /**
     * 查询某类店铺的某时刻的客流情况
     *
     * @param shopName       店铺名称
     * @param startDateYear  开始年份
     * @param startDateMonth 开始日期月份
     * @param startDateDay   开始日期的日
     * @param startDateHour  开始日期的小时
     * @param endDateYear    结束年份
     * @param endDateMonth   结束日期月份
     * @param endDateDay     结束日期的日
     * @param endDateHour    结束日期的小时
     * @return 店铺的客流信息
     */
    @Query("select ufd.shopName,sum (ufd.inCount) as inCount,sum(ufd.outCount) as outCount, ufd.dateHour,ufd.shopCode,ufd.dateYear,ufd.dateMonth,ufd.dateDay,ufd.createDate from UcPassengerFlowDetail ufd,UcPassengerFlowShopConfig upfs where ufd.shopCode=upfs.shopCode and ufd.shopName=?1 and ((ufd.dateYear=?2 and ufd.dateMonth=?3 and ufd.dateDay=?4 and ufd.dateHour>=?5 ) or (ufd.dateYear=?6 and ufd.dateMonth=?7 and ufd.dateDay=?8 and ufd.dateHour<=?9)) group by ufd.dateYear,ufd.dateMonth,ufd.dateDay,ufd.dateHour,ufd.shopName,ufd.shopCode,ufd.createDate order by ufd.createDate asc")
    public List<Object[]> findFlowByDay(String shopName, int startDateYear, int startDateMonth, int startDateDay, int startDateHour, int endDateYear, int endDateMonth, int endDateDay, int endDateHour);

    /**
     * 查询某类店铺的某时刻的客流情况
     *
     * @param shopType  店铺类型
     * @param shopType1 店铺类型
     * @param dateYear  年
     * @param dateMonth 月
     * @param dateDay   日
     * @return 店铺的客流信息
     */
    @Query("select ufd.shopName,sum (ufd.inCount) as inCount,sum(ufd.outCount) as outCount,ufd.shopCode,ufd.createDate from UcPassengerFlowDetail ufd,UcPassengerFlowShopConfig upfs where ufd.shopCode=upfs.shopCode and (upfs.shopType=?1 or upfs.shopType=?2)  and ufd.dateYear=?3 and ufd.dateMonth=?4 and ufd.dateDay=?5 group by ufd.dateYear,ufd.dateMonth,ufd.dateDay,ufd.dateHour,ufd.shopName,ufd.shopCode,ufd.createDate order by inCount desc")
    public List<Object[]> findByShopTypeAndDateYearAndDateMonthAndDateDay(String shopType, String shopType1, int dateYear, int dateMonth, int dateDay);
    /**
     * 查询某类店铺的某天的客流情况
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
    @Query("select ufd from UcPassengerFlowDetail ufd,UcPassengerFlowShopConfig upfs where ufd.shopCode=upfs.shopCode and (upfs.shopType=?1 or upfs.shopType=?2 ) and ((ufd.dateYear>=?3 and ufd.dateMonth>=?4 and ufd.dateDay>=?5 and ufd.dateHour>=?6 and ufd.dateMinute>=?7 ) or (ufd.dateYear<=?8 and ufd.dateMonth<=?9  and ufd.dateDay<=?10 and ufd.dateHour<=?11 and ufd.dateMinute<=?12 ))order by ufd.inCount desc")
    public List<UcPassengerFlowDetail> findByShopTypeAndMintue(String shopType, String shopType1, int dateYear, int dateMonth, int dateDay, int dateHour, int dateMinute, int endDateYear, int endDateMonth, int endDateDay, int endDateHour, int endDateMinute);

    /**
     * @param shopType1      店铺类型
     * @param shopType2      店铺类型
     * @param startDateYear  开始年份
     * @param startDateMonth 开始日期月份
     * @param startDateDay   开始日期的日
     * @param startDateHour  开始日期的小时
     * @param endDateYear    结束年份
     * @param endDateMonth   结束日期月份
     * @param endDateDay     结束日期的日
     * @param endDateHour    结束日期的小时
     * @return
     */
    @Query("select ufd.shopName,sum (ufd.inCount) as inCount,sum(ufd.outCount) as outCount, ufd.dateHour,ufd.shopCode,ufd.dateYear,ufd.dateMonth,ufd.dateDay from UcPassengerFlowDetail ufd,UcPassengerFlowShopConfig upfs where ufd.shopCode=upfs.shopCode and (upfs.shopType=?1 or upfs.shopType=?2) and ((ufd.dateYear=?3 and ufd.dateMonth=?4 and ufd.dateDay=?5 and ufd.dateHour>=?6 ) or (ufd.dateYear=?7 and ufd.dateMonth=?8 and ufd.dateDay=?9 and ufd.dateHour<=?10)) group by ufd.dateYear,ufd.dateMonth,ufd.dateDay,ufd.dateHour,ufd.shopName,ufd.shopCode order by ufd.dateYear asc,ufd.dateMonth asc,ufd.dateDay asc, ufd.dateHour asc")
    public List<Object[]> findTotalPassengerFlow(String shopType1, String shopType2, int startDateYear, int startDateMonth, int startDateDay, int startDateHour, int endDateYear, int endDateMonth, int endDateDay, int endDateHour);
    /**
     * @param shopType1      店铺类型
     * @param shopType2      店铺类型
     * @param startDateYear  开始年份
     * @param startDateMonth 开始日期月份
     * @param startDateDay   开始日期的日
     * @param startDateHour  开始日期的小时
     * @param endDateYear    结束年份
     * @param endDateMonth   结束日期月份
     * @param endDateDay     结束日期的日
     * @param endDateHour    结束日期的小时
     * @return
     */
    @Query("select ufd.shopName,sum (ufd.inCount) as inCount,sum(ufd.outCount) as outCount,ufd.shopCode,ufd.createDate from UcPassengerFlowDetail ufd,UcPassengerFlowShopConfig upfs where ufd.shopCode=upfs.shopCode and (upfs.shopType=?1 or upfs.shopType=?2) and ((ufd.dateYear=?3 and ufd.dateMonth=?4 and ufd.dateDay=?5 and ufd.dateHour>=?6 ) or (ufd.dateYear=?7 and ufd.dateMonth=?8 and ufd.dateDay=?9 and ufd.dateHour<=?10)) group by ufd.dateYear,ufd.dateMonth,ufd.dateDay,ufd.dateHour,ufd.shopName,ufd.shopCode,ufd.createDate order by ufd.createDate asc")
    public List<Object[]> findTotalPassengerFlowOrderByInCount(String shopType1, String shopType2, int startDateYear, int startDateMonth, int startDateDay, int startDateHour, int endDateYear, int endDateMonth, int endDateDay, int endDateHour);

    /**
     * 查询某个店铺同一天某个时间段内的总客流信息
     * @param shopType1
     * @param shopType2
     * @param Year
     * @param month
     * @param day
     * @param startDateHour
     * @param endDateHour
     * @return
     */
    @Query("select sum (ufd.inCount) as inCount,sum(ufd.outCount) as outCount from UcPassengerFlowDetail ufd,UcPassengerFlowShopConfig upfs where ufd.shopCode=upfs.shopCode and (upfs.shopType=?1 or upfs.shopType=?2) and ufd.dateYear=?3 and ufd.dateMonth=?4 and ufd.dateDay=?5 and ufd.dateHour>=?6 and ufd.dateHour<=?7 ")
    public List<Object[]> findTotalPassengerFlowSameDayBetweenHours(String shopType1, String shopType2, int Year, int month, int day, int startDateHour, int endDateHour);


    @Query("select ufd from UcPassengerFlowDetail ufd where ufd.shopName=?1")
    public Page<UcPassengerFlowDetail> findTotalPassengerFlowDetail(String shopName,Pageable pageable);
}
