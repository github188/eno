package com.energicube.eno.monitor.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Dahboard页面控制
 * @author zouzhixiang
 * @date 2014-11-21
 */
@Controller
public class DashboardController extends BaseController {

	private static final Log logger = LogFactory.getLog(DashboardController.class);

	/**
	 * Dahboard页面
	 */
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String initMctrlView(Model model) {
		return "dashboard/dashboard";
	}

}
