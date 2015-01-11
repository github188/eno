package com.energicube.eno.monitor.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.energicube.eno.monitor.model.UcPassengerFlowYear;

import java.util.List;

/**
 * 客流的存储接口
 * Created by LiuGuanglu
 * 2014/5/23.
 */
public interface UcPassengerFlowYearRepository extends JpaRepository<UcPassengerFlowYear, String> {

    /**
     * 查询店铺的某年的客流情况
     * @param shopCode  店铺编码
     * @param dateYear   年
     * @return 店铺的客流信息
     */
    public List<UcPassengerFlowYear> findByShopCodeAndDateYear(String shopCode,int dateYear);
}
