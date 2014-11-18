package com.energicube.eno.asset.service;

import com.energicube.eno.asset.model.MeterGroup;
import com.energicube.eno.asset.model.MeterInGroup;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 计量器具组逻辑操作接口
 *
 * @author CHENPING
 */
public interface MeterGroupService {

    /**
     * 获取所有记量器组
     *
     * @param pageable 分页对象
     * @return 所有记量器组
     */
    public Page<MeterGroup> findAllMeterGroup(Pageable pageable);


    /**
     * <p/>
     * Query used to populate the DataTables that display the list of MeterGroups.
     *
     * @param criterias The DataTables criterias used to filter the MeterGroups.
     *                  (maxResult, filtering, paging, ...)
     * @return a bean that wraps all the needed information by DataTables to
     * redraw the table with the data.
     */
    public DataSet<MeterGroup> findMeterGroupsWithDatatablesCriterias(DatatablesCriterias criterias);


    /**
     * 判断用户组名称是否存在
     *
     * @param groupname 用户组名称
     * @return true or false
     */
    public boolean existMeterGroup(String groupname);

    /**
     * 保存计量器具组信息
     *
     * @param meterGroup 计量器具组对象
     * @return 计量器具组信息
     */
    public MeterGroup saveMeterGroup(MeterGroup meterGroup);

    /**
     * 获取指定组ID的计量器具组信息
     *
     * @param 计量器具组ID
     * @return 计量器具组信息
     */
    public MeterGroup findOne(Long id);


    /**
     * 删除指定组ID的计量器具组信息
     *
     * @param 计量器具组ID
     */
    public void deleteMeterGroup(Long metergroupid);


    /**
     * 获取计量器组对应的计量器信息
     *
     * @param groupname 计量器组名称
     * @return 计量器列表
     */
    public List<MeterInGroup> findMeterInGroup(String groupname);

    /**
     * 判断计量器信息是否包含在指定的计量器组中
     *
     * @param groupname 计量器组名称
     * @param metername 计量器名称
     * @return true or false
     */
    public boolean existMeternameInGroup(String groupname, String metername);

    /**
     * 保存计量器组存在的计量器信息
     *
     * @param meterInGroup 计量器包含信息
     * @return 计量器组存在的计量器信息
     */
    public MeterInGroup saveMeterInGroup(MeterInGroup meterInGroup);

    /**
     * 删除计量器组中的计量器信息
     *
     * @param meteringroupid 计量器组中的计量器ID
     */
    public void deleteMeterInGroup(long meteringroupid);
}
