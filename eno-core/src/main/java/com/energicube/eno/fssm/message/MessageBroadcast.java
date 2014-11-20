package com.energicube.eno.fssm.message;

import com.energicube.eno.fssm.model.CheckMonitor;
import com.energicube.eno.fssm.model.FireMonitor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;

import java.util.List;

public class MessageBroadcast {

    private final static Log logger = LogFactory.getLog(MessageBroadcast.class);

    private static MessageSendingOperations<String> messagingTemplate;

    @Autowired
    public MessageBroadcast(MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public static void sendCheckMonitor(CheckMonitor checkMonitor) {
        if (checkMonitor != null) {
            messagingTemplate.convertAndSend("/queue/checkmonitor", checkMonitor);
        }
    }

    public static void sendFireMonitor(List<FireMonitor> fireMonitors) {
        if (fireMonitors != null && fireMonitors.size() > 0) {
            logger.info("send firemonitor msg to websocket,size:" + fireMonitors.size());
            messagingTemplate.convertAndSend("/queue/firemonitor", fireMonitors);
        }
    }


}
