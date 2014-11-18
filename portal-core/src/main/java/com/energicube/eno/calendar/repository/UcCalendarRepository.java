package com.energicube.eno.calendar.repository;

import com.energicube.eno.calendar.model.UcCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-8
 * Time: 下午12:38
 * To change this template use File | Settings | File Templates.
 */
public interface UcCalendarRepository extends JpaRepository<UcCalendar, String> {


    /**
     * 查询时间段内的日历
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public List<UcCalendar> findByIdBetweenOrderByIdAsc(String startDate, String endDate);
}
