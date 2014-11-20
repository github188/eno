package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.NetvideoDvrcfg;
import org.springframework.data.domain.Sort;

import java.util.List;


/**
 * DVR配置业务操作接口
 *
 * @author CHENPING
 * @version 1.0
 */
public interface NetvideoDvrcfgService {

    /**
     * 返回所有DVR配置
     */
    List<NetvideoDvrcfg> findNetvideoDvrcfgs(Sort sort);

    /**
     * 返回指定DVR ID的配置信息
     *
     * @param dvrcfgid DVR ID
     * @return DVR配置
     */
    NetvideoDvrcfg findNetvideoDvrcfgById(int dvrcfgid);

    /**
     * 保存DVR配置信息
     *
     * @param dvrcfg DVR配置
     * @return DVR配置信息
     */
    void saveNetvideoDvrcfg(NetvideoDvrcfg dvrcfg);

    /**
     * 批量保存DVR配置信息
     *
     * @param dvrcfg DVR配置
     * @return DVR配置信息
     */
    void saveNetvideoDvrcfg(Iterable<NetvideoDvrcfg> dvrcfgs);


    /**
     * 删除指定的DVR配置
     *
     * @param dvrcfg DVR配置对象
     */
    void deleteNetvideoDvrcfg(NetvideoDvrcfg dvrcfg);

    /**
     * 删除指定ID的DVR配置
     *
     * @param dvrcfgid DVR配置对象ID
     */
    void deleteNetvideoDvrcfg(int dvrcfgid);

    /**
     * 返回DVR配置信息中最大显示顺序的值
     *
     * @return 显示顺序最大值
     */
    int findMaxIndex();

    /**
     * 删除配置
     */
    void deleteAll();
}
