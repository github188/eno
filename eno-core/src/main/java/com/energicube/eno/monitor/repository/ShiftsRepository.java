package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Shifts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShiftsRepository extends JpaRepository<Shifts, Long> {

    public List<Shifts> findByName(String name);

    public List<Shifts> findByNameContaining(String name);

    @Query("select a from Shifts a WHERE YEAR(a.shiftdate) = ?1 and MONTH(a.shiftdate)=?2 order by a.name desc")
    public List<Shifts> selectOrderBynameDesc(int yearNow, int monthNow);

    @Query("select a from Shifts a where   a.name = ?1 and YEAR(a.shiftdate) = ?2 and MONTH(a.shiftdate)=?3  order   by a.shiftdate asc")
    public List<Shifts> findByNameOrderByShiftdate(String name, int yearNow, int monthNow);
}