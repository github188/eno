package com.energicube.eno.fssm.service.impl;

import com.energicube.eno.fssm.message.MessageBroadcast;
import com.energicube.eno.fssm.model.CheckMonitor;
import com.energicube.eno.fssm.repository.CheckMonitorRepository;
import com.energicube.eno.fssm.service.CheckMonitorService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;
import java.util.UUID;


/**
 * 查岗业务实现类
 *
 * @author CHENPING
 */
@Service
public class CheckMonitorServiceImpl implements CheckMonitorService {

    private static final Log logger = LogFactory.getLog(CheckMonitorServiceImpl.class);

    private CheckMonitorRepository checkMonitorRepository;


    private JmsTemplate jmsTemplate;

    @Autowired
    public CheckMonitorServiceImpl(CheckMonitorRepository checkMonitorRepository,
                                   JmsTemplate jmsTemplate) {
        this.checkMonitorRepository = checkMonitorRepository;
        this.jmsTemplate = jmsTemplate;
    }


    @Transactional
    public CheckMonitor saveConsumeOfCheckMonitor(CheckMonitor checkMonitor) {
        //保存接收到的查岗信息
        checkMonitor = checkMonitorRepository.save(checkMonitor);
        //发送消息到WEBSOCKET
        MessageBroadcast.sendCheckMonitor(checkMonitor);
        return checkMonitor;
    }

    @Transactional
    public CheckMonitor saveProduceOfCheckMonitor(CheckMonitor checkMonitor) {

        LocalDateTime now = LocalDateTime.now();

        //关联消息ID
        String relatemsgid = checkMonitor.getMsgid();
        if (StringUtils.hasLength(relatemsgid)) {
            CheckMonitor relateCheckMonitor = checkMonitorRepository.findOne(relatemsgid);
            //查岗消息时间
            LocalDateTime checktime;
            if (relateCheckMonitor != null) {
                relateCheckMonitor.setStatus(1);
                checkMonitorRepository.save(relateCheckMonitor);
                checktime = relateCheckMonitor.getTimestamp();

                //获取时间差，设置查岗类型
                long diff = now.getMillisOfDay() - checktime.getMillisOfDay();

                long diffSec = diff / 1000;
                if (diffSec > relateCheckMonitor.getResponsetime()) {
                    checkMonitor.setChecktype("TG");
                } else {
                    checkMonitor.setChecktype("ZG");
                }
            }
        }

        if (!StringUtils.hasLength(checkMonitor.getUserid())) {

        }

        //设置关联MSGID
        checkMonitor.setRelatemsg(relatemsgid);

        //生成UUID
        String msgid = UUID.randomUUID().toString().replaceAll("-", "");
        checkMonitor.setMsgid(msgid);


        //当前发送时间
        checkMonitor.setTimestamp(now);
        checkMonitor.setStatus(1);


        //保存响应的查岗消息
        checkMonitor = checkMonitorRepository.save(checkMonitor);


        //构造查岗消息字符串
        final String message = String
                .format("{\"msgid\":\"%s\",\"msgtype\":\"%s\",\"syscode\":\"%s\",\"timestamp\":\"%s\",\"checktype\":\"%s\",\"relatemsg\":\"%s\",\"description\":\"%s\",\"userid\":\"\",\"usertel\":\"\"}",
                        msgid, "CG", checkMonitor.getSyscode(), now.toString("yyyy-MM-dd'T'HH:mm:ss"), checkMonitor.getChecktype(), checkMonitor.getRelatemsg(), checkMonitor.getDescription(), checkMonitor.getUserid(), checkMonitor.getUsertel());

        //发送查岗消息
        jmsTemplate.send("checkmonitor", new MessageCreator() {

            public Message createMessage(Session session) throws JMSException {

                logger.info("response checkmonitor:" + message);

                return session.createTextMessage(message);
            }

        });


        return checkMonitor;
    }


    @Transactional(readOnly = true)
    public CheckMonitor getLastNoResponseCheckMonitor() {
        List<CheckMonitor> result = checkMonitorRepository.findLastNoResponseCheckMonitor();
        if (result != null && result.size() > 0)
            return result.get(0);
        return null;
    }


    @Transactional(readOnly = true)
    public List<CheckMonitor> findAllConsumeOfCheckMonitor() {
        return checkMonitorRepository.findAllConsumeOfCheckMonitor();
    }

    @Transactional(readOnly = true)
    public List<CheckMonitor> findAllProduceOfCheckMonitor() {
        return checkMonitorRepository.findAllProduceOfCheckMonitor();
    }

    @Transactional(readOnly = true)
    public List<CheckMonitor> findConsumeOfCheckMonitorByDateRange(
            LocalDateTime starttime, LocalDateTime endtime) {
        return checkMonitorRepository.findConsumeOfCheckMonitorByDateRange(starttime, endtime);
    }

    @Transactional(readOnly = true)
    public List<CheckMonitor> findProduceOfCheckMonitorByDateRange(
            LocalDateTime starttime, LocalDateTime endtime) {

        return checkMonitorRepository.findProduceOfCheckMonitorByDateRange(starttime, endtime);
    }

    public static void main(String[] args) {


    }
}
