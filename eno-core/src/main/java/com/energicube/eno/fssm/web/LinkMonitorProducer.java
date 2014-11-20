package com.energicube.eno.fssm.web;

import com.energicube.eno.fssm.model.LinkMonitor;
import com.energicube.eno.fssm.util.PlazainfoUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Controller
@RequestMapping("/fssm/linkmonitor")
public class LinkMonitorProducer {

    private static Log logger = LogFactory.getLog(LinkMonitorProducer.class);

    private JmsTemplate jmsTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    private InetAddress ip;
    private String msgid;

    @Autowired
    public LinkMonitorProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        try {
            ip = InetAddress.getLocalHost();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 每三分钟向指定的队列发送链路信息
     */
    //@Scheduled(fixedDelay = 10000)
    @Scheduled(fixedDelay = 180000)
    public void sendLinkMonitor() {

        //设置链路基本信息
        msgid = UUID.randomUUID().toString().replaceAll("-", "");
        LinkMonitor linkMonitor = new LinkMonitor();
        linkMonitor.setIpaddress(ip.getHostAddress());
        linkMonitor.setMsgguid(msgid);
        linkMonitor.setTimestamp(LocalDateTime.now().toString("yyyy-MM-dd'T'HH:mm:ss"));
        linkMonitor.setMsgtype("LL");
        linkMonitor.setSyscode(PlazainfoUtil.getInstance().getPlazaSyscode());
        linkMonitor.setUserid(PlazainfoUtil.getInstance().getPlazaManager());
        linkMonitor.setUsertel(PlazainfoUtil.getInstance().getPlazaPhone());

        try {
            //格式化信息为JSON字符
            final String message = mapper.writeValueAsString(linkMonitor);

            //发送消息至指定的队列
            jmsTemplate.send("linkmonitor", new MessageCreator() {
                public Message createMessage(Session session)
                        throws JMSException {
                    logger.info("send linkmonitor info...." + message);
                    return session.createTextMessage(message);
                }

            });

        } catch (JsonGenerationException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (JsonMappingException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }
}
