package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.MeasurePoint;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 测点状态监控数据操作类
 * <p>定义为计量器测量读数记录，及对技术规范参数的测量读书记录。</p>
 *
 * @author CHENPING
 */
public interface MeasurePointRepository extends
        JpaRepository<MeasurePoint, Long> {

    /**
     * 获取指定的资产编号和计量器的测点列表
     *
     * @param assetnum  资产编号
     * @param metername 计量器名称
     * @return MeasurePoint
     */
    public List<MeasurePoint> findByAssetnumAndMetername(String assetnum, String metername) throws DataAccessException;

    /**
     * 获取指定的资产编号和计量器的测点列表
     *
     * @param assetnum  资产编号
     * @param metername 计量器名称
     * @param isspec    是否技术规范参数测量读数（true=是,false=否)
     * @return MeasurePoint List
     */
    public List<MeasurePoint> findByAssetnumAndMeternameAndIsspec(String assetnum, String metername, Boolean isspec) throws DataAccessException;

    /**
     * 获取相同测点的数据
     *
     * @param pointnum 测点数据
     * @param siteid   地点
     * @param orgid    组织机构
     * @return 测点列表
     */
    public List<MeasurePoint> findByPointnumAndSiteidAndOrgid(String pointnum, String siteid, String orgid) throws DataAccessException;

    /**
     * 获取指定组织机构指定地点的测点数据
     *
     * @param siteid   地点
     * @param orgid    组织机构
     * @param pageable {@link Pageable} object
     * @return {@link MeasurePoint} list of {@link Page} list
     */
    public Page<MeasurePoint> findBySiteidAndOrgid(String siteid, String orgid, Pageable pageable) throws DataAccessException;

}
