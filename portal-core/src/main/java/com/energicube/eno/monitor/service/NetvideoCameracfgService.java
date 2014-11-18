package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.NetvideoCameracfg;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 摄像机配置业务操作接口
 *
 * @author CHENPING
 * @version 1.0
 */
public interface NetvideoCameracfgService {

    /**
     * 返回所有摄像机配置
     *
     * @return return all cameracfgs
     */
    List<NetvideoCameracfg> findNetvideoCameracfgs();

    /**
     * 返回指定ID的摄像机配置
     *
     * @param cameracfgid 摄像机ID
     * @return {@link NetvideoCameracfg}
     */
    NetvideoCameracfg findNetvideoCameraByCameracfgid(Integer cameracfgid);

    /**
     * 保存摄像机配置
     *
     * @param cameracfg 摄像机配置
     * @return {@link NetvideoCameracfg}
     */
    void saveNetvideoCameracfg(NetvideoCameracfg cameracfg);

    /**
     * 删除摄像机配置
     *
     * @param cameracfg 摄像机配置
     */
    void deleteNetvideoCameracfg(NetvideoCameracfg cameracfg);

    /**
     * 删除摄像机配置
     *
     * @param cameracfgid 摄像机ID
     */
    void deleteNetvideoCameracfg(Integer cameracfgid);

    /**
     * 读取配置文件
     *
     * @param string
     * @param request
     * @return
     */
    String readconfig(String string, HttpServletRequest request);

    List<NetvideoCameracfg> findSortNetvideoCameracfgs(Sort sort);

    /**
     * 删除所有
     */
    void deleteAllCamera();

    /**
     * 存储摄像机配置的组信息
     *
     * @param group
     */
    void saveGroupinfo(String group, String string, HttpServletRequest request);

    /**
     * 读取摄像机分组树
     *
     * @param string
     * @param request
     * @return
     */
    String readGroupinfo(String string, HttpServletRequest request);

    void deleteAll();

    void saveConfigs(List<NetvideoCameracfg> cameracfgs);

}
