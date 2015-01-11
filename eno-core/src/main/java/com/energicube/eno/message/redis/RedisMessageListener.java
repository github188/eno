package com.energicube.eno.message.redis;

import com.energicube.eno.common.Const;
import com.energicube.eno.message.activemq.MessageSender;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.HashMap;

/**
 * 消息侦听，接收生产者产生的消息,并处理接收到的消息
 *
 * @author CHENPING
 */
public class RedisMessageListener implements MessageListener {
    private final Log logger = LogFactory.getLog(RedisMessageListener.class);


    @Autowired
    private MessageSender messageSender;


    @Autowired
    private RedisOpsService redisOpsService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void onMessage(Message message, byte[] pattern) {

//		logger.info(message);
        try {
            //1、接收侦听到到的消息并发送消息对列
            //messageSender.sendMessageObject(message);

            //2、发送数据到WebSocket
            //消息体格式:  {"id":784,"f":0,"p":2,"t":41710.017384,"v":1692}
            String channelName = convertToString(pattern);

            //订阅发生变化的设备值
            if ("chan:cov_tag".equals(channelName)) {
                //反序列化消息对象
                //TagInfo taginfo = redisOpsService.deserialize(message.toString());
                TagInfo taginfo = null;
                String jsonString = message.toString().substring(1, message.toString().length() - 1);
//            JSONObject jsonObject = JSONObject.fromObject(jsonString);
                HashMap jsonObject = objectMapper.readValue(jsonString, HashMap.class);
                Integer id = Integer.parseInt(jsonObject.get("id").toString());
                Integer f = Integer.parseInt(jsonObject.get("f").toString());
                Integer p = Integer.parseInt(jsonObject.get("p").toString());
                Double t = Double.parseDouble(jsonObject.get("t").toString());
                String v = jsonObject.get("v").toString();
                taginfo = new TagInfo(id, f, p, t, Const.formatValue(v));
//                logger.info("--------chan:cov_tag-------" + id + "---" + v);
                //for(int i=0; i<1000; i++)
                {
                    //taginfo=new TagInfo(i+1, 0, 1, 43456.0, v);

                    if (taginfo != null && !"null".equals(taginfo)) {
                        // [ ChengKang 2014-07-16 ]
                        // 当监听到设备值发生变化时，不再放入List，而是直接调用ValueChanageService发送
                        //TaginfoCollection.getList().add(taginfo);

                        // [ ChengKang 2014-07-16 ]
                        // Self是ValueChanageService的静态成员，存放该类对象的this，提供给外包调用
                        if (ValueChanageService.Self != null) {
                            // 直接调用SendMessage向外发送 [ ChengKang 2014-07-16 ]
                            ValueChanageService.Self.SendMessage(taginfo);
                        } else    // 如果ValueChanageService对象未实例化，则无法向外发送 [ ChengKang 2014-07-16 ]
                        {
//						logger.trace("NOT Send taginfo " + taginfo);
                        }
                    }

                }    // End For

                //TaginfoCollection.setList(list);
            } else if ("chan:cov_alarm1".equals(channelName)) {
                AlarmsInfo alarmsInfo = null;
                String jsonString = message.toString().substring(1, message.toString().length() - 1);
//            JSONObject jsonObject = JSONObject.fromObject(jsonString);
                HashMap jsonObject = objectMapper.readValue(jsonString, HashMap.class);
                String serialNo = jsonObject.get("SerialNo").toString();
                String tagID = jsonObject.get("TagID").toString();
                String almType = jsonObject.get("AlmType").toString();
                String limitValue = jsonObject.get("LimitValue").toString();
                String almGroup = jsonObject.get("AlmGroup").toString();
                String almPriority = jsonObject.get("AlmPriority").toString();
                String tagName = jsonObject.get("TagName").toString();
                String tagComment = jsonObject.get("TagComment").toString();
                String almComment = jsonObject.get("AlmComment").toString();
                String assetID = jsonObject.get("AssetID").toString();
                String propertyID = jsonObject.get("PropertyID").toString();
                String groupName = jsonObject.get("GroupName").toString();
                String groupPath = jsonObject.get("GroupPath").toString();

                /*groupPath = new String(jsonObject.get("GroupPath").toString()
                        .getBytes("GBK"), "utf-8");*/

                String deviceName = jsonObject.get("DeviceName").toString();
                String eCode = jsonObject.get("ECode").toString();
                String almDirection = jsonObject.get("AlmDirection").toString();
                String almTime = jsonObject.get("AlmTime").toString().replace("-", "/");
                String almValue = jsonObject.get("AlmValue").toString();
                String almOperator = jsonObject.get("AlmOperator").toString();
                String ackTime = jsonObject.get("AckTime").toString();
                String ackValue = jsonObject.get("AckValue").toString();
                String ackOperator = jsonObject.get("AckOperator").toString();
                String retTime = jsonObject.get("RetTime").toString();
                String retValue = jsonObject.get("RetValue").toString();
                String retOperator = jsonObject.get("RetOperator").toString();
                String reviewTime = jsonObject.get("ReviewTime").toString();
                String reviewContent = jsonObject.get("ReviewContent").toString();
                String reviewer = jsonObject.get("Reviewer").toString();

                alarmsInfo = new AlarmsInfo(serialNo, tagID, almType, limitValue,
                        almGroup, almPriority, tagName, tagComment, almComment,
                        assetID, propertyID, groupName, groupPath, deviceName,
                        eCode, almDirection, almTime, almValue, almOperator,
                        ackTime, ackValue, ackOperator, retTime, retValue,
                        retOperator, reviewTime, reviewContent, reviewer);

			/*if (list2.size() > 20) {
				list2.clear();
			}*/
                if (alarmsInfo != null && !"null".equals(alarmsInfo)) {
                    //list2.add(alarmsInfo);
                    //TaginfoCollection.getList2().add(alarmsInfo);

                    // [ ChengKang 2014-07-16 ]
                    // Self是ValueChanageService的静态成员，存放该类对象的this，提供给外包调用
                    if (ValueChanageService.Self != null) {
                        // 直接调用SendAlarm向外发送 [ ChengKang 2014-07-16 ]
                        ValueChanageService.Self.SendAlarm(alarmsInfo);
                    } else    // 如果ValueChanageService对象未实例化，则无法向外发送 [ ChengKang 2014-07-16 ]
                    {
//					logger.trace("NOT Send alarmsInfo " + alarmsInfo);
                    }

                }
                //TaginfoCollection.setList2(list2);
            } else if ("chan:uckernal_2_tomcat:expvalue".equals(channelName)) { // 表达式改变值的通道，2014-07-30，张天乐添加
                //System.out.println(message.toString());"{"n":125415,"v":"zhan"}"
                CommandInfo commandInfo = null;
                String jsonString = message.toString().substring(1, message.toString().length() - 1);
//            JSONObject jsonObject = JSONObject.fromObject(jsonString);
                HashMap jsonObject = objectMapper.readValue(jsonString, HashMap.class);
                String n = jsonObject.get("h").toString();
                String v = jsonObject.get("v").toString();
                commandInfo = new CommandInfo();
                commandInfo.setValue(v);
                commandInfo.setP1(n);
                if (commandInfo != null && !"null".equals(commandInfo)) {
                    if (ValueChanageService.Self != null) {
                        ValueChanageService.Self.SendCommandValue(commandInfo);
                    } else {
//					logger.trace("NOT Send taginfo " + commandInfo);
                    }
                }

            } else if ("chan:l2_2_tomcat_4_passenger".equals(channelName)) { // 客流数据通道

                String jsonString = message.toString();
                if (ValueChanageService.Self != null) { // 直接调用SendPassenger向外发送
                    ValueChanageService.Self.SendPassengerInfo(jsonString);
                } else { // 如果ValueChanageService对象未实例化，则无法向外发送
                    logger.trace("NOT Send passengerInfo ");
                }

            }
        } catch (Exception e1) {
            logger.error("redis send msg  error", e1);
        }
    }

    private String convertToString(byte[] pattern) {
        StringBuilder s = new StringBuilder(pattern.length);
        for (int i = 0; i < pattern.length; i++) {
            s.append((char) pattern[i]);
        }
        return s.toString();
    }
}
