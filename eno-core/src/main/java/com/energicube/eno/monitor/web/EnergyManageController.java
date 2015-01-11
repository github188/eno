package com.energicube.eno.monitor.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * energyManage页面控制
 */
@Controller
@RequestMapping("/energyManage")
public class EnergyManageController extends BaseController {

	private static final Log logger = LogFactory.getLog(EnergyManageController.class);

	/**
	 * 能源管理页面控制首页（子系统概要页）
	 */
	@RequestMapping(value = "/energyManage", method = RequestMethod.GET)
	public String initMctrlView(Model model) {
		return "energyManage/energyManage";
	}

}
