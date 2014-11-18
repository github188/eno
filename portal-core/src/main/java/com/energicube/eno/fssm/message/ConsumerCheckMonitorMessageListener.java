package com.energicube.eno.fssm.message;

import com.energicube.eno.common.JsonValidUtil;
import com.energicube.eno.fssm.model.CheckMonitor;
import com.energicube.eno.fssm.service.CheckMonitorService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * 监听查岗消息
 *
 * @author CHENPING
 */
public class ConsumerCheckMonitorMessageListener implements MessageListener {

    private final static Log logger = LogFactory
            .getLog(ConsumerCheckMonitorMessageListener.class);

    @Autowired
    private CheckMonitorService checkMonitorService;

    private final ObjectMapper mapper = new ObjectMapper();


    public void onMessage(Message message) {

        if (message instanceof TextMessage) {

            String msgContent;
            CheckMonitor checkMonitor = null;
            try {
                // 获取查岗消息内容
                msgContent = ((TextMessage) message).getText();

                // 验证消息内容格式是否有效
                if (JsonValidUtil.isValidJSON(msgContent)) {

                    // 解析消息
                    checkMonitor = mapper.readValue(msgContent, CheckMonitor.class);
                    // 验证消息类型
                    if (checkMonitor != null && "CG".equals(checkMonitor.getMsgtype()) && ("CG").equals(checkMonitor.getChecktype())) {
                        // 持久化消息
                        checkMonitor = checkMonitorService.saveConsumeOfCheckMonitor(checkMonitor);

                    }
                } else {
                    logger.error("接收的查岗消息内容无效,内容：" + msgContent);
                }

            } catch (JMSException e) {
                e.printStackTrace();
                logger.error("监听查岗消息异常：" + e.getMessage());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error("Jackson解析查岗消息异常：" + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Jackson解析查岗消息IO异常：" + e.getMessage());
            }

        }

    }

}
