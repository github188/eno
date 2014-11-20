package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.asset.model.ClassSpec;
import com.energicube.eno.monitor.model.PanelConfig;
import com.energicube.eno.monitor.repository.PanelConfigRepository;
import com.energicube.eno.monitor.repository.jpa.AssetMeasurementRepository;
import com.energicube.eno.monitor.service.PanelConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PanelConfigServiceImpl implements PanelConfigService {

    private static final Log logger = LogFactory.getLog(PanelConfigServiceImpl.class);
    @Autowired
    private AssetMeasurementRepository assetMeasurementRepository;
    @Autowired
    private PanelConfigRepository panelConfigRepository;

    /**
     * 获取分类下的属性
     */
    @Override
    public List<ClassSpec> getSetAttribute(String classstructureid, String type) {
        List<ClassSpec> setList = assetMeasurementRepository.findSetAttribute(classstructureid, type);
        return setList;
    }

    /**
     * 保存面板配置
     */
    @Override
    public void savePanelConfig(String classstructureid, String description, String classStr_not, String classStr_yes) {
        try {
            panelConfigRepository.deleteAllPanelConfigs(classstructureid);
            ObjectMapper objectMapper = new ObjectMapper();
            if (classStr_not != null && !"".equals(classStr_not)) {
                List jsonArray=objectMapper.readValue(classStr_not, List.class);
                PanelConfig pc_n;
                Map jo_n;
                for (int i = 0; i < jsonArray.size(); i++) {
                    jo_n =objectMapper.readValue(jsonArray.get(i).toString(), Map.class);
                    pc_n = new PanelConfig();
                    pc_n.setDescription(description);
                    pc_n.setAssetattrid(jo_n.get("assetattrid").toString());
                    pc_n.setClassstructureid(jo_n.get("classstructureid").toString());
                    pc_n.setIseditor(0);
                    panelConfigRepository.save(pc_n);
                }
            }
            if (classStr_yes != null && !"".equals(classStr_yes)) {
                List jsonArray=objectMapper.readValue(classStr_yes, List.class);
                PanelConfig pc_y;
                Map jo_y;
                for (int j = 0; j < jsonArray.size(); j++) {
                    jo_y =objectMapper.readValue(jsonArray.get(j).toString(), Map.class);
                    pc_y = new PanelConfig();
                    pc_y.setDescription(description);
                    pc_y.setAssetattrid(jo_y.get("assetattrid").toString());
                    pc_y.setClassstructureid(jo_y.get("classstructureid").toString());
                    pc_y.setIseditor(1);
                    panelConfigRepository.save(pc_y);
                }
            }

        } catch (Exception e) {
            logger.error("savePanelConfig",e);
        }
    }

}
