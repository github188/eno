package com.energicube.eno.fssm.web;

import com.energicube.eno.fssm.model.FireMonitor;
import com.energicube.eno.fssm.service.FireMonitorService;
import com.energicube.eno.fssm.util.PlazainfoUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * 消防消息监视
 *
 * @author CHENPING
 */
@Controller
@RequestMapping("/fssm")
public class FireMonitorController {

    private final static Log logger = LogFactory.getLog(FireMonitorController.class);

    @Autowired
    private FireMonitorService fireMonitorService;

    /**
     * 发送紧急火警
     *
     * @param fireMonitor
     * @author CHENPING
     */
    @RequestMapping(value = "/mergencyfire", method = RequestMethod.POST)
    @ResponseBody
    public FireMonitor sendManualAlarm(Principal principal) {

        DateTimeZone.setDefault(DateTimeZone.forID("Asia/Shanghai"));
        LocalDateTime localDateTime = new LocalDateTime(DateTimeZone.getDefault());

        FireMonitor fireMonitor = new FireMonitor();
        if (principal != null) {
            fireMonitor.setUserid(principal.getName());
        } else {
            fireMonitor.setUserid(PlazainfoUtil.getInstance().getPlazaManager());
            fireMonitor.setUsertel(PlazainfoUtil.getInstance().getPlazaPhone());
        }

        fireMonitor.setSignaltype("JJHJ");    //紧急火警
        fireMonitor.setDisplaycode("HY");    //慧云
        fireMonitor.setDevicetype("HY");
        fireMonitor.setDevicecode("HY");
        fireMonitor.setRelatefile("");
        fireMonitor.setCoordinate("X:0-Y:0");    //坐标
        fireMonitor.setDescription("");
        fireMonitor.setSignaltime(localDateTime.toString("yyyy-MM-dd'T'HH:mm:ss"));
        fireMonitor = fireMonitorService.saveAndSendFireMonitor(fireMonitor);
        return fireMonitor;
    }

    /**
     * 发送火警信息至安监系统
     */
    @RequestMapping(value = "/fire", method = RequestMethod.POST)
    @ResponseBody
    public FireMonitor sendFireAlarm(FireMonitor fireMonitor, Principal principal) {

        logger.info("start save and send firemonitor msg to activemq.......");

        if (!StringUtils.hasLength(fireMonitor.getRelatefile())) {
            String syscode = PlazainfoUtil.getInstance().getPlazaSyscode();
            String businesstype = PlazainfoUtil.getInstance().getBusinessType();
            //1-ABC-DSY-DSY-F3
            if (!StringUtils.hasLength(fireMonitor.getRelatefile())) {
                String relatefile = String.format("1_%s_%s_%s_%s", syscode, businesstype, businesstype, "F1");
                fireMonitor.setRelatefile(relatefile);
            }
        }

        if (!StringUtils.hasLength(fireMonitor.getUserid())) {
            fireMonitor.setUserid(PlazainfoUtil.getInstance().getPlazaManager());
        }
        if (!StringUtils.hasLength(fireMonitor.getUsertel())) {
            fireMonitor.setUsertel(PlazainfoUtil.getInstance().getPlazaPhone());
        }

        //发送并保存发送记录
        fireMonitor = fireMonitorService.saveAndSendFireMonitor(fireMonitor);

        return fireMonitor;

    }


}
