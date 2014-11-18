package com.energicube.eno.fssm.web;

import com.energicube.eno.fssm.model.CheckMonitor;
import com.energicube.eno.fssm.service.CheckMonitorService;
import com.energicube.eno.fssm.util.PlazainfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * 查岗消息处理
 *
 * @author CHENPING
 */
@Controller
@RequestMapping("/fssm")
public class CheckMonitorController {

    @Autowired
    private CheckMonitorService checkMonitorService;

    /**
     * 响应查岗消息
     *
     * @param checkMonitor 查岗消息对象
     * @param principal    角色对象
     */
    @RequestMapping(value = "/checkmonitor", method = RequestMethod.POST)
    @ResponseBody
    public CheckMonitor reponseCheckMonitor(CheckMonitor checkMonitor,
                                            Principal principal) {
        if (principal != null) {
            checkMonitor.setUserid(principal.getName());
        } else {
            checkMonitor.setUserid(PlazainfoUtil.getInstance().getPlazaManager());
            checkMonitor.setUsertel(PlazainfoUtil.getInstance().getPlazaPhone());
        }

        checkMonitor = checkMonitorService.saveProduceOfCheckMonitor(checkMonitor);

        return checkMonitor;
    }

    @SubscribeMapping("/lastnoresponse")
    public CheckMonitor getNoReponseCheckMonitor() {
        CheckMonitor checkMonitor = checkMonitorService.getLastNoResponseCheckMonitor();
        return checkMonitor;
    }

}
