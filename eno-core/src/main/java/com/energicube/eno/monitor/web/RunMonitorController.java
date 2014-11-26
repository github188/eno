package com.energicube.eno.monitor.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.energicube.eno.common.Config;

/**
 * 运行监测页面
 * @author zouzhixiang
 * @date 2014-11-24
 */
@Controller
@RequestMapping("/runMonitor")
public class RunMonitorController extends BaseController {

	private static final Log logger = LogFactory.getLog(RunMonitorController.class);

	private Config config = new Config();

	/**
	 * 监测与控制页面控制首页（子系统概要页）
	 */
	@RequestMapping(value = "/runMonitor", method = RequestMethod.GET)
	public String initMctrlView(Model model) {
		return "runMonitor/runMonitor";
	}

}
