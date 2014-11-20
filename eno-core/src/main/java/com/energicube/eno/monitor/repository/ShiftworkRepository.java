package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Shiftwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ShiftworkRepository extends JpaRepository<Shiftwork, Long> {
    //	@Query("select a from Shiftwork a WHERE a.shiftitems = ?1 and  a.shiftendingtime = ?2 and a.shiftstartingbyid not  in (select shiftendingbyid from Shiftwork b where b.shiftstartingtime = ?2 and b.shiftendingbyid is not null)")
    @Query("select a from Shiftwork a WHERE a.shiftitems = ?1 and  a.shiftstartingtime = ?2 and a.shiftendingbyid is null")
    public List<Shiftwork> selectShiftworkBy1(String deptName, Date Time);

    @Query("select a from Shiftwork a WHERE a.shiftitems = ?1 and  a.shiftstartingtime = ?2 and a.shiftstartingbyid is null)")
    public List<Shiftwork> selectShiftworkBy2(String deptName, Date Time);

    @Query("select a from Shiftwork a where a.shiftstartingtime = ?1 order by a.shiftitems asc")
    public List<Shiftwork> selectWorkByStartTime(Date starttime);

    @Query("select a from Shiftwork a where   a.shiftstartingtime >=?1 and  a.shiftstartingtime<=?2 order   by a.shiftstartingtime DESC")
    public List<Shiftwork> findByNameOrderBy(Date date1, Date date2);

    @Query("select a from Shiftwork a where   a.shiftstartingtime =?1 and  a.shiftstartingbyid =?2")
    public List<Shiftwork> findByStart(Date starttime, Long userId);

    @Query("select a from Shiftwork a where   a.shiftstartingtime =?1 and  a.shiftendingbyid =?2")
    public List<Shiftwork> findByEnd(Date endtime, Long userId);

    public List<Shiftwork> findByShiftstartingbyid(Long userId);

    public List<Shiftwork> findByShiftendingbyid(Long userId);
}