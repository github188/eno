package com.energicube.eno.pattern.repository;

import com.energicube.eno.pattern.model.UcCombinationPattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-8
 * Time: 下午12:39
 * To change this template use File | Settings | File Templates.
 */
public interface UcCombinationPatternRepository extends JpaRepository<UcCombinationPattern, String> {


    /**
     * 查询全局模式关联的子系统
     *
     * @param systemId 子系统ID
     * @param globalId 全局模式Id
     * @return
     */
    public UcCombinationPattern findBySystemIdAndUcGlobalPattern_Id(String systemId, String globalId);

    /**
     * 查询全局模式拥有的子系统
     *
     * @param globalId 全局模式Id
     * @return
     */
    public List<UcCombinationPattern> findByUcGlobalPattern_Id(String globalId);
}
