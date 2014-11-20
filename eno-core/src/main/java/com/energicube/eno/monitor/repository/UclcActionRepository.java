package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.UclcAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 连动动作数据操作接口
 */
public interface UclcActionRepository extends
        JpaRepository<UclcAction, Integer> {

    @Transactional(readOnly = true)
    List<UclcAction> findByCellid(int cellid);

    /**
     * 查询指定动作类别的所有动作信息
     *
     * @param actiontype 动作类型
     * @return
     */
    @Transactional(readOnly = true)
    List<UclcAction> findByActiontype(int actiontype);

}
