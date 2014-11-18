package com.energicube.eno.pattern.repository;


import com.energicube.eno.pattern.model.UcSystemParam;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-7-11
 * Time: 上午11:31
 * To change this template use File | Settings | File Templates.
 */
public interface UcSystemParamRepository extends JpaRepository<UcSystemParam, String> {


    /**
     * 查询某系统的运行参数
     * @param systemId 子系统ID
     * @return
     */
    // public List<UcSystemParam>  findBySystemId(String systemId);
}
