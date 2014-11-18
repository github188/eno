package com.energicube.eno.calendar.service;

import com.energicube.eno.calendar.model.UcWeather;
import com.energicube.eno.monitor.model.Event;
import com.energicube.eno.monitor.model.EventType;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-7-2
 * Time: 上午10:44
 * 日历的服务
 */

public interface CalendarService {

    /**
     * 保存事件
     *
     * @param event 事件实体
     * @return
     */
    public Event saveEvent(Event event);

    /**
     * 保存事件类型
     *
     * @param eventType 事件类型实体
     * @return
     */
    public EventType saveEventType(EventType eventType);

    /**
     * 查询所有事件类型
     *
     * @return
     */
    public List<EventType> findEventTypeByAll();

    /**
     * 查询某类型所有事件
     *
     * @param typeId    事件类型ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public List<Event> findEventByEventTypeAndStartDateAndEndDate(long typeId, Date startDate, Date endDate);

    /**
     * 时间段来查询事件
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public List<Event> findEventByStartDateAndEndDate(Date startDate, Date endDate);


    /**
     * 查询某类型所有事件
     *
     * @param typeId 事件类型ID
     * @return
     */
    public List<Event> findEventByEventType(long typeId);


    /**
     * 查询所有事件
     *
     * @return
     */
    public List<Event> findEventAll();

    /**
     * 删除事件
     *
     * @param id 事件的ID
     */
    public void deleteEvent(String id);

    /**
     * 清除事件信息
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void clearEvent(String startDate, String endDate);

    /**
     * 清除活动信息
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void clearEvent(String id, String startDate, String endDate);

    /**
     * 查询某天的天气信息
     * @param calendarId 日期
     * @return 天气信息
     */
    public List<UcWeather> findUcWeatherByCalendarId(String calendarId);
}
