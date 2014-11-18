package com.energicube.eno.asset.service;

import com.energicube.eno.asset.model.LocHierarchySet;
import com.energicube.eno.asset.model.LocSystem;
import com.energicube.eno.asset.model.LocationMeter;
import com.energicube.eno.asset.model.Locations;
import com.energicube.eno.common.model.Tree;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * location service interface
 *
 * @author CHENPING
 */
public interface LocationsService {

    /**
     * find all Locations object
     *
     * @param pageNumber current page number
     * @return list of Locations
     */
    public Page<Locations> findAllLocations(Integer pageNumber);

    public Page<Locations> findAllLocations(Pageable pageable);

    /**
     * <p/>
     * Query used to populate the DataTables that display the list of Locations.
     *
     * @param criterias The DataTables criterias used to filter the Locations.
     *                  (maxResult, filtering, paging, ...)
     * @return a bean that wraps all the needed information by DataTables to
     * redraw the table with the data.
     */
    public DataSet<Locations> findLocationsWithDatatablesCriterias(DatatablesCriterias criterias);

    /**
     * return locations model object
     *
     * @param locationsid locations id
     * @return locations model
     */
    public Locations findOne(long locationsid);

    /**
     * 判断位置定义是否存在
     *
     * @param location location code
     * @return If is true, said exists, whether for does not exist
     */
    public boolean existLocation(String location);

    /**
     * save locations model object
     *
     * @param locations locations model object
     * @return locations model object
     */
    public Locations saveLocations(Locations locations);

    /**
     * find all locsystem object
     *
     * @param pageNumber current page number
     * @return list of locsystem
     */
    public Page<LocSystem> findAllLocSystem(Integer pageNumber);

    /**
     * persistence LocSystem object
     *
     * @param locSystem LocSystem Object
     * @return LocSystem Object
     */
    public LocSystem saveLocSystem(LocSystem locSystem);

    /**
     * 判断位置系统定义是否存在
     *
     * @param systemid 系统ID
     * @return If is true, said exists, whether for does not exist
     */
    public boolean existLocSystem(String systemid);

    /**
     * 查找附加到位置的计量器
     *
     * @param location 位置代码
     * @return 计量器列表
     */
    public List<LocationMeter> findMeterByLocation(String location);

    /**
     * 查找附加到位置的计量器
     *
     * @param location  位置代码
     * @param metername 计量器名称
     * @return 计量器列表
     */
    public List<LocationMeter> findByLocationAndMetername(String location, String metername);

    /**
     * 判断计量器是否存在于附加位置
     *
     * @param location  位置代码
     * @param metername 计量器名称
     * @return if is true the exist,else not exist
     */
    public boolean existMeterInLocationMeter(String location, String metername);

    /**
     * 保存附加到位置的计量器
     *
     * @param locationMeter 附加到位置的计量器
     * @return 附加到位置的计量器
     */
    public LocationMeter saveLocationMeter(LocationMeter locationMeter);


    /**
     * 获取所有位置层级关系
     *
     * @param orgid 组织机构
     */
    public List<LocHierarchySet> findLocHierarchyList(String siteid);

    /**
     * 获取所有位置层级关系树
     */
    public List<Tree> getLocHierarchyTree(String siteid);


}
