package com.energicube.eno.monitor.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.energicube.eno.common.ChartModel;
import com.energicube.eno.common.Config;
import com.energicube.eno.common.DataModel;
import com.energicube.eno.monitor.service.DataService;
import com.energicube.eno.monitor.service.UserService;

/**
 * All rights Reserved, Designed By ZCLF Copyright: Copyright(C) 2013-2014
 * Company ZCLF Energy Technologies Inc.
 *
 * @author 邹智祥
 * @version 1.0
 * @date 2014/12/04 15:09
 * @Description 数据接口页面
 */
@Controller
@RequestMapping("/databoard")
public class DataController extends BaseController {

	private static final Log logger = LogFactory.getLog(DataController.class);

	@Autowired
	private DataService dataService;
	
	/**
	 * Dahboard页面
	 */
	@RequestMapping(value = "/databoard", method = RequestMethod.GET)
	public String initMctrlView(Model model) {
		return "databoard/databoard";
	}
	
	/**
     * 根据以下参数，返回图表需要的数据
     */
    @RequestMapping(value = "/findRequestData", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> findRequestData(
    		@ModelAttribute("datamodel") DataModel datamodel,
    		@RequestParam(value = "pointname", required = true, defaultValue = "") String pointname,
            @RequestParam(value = "additioncontion", required = true, defaultValue = "") String additioncontion,
            @RequestParam(value = "aggregatefunction", required = true, defaultValue = "") String aggregatefunction,
            @RequestParam(value = "timeend", required = true, defaultValue = "") String timeend,
            @RequestParam(value = "timescales", required = true, defaultValue = "") String timescales,
            @RequestParam(value = "timestart", required = true, defaultValue = "") String timestart) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	DataModel dm = new DataModel();
        	dm.setAdditioncontion(additioncontion);
        	dm.setAggregatefunction(aggregatefunction);
        	dm.setPointname(pointname);
        	dm.setTimeend(timeend);
        	dm.setTimescales(timescales);
        	dm.setTimestart(timestart);
        	map = dataService.findRequestData(datamodel);
        } catch (Exception e) {
            logger.error("获取数据失败------" + e);
        }
        return map;
    }

}
