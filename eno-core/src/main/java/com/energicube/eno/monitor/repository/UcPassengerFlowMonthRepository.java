package com.energicube.eno.monitor.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.energicube.eno.monitor.model.UcPassengerFlowMonth;

import java.util.List;

/**
 * 客流的存储接口
 * Created by LiuGuanglu
 * 2014/5/23.
 */
public interface UcPassengerFlowMonthRepository extends JpaRepository<UcPassengerFlowMonth, String> {

    /**
     * 查询店铺的某月的客流情况
     * @param shopCode  店铺编码
     * @param dateYear   年
     * @param dateMonth  月
     * @return 店铺的客流信息
     */
    public List<UcPassengerFlowMonth> findByShopCodeAndDateYearAndDateMonth(String shopCode,int dateYear,int dateMonth);

    /**
     * 查询店铺的某月的客流情况
     * @param shopName  店铺编码
     * @param dateYear   年
     * @return 店铺的客流信息
     */
    public List<UcPassengerFlowMonth> findByShopNameAndDateYear(String shopName,int dateYear);
}
