package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.ReportConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by energicube on 2014/8/21.
 */
public interface ReportConfigRepository extends JpaRepository<ReportConfig, Integer> {
    /*
    *获取子系统列表
    * @return
     */
    @Query("select system, uid FROM ReportConfig")
    public List<String[]> findSystemList();

    /*
   *获取设备类型列表
   * @return
    */
    @Query("select devicetype, uid FROM ReportConfig")
    public List<String[]> findDeviceType();

    /*
   * 获取设备类型列表
   * @param system
   * @return
    */
    @Query("select devicetype, uid FROM ReportConfig where system = :system")
    public List<String[]> findDeviceType(@Param("system") String system);

    /*
     * 获取设备Id
     */
    @Query("select device, uid FROM ReportConfig")
    public List<String[]> findDevices();

    /*
     * 获取设备Id
     */
    @Query("select device,uid FROM ReportConfig where system = :system and devicetype = :devicetype")
    public List<String[]> findDevices(@Param("system") String system, @Param("devicetype") String devicetype);

    /*
     * 获取设备Id
     */
    @Query("select id, uid FROM ReportConfig")
    public List<String[]> findDevicesId();

    /*
     * 获取设备Id
     */
    @Query("select id, uid FROM ReportConfig where system = :system and devicetype = :devicetype")
    public List<String[]> findDevicesId(@Param("system") String system, @Param("devicetype") String devicetype);

    /*
      * 获取设备列表
      * @return
       */
    @Query("select a FROM ReportConfig a")
    public List<ReportConfig> findParamsList();

    /*
     * 获取设备列表
     * @return
      */
    @Query("select a FROM ReportConfig a where a.system = :system and a.devicetype = :devicetype and a.id = :id")
    public List<ReportConfig> findParamsList(@Param("system") String system, @Param("devicetype") String devicetype, @Param("id") String id);

    /*
     * 获取ReportConfig
     * @return
     */
    @Query("select a FROM ReportConfig a where a.system = :system and a.devicetype = :devicetype and a.device = :device")
    public List<ReportConfig> findReportConfigsList(@Param("system") String system, @Param("devicetype") String devicetype, @Param("device") String device);

}
