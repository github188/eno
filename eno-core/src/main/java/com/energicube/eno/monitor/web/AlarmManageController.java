package com.energicube.eno.monitor.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.energicube.eno.common.Config;

/**
 * 报警管理页面控制
 * @author zouzhixiang
 * @date 2014-11-24
 */
@Controller
@RequestMapping("/alarmManage")
public class AlarmManageController extends BaseController {

	private static final Log logger = LogFactory.getLog(AlarmManageController.class);

	private Config config = new Config();

	/**
	 * 报警管理主页面
	 */
	@RequestMapping(value = "/alarmManage", method = RequestMethod.GET)
	public String initMctrlView(Model model) {
		return "alarmManage/alarmManage";
	}

}
