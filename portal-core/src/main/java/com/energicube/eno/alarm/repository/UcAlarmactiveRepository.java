package com.energicube.eno.alarm.repository;

import com.energicube.eno.alarm.model.UcAlarmactive;
import com.energicube.eno.alarm.model.UcAlarmlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UcAlarmactiveRepository extends
        JpaRepository<UcAlarmactive, Integer> {


    @Query("select a from UcAlarmactive a where a.almpriority < 800 and a.almpriority = ?1 and YEAR(a.almt) = ?2 and MONTH(a.almt)=?3 and DAY(a.almt)=?4 ")
    public Page<UcAlarmactive> findByAlarmpriorityAndAlmt(int alarmpriority, int yearNow, int monthNow, int dateNow, Pageable pageable);

    @Query("select a from UcAlarmactive a where a.almpriority < 800 and YEAR(a.almt) = ?1 and MONTH(a.almt)=?2 and DAY(a.almt)=?3")
    public Page<UcAlarmactive> findAlarmpriorityByAlmt(int yearNow, int monthNow, int dateNow, Pageable pageable);

    @Query("select a from UcAlarmactive a where a.almpriority < 800 and a.almpriority = ?1 and YEAR(a.almt) = ?2 and MONTH(a.almt)=?3 and DAY(a.almt)=?4 and a.groupname=?5")
    public Page<UcAlarmactive> findAlarmpriorityAndAlmtAndGroupname(int alarmpriority, int yearNow, int monthNow, int dateNow, String groupname, Pageable pageable);

    @Query("select a from UcAlarmactive a where a.almpriority < 800 and YEAR(a.almt) = ?1 and MONTH(a.almt)=?2 and DAY(a.almt)=?3 and a.groupname=?4")
    public Page<UcAlarmactive> findAlarmpriorityByAlmtAndGroupname(int yearNow, int monthNow, int dateNow, String groupname, Pageable pageable);

    @Query("select a from UcAlarmactive a where a.almpriority < 800 and groupname = ?1")
    public List<UcAlarmactive> findByGroupname(String groupname);

    @Query("select a from UcAlarmactive a where a.almpriority < 800 and YEAR(a.almt) = ?1 and MONTH(a.almt)=?2 and DAY(a.almt)=?3")
    public List<UcAlarmactive> findAlarmpriorityByAlmt(int yearNow, int monthNow, int dateNow);

    @Query("select a from UcAlarmactive a where a.almpriority < 800 and YEAR(a.almt) = ?1 and MONTH(a.almt)=?2 and DAY(a.almt)=?3")
    public Page<UcAlarmactive> findAlarmpriorityByAlmtAndPage(int yearNow, int monthNow, int dateNow, Pageable pageable);

    @Query("select a from UcAlarmactive a where a.almpriority < 800 and YEAR(a.almt) = ?1 and MONTH(a.almt)=?2 and DAY(a.almt)=?3 and a.groupname=?4")
    public List<UcAlarmactive> findAlarmpriorityByAlmtAndGroupname(int yearNow, int monthNow, int dateNow, String groupname);

    @Query("select a from UcAlarmactive a where a.almpriority < 800 and YEAR(a.almt) = ?1 and MONTH(a.almt)=?2 and DAY(a.almt)=?3 and a.groupname=?4")
    public Page<UcAlarmactive> findAlmtAndGroupname(int yearNow, int monthNow, int dateNow, String groupname, Pageable pageable);

    @Query("select a from UcAlarmlog a where a.almpriority < 800 and YEAR(a.almt) = ?1 and MONTH(a.almt)=?2 and DAY(a.almt)=?3")
    public List<UcAlarmlog> findAlarmlogByAlmt(int yearNow, int monthNow, int dateNow);

    @Query("select a from UcAlarmlog a where a.almpriority < 800 and YEAR(a.almt) = ?1 and MONTH(a.almt)=?2 and DAY(a.almt)=?3 and a.groupname=?4")
    public List<UcAlarmlog> findAlarmlogByAlmtAndGroupname(int yearNow, int monthNow, int dateNow, String groupname);



    // 根据时间和报警级别找出对应的个数
    @Query("select a from UcAlarmactive a where a.almt >= ?1 and a.almt < ?2 and (a.almpriority = ?3 or a.almpriority = ?4)")
    public List<UcAlarmactive> findCountByTimeAndAlmpriority(String starttime, String endtime, int almpriority1, int almpriority2);

    // 根据时间查对应级别的报警
    @Query("select a from UcAlarmactive a where a.almpriority < 800 and a.almpriority = ?1 and YEAR(a.almt) = ?2 and MONTH(a.almt)=?3 and DAY(a.almt)=?4 and a.groupname=?5")
    public Page<UcAlarmactive> findListByAlmpriorityAndGroupname(int almpriority, int yearNow, int monthNow, int dateNow, String groupname, Pageable pageable);


    @Query("select a from UcAlarmactive a where a.almpriority < 800 and a.almpriority = ?1 and YEAR(a.almt) = ?2 and MONTH(a.almt)=?3 and DAY(a.almt)=?4")
    public Page<UcAlarmactive> findAlarmpriorityByAlmtAndPage(int almpriority, int yearNow, int monthNow, int dateNow, Pageable pageable);
}
