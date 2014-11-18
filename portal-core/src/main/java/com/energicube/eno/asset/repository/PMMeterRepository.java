package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.PMMeter;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * PM计量器数据操作接口
 */
public interface PMMeterRepository extends JpaRepository<PMMeter, Long> {

    /**
     * 获取PM指定的计量器
     *
     * @param metername 计量器名称
     * @param pmnum     PM编号
     * @param siteid    地点
     * @return PM计量器列表
     */
    @Query("select m from PMMeter m where m.metername=?1 and m.pmnum=?2 and ((m.siteid=?3) or (m.siteid is null))")
    List<PMMeter> findByMeternameAndPmnumAndSiteid(String metername, String pmnum, String siteid) throws DataAccessException;

    /**
     * 查找PM的计量器
     *
     * @param pmnum  PM编号
     * @param siteid 地点
     * @return PM计量器列表
     */
    Page<PMMeter> findByPmnumAndSiteid(String pmnum, String siteid, Pageable pageable) throws DataAccessException;


    @Query("select pmmeter,meter from PMMeter pmmeter,Meter meter where pmmeter.metername=meter.metername and pmmeter.pmnum=?1 and pmmeter.siteid=?2")
    Page<Object[]> findByPmnum(String pmnum, String siteid, Pageable pageable) throws DataAccessException;


}
