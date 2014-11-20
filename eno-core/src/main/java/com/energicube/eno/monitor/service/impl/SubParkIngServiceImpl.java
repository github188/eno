package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.repository.jpa.SubParkingRepository;
import com.energicube.eno.monitor.service.SubParkIngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @author LiHuiHui
 * @since JDK 1.7
 */
@Service
public class SubParkIngServiceImpl implements SubParkIngService {

    @Autowired
    private SubParkingRepository jpaSubParkIng;

    /**
     * 停车管理
     *
     * @param Pageable    pageable
     * @param Map<String, String[]> condition  页面传过来的查询条件
     * @return Page<OpLogs>
     */
    @Override
    public Page<Object[]> findByCondition(Pageable pageable, Map<String, String[]> condition) {
        return jpaSubParkIng.findByCondition(pageable, condition);
    }
}
