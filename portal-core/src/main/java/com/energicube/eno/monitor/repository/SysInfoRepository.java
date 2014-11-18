package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.SysInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/**
 * 系统属性数据库操作接口  LiHuiHui 2014-10-24
 *
 * @author MABING
 */
public interface SysInfoRepository extends JpaRepository<SysInfo, Integer> {


    @Query(value = "from SysInfo where syscode = ?1")
    SysInfo findBySyscode(String syscode);

    @Modifying
    @Query(value = "UPDATE SYSINFO SET SYSNAME=?1,TITLE=?2", nativeQuery = true)
    Integer updateSysInfo(String sysname, String title);
}
