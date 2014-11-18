package com.energicube.eno.monitor.web;

import com.energicube.eno.monitor.service.SubParkIngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 * 车辆管理
 */
@Controller
@RequestMapping("/park/operate")
public class SubParkIngController {

    @Autowired
    SubParkIngService subParkSerivce;

    /**
     * 停车管理 条件查询
     */
    @RequestMapping(value = "/query", produces = "application/json")
    @ResponseBody
    public Page<Object[]> queryOperate(WebRequest request,
                                       @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize) {
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return subParkSerivce.findByCondition(pageable, request.getParameterMap());
    }

}
