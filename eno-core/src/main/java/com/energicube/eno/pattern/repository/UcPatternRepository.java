package com.energicube.eno.pattern.repository;

import com.energicube.eno.pattern.model.UcPattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-7-11
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
public interface UcPatternRepository extends JpaRepository<UcPattern, String> {


    /**
     * 查询某系统的所有模式
     *
     * @param systemId 子系统ID
     * @return
     */
    public List<UcPattern> findBySystemId(String systemId);

    /**
     * 查询模式通过子系统和模式类型
     *
     * @param systemId    子系统ID
     * @param patternType 模式类型
     * @param orderType   时序或非时序
     * @return
     */
    public List<UcPattern> findBySystemIdAndPatternTypeAndOrderType(String systemId, String patternType, String orderType);

    /**
     * 通过模式类型和时间段查询此时间内是否有特例要模式
     *
     * @param patternType 模式类型
     * @param startDate   开始时间
     * @param endDate     结束时间
     * @return
     */
    public List<UcPattern> findByPatternTypeAndStartDateLessThanAndEndDateGreaterThan(String patternType, Date startDate, Date endDate);

    /**
     * 查询模式通过子系统和模式类型
     *
     * @param orderType 模式类型
     * @return
     */
    public List<UcPattern> findByOrderType(String orderType);

    /**
     * 查询子系统默认的模式
     *
     * @param systemId       子系统的Id
     * @param defaultPattern 默认的状态
     * @return
     */
    public List<UcPattern> findBySystemIdAndDefaultPattern(String systemId, String defaultPattern);
}
