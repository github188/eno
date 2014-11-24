package com.energicube.eno.monitor.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.energicube.eno.common.Config;

/**
 * Dahboard页面控制
 */
@Controller
public class DashboardController extends BaseController {

	private static final Log logger = LogFactory.getLog(DashboardController.class);

	private Config config = new Config();

	/**
	 * 监测与控制页面控制首页（子系统概要页）
	 */
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String initMctrlView(Model model) {
		return "dashboard/dashboard";
	}

}
