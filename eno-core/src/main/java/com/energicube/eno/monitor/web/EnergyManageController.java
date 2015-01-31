package com.energicube.eno.monitor.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.energicube.eno.common.DataModel;
import com.energicube.eno.monitor.service.DataService;

/**
 * energyManage页面控制
 */
@Controller
@RequestMapping("/energyManage")
public class EnergyManageController extends BaseController {

	private static final Log logger = LogFactory.getLog(EnergyManageController.class);

	@Autowired
	private DataService dataService;
	
	/**
	 * 能源管理页面控制首页（子系统概要页）
	 */
	@RequestMapping(value = "/energyManage")
	public String initMctrlView(Model model) {
		return "energyManage/energyManage";
	}
	
	/**
     * 根据以下参数，返回图表需要的数据
     */
    @RequestMapping(value = "/getDataAndCataList")
    @ResponseBody
    public Map<String, Object> getDataAndCataList(DataModel datamodel) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	map = dataService.getDataAndCataList(datamodel);
        } catch (Exception e) {
            logger.error("getDataAndCataList获取数据失败------" + e);
        }
        return map;
    }

    /**
     * 根据以下参数，返回图表需要的数据
     */
    @RequestMapping(value = "/getPieDataList")
    @ResponseBody
    public Map<String, Object> getPieDataList(DataModel datamodel) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		map = dataService.getPieDataList(datamodel);
    	} catch (Exception e) {
    		logger.error("getDataAndCataList获取数据失败------" + e);
    	}
    	return map;
    }

}
