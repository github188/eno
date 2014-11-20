package com.energicube.eno.common;

import com.energicube.eno.common.dto.DeviceCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Collection;

/**
 * 发送命令的任务集合
 */
public class SendCommandTask implements Job {
    private static Log logger = LogFactory.getLog(SendCommandTask.class);

    private Config config = new Config();


    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("-------SendCommandTask--------");
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        //发送命令
        Collection<DeviceCommand> deviceCommands = (Collection<DeviceCommand>) jobDataMap.get("deviceCommand");
        for (DeviceCommand command : deviceCommands) {
            SendCommandToSystem.sendCommand(command);
        }
    }
}
