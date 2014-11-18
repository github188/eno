package com.energicube.eno.common;

import com.energicube.eno.common.dto.DeviceCommand;
import com.energicube.eno.pattern.model.UcDeviceStrategy;
import com.energicube.eno.pattern.service.PatternService;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-9-17
 * Time: 下午6:13
 * To change this template use File | Settings | File Templates.
 */
public class StrategyTask implements Job {

    private static Log logger = LogFactory.getLog(StrategyTask.class);

    private Config config = new Config();


    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap jobDataMap = context.getMergedJobDataMap();

        PatternService patternService = (PatternService) jobDataMap.get("patternService");
        DeviceCommand deviceCommand = (DeviceCommand) jobDataMap.get("deviceCommand");
        //检查是否符合策略的条件
        boolean f = checkStrategy(patternService, deviceCommand);
        if (f) {
            //检查是否已经执行
            boolean execute = checkExeceuteStrategy(deviceCommand);
            if (execute) {
                // 检查任务在半小时内执行
                long prove = deviceCommand.getExecuteTime();
                long now = System.currentTimeMillis();
                long va = now - prove;
                if (va < 1800000) { //小于半小时，不执行命令

                } else {
                    //执行命令
                    SendCommandToSystem.sendCommand(deviceCommand);
                    //sendCommand(deviceCommand);
                }
            } else {
                //执行命令
//                 sendCommand(deviceCommand);
                SendCommandToSystem.sendCommand(deviceCommand);
            }
        }
    }

    /**
     * 检查是否符合策略执行的条件
     *
     * @param patternService 模式服务接口
     * @param deviceCommand  策略
     * @return
     */
    private boolean checkStrategy(PatternService patternService, DeviceCommand deviceCommand) {
        //查询到策略
        UcDeviceStrategy ucDeviceStrategy = patternService.findDeviceStrategy(deviceCommand.getStrategyId());
        String va = ucDeviceStrategy.getCompareValue();//比较的值
        String compare = ucDeviceStrategy.getCompareSymbol();//比较的符号
        String valueType = ucDeviceStrategy.getValueType();//比较的符号
        boolean result = false;
        //T--布尔型真 F--布尔值假  N--数字 P---项目
        if (valueType != null && !"".equals(compare)) {
            if (valueType.equals(PatternConst.STRATEGY_COMPARE_TYPE_NUM)) {

                String tagId = patternService.findTagId(deviceCommand.getDeviceId(), ucDeviceStrategy.getItemParam());

                String tagValue = getTagValue(tagId);
                if (tagValue != null && !"".equals(tagValue)) {
                    //比较是否符合条件
                    result = PatternConst.compareStrategyVa(tagValue, compare, va);
                }

                return result;
            }
            if (valueType.equals(PatternConst.STRATEGY_COMPARE_TYPE_ITEM)) {  //项目比较

                String tagId = patternService.findTagId(deviceCommand.getDeviceId(), ucDeviceStrategy.getItemParam());

                String oldVa = getTagValue(tagId);

                tagId = patternService.findTagId(deviceCommand.getDeviceId(), ucDeviceStrategy.getCompareParam());

                String newVa = getTagValue(tagId);
                if (oldVa != null && !"".equals(oldVa) && newVa != null && !"".equals(newVa)) {
                    //比较是否符合条件
                    result = PatternConst.compareStrategyVa(oldVa, compare, newVa);
                }
                return result;
            }
            if (valueType.equals(PatternConst.STRATEGY_COMPARE_TYPE_F)) {  //false比较

                String tagId = patternService.findTagId(deviceCommand.getDeviceId(), ucDeviceStrategy.getItemParam());

                String oldVa = getTagValue(tagId);

                if (oldVa != null && !"".equals(oldVa)) {
                    if (oldVa.equals("0") || oldVa.toLowerCase().equals("false")) {
                        return true;
                    }
                }
                return result;
            }
            if (valueType.equals(PatternConst.STRATEGY_COMPARE_TYPE_T)) {  //TRUE比较

                String tagId = patternService.findTagId(deviceCommand.getDeviceId(), ucDeviceStrategy.getItemParam());

                String oldVa = getTagValue(tagId);

                if (oldVa != null && !"".equals(oldVa)) {
                    if (oldVa.equals("1") || oldVa.toLowerCase().equals("true")) {
                        return true;
                    }
                }
                return result;
            }
        }

        return false;
    }

    /**
     * 查检策略是否执行
     *
     * @param deviceCommand 策略
     * @return
     */
    private boolean checkExeceuteStrategy(DeviceCommand deviceCommand) {
        String isExecute = deviceCommand.getExecuteStatus();
        if (PatternConst.DEVICE_EXECUTE_YES.equals(isExecute)) {
            return true;
        }
        return false;
    }

    /**
     * 获取TAGID里的值
     *
     * @param tagId
     */
    private String getTagValue(String tagId) {

        try {
            String url = config.getProps().getProperty("getTagValueUrl");
            String requestUrl = url + "?tagid=" + tagId + "&fieldid=";

            logger.debug("-------requestUrl--------" + requestUrl);
            GetMethod postMethod = new GetMethod(requestUrl);
            HttpMethodParams param = postMethod.getParams();
            param.setContentCharset("UTF-8");
            //添加头信息
            List<Header> headers = new ArrayList<Header>();
            headers.add(new Header("Referer", requestUrl));
            headers.add(new Header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0"));
            HttpClient client = new HttpClient();
            client.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
            client.executeMethod(postMethod);
            int status = postMethod.getStatusCode();
            String result = postMethod.getResponseBodyAsString();
            //[{"tid":"12140","fid":"0","val":"0"}]
            if (result != null && !"".equals(result)) {
                int x = result.lastIndexOf(":");
                int y = result.lastIndexOf("}");
                String va = result.substring(x, y);
                va = va.replace("\"", "").trim();
                return va;
            }
            logger.debug("query result resultString:" + status);

        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }
}
