package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.NetvideoMatrixConfig;

import javax.servlet.http.HttpServletRequest;

public interface NetvideoMatrixConfigService {

    /**
     * 保存matrix配置到ini文件
     *
     * @param config
     * @param request
     */
    void saveMatrixconfig(NetvideoMatrixConfig config, HttpServletRequest request);

    /**
     * 读取ini配置文件
     *
     * @param filename
     * @param request
     * @return
     */
    String readMatrixconfig(String filename, HttpServletRequest request);

}
