package com.energicube.eno.alarm.repository;

import com.energicube.eno.alarm.model.UcAlarmlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UcAlarmlogRepository extends JpaRepository<UcAlarmlog, Integer>, JpaSpecificationExecutor<UcAlarmlog> {
    @Query("select a from UcAlarmlog a  where a.almt between ?1 and ?2 and a.groupname = ?3")
    public Page<UcAlarmlog> findByAlmtBetweenAndGroupname(Date date1, Date date2, String groupname, Pageable pageable);


    @Query("select a from UcAlarmlog a  where a.almt between ?1 and ?2 and a.almpriority = ?3 and a.groupname=?4")
    public Page<UcAlarmlog> findByAlmpriorityAndAlmtBetweenGroupname(Date date1, Date date2, int alarmpriority1, String groupname, Pageable pageable);

    public Page<UcAlarmlog> findByGroupname(String groupname, Pageable pageable);

    public List<UcAlarmlog> findByGroupname(String groupname);

    public List<UcAlarmlog> findByAlmlogid(int almlogid);

    public Page<UcAlarmlog> findByAlmtBetween(Date date1, Date date2, Pageable pageable);

    @Query("select a from UcAlarmlog a  where a.almt between ?1 and ?2 and a.almpriority = ?3")
    public Page<UcAlarmlog> findByAlmpriorityAndAlmtBetween(Date date1, Date date2, int alarmpriority1, Pageable pageable);

    /**
     * 查询报警记录
     *
     * @param startDate     开始时间
     * @param endDate       结束时间
     * @param alarmpriority 级别
     * @param groupname     子系统
     * @return
     */
    @Query("select a from UcAlarmlog a  where a.almpriority < 800 and a.almt >=?1 and a.almt <= ?2 and a.almpriority = ?3 and a.groupname=?4")
    public List<UcAlarmlog> findByDateAndGradeAndGroupname(Date startDate, Date endDate, int alarmpriority, String groupname);

    /**
     * 查询报警记录
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param groupname 子系统
     * @return
     */
    @Query("select a from UcAlarmlog a  where a.almpriority < 800 and a.almt >=?1 and a.almt <= ?2  and a.groupname=?3")
    public List<UcAlarmlog> findByDateAndGroupname(Date startDate, Date endDate, String groupname);

    /**
     * 查询报警记录
     *
     * @param startDate     开始时间
     * @param endDate       结束时间
     * @param alarmpriority 报警级别
     * @return 报警记录
     */
    @Query("select a from UcAlarmlog a where a.almpriority < 800 and a.almt >=?1 and a.almt <= ?2 and a.almpriority = ?3 ")
    public List<UcAlarmlog> findByDateAndGradeAndGroupname(Date startDate, Date endDate, int alarmpriority);

    /**
     * 查询报警记录
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 报警记录
     */
    @Query("select a from UcAlarmlog a where a.almpriority < 800 and a.almt >=?1 and a.almt <= ?2  ")
    public List<UcAlarmlog> findByDate(Date startDate, Date endDate);

    /**
     * 统计某段时间内各报警级别报警的数量
     *
     * @return
     */
    @Query(value="select almpriority,COUNT(almpriority) from UC_alarmlog where almt >= ?1 and almt < ?2 group by almpriority",nativeQuery=true)
    List<Object[]> getAlarmCouGroupByPriority(String sDate, String eDate);


    /**
     * 统计某段时间内,某系统各报警级别报警的数量
     *
     * @return
     */
    @Query(value="select almpriority,COUNT(almpriority) from UC_alarmlog where almt >= ?1 and almt < ?2 and groupname = ?3 group by almpriority",nativeQuery=true)
    List<Object[]> getAlarmCouGroupByPriority(String sDate, String eDate, String groupName);

}
