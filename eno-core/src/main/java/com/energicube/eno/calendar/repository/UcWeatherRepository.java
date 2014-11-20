package com.energicube.eno.calendar.repository;


import com.energicube.eno.calendar.model.UcWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-7-11
 * Time: 上午11:30
 * To change this template use File | Settings | File Templates.
 */
public interface UcWeatherRepository extends JpaRepository<UcWeather, String> {


    /**
     * 通过日历查询天气
     *
     * @param calendarId 日历Id
     * @return
     */
    public List<UcWeather> findByUcCalendar_Id(String calendarId);

    /**
     * 查询某段时间的天气
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public List<UcWeather> findByUcCalendar_IdBetween(String startDate, String endDate);


    @Query(" from UcWeather a where a.ucCalendar.id >=?1 and a.isNew=?2  order by ucCalendar.id")
    public List<UcWeather> findByUcCalendar(String calendarId, String isNew);


    /**
     * 查询最新的天气信息
     *
     * @param calendarId 日期
     * @param isNew      是否最新  Y-是  N-否
     * @return 天气信息
     */
    @Query(" from UcWeather a where a.ucCalendar.id =?1 and a.isNew=?2 ")
    public UcWeather findByUcCalendar_IdAndIsNew(String calendarId, String isNew);

    /**
     * 查询某段时间的天气
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @Query(" from UcWeather a where a.ucCalendar.id >=?1 and a.ucCalendar.id <=?2 and a.isNew=?3 ")
    public List<UcWeather> findByUcCalendar_IdBetweenAnd_IsNew(String startDate, String endDate, String isNew);
}
