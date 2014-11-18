package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.NetvideoSystemConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface NetvideoSysConfigService {

    /**
     * 读取驱动、配置版本
     *
     * @param request
     * @param string
     * @return
     */
    String readVersions(HttpServletRequest request, String string);

    /**
     * 上传驱动
     *
     * @param request
     * @param filename
     * @return
     */
    String saveDriver(HttpServletRequest request, String filename, String drivername) throws Exception;

    /**
     * 保存系统配置到ini文件
     *
     * @param config
     * @param request
     */
    void saveSysconfig(NetvideoSystemConfig config, HttpServletRequest request);

    /**
     * 读取ini文件
     *
     * @param request
     * @param filename
     * @return
     */
    String readSysconfig(HttpServletRequest request, String filename);

    /**
     * 下载驱动
     *
     * @param request
     * @param response
     * @param drivername（驱动名）
     */
    void downDriver(HttpServletRequest request, HttpServletResponse response,
                    String configname);

}
