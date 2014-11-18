package com.energicube.eno.alarm.repository;

import com.energicube.eno.alarm.model.UcAlarmgroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UcAlarmgroupRepository extends JpaRepository<UcAlarmgroup, Long> {

    @Query("from UcAlarmgroup where parentid= :pid")
    public List<UcAlarmgroup> findListByPid(@Param("pid") int pid);

    @Query("from UcAlarmgroup where parentid = (select groupid from UcAlarmgroup where parentid=-1) or groupid in (select groupid from UcAlarmgroup where parentid in (select groupid from UcAlarmgroup where parentid = (select groupid from UcAlarmgroup where parentid=-1)))")
    public List<UcAlarmgroup> findListByGroupMain();

}
