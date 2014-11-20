package com.energicube.eno.monitor.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 报表页
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {

    /**
     * 报表列表
     */
    @RequestMapping(method = RequestMethod.GET)
    public String initShowReportList(Model model) {
        return "report/list";
    }

    /**
     * 报表明细
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String initShowReportDetail() {
        return "report/detail";
    }

    /**
     * 设备运行趋势
     */
    @RequestMapping(value = "/deviceTrend", method = RequestMethod.GET)
    public String deviceTrend() {
        return "report/deviceTrend";
    }

    /**
     * 运行报表
     */
    @RequestMapping(value = "/runningReport", method = RequestMethod.GET)
    public String runningReport() {
        return "report/runningReport";
    }


    @RequestMapping(value = "/operatingAhuView", method = RequestMethod.GET)
    public String initoperatingAhuView(HttpServletRequest request,
                                       HttpServletResponse response, Model model) {
        String querytime = request.getParameter("querytime");
        model.addAttribute("querytime", querytime);
        return "report/operatingAhuView";
    }

    @RequestMapping(value = "/operatingChillerView", method = RequestMethod.GET)
    public String initoperatingChillerView(HttpServletRequest request,
                                           HttpServletResponse response, Model model) {
        String querytime = request.getParameter("querytime");
        model.addAttribute("querytime", querytime);
        return "report/operatingChillerView";
    }

    @RequestMapping(value = "/operatingMahuView", method = RequestMethod.GET)
    public String initoperatingMahuView(HttpServletRequest request,
                                        HttpServletResponse response, Model model) {
        String querytime = request.getParameter("querytime");
        model.addAttribute("querytime", querytime);
        return "report/operatingMahuView";
    }

    @RequestMapping(value = "/operatingFahuView", method = RequestMethod.GET)
    public String initoperatingFahuView(HttpServletRequest request,
                                        HttpServletResponse response, Model model) {
        String querytime = request.getParameter("querytime");
        model.addAttribute("querytime", querytime);
        return "report/operatingFahuView";
    }
}
