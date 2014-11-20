package com.energicube.eno.pattern.repository;

import com.energicube.eno.pattern.model.UcPatternRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-8
 * Time: 上午11:26
 * To change this template use File | Settings | File Templates.
 */
public interface UcPatternRecordRepository extends JpaRepository<UcPatternRecord, String> {


    /**
     * 通过日历查询这天运行的模式
     *
     * @param calendarId 日历ID
     * @return
     */
    public List<UcPatternRecord> findByUcCalendar_Id(String calendarId);


    /**
     * 通过子系统ID和日历查询模式记录
     *
     * @param systemId   子系统ID
     * @param calendarId 日历ID
     * @return
     */
    public List<UcPatternRecord> findBySystemIdAndUcCalendar_Id(String systemId, String calendarId);


    /**
     * 查询某子系统在某段时间内运行的模式
     *
     * @param systemId  子系统ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public List<UcPatternRecord> findBySystemIdAndUcCalendar_IdGreaterThanAndUcCalendar_IdLessThanOrderByUcCalendar_IdAsc(String systemId, String startDate, String endDate);

    /**
     * 查询在某段时间内运行的模式
     *
     * @param patternType 模式类型
     * @param startDate   开始时间
     * @param endDate     结束时间
     * @return
     */
    public List<UcPatternRecord> findByPatternTypeAndUcCalendar_IdGreaterThanAndUcCalendar_IdLessThanOrderByUcCalendar_IdAsc(String patternType, String startDate, String endDate);


    /**
     * 查询在某段时间内运行的模式
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public List<UcPatternRecord> findByUcCalendar_IdBetweenOrderByUcCalendar_IdAsc(String startDate,String endDate);
}
