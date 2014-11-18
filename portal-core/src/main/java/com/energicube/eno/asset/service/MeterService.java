package com.energicube.eno.asset.service;

import com.energicube.eno.asset.model.Meter;
import com.energicube.eno.asset.model.Metertype;
import com.energicube.eno.asset.model.Readingtype;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 计量器逻辑操作接口
 */
public interface MeterService {

    /**
     * 获取计量器列表
     *
     * @param pageNumber 当前页
     * @return 计量器列表
     */
    public Page<Meter> findAllMeters(Pageable pageable);


    /**
     * 判断仪表名称是否存
     *
     * @param metername 仪表名称
     * @return true or false
     */
    public boolean existMetername(String metername);

    /**
     * 持久化仪表对象
     *
     * @param meter 仪表对象
     * @return 仪表对象
     */
    public Meter saveMeter(Meter meter);

    /**
     * 获取仪表对象
     *
     * @param id   仪表ID
     * @param 仪表对象
     */
    public Meter findOne(Long id);

    /**
     * 获取仪表类型
     */
    public List<Metertype> findMetertypes();

    /**
     * 获取仪表类型对象
     *
     * @param id 对象ID
     */
    public Metertype findMetertypeById(String id);


    /**
     * 获取读数类型
     */
    public List<Readingtype> findReadingtypes();

    /**
     * 获取读数类型对象
     *
     * @param id 对象ID
     */
    public Readingtype findReadingtypeById(String id);


    /**
     * <p/>
     * Query used to populate the DataTables that display the list of Meters.
     *
     * @param criterias The DataTables criterias used to filter the Meters.
     *                  (maxResult, filtering, paging, ...)
     * @return a bean that wraps all the needed information by DataTables to
     * redraw the table with the data.
     */
    public DataSet<Meter> findMetersWithDatatablesCriterias(DatatablesCriterias criterias);
}
