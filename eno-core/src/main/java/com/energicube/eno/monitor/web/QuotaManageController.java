package com.energicube.eno.monitor.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * quotaMange页面控制
 */
@Controller
@RequestMapping("/quotaManage")
public class QuotaManageController extends BaseController {

	private static final Log logger = LogFactory.getLog(QuotaManageController.class);

	/**
	 * 定额管理页面控制首页（子系统概要页）
	 */
	@RequestMapping(value = "/quotaManage", method = RequestMethod.GET)
	public String initMctrlView(Model model) {
		return "quotaManage/quotaManage";
	}

}
