package com.energicube.eno.monitor.web;


import com.energicube.eno.asset.repository.AssetRepository;
import com.energicube.eno.message.redis.CommandInfo;
import com.energicube.eno.message.redis.PassengerCmdInfo;
import com.energicube.eno.message.redis.RedisOpsService;
import com.energicube.eno.message.redis.TagInfo;
import com.energicube.eno.monitor.model.*;
import com.energicube.eno.monitor.repository.PagetagRepository;
import com.energicube.eno.monitor.repository.jpa.AssetMeasurementRepository;
import com.energicube.eno.monitor.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Socket通信消息控制
 *
 * @author CHENPING
 * @version 1.0
 */
@Controller
public class SocketMsgController {

    private static final Log logger = LogFactory.getLog(SocketMsgController.class);

    @Autowired
    private PagetagService pagetagService;

    @Autowired
    private PagelayoutService pagelayoutService;

    @Autowired
    private ExpressionService expressionService;

    @Autowired
    private RedisOpsService redisOpsService;

    @Autowired
    private SyscontrolService syscontrolService;

    @Autowired
    private OkcLogsService okcLogsService;

    @Autowired
    private PagetagRepository pagetagRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetMeasurementRepository assetMeasurementRepository;

    /**
     * 返回指定菜单对应的设备
     *
     * @param nativeHeaders message header
     */
    @SubscribeMapping("/positions")
    public Map<String, List> getPositions(@Header Map<String, List<String>> nativeHeaders) throws Exception {

        String menuid = nativeHeaders.get("menuid") != null ? nativeHeaders.get("menuid").get(0) : "";
        String layoutid = nativeHeaders.get("layoutid") != null ? nativeHeaders.get("layoutid").get(0) : "";
        String elementvalue = nativeHeaders.get("elementvalue") != null ? nativeHeaders.get("elementvalue").get(0) : "";

        logger.info("websocket request positions for menuid:" + menuid + ",layoutid:" + layoutid + ",elementvalue:" + elementvalue);
        if (!StringUtils.hasLength(menuid)) {
            throw new IllegalArgumentException("请求菜单ID不能为空");
        }

        // 返回指定布局的测量点和控件信息
        Map<String, List> map = pagetagService.findPagetagAndControlByMenuid(menuid, layoutid, elementvalue);
        // 表达式列表
        List<Expression> expressions = expressionService.findAllExp();
        map.put("expressions", expressions);

        List<Pagetag> list = map.get("pagetag");
        map.put("pagetag", list);

        // 根据layoutid查找对应配置表中的项，[2014-08-03 zzx]
        List<Pagelayout> pageLayoutList = pagelayoutService.findByLayoutid(layoutid);
        map.put("pageLayoutList", pageLayoutList);

        if (logger.isDebugEnabled()) {
            logger.info("websocket request result,map size:" + map.size());
        }

        return map;
    }


    /**
     * 获取指定菜单ID对应的设备列表
     *
     * @param menuid 菜单ID
     * @return tags and controls list
     */
    @RequestMapping("/app/positions")
    @ResponseBody
    public Map<String, List> getPositions(@RequestParam("menuid") String menuid,
                                          @RequestParam("elementvalue") String elementvalue) throws Exception {

        logger.info("web ajax request positions for menuid:" + menuid);
        Map<String, List> map = new HashMap<String, List>();
        if (StringUtils.hasLength(menuid)) {
            map = pagetagService.findPagetagAndControlByMenuid(menuid, "", elementvalue);
        } else {
            throw new IllegalArgumentException("菜单ID不能为空");
        }

        if (logger.isDebugEnabled()) {
            logger.info("web ajax request result map size:" + map.size());
        }
        return map;
    }

    /**
     * 获取指定菜单下所有设备点最后的数据
     *
     * @param nativeHeaders header
     * @return {@link Pagetag} of list
     */
    @SubscribeMapping("/lastValue")
    public List<Pagetag> getTagLastValue(@Header Map<String, List<String>> nativeHeaders) throws Exception {

        String menuid = nativeHeaders.get("menuid") != null ? nativeHeaders.get("menuid").get(0) : "";
        logger.debug("get tag last value by menuid:" + menuid);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("--进来--" + sf.format(new Date()));
        List<Pagetag> lastValues = null;
        if (StringUtils.hasLength(menuid)) {
            lastValues = pagetagService.getTagLastValues(menuid);
        }

        logger.info("--执行完--" + sf.format(new Date()));
        return lastValues;
    }

