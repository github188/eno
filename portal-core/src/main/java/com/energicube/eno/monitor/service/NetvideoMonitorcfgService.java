package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.NetvideoMonitorcfg;
import org.springframework.data.domain.Sort;

import java.util.List;


/**
 * 监视器配置业务操作接口
 *
 * @author CHENPING
 * @version 1.0
 */
public interface NetvideoMonitorcfgService {

    /**
     * 返回所有监视器配置
     */
    List<NetvideoMonitorcfg> findNetvideoMonitorcfgs();

    /**
     * 返回指定监视器ID的配置信息
     *
     * @param dvrcfgid DVR ID
     * @return DVR配置
     */
    NetvideoMonitorcfg findNetvideoMonitorcfgById(int monitorid);

    /**
     * 保存监视器配置信息
     *
     * @param monitorcfg 监视器配置
     * @return 监视器配置信息
     */
    void saveNetvideoMonitorcfg(NetvideoMonitorcfg monitorcfg);

    /**
     * 删除指定的监视器配置信息
     *
     * @param monitorcfg 监视器配置信息
     */
    void deleteNetvideoMonitorcfg(NetvideoMonitorcfg monitorcfg);

    /**
     * 删除指定ID的监视器配置
     *
     * @param monitorid 监视器配置对象ID
     */
    void deleteNetvideoMonitorcfg(int monitorid);


    List<NetvideoMonitorcfg> findNetvideoMonitorcfgs(Sort sort);

}
