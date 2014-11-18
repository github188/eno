package com.energicube.eno.calendar.service;

import com.energicube.eno.calendar.model.UcWeather;
import com.energicube.eno.calendar.repository.UcHolidayRepository;
import com.energicube.eno.calendar.repository.UcWeatherRepository;
import com.energicube.eno.monitor.model.Event;
import com.energicube.eno.monitor.model.EventType;
import com.energicube.eno.monitor.repository.EventRepository;
import com.energicube.eno.monitor.repository.EventTypeRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-7-2
 * Time: 上午10:46
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CalendarServiceImpl implements CalendarService {

    private static Log logger = LogFactory.getLog(CalendarServiceImpl.class);

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    UcWeatherRepository ucWeatherRepository;

    @Autowired
    UcHolidayRepository ucHolidayRepository;

    public Event saveEvent(Event event) {
        return eventRepository.saveAndFlush(event);
    }


    public EventType saveEventType(EventType eventType) {
        return eventTypeRepository.save(eventType);
    }


    public List<EventType> findEventTypeByAll() {
        return eventTypeRepository.findAll();
    }


    public List<Event> findEventByEventTypeAndStartDateAndEndDate(long typeId, Date startDate, Date endDate) {
        return eventRepository.findByEventTypeAndStartDateAndEndDate(typeId, startDate, endDate);
    }


    public List<Event> findEventByStartDateAndEndDate(Date startDate, Date endDate) {
        return eventRepository.findByTime(startDate, endDate);
    }


    public List<Event> findEventByEventType(long typeId) {
        return eventRepository.findByEventType_TypeId(typeId);
    }


    public List<Event> findEventAll() {
        return eventRepository.findAll();
    }


    public void deleteEvent(String id) {
        eventRepository.delete(Long.parseLong(id));
    }


    public void clearEvent(String startDate, String endDate) {
        Date sdate = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyyMMdd")).toDate();
        Date beforDate = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyyMMdd")).plusDays(-1).toDate();
        Date edate = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyyMMdd")).toDate();
        Date aferDate = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyyMMdd")).plusDays(1).toDate();
        List<Event> eventList = eventRepository.findByEventTypeAndStartDateAndEndDate(18, beforDate, aferDate);
        logger.debug("---------eventList-------" + eventList.size());
        for (Event event : eventList) {
            Date sd = event.getStartDate();
            Date ed = event.getEndDate();
            if (sd.before(sdate)) {
                logger.debug("---------sd-------" + sd.toString());
                logger.debug("----------sdate------" + sdate.toString());
                //开始时间之前也有此事件。
                Event event1 = new Event();
                BeanUtils.copyProperties(event, event1);
                event1.setId(0);
                event1.setEndDate(beforDate);
                eventRepository.save(event1);
            }
            if (ed.after(edate)) {
                Event event1 = new Event();
                BeanUtils.copyProperties(event, event1);
                event1.setId(0);
                event1.setStartDate(aferDate);
                eventRepository.save(event1);
            }
            eventRepository.delete(event);
        }
    }


    public void clearEvent(String id, String startDate, String endDate) {
        Date sdate = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyyMMdd")).toDate();
        Date beforDate = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyyMMdd")).plusDays(-1).toDate();
        Date edate = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyyMMdd")).toDate();
        Date aferDate = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyyMMdd")).plusDays(1).toDate();
        List<Event> eventList = eventRepository.findByEventTypeAndStartDateAndEndDate(18, beforDate, aferDate);
        logger.debug("---------eventList-------" + eventList.size());
        for (Event event : eventList) {

            String eId = event.getId() + "";
            if (eId.equals(id)) {
                Date sd = event.getStartDate();
                Date ed = event.getEndDate();
                if (sd.before(sdate)) {
                    logger.debug("---------sd-------" + sd.toString());
                    logger.debug("----------sdate------" + sdate.toString());
                    //开始时间之前也有此事件。
                    Event event1 = new Event();
                    BeanUtils.copyProperties(event, event1);
                    event1.setId(0);
                    event1.setEndDate(beforDate);
                    eventRepository.save(event1);
                }
                if (ed.after(edate)) {
                    Event event1 = new Event();
                    BeanUtils.copyProperties(event, event1);
                    event1.setId(0);
                    event1.setStartDate(aferDate);
                    eventRepository.save(event1);
                }
                eventRepository.delete(event);
            }
        }
    }


    @Override
    public List<UcWeather> findUcWeatherByCalendarId(String calendarId) {
        return ucWeatherRepository.findByUcCalendar_Id(calendarId);
    }
}
