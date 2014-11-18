package com.energicube.eno.monitor.web;

import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.monitor.model.OkcLogs;
import com.energicube.eno.monitor.service.OkcLogsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 系统日志
 * <p>系统日志查询</p>
 *
 * @author CHENPING
 */
@Controller
@RequestMapping("/okcsys/logs")
public class OkcLogsController extends BaseController {

    private static final Log logger = LogFactory.getLog(OkcLogsController.class);

    @Autowired
    private OkcLogsService okcLogsService;


    /**
     * 显示系统日志页
     */
    @RequestMapping(method = RequestMethod.GET)
    public String initShowLogs(Model model) {
        return "okclogs/list";
    }

    /**
     * 日志数据，分页显示
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<OkcLogs> showPMListToDataTables(DataTablesRequestParams params,
                                                              @RequestParam(value = "startdate", required = true, defaultValue = "") String startdate,
                                                              @RequestParam(value = "enddate", required = true, defaultValue = "") String enddate,
                                                              @RequestParam(value = "level", required = true, defaultValue = "") String level) {
        try {
            if (!StringUtils.hasLength(startdate)) {
                startdate = DateTime.now().toString("yyyy-MM-dd");
            }
            if (!StringUtils.hasLength(enddate)) {
                enddate = DateTime.now().toString("yyyy-MM-dd HH:mm");
            }
            if (!StringUtils.hasLength(level)) {
                level = "ERROR";
            }

            return okcLogsService.findByDateRange(startdate, enddate, level, params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return new DataTablesResponse<OkcLogs>();
        }
    }


    /**
     * 显示系统日志页
     */
    @RequestMapping(value = "/{logid}", method = RequestMethod.GET)
    public String initShowLogContent(@PathVariable Long logid, Model model) {
        OkcLogs okcLogs = new OkcLogs();
        if (logid > 0) {
            try {
                okcLogs = okcLogsService.findByLogid(logid);
            } catch (Exception e) {
                logger.equals(e);
            }
        }
        model.addAttribute("okcLogs", okcLogs);

        return "okclogs/content";
    }


}
