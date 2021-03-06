package com.energicube.eno.monitor.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * lowCarbon页面控制
 */
@Controller
@RequestMapping("/lowCarbon")
public class LowCarbonController extends BaseController {

	private static final Log logger = LogFactory.getLog(LowCarbonController.class);

	/**
	 * 低碳管理页面控制首页（子系统概要页）
	 */
	@RequestMapping(value = "/lowCarbon", method = RequestMethod.GET)
	public String initMctrlView(Model model) {
		return "lowCarbon/lowCarbon";
	}

}
