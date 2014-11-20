package com.energicube.eno.message.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ValueChanageService implements
        ApplicationListener<BrokerAvailabilityEvent> {
    private static Log logger = LogFactory.getLog(ValueChanageService.class);

    private final MessageSendingOperations<String> messagingTemplate;

    // [ ChengKang 2014-07-16 16:03:14 ]
    // 静态变量用于存放类实例对象的this指针
    // 外部类可通过Self访问本类的成员函数
    public static ValueChanageService Self;

    private AtomicBoolean brokerAvailable = new AtomicBoolean();

    @Autowired
    public ValueChanageService(
            MessageSendingOperations<String> messagingTemplate) {
        Self = this; // 存放对象的this指针，供其他类调用 [ ChengKang 2014-07-16 16:00:24 ]
        this.messagingTemplate = messagingTemplate;
    }

    public void onApplicationEvent(BrokerAvailabilityEvent event) {
        this.brokerAvailable.set(event.isBrokerAvailable());
    }

    /**
     * 发送
     */
    // 该方法已经不用，由SendMessage方法替代其功能 [ ChengKang 2014-07-16 16:03:30 ]
    @Scheduled(fixedDelay = 2000)
    public void sendQuotes() {
        List<TagInfo> taginfos = TaginfoCollection.getList();
        List<AlarmsInfo> taginfos2 = TaginfoCollection.getList2();

        if (taginfos != null && taginfos.size() > 0) {
            for (TagInfo taginfo : taginfos) {
//				logger.info(taginfo);
                if (logger.isTraceEnabled()) {
//					logger.trace("Sending taginfo " + taginfo);
                }
                if (this.brokerAvailable.get()) {
                    this.messagingTemplate.convertAndSend("/topic/value.tag."
                            + taginfo.getId(), taginfo);
                }
            }
            // 链表数据发送完毕，执行清空
            taginfos.clear();
        }
        if (taginfos2 != null && taginfos2.size() > 0) {
            for (AlarmsInfo alarmsInfo : taginfos2) {
//				logger.info(alarmsInfo);

                if (logger.isTraceEnabled()) {
//					logger.trace("Sending alarmsInfo " + alarmsInfo);
                }
                if (this.brokerAvailable.get()) {
                    this.messagingTemplate.convertAndSend("/topic/value.tag."
                            + alarmsInfo.getTagID(), alarmsInfo);
                }
            }
            taginfos2.clear();
        }
    }

    // 发送taginfo信息 [ ChengKang 2014-07-16 ]
    public void SendMessage(TagInfo taginfo) {
//		logger.info(taginfo);
        if (logger.isTraceEnabled()) {
//			logger.trace("Sending taginfo " + taginfo);
        }
        if (this.brokerAvailable.get()) {
            this.messagingTemplate.convertAndSend(
                    "/topic/value.tag." + taginfo.getId(), taginfo);
        }
    }

    // 发送alarminfo信息 [ ChengKang 2014-07-16 ]
    public void SendAlarm(AlarmsInfo alarmInfo) {
//		logger.info(alarmInfo);

        if (logger.isTraceEnabled()) {
//			logger.trace("Sending alarmsInfo " + alarmInfo);
        }
        if (this.brokerAvailable.get()) {
            this.messagingTemplate.convertAndSend("/topic/value.tag."
                    + alarmInfo.getTagID(), alarmInfo);
        }
    }

    // 发送taginfo信息 [ zouzhixiang 2014-08-04 ]
    public void SendPassengerInfo(String jsonString) {
        if (logger.isTraceEnabled()) {
            logger.trace("Sending passengerInfo " + jsonString);
        }
        if (this.brokerAvailable.get()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                HashMap hashMap = objectMapper.readValue(jsonString, HashMap.class);
//            JSONObject jsonObject = JSONObject.fromObject(jsonString);
                String paramter = hashMap.get("paramter").toString(); // 店铺名称,对应配置表中的paramter
                String name = hashMap.get("name").toString(); // 对应配置表中的name
                this.messagingTemplate.convertAndSend("/topic/pfe.tag." + paramter + "_" + name, hashMap);
            } catch (Exception e) {
                logger.error("SendPassengerInfo", e);
            }
        }
    }

    //向前台发送楼层统计信息[ztl 20140909]
    public void SendCommandValue(CommandInfo commandInfo) {
//		logger.info(commandInfo);

        if (logger.isTraceEnabled()) {
//			logger.trace("Sending commandInfo " + commandInfo);
        }
        if (this.brokerAvailable.get()) {
            this.messagingTemplate.convertAndSend("/topic/buildinfo." + commandInfo.getP1(), commandInfo);
        }

    }
}