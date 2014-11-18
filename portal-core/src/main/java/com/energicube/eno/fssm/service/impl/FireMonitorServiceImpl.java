package com.energicube.eno.fssm.service.impl;

import com.energicube.eno.fssm.message.MessageBroadcast;
import com.energicube.eno.fssm.model.FireMonitor;
import com.energicube.eno.fssm.model.UcAlarmactiveSet;
import com.energicube.eno.fssm.repository.FireMonitorRepository;
import com.energicube.eno.fssm.repository.jpa.JpaFireMonitorRepository;
import com.energicube.eno.fssm.service.FireMonitorService;
import com.energicube.eno.fssm.util.PlazainfoUtil;
import com.energicube.eno.monitor.model.KeyValue;
import com.energicube.eno.alarm.model.UcAlarmactive;
import com.energicube.eno.monitor.model.UcTaginfoExt;
import com.energicube.eno.alarm.service.UcAlarmactiveService;
import com.energicube.eno.alarm.service.UcAlarmlogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTimeZone;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 火警信息业务处理类
 *
 * @author CHENPING
 */
@Service
public class FireMonitorServiceImpl implements FireMonitorService {

    private final static Log logger = LogFactory
            .getLog(FireMonitorServiceImpl.class);

    private FireMonitorRepository fireMonitorRepository;

    private JpaFireMonitorRepository jpaFireMonitorRepository;

    private final JmsTemplate jmsTemplate;

    private UcAlarmactiveService ucAlarmactiveService;
    private UcAlarmlogService ucAlarmlogService;

