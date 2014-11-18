package com.energicube.eno.monitor.web;

import com.energicube.eno.monitor.model.SubDoor;
import com.energicube.eno.monitor.service.DoorInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/doorinfo")
public class DoorInfoController extends BaseController {
    private static final Log logger = LogFactory.getLog(DoorInfoController.class);
    @Autowired
    DoorInfoService doorInfoService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Object getDoorInfosByTagName(HttpServletRequest r, String time, String tagname) {
            //@todo 未进行分页显示
        if (StringUtils.isNotEmpty(time)) {
            DateTime dateTime = DateTime.parse(time, DateTimeFormat.forPattern("yyyy-MM-dd"));
            DateTime end = dateTime.plusDays(1);
            DateTime start = dateTime.plusDays(-1);
            List<SubDoor> doorInfoList = doorInfoService.findDoorInfosByTagNameAndDate(tagname, start.toDate(), end.toDate());
            logger.info("--getDoorInfosByTagName---" + doorInfoList.size());
            return doorInfoList;

        } else {
            List<SubDoor> doorInfoList = doorInfoService.findDoorInfosByTagName(tagname);
            return doorInfoList;
        }

    }
}
