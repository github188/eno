package com.energicube.eno.monitor.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * deviceMange页面控制
 */
@Controller
@RequestMapping("/deviceManage")
public class DeviceManageController extends BaseController {

	private static final Log logger = LogFactory.getLog(DeviceManageController.class);

	/**
	 * 设备管理页面控制首页（子系统概要页）
	 */
	@RequestMapping(value = "/deviceManage", method = RequestMethod.GET)
	public String initMctrlView(Model model) {
		return "deviceManage/deviceManage";
	}

}
