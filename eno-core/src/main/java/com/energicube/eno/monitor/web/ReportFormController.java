package com.energicube.eno.monitor.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * reportForm页面控制
 */
@Controller
@RequestMapping("/reportForm")
public class ReportFormController extends BaseController {

	private static final Log logger = LogFactory.getLog(ReportFormController.class);

	/**
	 * 报表管理页面控制首页（子系统概要页）
	 */
	@RequestMapping(value = "/reportForm", method = RequestMethod.GET)
	public String initMctrlView(Model model) {
		return "reportForm/reportForm";
	}

}
