package com.energicube.eno.common;

import com.energicube.eno.common.dto.DeviceCommand;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-27
 * Time: 下午2:25
 * 向子系统发送命令
 */
public class SendCommandToSystem {

    private static Log logger = LogFactory.getLog(SendCommandToSystem.class);

    private static Config config = new Config();

    public static String sendCommand(String requestUrl, String tagId, String value) {

        logger.debug("remote request url is:" + requestUrl);
        StringBuilder resultString = new StringBuilder();
        URL url = null;
        try {
            requestUrl = requestUrl + "?TAGID=" + tagId + "&value=" + value;

            logger.debug("-------requestUrl--------" + requestUrl);

            url = new URL(requestUrl);
            URLConnection URLconnection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                // 获取查询结果
                InputStream urlStream = httpConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(urlStream));
                String currentLine = "";
                while ((currentLine = bufferedReader.readLine()) != null) {
                    resultString.append(currentLine);
                }
                logger.debug("query result resultString:" + resultString.toString());
                // 转换JSON字符为LIST列表
//                ObjectMapper mapper = new ObjectMapper();
//                TypeFactory typeFactory = TypeFactory.defaultInstance();
//                list = mapper
//                        .readValue(resultString.toString(), typeFactory
//                                .constructCollectionType(List.class,
//                                        Tag.class));
//                logger.debug("query result size:" + list.size());
            } else {
                logger.warn("HttpURLConnection StatusCode:" + responseCode);
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return resultString.toString();
    }

    /**
     * 发送控制命令
     *
     * @param requestUrl 接收命令的URL
     * @param params     命令参数
     * @return
     */
    public static String sendCommand(String requestUrl, String params) {

        //  logger.debug("remote request url is:" + requestUrl);
        StringBuilder resultString = new StringBuilder();
        URL url = null;
        try {
            requestUrl = requestUrl + "?" + params;

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

            logger.debug("query result resultString:" + status);

        } catch (Exception e) {
            logger.error(e);
        }
        return resultString.toString();
    }

    /**
     * 发送控制命令
     *
     * @param requestUrl 接收命令的URL
     * @param tagName    命令参数
     * @param value      命令参数
     * @return
     */
    public static String sendCommandTagName(String requestUrl, String tagName, String value) {

        logger.debug("remote request url is:" + requestUrl);
        StringBuilder resultString = new StringBuilder();
        URL url = null;
        try {
            requestUrl = requestUrl + "?tagname=" + tagName + "&value=" + value + "&jsoncallback=?";

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

            logger.debug("query result resultString:" + status);

        } catch (Exception e) {
            logger.error(e);
        }
        return resultString.toString();
    }

    /**
     * 发送命令
     *
     * @param deviceCommand 命令
     */
    public static void sendCommand(DeviceCommand deviceCommand) {
        List<DeviceCommand> deviceCommandList1 = deviceCommand.getParams();

        String url = config.getProps().getProperty("sendCommandUrl");
        for (DeviceCommand command : deviceCommandList1) {
            logger.info(deviceCommand.getTagId() + "----------" + command.getTagId() + "-----" + command.getParamValue());
            String cmd = "tagid=" + command.getTagId() + "&value=" + command.getParamValue() + "&jsoncallback=?";
            sendCommand(url, cmd);
        }
        Date date = new Date();
        deviceCommand.setExecuteTime(date.getTime());
        deviceCommand.setExecuteStatus(PatternConst.DEVICE_EXECUTE_YES);
    }


    /**
     * 一组命令按延时要求来进行发送
     *
     * @param deviceCommandList 命令组
     */
    public static void sendLazyTimeCommand(List<DeviceCommand> deviceCommandList) {

        long start = System.currentTimeMillis();

        for (DeviceCommand deviceCommand : deviceCommandList) {
            String isStrategy = deviceCommand.getStrategy();
            if (isStrategy.equals(PatternConst.IS_STRATEGY_NO)) {

                try {
                    int min = deviceCommand.getSpaceTime();
                    long spaceTime = min * 60 * 1000;//实际要间隔的时间
                    long now = System.currentTimeMillis();
                    long temp = now - start;//已经等待的时间
                    long x = spaceTime - temp;//还要等待的时间
                    //延时的时间
                    Thread.sleep(x);
                    //发送命令
                    sendCommand(deviceCommand);

                } catch (InterruptedException e) {
                    logger.error("", e);
                } catch (Exception ex) {
                    logger.error("", ex);
                }
            }
        }
    }

    /**
     * 设置运行的模式内容
     *
     * @param tagId
     * @param patternName 模式名称
     */
    public static void sendCommandPatternName(String tagId, String patternName) {
        String url = config.getProps().getProperty("sendCommandUrl");
        logger.debug("------sendCommandPatternName----" + tagId + "---" + patternName);
        try {
            String name = URLEncoder.encode(patternName, "utf-8");
            String cmd = "tagid=" + tagId + "&value=" + name + "&jsoncallback=?";
            sendCommand(url, cmd);
        } catch (Exception e) {
            logger.error("");
        }
    }

    /**
     * 设备C/S模式的运行
     *
     * @param systemCode
     * @param patternName
     */
    public static void setSystemPatternName(String systemCode, String patternName) {
        logger.debug("------setSystemPatternName----" + systemCode + "---" + patternName);
        String tagId = config.getProps().getProperty("TAGID_" + systemCode);
        sendCommandPatternName(tagId, patternName);
    }
}
