package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {


    /**
     * 查询某类型在某段时间所有事件
     *
     * @param typeId    事件类型的ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @Query(" from Event a where a.eventType.typeId = :id and ( (a.startDate>:sd and a.startDate<:ed )or (a.endDate>:sd and a.endDate<:ed ) or (a.startDate<:sd and a.endDate>:ed )) order by a.startDate")
    public List<Event> findByEventTypeAndStartDateAndEndDate(@Param("id") long typeId, @Param("sd") Date startDate, @Param("ed") Date endDate);

    /**
     * 时间段来查询事件
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @Query(" from Event a where  (a.startDate>:sd and a.startDate<:ed )or (a.endDate>:sd and a.endDate<:ed ) or (a.startDate<:sd and a.endDate>:ed ) order by a.startDate")
    public List<Event> findByTime(@Param("sd") Date startDate, @Param("ed") Date endDate);


    /**
     * 查询某类型所有事件
     *
     * @param typeId 事件类型的ID
     * @return
     */
    public List<Event> findByEventType_TypeId(long typeId);

    /**
     * 查询活动
     *
     * @param date
     * @return
     */
    @Query(" from Event a where a.startDate >=?1")
    public List<Event> findByEventByDate(Date date);


}
