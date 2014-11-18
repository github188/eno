package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.FileUtil;
import com.energicube.eno.monitor.model.NetvideoMatrixConfig;
import com.energicube.eno.monitor.service.NetvideoMatrixConfigService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class NetvideoMatrixConfigImpl implements NetvideoMatrixConfigService {

    @Override
    public void saveMatrixconfig(NetvideoMatrixConfig config,
                                 HttpServletRequest request) {
        FileUtil.saveconfigToFile(config, "matrixconfig", request);
    }

    @Override
    public String readMatrixconfig(String filename, HttpServletRequest request) {
        return FileUtil.readConfig(filename, request);
    }

}
