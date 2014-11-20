package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.FileUtil;
import com.energicube.eno.monitor.model.NetvideoRotationConfig;
import com.energicube.eno.monitor.service.NetvideoRotationConfigService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class NetvideoRotationConfigServiceImpl implements
        NetvideoRotationConfigService {

    @Override
    public String readconfig(String string, HttpServletRequest request) {
        return FileUtil.readConfig(string, request);
    }

    @Override
    public void saveRotationconfig(NetvideoRotationConfig[] rotationConfigs,
                                   HttpServletRequest request) {
        FileUtil.saveconfigToFile(rotationConfigs, "rotationconfig", request);
    }

}