    @Autowired
    public FireMonitorServiceImpl(FireMonitorRepository fireMonitorRepository,
                                  JmsTemplate jmsTemplate,
                                  JpaFireMonitorRepository jpaFireMonitorRepository,
                                  UcAlarmactiveService ucAlarmactiveService,
                                  UcAlarmlogService ucAlarmlogService) {
        this.fireMonitorRepository = fireMonitorRepository;
        this.jpaFireMonitorRepository = jpaFireMonitorRepository;
        this.ucAlarmactiveService = ucAlarmactiveService;
        this.ucAlarmlogService = ucAlarmlogService;
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional
    public FireMonitor saveAndSendFireMonitor(FireMonitor fireMonitor) {

        logger.info("execute saveAndSendFireMonitor...");
        logger.info("fireMonitor model:" + fireMonitor);

        DateTimeZone.setDefault(DateTimeZone.forID("Asia/Shanghai"));
        LocalDateTime localDateTime = new LocalDateTime(
                DateTimeZone.getDefault());

        fireMonitor.setMsgtype("XF"); // 消息类型
        fireMonitor.setSyscode(PlazainfoUtil.getInstance().getPlazaSyscode()); // 系统编码
        fireMonitor.setTimestamp(localDateTime.toString("yyyy-MM-dd'T'HH:mm:ss"));
        fireMonitor.setBusinesstype(PlazainfoUtil.getInstance().getBusinessType());

        // 保存消防消息
        fireMonitor = fireMonitorRepository.save(fireMonitor);

        ObjectMapper mapper = new ObjectMapper();
        try {
            final String message = mapper.writeValueAsString(fireMonitor);

            // 发送消防消息至MQ,对列名称为 firemonitor
            jmsTemplate.send("firemonitor", new MessageCreator() {

                public Message createMessage(Session session)
                        throws JMSException {
                    logger.info("send firemonitor msg to activemq:" + message);
                    return session.createTextMessage(message);
                }
            });

            //更新历史，删除已处理的实时数据
            if (fireMonitor.getAlmlogid() > 0) {
                //删除实时数据
                ucAlarmactiveService.deleteUcAlarmactive(fireMonitor.getAlmlogid());
                //应答历史数据
                ucAlarmlogService.batchSaveUcAlarmlog(fireMonitor.getAlmlogid());
            }


        } catch (JsonGenerationException e) {
            logger.error("生成消防消息失败：" + e.getMessage());
            e.printStackTrace();
        } catch (JsonMappingException e) {
            logger.error("生成消防消息失败:" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("生成消防消息失败:" + e.getMessage());
            e.printStackTrace();
        }

        return fireMonitor;
    }

    /**
     * 获取实时报警信息，并发送报警信息到页面
     *
     * @author CHENPING
     */
    @Transactional(readOnly = true)
    public List<FireMonitor> findFireMonitors() {

        String syscode = PlazainfoUtil.getInstance().getPlazaSyscode();
        String businessType = PlazainfoUtil.getInstance().getBusinessType();
        // SimpleDateFromat
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        // 获取所有未处理的实时火警
        List<UcAlarmactiveSet> ucAlarmactiveSets = jpaFireMonitorRepository.findAlarmActivelog();


        List<FireMonitor> results = new LinkedList<FireMonitor>();

        // 结果列表
        if (ucAlarmactiveSets == null || ucAlarmactiveSets.size() == 0) {
            return results;
        }
        for (UcAlarmactiveSet ucAlarmactiveSet : ucAlarmactiveSets) {

            FireMonitor fireMonitor = new FireMonitor();
            UcTaginfoExt ucTaginfoExt = null;
            // 每条内容
            if (ucAlarmactiveSet.getUcTaginfoExt() != null) {
                ucTaginfoExt = ucAlarmactiveSet.getUcTaginfoExt();
                fireMonitor.setCoordinate(ucTaginfoExt.getCoordinate());
                fireMonitor.setDevicetype(ucTaginfoExt.getDevicetype());

                fireMonitor.setDevicecode(ucTaginfoExt.getDevicecode());
                fireMonitor.setDisplaycode(ucTaginfoExt.getDisplaycode());
                fireMonitor.setDevicelocation(ucTaginfoExt.getDevicelocation());
                String floorNum = StringUtils.hasLength(ucTaginfoExt
                        .getFloornum()) ? ucTaginfoExt.getFloornum() : "F1";

                String relatefile = String.format("1_%s_%s_%s_%s", syscode,
                        businessType, businessType, floorNum);
                fireMonitor.setRelatefile(relatefile);
            }
            if (ucAlarmactiveSet.getUcAlarmactive() != null) {
                UcAlarmactive ucAlarmactive = ucAlarmactiveSet
                        .getUcAlarmactive();

                if (StringUtils.hasLength(ucAlarmactive.getTagname())
                        && !StringUtils.hasLength(fireMonitor.getDevicecode())) {
                    fireMonitor.setDevicecode(ucAlarmactive.getTagname());
                }

                if (StringUtils.hasLength(ucAlarmactive.getTagname())
                        && !StringUtils.hasLength(fireMonitor.getDevicecode())) {
                    fireMonitor.setDisplaycode(ucAlarmactive.getTagname());
                }
                if (StringUtils.hasLength(ucAlarmactive.getTagcomment())
                        && !StringUtils.hasLength(fireMonitor.getDevicelocation())) {
                    fireMonitor.setDevicelocation(ucAlarmactive.getTagcomment());
                }
                fireMonitor.setDescription(ucAlarmactive.getTagcomment());
                fireMonitor.setSignaltime(sdf.format(ucAlarmactive.getAlmt()));
                fireMonitor.setAlmlogid(ucAlarmactive.getAlmlogid());

                //如果扩展数据为空时，从TAGCOMMENT中分析并获取DEVICETYPE
                //设备类型列表：  慧云系统: HY  烟感:YG  温感:WG  手动报警器:SD  漏电:LD 感温电缆:GWDL  消防水泡:XFSP  气体灭火:QTMH  燃气:RQ
                if (ucTaginfoExt == null) {
                    String tagcomment = ucAlarmactive.getTagcomment();
                    if (StringUtils.hasLength(tagcomment)) {

                        List<KeyValue> devicetypes = getDevicetypes();
                        for (KeyValue keyvalue : devicetypes) {
                            if (tagcomment.indexOf(keyvalue.getValue()) > -1) {
                                fireMonitor.setDevicetype(keyvalue.getKey());
                            }
                        }
                    }
                }
            }
            fireMonitor.setSyscode(syscode);
            results.add(fireMonitor);
        }
        // 发送消息到WebSocket
        MessageBroadcast.sendFireMonitor(results);
        return results;
    }

    public List<KeyValue> getDevicetypes() {
        List<KeyValue> list = new ArrayList<KeyValue>();
        list.add(new KeyValue("YG", "烟感"));
        list.add(new KeyValue("WG", "温感"));
        list.add(new KeyValue("SD", "手动报警"));
        list.add(new KeyValue("LD", "漏电"));
        list.add(new KeyValue("GWDL", "感温电缆"));
        list.add(new KeyValue("XFSP", "消防水泡"));
        list.add(new KeyValue("QTMH", "气体灭火"));
        list.add(new KeyValue("RQ", "燃气"));
        return list;
    }


    public static void main(String[] args) {

    }
}
