package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.SubDoor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author 刘广路
 * @since 2014-08-11
 */
public interface SubDoorRepository extends JpaRepository<SubDoor, String> {

    /**
     * 根据门编号查询刷卡记录
     *
     * @param doorNum  门编号
     * @param pageable 分页
     * @return
     */
    public Page<SubDoor> findByDoorNumOrderByEventTimeDesc(String doorNum, Pageable pageable);

    /**
     * 根据门编号查询刷卡记录
     *
     * @param doorNum 门编号
     * @return 刷卡列表
     */
    @Query("select a from SubDoor a  where   a.doorNum = ?1 and  a.eventTime between ?2 and ?3 ")
    public List<SubDoor> findByDoorNumAndEventTimeOrderByEventTimeDesc(String doorNum, Date start, Date end);

    /**
     * 根据门编号查询刷卡记录
     *
     * @param doorNum 门编号
     * @return 刷卡列表
     */
    public List<SubDoor> findByDoorNumOrderByEventTimeDesc(String doorNum);
}
