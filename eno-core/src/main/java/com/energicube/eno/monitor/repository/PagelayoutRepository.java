package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Pagelayout;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PagelayoutRepository extends JpaRepository<Pagelayout, Long> {


    /**
     * 查询指定菜单的页面布局
     *
     * @param menuid 菜单ID
     * @return 页面布局列表
     */
    @Query("select layout from Pagelayout layout where layout.menuid=?1 order by layout.pageindex asc")
    public List<Pagelayout> findByMenuid(String menuid) throws DataAccessException;


    /**
     * 查询指定菜单的页面布局
     *
     * @param menuid 菜单ID
     * @return 页面布局列表
     */
    public List<Pagelayout> findByMenuidAndPageindex(String menuid, int pageindex) throws DataAccessException;

    /**
     * 根据layoutid查询记录
     *
     * @param layoutid
     * @return
     * @throws DataAccessException
     */
    public List<Pagelayout> findByLayoutid(String layoutid) throws DataAccessException;

    /**
     * 根据设备列表id找到使用该设备的页面
     *
     * @param deviceconfigid
     * @return
     * @throws DataAccessException
     */
    public List<Pagelayout> findByDeviceconfigid(int deviceconfigid) throws DataAccessException;
}
