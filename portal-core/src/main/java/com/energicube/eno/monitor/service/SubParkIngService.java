package com.energicube.eno.monitor.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author LiHuiHui
 * @version ͣ������ӿ�
 */
public interface SubParkIngService {
    /**
     * ������ҳ��ѯ
     *
     * @param Pageable    pageable
     * @param Map<String, String[]> condition  ��ѯ����
     * @return Page<Object[]> object[0]:OpLogs object[1]:PersonVo
     */
    public Page<Object[]> findByCondition(Pageable pageable, Map<String, String[]> condition);

}
