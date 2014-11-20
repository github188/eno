package com.energicube.eno.calendar.repository;

import com.energicube.eno.calendar.model.UcHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-8
 * Time: 上午11:30
 * To change this template use File | Settings | File Templates.
 */
public interface UcHolidayRepository extends JpaRepository<UcHoliday, String> {

    /**
     * 查询某段时间的天气
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public List<UcHoliday> findByUcCalendar_IdBetween(String startDate, String endDate);


    @Query(" from UcHoliday a where a.ucCalendar.id >=?1  order by ucCalendar.id")
    public List<UcHoliday> findByUcCalendarId(String calendarId);

}
