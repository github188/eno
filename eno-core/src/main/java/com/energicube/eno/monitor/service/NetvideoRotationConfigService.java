package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.NetvideoRotationConfig;

import javax.servlet.http.HttpServletRequest;

public interface NetvideoRotationConfigService {

    /**
     * 保存轮显配置到ini文件
     *
     * @param map
     * @param request
     */
    void saveRotationconfig(NetvideoRotationConfig[] obj,
                            HttpServletRequest request);

    /**
     * 读取ini文件
     *
     * @param string
     * @param request
     * @return
     */
    String readconfig(String string, HttpServletRequest request);


}
