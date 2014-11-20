package com.energicube.eno.monitor.service;

import com.energicube.eno.asset.model.ClassSpec;

import java.util.List;

/**
 * 面板配置处理类
 *
 * @author shangpeibao
 * @date 2014-09-23
 */
public interface PanelConfigService {

    public List<ClassSpec> getSetAttribute(String classstructureid, String type);

    public void savePanelConfig(String classstructureid, String description, String classStr_not, String classStr_yes);

}