    /**
     * 订阅所有变量变量
     *
     * @param nativeHeaders 消息头
     */
    @SubscribeMapping("/controls")
    public List<Syscontrol> getControls(@Header Map<String, List<String>> nativeHeaders) throws Exception {
        List<Syscontrol> syscontrols = syscontrolService.findAll();
        return syscontrols;
    }

    /**
     * 清除静态变量中保存的点信息
     *
     * @param listtwo
     */
    public void remoListInfos(List<Pagetag> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                list.remove(i);
            }
        }
    }

    /**
     * 更改变量值
     */
    @MessageMapping("/change")
    public void executeUpdate(@RequestBody Map map) {

        TagInfo tagInfo = new TagInfo();

        int id = 0;
        int f = 0, p = 0;
        double t = 0;
        String v = "";

        if (map.containsKey("id")) {
            id = Integer.parseInt(map.get("id").toString());
            tagInfo.setId(id);
        }
        if (map.containsKey("f")) {
            f = Integer.parseInt(map.get("f").toString());
            tagInfo.setF(f);
        }
        if (map.containsKey("p")) {
            p = Integer.parseInt(map.get("p").toString());
            tagInfo.setP(p);
        }
        if (map.containsKey("t")) {
            t = Double.parseDouble(map.get("t").toString());
            tagInfo.setT(t);
        }
        if (map.containsKey("v")) {
            v = map.get("v").toString();
            tagInfo.setV(v);
        }

        logger.debug("taginfo:" + tagInfo.toSendString());
        this.redisOpsService.sendTagInfo(tagInfo);

    }

    /**
     * 界面热区单击发送给Redis的消息  [ ChengKang 2014-08-02 ]
     *
     * @param id
     * @param f
     * @param p
     * @param t
     * @param v
     */
    @RequestMapping("/tag/change")
    @ResponseBody
    public void executeUpdate(int id, int f, int p, double t, String v) {

        TagInfo tagInfo = new TagInfo();
        tagInfo.setId(id);
        tagInfo.setF(f);
        tagInfo.setP(p);
        tagInfo.setT(t);
        tagInfo.setV(v);

        logger.debug("taginfo:" + tagInfo.toSendString());
        this.redisOpsService.sendTagInfo(tagInfo);
    }

    /**
     * 更改客流值
     */
    @MessageMapping("/pfechange")
    public void executePfeUpdate(@RequestBody Map map) {
        PassengerCmdInfo pfeinfo = new PassengerCmdInfo();

        String partype = "", paramter = "", name = "", childpoint = "";

        if (map.containsKey("partype")) {
            partype = map.get("partype").toString();
            pfeinfo.setPartype(partype);
        }

        if (map.containsKey("paramter")) {
            paramter = map.get("paramter").toString();
            pfeinfo.setParamter(paramter);
        }

        if (map.containsKey("name")) {
            name = map.get("name").toString();
            pfeinfo.setName(name);
        }

        if (map.containsKey("childpoint")) {
            childpoint = map.get("childpoint").toString();
            pfeinfo.setChildpoint(childpoint);
        }

        this.redisOpsService.sendPfeCommand(pfeinfo);
    }

    //添加表达式字段，实现添加或修改表达式ztl
    @MessageMapping("/update/pagetag")
    public void updatePagetagInfo(@RequestBody Map map) {
        long pagetagid = 0;
        String controlid = "", controlid2 = "", controlid3 = "", expressions = "";
        Pagetag pagetag = null;
        if (map.containsKey("pagetagid")) {
            pagetagid = Long.parseLong(map.get("pagetagid").toString());
        }
        if (map.containsKey("controlid")) {
            controlid = map.get("controlid").toString();
        }
        if (map.containsKey("controlid2")) {
            controlid2 = map.get("controlid2").toString();
        }
        if (map.containsKey("controlid3")) {
            controlid3 = map.get("controlid3").toString();
        }
        if (map.containsKey("expressions")) {
            expressions = map.get("expressions").toString();
        }
        if (pagetagid > 0) {
            pagetag = pagetagService.findOne(pagetagid);
            if (StringUtils.hasLength(controlid)) {
                pagetag.setControlid(controlid);
            }
            if (StringUtils.hasLength(controlid2)) {
                pagetag.setControlid2(controlid2);
            }
            if (StringUtils.hasLength(controlid3)) {
                pagetag.setControlid3(controlid3);
            }
            if (StringUtils.hasLength(expressions)) {
                pagetag.setExpressions(expressions);
            }
            pagetag = pagetagService.savePagetag(pagetag);

            logger.debug("pagetag update successed!");
        }
    }


    /**
     * 异常处理
     */
    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }


    /**
     * 面板中按钮点击命令发送
     * 格式：PUBLISH %s "{"cmd":"%s","p1":"%s"}
     *
     * @param p1
     * @param eventScript
     * @param model
     * @return
     */
    @RequestMapping(value = "/app/panelButtionEvent")
    public
    @ResponseBody
    String panelButtionEvent(String script_index, Model model) {
        long expId = Long.valueOf(script_index);
        Expression expression = expressionService.findOneExpression(expId);
        CommandInfo commandInfo = new CommandInfo();
        commandInfo.setP1("4");
        commandInfo.setCmd(expression.getExpcontent());
        this.redisOpsService.sendCommand(commandInfo);
        return null;
    }

    /**
     * 面板中文本框值改变命令发送
     *
     * @param p1
     * @param inputval
     * @param model
     * @return
     */
    @RequestMapping(value = "/app/panelInputEvent")
    public
    @ResponseBody
    String panelInputEvent(String p1, String inputval, Model model) {
        CommandInfo commandInfo = new CommandInfo();
        commandInfo.setP1("2");
        commandInfo.setCmd(inputval);
        this.redisOpsService.sendCommand(commandInfo);
        return null;
    }

    //发送查询命令到uc
    @RequestMapping(value = "/app/getinfo")
    @ResponseBody
    public void getInfo(String map) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList arrayList = objectMapper.readValue(map, ArrayList.class);
            //查询所有表达式
            List<Expression> exceptions = expressionService.findAllExp();
            Map<String, String> map_exp = new HashMap<String, String>();
            for (int i = 0; i < arrayList.size(); i++) {
                HashMap hashMap = objectMapper.readValue(arrayList.get(i).toString(), HashMap.class);
                String id = hashMap.get("id").toString();
                String expid = hashMap.get("exp").toString();
                String scriptString = "";
                for (int j = 0; j < exceptions.size(); j++) {
                    if (exceptions.get(j).getExpindex() == Long.valueOf(expid)) {
                        scriptString = exceptions.get(j).getExpcontent();
                    }
                }
                map_exp.put(id, scriptString);
            }
            this.redisOpsService.sendExpression(map_exp);

        } catch (Exception e) {
            logger.error("getInfo", e);
        }
    }


    //电子巡更点击发送表达式
    @MessageMapping("/send/SASSA/expre")
    public void SendEPExpression(@RequestBody Map map) {
        CommandInfo info = new CommandInfo();
        String p1;
        String v;
        String cmd;
        if (map.containsKey("p1")) {
            p1 = map.get("p1").toString();
            info.setP1(p1);
        }
        if (map.containsKey("v")) {
            v = map.get("v").toString();
            info.setValue(v);
        }
        if (map.containsKey("cmd")) {
            cmd = map.get("cmd").toString();
            info.setCmd(cmd);
        }
        this.redisOpsService.sendCommand(info);
    }

    /**
     * 获取某个页面上所有EDT设备的参数面板数据
     *
     * @param nativeHeaders header
     * @return {@link ClassSpec} of list
     */
    @SubscribeMapping("/getETDClassSpecList")
    @ResponseBody
    public Map<String, Object> getETDClassSpecList(@Header Map<String, List<String>> nativeHeaders) throws Exception {

        String pagelayoutuid = nativeHeaders.get("pagelayoutuid") != null ? nativeHeaders.get("pagelayoutuid").get(0) : "";
        logger.info("getETDClassSpecList by pagelayoutuid:" + pagelayoutuid);
        Map<String, Object> retMap = null;
		try {
			retMap = pagetagService.findETDPanelDatas(pagelayoutuid);
		} catch (Exception e) {
			logger.debug(e);
		}
        return retMap;

    }
}
